/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author gg005249
 */
public class EmpresaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        resp.setContentType("text/html");
        
        PrintWriter saida = resp.getWriter();
        
        try {
            Connection connection = DriverManager.getConnection("jdbc:derby://localhost:1527/aula05", "sa", "sa");
            
            PreparedStatement p = connection.prepareStatement("SELECT * FROM EMPRESA");
            
            ResultSet rs = p.executeQuery();
            
            while(rs.next()){
                saida.println(" -> " + rs.getString("nome"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmpresaServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
}
