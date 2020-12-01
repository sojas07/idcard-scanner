package com.miniproject.sdl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class db {
        public Connection con;
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
        public boolean checkCredentials(String user,String pass){
            
            try{
                 Statement stmt=con.createStatement();
                 ResultSet r = stmt.executeQuery("select * from record where username = '"+user+"' and password = '"+pass+"' ");
                 if(r.next() == false){
                    return false;
                 }
                 else{
                     return true;
                 }
            }
            catch(Exception e){
                System.out.println(e);
            }
           
            return true;
        }
            

}
