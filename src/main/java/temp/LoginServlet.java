package temp;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/Electric_Bill_Management";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "#Gourabdas10122000";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String userId = request.getParameter("userId");  // Changed to String to avoid conversion issues
        String password = request.getParameter("password");

        try {
            System.out.println("UserID: " + userId);

            // Load JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);

            // Query to check user in the admin table
            String sql = "SELECT id FROM admin WHERE id = ? AND hash_token = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userId);  // Use setString to match VARCHAR/BIGINT types
            pstmt.setString(2, password); 

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                // Store user details in session
                HttpSession session = request.getSession();
                session.setAttribute("userId", userId);

                response.getWriter().println("<script>alert('Login Successful!'); window.location='" + request.getContextPath() + "/assets/index.jsp';</script>");


            } else {
                // Login failed -> Redirect to login page with error
                response.sendRedirect("login.jsp?error=invalid");
            }

            // Close resources
            rs.close();
            pstmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}
