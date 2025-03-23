package temp;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/Electric_Bill_Management"; // Change DB name if needed
    private static final String DB_USER = "root"; // Change as needed
    private static final String DB_PASSWORD = "#Gourabdas10122000"; // Change as needed
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String countryCode = request.getParameter("country_code");
        String mobileNumber = request.getParameter("mobile_number");
        String userId = request.getParameter("userId");
        String role = request.getParameter("role");
        String password = request.getParameter("password");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
            System.out.println("Database is Successfully Connected");
            if ("user".equals(role)) {
                String sql = "INSERT INTO consumer (name, email, country_code, mobile_number, userId) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, name);
                pstmt.setString(2, email);
                pstmt.setString(3, countryCode);
                pstmt.setString(4, mobileNumber);
                pstmt.setString(5, userId);
                pstmt.executeUpdate();
                pstmt.close();
            } else if ("admin".equals(role) && password != null && !password.isEmpty()) {
                String sql = "INSERT INTO admin (id, hash_token) VALUES (?, ?)";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, userId); // Using userId as admin ID
                pstmt.setString(2, password); // Ideally, hash the password before storing
                pstmt.executeUpdate();
                pstmt.close();
            }

            conn.close();

            // Redirect to login page
            response.getWriter().println("Success");

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}
