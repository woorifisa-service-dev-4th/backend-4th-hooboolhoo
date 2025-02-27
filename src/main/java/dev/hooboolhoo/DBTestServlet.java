package dev.hooboolhoo;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dev.hooboolhoo.config.DataSource;

@WebServlet("/dbtest")
public class DBTestServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    

    @Override
	public void init() throws ServletException {
    	ServletContext context = getServletContext();
        String configFilePath = context.getInitParameter("configFilePath");
        
        Properties properties = new Properties();
        try (InputStream input = context.getResourceAsStream(configFilePath)) {
            if (input == null) {
                throw new ServletException("설정 파일을 찾을 수 없습니다: " + configFilePath);
            }
            properties.load(input);
        } catch (IOException e) {
            throw new ServletException("설정 파일 로드 실패", e);
        }
        
        System.out.println(properties);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<html><body>");
        out.println("<h2>HikariCP Connection Pool Test</h2>");

        // MySQL 데이터베이스 연결 시도
        try (Connection conn = DataSource.getConnection(this.getServletContext())) {
            if (conn != null) {
                out.println("<p>Database connection successful!</p>");

                // SQL 실행 (animal 테이블 생성)
                String createTableSQL = "CREATE TABLE IF NOT EXISTS animal (" +
                        "id INT AUTO_INCREMENT PRIMARY KEY, " +
                        "name VARCHAR(100) NOT NULL, " +
                        "species VARCHAR(100) NOT NULL, " +
                        "age INT NOT NULL" +
                        ")";
                
                try (Statement stmt = conn.createStatement()) {
                    stmt.executeUpdate(createTableSQL);
                    out.println("<p>'animal' table created successfully! 🐶🐱</p>");
                } catch (SQLException e) {
                    out.println("<p>❌ SQLException while creating table: " + e.getMessage() + "</p>");
                    e.printStackTrace(out);
                }
            } else {
                out.println("<p>Database connection failed!</p>");
            }
        } catch (SQLException e) {
            out.println("<p>❌ SQLException: " + e.getMessage() + "</p>");
            e.printStackTrace(out);
        }

        out.println("</body></html>");
    }
}
