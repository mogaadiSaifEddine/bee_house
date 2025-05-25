package com.beehopuse.service.impl;

import com.beehopuse.model.Farm;
import com.beehopuse.model.User;
import com.beehopuse.service.FarmService;
import com.beehopuse.util.DBUtil;
import jakarta.ejb.Stateless;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class FarmServiceImpl implements FarmService {

    @Override
    public Farm createFarm(Farm farm) {
        String sql = "INSERT INTO farms (name, description, owner_id) VALUES (?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, farm.getName());
            pstmt.setString(2, farm.getDescription());
            pstmt.setLong(3, farm.getOwner().getId());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating farm failed, no rows affected.");
            }

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    farm.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating farm failed, no ID obtained.");
                }
            }
            return farm;
        } catch (SQLException e) {
            throw new RuntimeException("Error creating farm", e);
        }
    }

    @Override
    public Farm updateFarm(Farm farm) {
        String sql = "UPDATE farms SET name = ?, description = ?, owner_id = ? WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, farm.getName());
            pstmt.setString(2, farm.getDescription());
            pstmt.setLong(3, farm.getOwner().getId());
            pstmt.setLong(4, farm.getId());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating farm failed, no rows affected.");
            }
            return farm;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating farm", e);
        }
    }

    @Override
    public void deleteFarm(Long id) {
        String sql = "DELETE FROM farms WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting farm failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting farm", e);
        }
    }

    @Override
    public Farm getFarmById(Long id) {
        String sql = "SELECT * FROM farms WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToFarm(rs);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting farm by id", e);
        }
    }

    @Override
    public List<Farm> getFarmsByOwner(User owner) {
        String sql = "SELECT * FROM farms WHERE owner_id = ?";
        List<Farm> farms = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, owner.getId());
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    farms.add(mapResultSetToFarm(rs));
                }
            }
            return farms;
        } catch (SQLException e) {
            throw new RuntimeException("Error getting farms by owner", e);
        }
    }

    @Override
    public List<Farm> getAllFarms() {
        String sql = "SELECT * FROM farms";
        List<Farm> farms = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                farms.add(mapResultSetToFarm(rs));
            }
            return farms;
        } catch (SQLException e) {
            throw new RuntimeException("Error getting all farms", e);
        }
    }

    private Farm mapResultSetToFarm(ResultSet rs) throws SQLException {
        Farm farm = new Farm();
        farm.setId(rs.getLong("id"));
        farm.setName(rs.getString("name"));
        farm.setDescription(rs.getString("description"));
        // Optionally fetch and set the related User (owner) object
        Long ownerId = rs.getLong("owner_id");
        if (ownerId != null && ownerId > 0) {
            // Example: farm.setOwner(new UserServiceImpl().getUserById(ownerId));
            // Uncomment the above line if you want to fetch the User object here
        }
        return farm;
    }
}