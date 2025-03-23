package temp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ViewComplaintsServlet")
public class ViewComplaintsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/Electric_Bill_Management";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "#Gourabdas10122000";

    protected void doGet(HttpServletRequest reque
    		st, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        
        String searchQuery = request.getParameter("query");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);

            String sql = "SELECT c.name, cmp.complaintId, cmp.problem_description, cmp.complaint_type, cmp.category " +
                         "FROM complaint cmp " +
                         "JOIN consumer c ON cmp.consumerId = c.consumerId";
            
            if (searchQuery != null && !searchQuery.isEmpty()) {
                sql += " WHERE c.name LIKE ? OR cmp.complaintId LIKE ?";
            }

            PreparedStatement pstmt = conn.prepareStatement(sql);
            
            if (searchQuery != null && !searchQuery.isEmpty()) {
                pstmt.setString(1, "%" + searchQuery + "%");
                pstmt.setString(2, "%" + searchQuery + "%");
            }

            ResultSet rs = pstmt.executeQuery();

            List<String> complaints = new ArrayList<>();

            while (rs.next()) {
                String jsonComplaint = "{"
                        + "\"name\":\"" + rs.getString("name") + "\","
                        + "\"complaintId\":\"" + rs.getLong("complaintId") + "\","
                        + "\"description\":\"" + rs.getString("problem_description") + "\","
                        + "\"solution\":\"" + "Pending" + "\","
                        + "\"status\":\"" + "Under Review" + "\""
                        + "}";
                complaints.add(jsonComplaint);
            }

            out.print("[" + String.join(",", complaints) + "]");
            out.flush();

            rs.close();
            pstmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            out.print("{\"error\": \"Database error: " + e.getMessage() + "\"}");
        }
    }
}
