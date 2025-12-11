package org.example;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.apache.commons.dbcp2.BasicDataSource;

@WebListener
public class ContextListeners implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUrl("jdbc:mysql://localhost:3306/customer_db");
        ds.setUsername("root");
        ds.setPassword("root");
        ds.setInitialSize(5);
        ds.setMaxTotal(5);
        ds.setMaxIdle(5);
        ds.setMinIdle(5);
        ds.setMaxOpenPreparedStatements(100);
        ServletContext sc = sce.getServletContext();
        sc.setAttribute("datasource", ds);

    }
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Context Destroyed");
    }
}