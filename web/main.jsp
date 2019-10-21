

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>MAIN</title>
        <link href="css/estilo_main.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <p id="titulo">SISTEMA DIRESA CAJAMARCA</p>
       
        
        <p id="sub_titulo">            
            Punto de Digitacion-<%  
            out.write(request.getSession().getAttribute("Nombre_Punto").toString()); %>
        </p>
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
        
        
        
    </body>
</html>
