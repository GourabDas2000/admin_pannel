package temp;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@WebServlet("/CreateBillServlet")
public class CreateBillServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/Electric_Bill_Management";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "#Gourabdas10122000";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String consumerId = request.getParameter("billCustomerId");
        String amount = request.getParameter("billAmount");
        String dueDate = request.getParameter("billDueDate");
        String status = request.getParameter("billStatus");

        if (consumerId == null || amount == null || dueDate == null || status == null) {
            response.sendRedirect("create_bill.jsp?error=Invalid input");
            return;
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);

            // Generate unique bill ID
            String billId = "BILL-" + UUID.randomUUID().toString().substring(0, 8);
            LocalDate currentDate = LocalDate.now();
            
            String sql = "INSERT INTO bill (billId, unit, month, amount, date, due_date, payment_status, consumerId) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            
            pstmt.setString(1, billId);
            pstmt.setInt(2, 100);  // Assuming 100 units (Change this logic as needed)
            pstmt.setString(3, "March");  // Get actual month dynamically if required
            pstmt.setDouble(4, Double.parseDouble(amount));
            pstmt.setString(5, currentDate.format(DateTimeFormatter.ISO_LOCAL_DATE));
            pstmt.setString(6, dueDate);
            pstmt.setString(7, status);
            pstmt.setString(8, consumerId);

            int rowsInserted = pstmt.executeUpdate();
            
            pstmt.close();
            conn.close();

            if (rowsInserted > 0) {
            	request.getRequestDispatcher("/assets/index.jsp").forward(request, response);
            } else {
                response.sendRedirect("create_bill.jsp?error=Bill creation failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("create_bill.jsp?error=Database error: " + e.getMessage());
        }
    }
}
