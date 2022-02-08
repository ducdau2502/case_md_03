package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection {
    private String jdbcURL = "jdbc:mysql://localhost:3306/case_blog_md3?useSSL=false";
    private String jdbcUsername = "root";
    private String jdbcPassword = "12345678";

    public Connection getConnection() {
        Connection connection = null;
        try {
            // Đổi class hiện tại về quản lý như 1 drive
            Class.forName("com.mysql.jdbc.Driver");

            //Quản lý connection vs driver manager
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
