package Modelado;

import java.sql.*;

public class Operaciones {
    
    String driver;
    String url;
    String uss;
    String contra;
    
    public Operaciones(){
        driver = "com.mysql.jdbc.Driver";
        url = "jdbc:mysql://localhost:3306/bddiresa";
        uss = "root";
        contra = ".";        
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

    
}