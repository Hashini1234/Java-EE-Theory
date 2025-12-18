package org.example;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.dbcp2.BasicDataSource;

import java.io.IOException;
import java.sql.*;

@WebServlet(urlPatterns = "/api/v1/item")
public class ItemServlet extends HttpServlet {

    private BasicDataSource ds;
    private final Gson gson = new Gson();

    @Override
    public void init() throws ServletException {
        ds = (BasicDataSource) getServletContext().getAttribute("datasource");
    }

    // ---------- CORS ----------
    private void enableCORS(HttpServletResponse resp) {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) {
        enableCORS(resp);
    }

    // ---------- CREATE ----------
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        enableCORS(resp);
        resp.setContentType("application/json");

        JsonObject json = gson.fromJson(req.getReader(), JsonObject.class);

        String itemId = json.get("itemId").getAsString();
        String itemName = json.get("itemName").getAsString();
        int qty = json.get("qty").getAsInt();

        try (Connection con = ds.getConnection()) {
            String sql = "INSERT INTO item (itemId,itemName,qty) VALUES (?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, itemId);
            ps.setString(2, itemName);
            ps.setInt(3, qty);

            int result = ps.executeUpdate();
            resp.getWriter().write(
                    result > 0 ? "{\"message\":\"Item saved\"}" : "{\"message\":\"Save failed\"}"
            );

        } catch (SQLException e) {
            resp.setStatus(500);
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    // ---------- READ ----------
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        enableCORS(resp);
        resp.setContentType("application/json");

        try (Connection con = ds.getConnection()) {
            String sql = "SELECT * FROM item";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            var list = new java.util.ArrayList<JsonObject>();
            while (rs.next()) {
                JsonObject obj = new JsonObject();
                obj.addProperty("itemId", rs.getString("itemId"));
                obj.addProperty("itemName", rs.getString("itemName"));
                obj.addProperty("qty", rs.getInt("qty"));
                list.add(obj);
            }

            resp.getWriter().write(gson.toJson(list));

        } catch (SQLException e) {
            resp.setStatus(500);
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    // ---------- UPDATE ----------
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        enableCORS(resp);
        resp.setContentType("application/json");

        JsonObject json = gson.fromJson(req.getReader(), JsonObject.class);

        String itemId = json.get("itemId").getAsString();
        String itemName = json.get("itemName").getAsString();
        int qty = json.get("qty").getAsInt();

        try (Connection con = ds.getConnection()) {
            String sql = "UPDATE item SET itemName=?, qty=? WHERE itemId=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, itemName);
            ps.setInt(2, qty);
            ps.setString(3, itemId);

            int result = ps.executeUpdate();
            resp.getWriter().write(
                    result > 0 ? "{\"message\":\"Item updated\"}" : "{\"message\":\"Update failed\"}"
            );

        } catch (SQLException e) {
            resp.setStatus(500);
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    // ---------- DELETE ----------
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        enableCORS(resp);
        resp.setContentType("application/json");

        String itemId = req.getParameter("itemId");

        try (Connection con = ds.getConnection()) {
            String sql = "DELETE FROM item WHERE itemId=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, itemId);

            int result = ps.executeUpdate();
            resp.getWriter().write(
                    result > 0 ? "{\"message\":\"Item deleted\"}" : "{\"message\":\"Delete failed\"}"
            );

        } catch (SQLException e) {
            resp.setStatus(500);
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
}
