package temp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/SearchConsumerForBillServlet")
public class SearchConsumerForBillServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/Electric_Bill_Management";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "#Gourabdas10122000";

    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String searchQuery = request.getParameter("query");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);

            String sql = "SELECT c.name, c.consumerId, b.amount, b.due_date, b.payment_status FROM consumer c LEFT JOIN bill b ON c.consumerId = b.consumerId WHERE c.name LIKE ? OR c.consumerId LIKE ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + searchQuery + "%");
            pstmt.setString(2, "%" + searchQuery + "%");

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                out.println("<tr><td>" + rs.getString("name") + "</td>");
                out.println("<td>" + rs.getString("consumerId") + "</td>");
                out.println("<td>" + rs.getDouble("amount") + "</td>");
                out.println("<td>" + rs.getString("due_date") + "</td>");
                out.println("<td>" + rs.getString("payment_status") + "</td>");
                out.println("<td><button onclick=\"setCustomerId('" + rs.getString("consumerId") + "')\">Generate Bill</button></td></tr>");
            }

            rs.close();
            pstmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
