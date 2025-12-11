package org.example;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = "/json")
public class JsonProcessing extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        JsonObject customer = gson.fromJson(req.getReader(), JsonObject.class);
        System.out.println("Customer : " + customer);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonObject customer = new JsonObject();
        customer.addProperty("name","Hashini Devindi");
        customer.addProperty("age",21);
        customer.addProperty("contact","0717720092");
        JsonObject address1 = new JsonObject();
        address1.addProperty("no","24");
        address1.addProperty("street","3rd lane");
        address1.addProperty("city","panadura");
        JsonObject address2 = new JsonObject();
        address2.addProperty("no","24");
        address2.addProperty("street","3rd lane");
        address2.addProperty("city","panadura");

        JsonArray addresses = new JsonArray();
        addresses.add(address1);
        addresses.add(address2);
        customer.add("addresses",addresses);
        resp.setContentType("application/json");
        resp.getWriter().print(customer);
    }
}