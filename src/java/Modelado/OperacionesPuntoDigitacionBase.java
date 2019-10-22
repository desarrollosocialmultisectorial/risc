/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelado;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ALEX ARANA
 */
public class OperacionesPuntoDigitacionBase {
   String base,servidor,puerto,usuario,clave,url,driver;
    Connection conn;

    public OperacionesPuntoDigitacionBase(String base ) {
        this.base = base;
        this.servidor = "localhost";
        this.puerto = "3306";
        this.usuario = "root";
        this.clave = ".";
        this.url="jdbc:mysql://"+this.servidor+":"+this.puerto+"/"+this.base;
        this.driver="com.mysql.jdbc.Driver";
    }
    
    public  Connection dev_conexion(){
       try {
           Class.forName(this.driver); 
           try {
                conn= DriverManager.getConnection(this.url,this.usuario,this.clave);
           } catch (SQLException ex) {
               Logger.getLogger(OperacionesPuntoDigitacionBase.class.getName()).log(Level.SEVERE, null, ex);
           }
       } catch (ClassNotFoundException ex) {
           Logger.getLogger(OperacionesPuntoDigitacionBase.class.getName()).log(Level.SEVERE, null, ex);
       }
          return conn;  
            
    }
    
    public ResultSet dev_cargas_sis(String nom_pun_dig){
        ResultSet resul = null;
       try {
           PreparedStatement st = null;
           try {
               String  ls_cad = "select distinct a.año,a.mes,cantidad from "+nom_pun_dig+".sis_his as a where a.programa='SIS' order by cast(a.año as decimal) desc,cast(a.mes as decimal) desc";
               ResultSet re=null;
               System.out.print(ls_cad);
               st=dev_conexion().prepareStatement(ls_cad);
           } catch (SQLException ex) {
               Logger.getLogger(OperacionesPuntoDigitacionBase.class.getName()).log(Level.SEVERE, null, ex);
           }
         resul=  st.executeQuery();
       } catch (SQLException ex) {
           Logger.getLogger(OperacionesPuntoDigitacionBase.class.getName()).log(Level.SEVERE, null, ex);
       }
       return resul;
    }
       public ResultSet dev_cargas_his(String nom_pun_dig){
        ResultSet resul = null;
       try {
           PreparedStatement st = null;
           try {
               String  ls_cad = "select distinct a.año,a.mes,cantidad from "+nom_pun_dig+".sis_his as a where a.programa='HIS' order by cast(a.año as decimal) desc,cast(a.mes as decimal) desc";
               ResultSet re=null;
               System.out.print(ls_cad);
               st=dev_conexion().prepareStatement(ls_cad);
           } catch (SQLException ex) {
               Logger.getLogger(OperacionesPuntoDigitacionBase.class.getName()).log(Level.SEVERE, null, ex);
           }
         resul=  st.executeQuery();
       } catch (SQLException ex) {
           Logger.getLogger(OperacionesPuntoDigitacionBase.class.getName()).log(Level.SEVERE, null, ex);
       }
       return resul;
    }
    
   
    
}
