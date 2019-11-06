/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverlets;

import Modelado.Operaciones;
import Modelado.OperacionesPuntoDigitacionBase;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 *
 * @author ALEX ARANA
 */
@WebServlet(name = "LoginServerlet", urlPatterns = {"/LoginServerlet"})
public class LoginServerlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
     String usu=  request.getParameter("txtUsuario").toString();
        String con=  request.getParameter("txtContra").toString();
        Writer ot=  response.getWriter();
      ot.write(usu);
      Operaciones op =new Operaciones();
         Integer nivel= op.loguear(usu, con);
        System.out.println(usu);
        System.out.println("ESTA TRAANDO DE INGRESAR EL PUNTO DE DIGITACION "+con);
         System.out.println("USUARIO NIVEL" +nivel);
         
         request.getSession().setAttribute("Punto de Digitacion", con);
         
         request.getSession().setAttribute("Nombre_Punto", op.dev_pun_dig(con));
          
         request.getSession().setAttribute("Nombre_Base_Punto", op.dev_nom_base_pun_dig(con));
         request.getSession().setAttribute("mensaje_flash_carga_his","");
         
        if(nivel ==2){
        response.sendRedirect("main.jsp");
        }else{
            response.sendRedirect("index.html");
        }
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
