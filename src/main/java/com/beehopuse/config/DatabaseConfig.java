package com.beehopuse.config;

import org.apache.commons.dbcp2.BasicDataSource;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

@WebListener
public class DatabaseConfig implements ServletContextListener {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseConfig.class);
    private static final String PROPERTIES_FILE = "database.properties";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            logger.info("Initializing database connection pool...");
            Properties props = loadProperties();
            BasicDataSource dataSource = createDataSource(props);

            // Test the connection
            try (Connection conn = dataSource.getConnection()) {
                if (conn.isValid(5)) {
                    logger.info("Database connection test successful");
                } else {
                    throw new SQLException("Database connection test failed");
                }
            }

            // Store the DataSource in the ServletContext
            sce.getServletContext().setAttribute("dataSource", dataSource);
            logger.info("Database connection pool initialized successfully");
        } catch (SQLException | IOException e) {
            logger.error("Failed to initialize database connection", e);
            throw new RuntimeException("Failed to initialize database connection: " + e.getMessage(), e);
        }
    }

    private Properties loadProperties() throws IOException {
        Properties props = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            if (input == null) {
                throw new IOException("Unable to find " + PROPERTIES_FILE);
            }
            props.load(input);
            logger.info("Database properties loaded successfully");
        }
        return props;
    }

    private BasicDataSource createDataSource(Properties props) {
        BasicDataSource dataSource = new BasicDataSource();
        try {
            dataSource.setUrl(props.getProperty("db.url"));
            dataSource.setUsername(props.getProperty("db.user"));
            dataSource.setPassword(props.getProperty("db.password"));
            dataSource.setInitialSize(Integer.parseInt(props.getProperty("db.initialPoolSize")));
            dataSource.setMaxTotal(Integer.parseInt(props.getProperty("db.maxPoolSize")));
            dataSource.setDriverClassName(props.getProperty("db.driver"));

            // Additional connection pool settings
            dataSource.setValidationQuery("SELECT 1");
            dataSource.setTestOnBorrow(true);
            dataSource.setTestOnReturn(false);
            dataSource.setTestWhileIdle(true);
            dataSource.setTimeBetweenEvictionRunsMillis(60000);
            dataSource.setMinEvictableIdleTimeMillis(300000);

            logger.info("DataSource configured with URL: {}", props.getProperty("db.url"));
            return dataSource;
        } catch (Exception e) {
            logger.error("Error creating DataSource", e);
            throw new RuntimeException("Error creating DataSource: " + e.getMessage(), e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        DataSource dataSource = (DataSource) sce.getServletContext().getAttribute("dataSource");
        if (dataSource instanceof BasicDataSource) {
            try {
                ((BasicDataSource) dataSource).close();
                logger.info("Database connection pool closed successfully");
            } catch (SQLException e) {
                logger.error("Error closing database connection pool", e);
            }
        }
    }
}