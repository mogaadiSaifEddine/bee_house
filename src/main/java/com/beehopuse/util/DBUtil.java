package com.beehopuse.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DBUtil {
    private static final Logger LOGGER = Logger.getLogger(DBUtil.class.getName());
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/bee_house";
    private static final String JDBC_USER = "root";
    private static final String JNDI_DATASOURCE = "java:comp/env/jdbc/bee_house";
    private static final boolean USE_CONNECTION_POOL = false;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            LOGGER.info("Mysql JDBC Driver Registered!");
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Error loading Mysql JDBC driver", e);
            throw new RuntimeException("Error loading Mysql JDBC driver", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        if (USE_CONNECTION_POOL) {
            return getConnectionFromPool();
        } else {
            return getDirectConnection();
        }
    }

    private static Connection getDirectConnection() throws SQLException {
        try {
            Properties connectionProps = new Properties();
            connectionProps.put("user", JDBC_USER);
            connectionProps.put("password", "");

            LOGGER.fine("Attempting to connect to database directly at: " + JDBC_URL);
            Connection conn = DriverManager.getConnection(JDBC_URL, connectionProps);
            LOGGER.info("Successfully connected to H2 database");
            return conn;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error connecting to database", e);
            throw e;
        }
    }

    private static Connection getConnectionFromPool() throws SQLException {
        try {
            Context initContext = new InitialContext();
            DataSource ds = (DataSource) initContext.lookup(JNDI_DATASOURCE);
            LOGGER.fine("Obtained connection from pool: " + JNDI_DATASOURCE);
            return ds.getConnection();
        } catch (NamingException e) {
            LOGGER.log(Level.SEVERE, "Error looking up datasource", e);
            throw new SQLException("Error looking up datasource", e);
        }
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                    LOGGER.fine("Database connection closed successfully");
                }
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "Error closing database connection", e);
            }
        }
    }

    public static boolean testConnection() {
        Connection conn = null;
        try {
            conn = getConnection();
            LOGGER.info("Database connection test successful!");
            return true;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database connection test failed!", e);
            return false;
        } finally {
            closeConnection(conn);
        }
    }
}