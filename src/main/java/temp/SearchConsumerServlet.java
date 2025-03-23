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

@WebServlet("/SearchConsumerServlet")
public class SearchConsumerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/Electric_Bill_Management";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "#Gourabdas10122000";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String searchQuery = request.getParameter("query");
        String filter = request.getParameter("filter");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);

            // Default query (View All)
            String sql = "SELECT name, consumerId, bill_date, payment_date, status, amount FROM consumer";

            // Modify query based on search/filter
            if (searchQuery != null && !searchQuery.isEmpty()) {
                sql += " WHERE (name LIKE ? OR consumerId LIKE ?)";
            }
            if (filter != null && !filter.equals("all")) {
                sql += searchQuery != null ? " AND status = ?" : " WHERE status = ?";
            }

            PreparedStatement pstmt = conn.prepareStatement(sql);

            int paramIndex = 1;
            if (searchQuery != null && !searchQuery.isEmpty()) {
                pstmt.setString(paramIndex++, "%" + searchQuery + "%");
                pstmt.setString(paramIndex++, "%" + searchQuery + "%");
            }
            if (filter != null && !filter.equals("all")) {
                pstmt.setString(paramIndex, filter);
            }

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                out.println("<tr>");
                out.println("<td>" + rs.getString("name") + "</td>");
                out.println("<td>" + rs.getString("consumerId") + "</td>");
                out.println("<td>" + (rs.getString("bill_date") != null ? rs.getString("bill_date") : "N/A") + "</td>");
                out.println("<td>" + (rs.getString("payment_date") != null ? rs.getString("payment_date") : "N/A") + "</td>");
                out.println("<td>" + rs.getString("status") + "</td>");
                out.println("<td>" + rs.getDouble("amount") + "</td>");
                out.println("</tr>");
            }

            rs.close();
            pstmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
            out.println("<tr><td colspan='6'>Error: " + e.getMessage() + "</td></tr>");
        }
    }
}
