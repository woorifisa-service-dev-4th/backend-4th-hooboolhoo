package dev.hooboolhoo.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletContext;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DataSource {
	
	// HikariCP의 설정 정보 갱신
	private static HikariConfig config = new HikariConfig();
	// 커넥션 풀을 생성해주는 클래스
	private static HikariDataSource dataSource;


	// 커넥션 풀에서 커넥션 객체를 하나 꺼내는 작업
    public static Connection getConnection(ServletContext context) {
        try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
        	Properties properties = (Properties) context.getAttribute("properties");
    			 
    			// 필수 설정값 적용
                config.setJdbcUrl(properties.getProperty("jdbcUrl"));
                config.setUsername(properties.getProperty("username"));
                config.setPassword(properties.getProperty("password"));
                
                
             // HikariCP 설정 적용
                for (String key : properties.stringPropertyNames()) {
                    String value = properties.getProperty(key);
                     value = value.split("#")[0].trim();
                    
                    switch (key) {
                        case "maximumPoolSize":
                        	System.out.println(value);
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
        	
        	return dataSource.getConnection();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
    }

}
