package com.beehopuse.service.impl;

import com.beehopuse.model.Site;
import com.beehopuse.model.Farm;
import com.beehopuse.service.SiteService;
import com.beehopuse.util.DBUtil;
import jakarta.ejb.Stateless;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class SiteServiceImpl implements SiteService {

    @Override
    public Site createSite(Site site) {
        String sql = "INSERT INTO sites (name, description, latitude, longitude, altitude, start_date, farm_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, site.getName());
            pstmt.setString(2, site.getDescription());
            pstmt.setDouble(3, site.getLatitude());
            pstmt.setDouble(4, site.getLongitude());
            pstmt.setDouble(5, site.getAltitude());
            pstmt.setTimestamp(6, Timestamp.valueOf(site.getStartDate()));
            pstmt.setLong(7, site.getFarm().getId());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating site failed, no rows affected.");
            }

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    site.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating site failed, no ID obtained.");
                }
            }
            return site;
        } catch (SQLException e) {
            throw new RuntimeException("Error creating site", e);
        }
    }

    @Override
    public Site updateSite(Site site) {
        String sql = "UPDATE sites SET name = ?, description = ?, latitude = ?, longitude = ?, altitude = ?, start_date = ?, end_date = ?, farm_id = ? WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, site.getName());
            pstmt.setString(2, site.getDescription());
            pstmt.setDouble(3, site.getLatitude());
            pstmt.setDouble(4, site.getLongitude());
            pstmt.setDouble(5, site.getAltitude());
            pstmt.setTimestamp(6, Timestamp.valueOf(site.getStartDate()));
            pstmt.setTimestamp(7, site.getEndDate() != null ? Timestamp.valueOf(site.getEndDate()) : null);
            pstmt.setLong(8, site.getFarm().getId());
            pstmt.setLong(9, site.getId());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating site failed, no rows affected.");
            }
            return site;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating site", e);
        }
    }

    @Override
    public void deleteSite(Long id) {
        String sql = "DELETE FROM sites WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting site failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting site", e);
        }
    }

    @Override
    public Site getSiteById(Long id) {
        String sql = "SELECT * FROM sites WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToSite(rs);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting site by id", e);
        }
    }

    @Override
    public List<Site> getSitesByFarm(Farm farm) {
        String sql = "SELECT * FROM sites WHERE farm_id = ?";
        List<Site> sites = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, farm.getId());
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    sites.add(mapResultSetToSite(rs));
                }
            }
            return sites;
        } catch (SQLException e) {
            throw new RuntimeException("Error getting sites by farm", e);
        }
    }

    @Override
    public List<Site> getAllSites() {
        String sql = "SELECT * FROM sites";
        List<Site> sites = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                sites.add(mapResultSetToSite(rs));
            }
            return sites;
        } catch (SQLException e) {
            throw new RuntimeException("Error getting all sites", e);
        }
    }

    private Site mapResultSetToSite(ResultSet rs) throws SQLException {
        Site site = new Site();
        site.setId(rs.getLong("id"));
        site.setName(rs.getString("name"));
        site.setDescription(rs.getString("description"));
        site.setLatitude(rs.getDouble("latitude"));
        site.setLongitude(rs.getDouble("longitude"));
        site.setAltitude(rs.getDouble("altitude"));
        site.setStartDate(rs.getTimestamp("start_date").toLocalDateTime());
        Timestamp endDate = rs.getTimestamp("end_date");
        if (endDate != null) {
            site.setEndDate(endDate.toLocalDateTime());
        }
        // Optionally fetch and set the related Farm object
        Long farmId = rs.getLong("farm_id");
        if (farmId != null && farmId > 0) {
            // Example: site.setFarm(new FarmServiceImpl().getFarmById(farmId));
            // Uncomment the above line if you want to fetch the Farm object here
        }
        return site;
    }
}