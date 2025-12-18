package org.example;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.apache.commons.dbcp2.BasicDataSource;

@WebListener
public class DBConnection implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUrl("jdbc:mysql://localhost:3306/post73");
        ds.setUsername("root");
        ds.setPassword("1234");
        ds.setInitialSize(5);
        ds.setMaxTotal(5);
        ds.setMaxIdle(5);
        ds.setMinIdle(5);
        ds.setMaxOpenPreparedStatements(100);
        ServletContext sc = sce.getServletContext();
        sc.setAttribute("datasource", ds);


    }
}
