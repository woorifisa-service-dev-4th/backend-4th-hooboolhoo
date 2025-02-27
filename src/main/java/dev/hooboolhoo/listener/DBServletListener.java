package dev.hooboolhoo.listener;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebListener;

@WebListener
public class DBServletListener implements ServletContextListener {
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext context = sce.getServletContext();
        String configFilePath = context.getInitParameter("configFilePath");
        
        Properties properties = new Properties();
        try (InputStream input = context.getResourceAsStream(configFilePath)) {
            if (input == null) {
                throw new RuntimeException("설정 파일을 찾을 수 없습니다: " + configFilePath);
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("설정 파일 로드 실패", e);
        }
        context.setAttribute("properties",properties);
        System.out.println(properties);
	}
	
	
}
