package org.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;

//@WebServlet (urlPatterns = "/data-formats")
@MultipartConfig
@WebServlet(urlPatterns =  "/data-formats/*")
public class DataFormatServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //check the content type
        String contentType = request.getContentType();
        System.out.println("contentType:"+contentType);

        //1.query parameters(url eka piti passe )
                //System.out.println(request.getParameter("id"));
                //System.out.println(request.getParameter("name"));


        //2.path variables
        System.out.println(request.getPathInfo());
        System.out.println(request.getPathInfo().substring(1));



        //3.x-www-form-urlencoded(body eke data tika enne)
                 //System.out.println(request.getParameter("id"));
                // System.out.println(request.getParameter("name"));

        //4.form-data
//        System.out.println(request.getParameter("id"));
//        System.out.println(request.getParameter("name"));

            //read the file
//            Part image = request.getPart("image");
//            System.out.println(image.getSubmittedFileName());

            //Create a directory
//            String fileName = image.getSubmittedFileName();
//            File file = new File("C:\\Users\\H A S H I N I\\Documents\\AAD\\JAVA EE\\Java-EE-Theory\\Day4-Application2\\src\\main\\resources\\images"+fileName);
//            if(!file.exists()){
//                file.mkdir();
            //save the file
//                String path = file.getAbsolutePath()+File.separator+image.getSubmittedFileName();
//                image.write(path);

        }


        //5.json
    //Feature           // JSON                 //XML                       //X.WWW.FORM-URLENCODED         //form-data
    //human readable    //Yes                    //Yes                       //Yes                           //Partial
    //File support      //No                     //Yes                       //No                            //Yes
    //Complex data      //Yes                    //Yes                       //NO                            //YES
    //Modern API        //Yes*3                  //NOOOOO                    //NOOO                           //Yes


    }

