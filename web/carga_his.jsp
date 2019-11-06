<%-- 
    Document   : carga_his
    Created on : 20/10/2019, 12:16:49 AM
    Author     : ALEX ARANA
--%>

<%@page import="java.sql.ResultSet"%>
<%@page import="Modelado.OperacionesPuntoDigitacionBase"%>
<%@page import="Modelado.Operaciones"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>MAIN</title>
        <link href="css/estilo_main.css" rel="stylesheet" type="text/css"/>
        <link href="css/estilo_main.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <p id="titulo">SISTEMA DIRESA CAJAMARCA</p>
        
       <a href='index.html'><p id='titulox'>Cerrar sesion</p></a>
        
       <p id="sub_titulo"> Punto de Digitacion-<%  
            out.write(request.getSession().getAttribute("Nombre_Punto").toString()); %></p>
        <hr>
        
        <div id="menu-wrapper">
            <ul id="hmenu">
                <li><a href="">Inicio</a></li>
                <li><a href="">Cargas</a>

                <ul id="sub-menu">
                        <li><a href="carga_sis.jsp">SIS</a></li>
                        <li><a href="carga_his.jsp">HIS</a></li>
                        <li><a href="">SISMED</a></li>
                </ul>
                 </li>       
                    
  
             <li><a href="">Reportes</a>
                    <ul id="sub-menu">
                        <li><a href="Reporte_Nino.jsp"><img src="imagen/editar.png" >Niño</a></li>
                        <li><a href="Reporte_Materno.jsp"><img src="imagen/eliminar.png" >Materno</a></li>
                        <li><a href="">Produccion digitadores SIS</a></li>
                        <li><a href="">Comparativos</a></li>
                        <li><a href="ReporteAnemia.jsp">Reporte de anemia</a>
                    </ul>
                </li>
            </ul>
        </div>     
        
        <h2 align="center">CARGA DE DATOS HIS</h2>        
        </br>
        <form action="CargaHisServerlet" method="post" enctype="multipart/form-data">
            <table border="1" width="250" align="center">
                <tr>
                    <td>
                        <input type="file" name="file"/>
                            </br>
                        <input type="submit" name="bb" value="SUBIR ARCHIVO"/>
                    </td>
                </tr>
            </table>
        </form>
        
      
        
        <h2 align="center"> 
            <%= request.getSession().getAttribute("mensaje_flash_carga_his").toString() %>
            <% request.getSession().setAttribute("mensaje_flash_carga_his",""); %>
        </h2>
        
        <table border="1" width="600" align="center">
            <tr bgcolor="skyblue">
                <th colspan="5">Resumen de carga de datos</th>
                
            </tr>
            <tr bgcolor="skyblue">
                <th>Año</th>
                <th>Mes</th>
                <th>Cantidad</th>
            </tr>
             <% 
             OperacionesPuntoDigitacionBase pd=new OperacionesPuntoDigitacionBase( request.getSession().getAttribute("Nombre_Base_Punto").toString());
            String var=request.getSession().getAttribute("Nombre_Base_Punto").toString();
              ResultSet rs=pd.dev_cargas_his(var);
              System.out.print(var);
                try{
                
                    while (rs.next()){
                        %>
                        <tr>
                            <th><%=rs.getString(1)%></th>
                            <th><%=rs.getString(2)%></th>
                            <th><%=rs.getString(3)%></th>
                        </tr>
                        <%
                    }
                }catch (Exception e){}
            %>            
        
        
        </table>
            
            
    </body>
</html>
