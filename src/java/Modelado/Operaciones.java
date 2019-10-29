package Modelado;

import java.io.File;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Operaciones {
    
    String driver;
    String url;
    String uss;
    String contra;
       ResultSet re=null;
    
    public Operaciones(){
        driver = "com.mysql.jdbc.Driver";
        url = "jdbc:mysql://localhost:3306/bddiresa";
        uss = "root";
        contra = ".";        
    }

    public ResultSet getRe() {
        return re;
    }
    
    public int loguear(String uss, String contra){
        Connection conn;
        PreparedStatement ps;
        ResultSet rs;
        int cont = 0;
        int nivel=0;
          
        String sql ="select nivel from bddiresa.login where usuario='"+uss+"' and contra='" + contra +"'";  
      
        try {
          
            Class.forName(this.driver);
            conn= DriverManager.getConnection(this.url,this.uss,this.contra);            
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();     
          
     
            while (rs.next()){
                nivel = rs.getInt(1);
            }
            conn.close();            
        } catch (ClassNotFoundException e){
            System.out.print("Error :"+e);
            
      
        
        }catch(SQLException e) {
            System.out.print("Error :"+e);
        }        
        return nivel;
    }
    
    public String met_dev_reg(String cadsql){
        String ls_res="";
        Connection conn;
        PreparedStatement ps;
        ResultSet rs;
        String nivel="";
        String sql = cadsql;        
        try {
            Class.forName(this.driver);
            conn= DriverManager.getConnection(url,uss,contra);            
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                nivel = rs.getString(1);
            }
            conn.close();
        } catch (ClassNotFoundException e){
            System.out.print("Error :"+e);
        }catch(SQLException e) {
            System.out.print("Error :"+e);
        }        
        return nivel;
    }

    public void met_graba_reg(String cadsql){
        String ls_res="";
        Connection conn;
        PreparedStatement ps;
        ResultSet rs;
        String nivel="";
        String sql = cadsql;        
        try {
            Class.forName(this.driver);
            conn= DriverManager.getConnection(url,uss,contra);            
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                nivel = rs.getString(1);
            }
            conn.close();
        } catch (ClassNotFoundException e){
            System.out.print("Error :"+e);
        }catch(SQLException e) {
            System.out.print("Error :"+e);
        }
    } 
        public String dev_pun_dig(String contra){
         String ls_cad = "select pundig_nom from bddiresa.pun$dig where id_pundig="+contra;
                    ls_cad=this.met_dev_reg(ls_cad);

                    return ls_cad;

        }
         public String dev_nom_base_pun_dig(String contra){
             
                    String ls_cad = "select punto_base from bddiresa.pun$dig where id_pundig="+contra;
                    System.out.print(ls_cad);
                    ls_cad=this.met_dev_reg(ls_cad);
                    
                    return ls_cad;

        }
        
        
        public ResultSet dev_cargas_sis(String nom_pun_dig){
        
         String  ls_cad = "select distinct a.año,a.mes,cantidad from "+nom_pun_dig+".sis_his as a where a.programa='SIS' order by cast(a.año as decimal) desc,cast(a.mes as decimal) desc";
     
         System.out.print(ls_cad);
       
       try {
          
            Class.forName(this.driver);
          Connection  conn= DriverManager.getConnection(this.url,this.uss,this.contra);            
           PreparedStatement ps = conn.prepareStatement(ls_cad);
           this.re= ps.executeQuery();     
          
     
            conn.close();
                     
        } catch (ClassNotFoundException e){
            System.out.print("Error :"+e);
            
      
        
        }catch(SQLException e) {
            System.out.print("Error :"+e);
        }                   

        return this.re;

        }
        
        public boolean crear_carpetas_cargas(){
        boolean res=false;
          Connection cnx = null ;
        try {
            String ls_pd="";
          
            try {
                Class.forName(this.driver);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Operaciones.class.getName()).log(Level.SEVERE, null, ex);
            }
             cnx = DriverManager.getConnection(this.url,this.uss,this.contra);
            Statement sta = cnx.createStatement();
            ResultSet rs = sta.executeQuery("select id_pundig from bddiresa.pun$dig;");            
            File folder;
            while (rs.next()){                
                folder = new File("D://Cargas//"+rs.getString(1));
                if (!folder.isDirectory()) { // escribimos algo si es un directorio }
                    folder.mkdirs();
                }
            } 
            res=true;
        } catch (SQLException ex) {
            Logger.getLogger(Operaciones.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            cnx.close();
        } catch (SQLException ex) {
            Logger.getLogger(Operaciones.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
        }
        
        
        public  void ejecutar_consulta  (String consulta){
            
                try {
                     Class.forName(this.driver);
                    Connection conn = DriverManager.getConnection(this.url,this.uss,this.contra);
                    PreparedStatement ps = conn.prepareStatement(consulta);  
                    ps.executeQuery();
                    conn.close();
               System.out.print(consulta );
                } catch (Exception ex) {
                    Logger.getLogger(Operaciones.class.getName()).log(Level.SEVERE, null, ex);
                }

        }
    
}
