/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverlets;

import Modelado.Operaciones;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

/**
 *
 * @author ALEX ARANA
 */
@WebServlet(name = "CargaSisServerlet", urlPatterns = {"/CargaSisServerlet"})
public class CargaSisServerlet extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
        //crear carpetas de cargas   
            Operaciones op=new Operaciones();
            op.crear_carpetas_cargas();
            
            
              //CARGAR ARCHIVOS            
            String archivourl = "D://Cargas//"+request.getSession().getAttribute("Punto de Digitacion");
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setSizeThreshold(1024);
            factory.setRepository(new File(archivourl));
            ServletFileUpload upload = new ServletFileUpload(factory);
            try{
                List<FileItem> partes = upload.parseRequest(request);
                for(FileItem items: partes){
                     if(!items.isFormField()){
                    File file = new File(archivourl,"exportarsis"+request.getSession().getAttribute("Punto de Digitacion")+".zip");
                    items.write(file);
                     }
                }               
                    
            }catch(Exception e){
               
                    e.printStackTrace();
                
            }
            
                 //DESCOMPRIMIR ARCHIVO
      
            try {
                File src = new File("D://Cargas//"+request.getSession().getAttribute("Punto de Digitacion")+"//exportarsis"+request.getSession().getAttribute("Punto de Digitacion")+".zip");                
                ZipFile zipFile = null;
                try {
                    zipFile = new ZipFile(src);
                } catch (ZipException ex) {
                    Logger.getLogger(CargaSisServerlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                String dest = new String("D://Cargas//"+request.getSession().getAttribute("Punto de Digitacion"));
                zipFile.extractAll(dest);
               
                } catch (ZipException e) {
                
               //     e.printStackTrace();
                    request.getSession().setAttribute("ArchivoCorrupto","El archivo no existe o esta corrupto");
                    
                
            }
        
            
            //cargar ha base de datos
                
         String   ls_cad = "D://Cargas//"+request.getSession().getAttribute("Punto de Digitacion")+"//exportar//pd.txt";
            
            File archivo = new File(ls_cad);    
            
            if (archivo.exists()){
                
               
                    String ls_idPPDD=request.getSession().getAttribute("Punto de Digitacion").toString();
                    String ls_pd=request.getSession().getAttribute("Nombre_Base_Punto").toString();
                  
                    
                    //Limpiar base
                    ls_cad="call bddiresa.p_limpia_tablas('"+request.getSession().getAttribute("Nombre_Base_Punto")+"');";
                    
                    //out.print(ls_cad);
                    
                    op.ejecutar_consulta(ls_cad);                    
                
                    //Cargar data
                    ls_cad="LOAD DATA INFILE 'D://Cargas//"+ls_idPPDD+"//exportar//i_atencion.txt' INTO TABLE "+ls_pd+".aux_sis_i_atencion;";
                    op.ejecutar_consulta(ls_cad);   
                    out.print(ls_cad);
                    ls_cad="LOAD DATA INFILE 'D://Cargas//"+ls_idPPDD+"//exportar//i_atediagnosticos.txt' INTO TABLE "+ls_pd+".aux_sis_i_atediagnosticos";
                    op.ejecutar_consulta(ls_cad);               
                    ls_cad="LOAD DATA INFILE 'D://Cargas//"+ls_idPPDD+"//exportar//i_ateprocedimientos.txt' INTO TABLE "+ls_pd+".aux_sis_i_ateprocedimientos";
                    op.ejecutar_consulta(ls_cad);
                    ls_cad="LOAD DATA INFILE 'D://Cargas//"+ls_idPPDD+"//exportar//i_atencionser.txt' INTO TABLE "+ls_pd+".aux_sis_i_atencionser";
                    op.ejecutar_consulta(ls_cad);
                    ls_cad="LOAD DATA INFILE 'D://Cargas//"+ls_idPPDD+"//exportar//i_atesmi.txt' INTO TABLE "+ls_pd+".aux_sis_i_atesmi";   
                    op.ejecutar_consulta(ls_cad);
                    ls_cad="LOAD DATA INFILE 'D://Cargas//"+ls_idPPDD+"//exportar//i_atern.txt' INTO TABLE "+ls_pd+".aux_sis_i_atern";  
                    op.ejecutar_consulta(ls_cad);
                    ls_cad="LOAD DATA INFILE 'D://Cargas//"+ls_idPPDD+"//exportar//i_atemedicamentos.txt' INTO TABLE "+ls_pd+".aux_sis_i_atemedicamentos";    
                    op.ejecutar_consulta(ls_cad);
                    ls_cad="LOAD DATA INFILE 'D://Cargas//"+ls_idPPDD+"//exportar//i_ateinsumos.txt' INTO TABLE "+ls_pd+".aux_sis_i_ateinsumos";    
                    op.ejecutar_consulta(ls_cad);   
                   
                  
            //Migrar datos
         
            ls_cad = "select distinct ate_periodo from "+ls_pd+".aux_sis_i_atencion";
                //out.print(ls_cad);
                String ls_ano = op.met_dev_reg(ls_cad);
            
            //Extraer mes
            ls_cad = "select distinct ate_mes from "+ls_pd+".aux_sis_i_atencion";
                //out.print(ls_cad);
                String ls_mes = op.met_dev_reg(ls_cad);
            
            ls_cad = "call bddiresa.p_migra('"+ls_pd+"','"+ls_ano+"','"+ls_mes+"');";
            //out.print(ls_cad);
            op.ejecutar_consulta(ls_cad);   
                     
        }
            request.getSession().setAttribute("Mensaje_SIS", "ARCHIVO CORRECTAMENTE SUBIDO...");
            response.sendRedirect("carga_sis.jsp");
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
