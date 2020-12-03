package com.miniproject.sdl;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class db {
        public Connection con;
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
        public JSONObject checkCredentials(String user,String pass){
        	//ResultSet r = null;
        	JSONObject userobj = new JSONObject();
            try{
                 Statement stmt=con.createStatement();
                 ResultSet r = stmt.executeQuery("select * from record where username = '"+user+"' and password = '"+pass+"' ");
                 while(r.next()) {
                 userobj.put("id", r.getString(1));
                 userobj.put("username", r.getString(2));
                 userobj.put("firstName", r.getString(4));
                 userobj.put("lastName", r.getString(5));
                 userobj.put("division", r.getString(6));
                 userobj.put("yearOfStudy", r.getString(7));
                 userobj.put("rollNo", r.getString(8));}
                 return userobj;
            }
            catch(Exception e){
                System.out.println(e);
            }
            return userobj;
        }
            

}
