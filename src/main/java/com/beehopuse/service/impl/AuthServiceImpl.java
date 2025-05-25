package com.beehopuse.service.impl;

import com.beehopuse.model.User;
import com.beehopuse.service.AuthService;
import com.beehopuse.util.PasswordHasher;
import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class AuthServiceImpl implements AuthService {
    private final DataSource dataSource;
    private final PasswordHasher passwordHasher;

    public AuthServiceImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        this.passwordHasher = new PasswordHasher();
    }

    @Override
    public User authenticate(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND active = true";

        try (Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String hashedPassword = rs.getString("password");
                if (passwordHasher.verifyPassword(password, hashedPassword)) {
                    return mapUserFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error authenticating user", e);
        }
        return null;
    }

    @Override
    public boolean register(User user) {
        if (!isUsernameAvailable(user.getUsername()) || !isEmailAvailable(user.getEmail())) {
            return false;
        }

        String sql = "INSERT INTO users (username, password, email, full_name, role, active, created_at, updated_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            String hashedPassword = passwordHasher.hashPassword(user.getPassword());
            LocalDateTime now = LocalDateTime.now();

            stmt.setString(1, user.getUsername());
            stmt.setString(2, hashedPassword);
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getFullName());
            stmt.setString(5, user.getRole());
            stmt.setBoolean(6, true);
            stmt.setObject(7, now);
            stmt.setObject(8, now);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error registering user", e);
        }
    }

    @Override
    public boolean isUsernameAvailable(String username) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";

        try (Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) == 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error checking username availability", e);
        }
        return false;
    }

    @Override
    public boolean isEmailAvailable(String email) {
        String sql = "SELECT COUNT(*) FROM users WHERE email = ?";

        try (Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) == 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error checking email availability", e);
        }
        return false;
    }

    private User mapUserFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setUsername(rs.getString("username"));
        user.setEmail(rs.getString("email"));
        user.setFullName(rs.getString("full_name"));
        user.setRole(rs.getString("role"));
        user.setActive(rs.getBoolean("active"));
        user.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        user.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        return user;
    }
}