import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;

@WebServlet(urlPatterns = "/customer")
public class CustomerServlet extends HttpServlet {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/javaeeapp";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "1234";

    // CREATE
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String address = req.getParameter("address");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
            String query = "INSERT INTO customer(id,name,address) VALUES (?,?,?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, id);
            ps.setString(2, name);
            ps.setString(3, address);

            int inserted = ps.executeUpdate();
            if (inserted > 0) {
                resp.getWriter().println("Customer saved successfully");
            } else {
                resp.getWriter().println("Customer not saved");
            }
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // READ
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain;charset=UTF-8");
        String id = req.getParameter("id");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);

            String query;
            PreparedStatement ps;

            if (id == null || id.isEmpty()) {
                query = "SELECT * FROM customer";
                ps = connection.prepareStatement(query);
            } else {
                query = "SELECT * FROM customer WHERE id=?";
                ps = connection.prepareStatement(query);
                ps.setString(1, id);
            }


            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String cusId = rs.getString("id");
                String name = rs.getString("name");
                String address = rs.getString("address");
                resp.getWriter().println(cusId + "," + name + "," + address);
            }
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // UPDATE
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String address = req.getParameter("address");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);

            String query = "UPDATE customer SET name=?, address=? WHERE id=?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, id);

            int updated = ps.executeUpdate();
            if (updated > 0) {
                resp.getWriter().println("Customer updated successfully");
            } else {
                resp.getWriter().println("Customer not updated");
            }
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // DELETE
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);

            String query = "DELETE FROM customer WHERE id=?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, id);

            int deleted = ps.executeUpdate();
            if (deleted > 0) {
                resp.getWriter().println("Customer deleted successfully");
            } else {
                resp.getWriter().println("Customer not deleted");
            }
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
