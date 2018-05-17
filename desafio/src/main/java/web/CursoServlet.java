/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
public class CursoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        
        int id = 0;
        String nome = req.getParameter("nome");
        String turno = req.getParameter("turno");
        char turnoChar;
        int qtde = 0;
        Date data = new Date(new java.util.Date().getTime());
        try {
            data = new java.sql.Date(formato.parse(req.getParameter("data")).getTime());
        } catch (ParseException ex) {
            Logger.getLogger(CursoServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (turno == null || turno.equals("")){
            turnoChar = '-';
        } else {
            turnoChar = turno.charAt(0);
        }

        if (!req.getParameter("id").isEmpty()){
            id = Integer.parseInt(req.getParameter("id"));
        }
        
        if (nome == null) {
            nome = "";
        }
        
        if (!req.getParameter("qtde").isEmpty()){
            qtde = Integer.parseInt(req.getParameter("qtde"));
        }
            
        PrintWriter saida = resp.getWriter();
        
        try {
            Connection connection = DriverManager.getConnection("jdbc:derby://localhost:1527/erpcolegio", "sa", "sa");
            
            PreparedStatement p = connection.prepareStatement("INSERT INTO CURSO(ID, NOME, TURNO, QTDE, DATACADASTRO) VALUES (?, ?, ?, ?, ?)");
            
            p.setInt(1, id);
            p.setString(2, nome);
            p.setString(3, turnoChar + "");
            p.setInt(4, qtde);
            p.setDate(5, new java.sql.Date(data.getTime()));
            p.execute();
            
            saida.println("Concluído!");
        } catch (SQLException ex) {
            Logger.getLogger(CursoServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        try {
            
            PrintWriter saida = resp.getWriter();
            
            Connection connection = DriverManager.getConnection("jdbc:derby://localhost:1527/erpcolegio", "sa", "sa");
            
            PreparedStatement p = connection.prepareStatement("SELECT NOME FROM CURSO");
            
            ResultSet rs = p.executeQuery();
            
            while(rs.next()){
                saida.println("<ul>");
                saida.println("<li>");
                saida.println(rs.getString("nome") + "<br>");
                saida.println("</li>");
                saida.println("</ul>");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CursoServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
}
