/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverlets;

import Modelado.Operaciones;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author ALEX ARANA
 */
@WebServlet(name = "CargaHisServerlet", urlPatterns = {"/CargaHisServerlet"})
public class CargaHisServerlet extends HttpServlet {

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
        
    
        response.setContentType("text/html;charset=UTF-8");
         String archivourl = "D://Cargas//"+request.getSession().getAttribute("Punto de Digitacion");
         File filenc = new File(archivourl,"Nominal"+request.getSession().getAttribute("Punto de Digitacion")+"nc.csv");
         File file = new File(archivourl,"Nominal"+request.getSession().getAttribute("Punto de Digitacion")+".csv");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
          
            //crear carpetas de cargas   
            Operaciones op=new Operaciones();
            op.crear_carpetas_cargas();
            
            
              //CARGAR ARCHIVOS            
           
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setSizeThreshold(1024);
            factory.setRepository(new File(archivourl));
            ServletFileUpload upload = new ServletFileUpload(factory);
             
            try{
                List<FileItem> partes = upload.parseRequest(request);
                
                for(FileItem items: partes){
                     if(!items.isFormField()){
                    
                    items.write(filenc);
                     }
                }               
                    
            }catch(Exception e){
               
                    e.printStackTrace();
                
            }
            //codifica en utf-8
            FileInputStream fis = new FileInputStream(filenc);
            InputStreamReader isr = new InputStreamReader(fis);

            Reader in = new BufferedReader(isr);
            StringBuffer buffer = new StringBuffer();

            int ch;
            while ((ch = in.read()) > -1) {
                buffer.append((char)ch);
            }
            in.close();


            FileOutputStream fos = new FileOutputStream(file);
            Writer csv = new OutputStreamWriter(fos, "UTF8");
            csv.write(buffer.toString());
            csv.close();
            
            
            
            
            
            
            
            
            
            
            //CARGAR ARCHIVOS A BASE DE DATOS
           
            String punt_dig=null,nom_base=null;
         punt_dig=request.getSession().getAttribute("Punto de Digitacion").toString();
        nom_base= request.getSession().getAttribute("Nombre_Base_Punto").toString();
           String ls_cad = "D://Cargas//"+request.getSession().getAttribute("Punto de Digitacion").toString()+"//Nominal"+request.getSession().getAttribute("Punto de Digitacion").toString()+".csv";
            
                    
            if (file.exists()){
                
            

                /*while(entrada.ready()){                                                        
                    ls_idPPDD = entrada.readLine();  
                }*/
                    //Obtener nombre de base                
                    

                    //Limpiar base
                    ls_cad="call bddiresa.p_limpia_tablas_his('"+request.getSession().getAttribute("Nombre_Base_Punto").toString()+"');";
                    op.ejecutar_consulta(ls_cad);
                    
                    //Cargar data
                    ls_cad="LOAD DATA INFILE 'D://Cargas//"+punt_dig+"//Nominal"+punt_dig+".csv' INTO TABLE "+nom_base+".aux_his_his FIELDS TERMINATED BY ';' LINES TERMINATED BY '\r\n' IGNORE 1 LINES;";
                    op.ejecutar_consulta(ls_cad);
                    //out.print(ls_cad);
                      
                    
                    /*PASAR DATOS A TABLAS DEFINITIVAS*/
                    ls_cad ="select a.año from "+nom_base+".aux_his_his as a limit 1";
                     String gs_periodo = op.met_dev_reg(ls_cad);
                    
                    ls_cad ="select a.mes from "+nom_base+".aux_his_his as a limit 1";
                  String gs_mes = op.met_dev_reg(ls_cad);
                    
                    ls_cad="call bddiresa.p_migra_his('"+nom_base+ "','" + gs_periodo+ "','" + gs_mes+ "');";
                    
                    op.ejecutar_consulta(ls_cad);
                    
                    // ELIMINAR EL ARCHIVO CARGADO
            
          
              request.getSession().setAttribute("mensaje_flash_carga_his", "ARCHIVO CORRECTAMENTE SUBIDO...");
            
        }
            System.out.print(file.getAbsoluteFile());
             System.out.print(filenc.getAbsoluteFile());
            
              if(file.delete()){
               System.out.print("Se elimino"+file.getAbsoluteFile());
              };
               if(filenc.delete()){
               System.out.print("Se elimino"+filenc.getAbsoluteFile());
              };
            
            response.sendRedirect("carga_his.jsp");
            
            
            
    
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
