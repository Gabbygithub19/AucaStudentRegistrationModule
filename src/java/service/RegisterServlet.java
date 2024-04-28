package service;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        Connection conn = null;
        PreparedStatement preparedStatement = null;

        try {
            // Load the MySQL JDBC driver (make sure you have the MySQL JDBC driver JAR in your classpath)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish a database connection
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Alinemid23678", "root", "12345");

            String query = "INSERT INTO student (username, password) VALUES (?, ?)";
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            int rows = preparedStatement.executeUpdate();

            if (rows > 0) {
                response.sendRedirect("Login.jsp");
            } else {
                response.getWriter().write("Registration failed. Please try again.");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            response.getWriter().write("An error occurred.");
        } finally {
            // Close resources in a finally block to ensure they are always closed
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
