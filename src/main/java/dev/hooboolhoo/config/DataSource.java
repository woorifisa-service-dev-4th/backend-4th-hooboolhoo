package dev.hooboolhoo.config;

import javax.servlet.ServletContext;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DataSource {
    private static HikariDataSource dataSource;

    // 애플리케이션 시작 시 한 번만 HikariCP 설정
    public static void init(ServletContext context) {
        if (dataSource != null) {
            return; // 이미 초기화되었으면 다시 설정하지 않음
        }

        try {
            Properties properties = (Properties) context.getAttribute("properties");

            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(properties.getProperty("jdbcUrl"));
            config.setUsername(properties.getProperty("username"));
            config.setPassword(properties.getProperty("password"));

            for (String key : properties.stringPropertyNames()) {
                String value = properties.getProperty(key).split("#")[0].trim();

                switch (key) {
                    case "maximumPoolSize":
                        config.setMaximumPoolSize(Integer.parseInt(value));
                        break;
                    case "minimumIdle":
                        config.setMinimumIdle(Integer.parseInt(value));
                        break;
                    case "connectionTimeout":
                        config.setConnectionTimeout(Long.parseLong(value));
                        break;
                    case "idleTimeout":
                        config.setIdleTimeout(Long.parseLong(value));
                        break;
                    case "keepaliveTime":
                        config.setKeepaliveTime(Long.parseLong(value));
                        break;
                    case "maxLifetime":
                        config.setMaxLifetime(Long.parseLong(value));
                        break;
                    default:
                        config.addDataSourceProperty(key, value);
                        break;
                }
            }

            dataSource = new HikariDataSource(config);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // DB 연결 반환
    public static Connection getConnection() throws SQLException {
        if (dataSource == null) {
            throw new IllegalStateException("DataSource is not initialized. Call DataSource.init(context) first.");
        }
        return dataSource.getConnection();
    }
}
