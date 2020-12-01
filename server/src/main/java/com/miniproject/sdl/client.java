package com.miniproject.sdl;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.*;
import java.net.Socket;
import java.util.Base64;

public class client {
    public static String encodeImage(byte[] imageByteArray) {
        return Base64.getEncoder().encodeToString(imageByteArray);
    }

    public static byte[] decodeImage(String imageDataString) {
        return Base64.getDecoder().decode(imageDataString);
    }
     public static void main(String[] args) {

        try {            
            Socket clientSocket= new Socket ("localhost", 7777);

            //send username password
            JSONObject obj = new JSONObject();
            obj.put("username","sk");
            obj.put("password","123456" );

            //output stream
            DataOutputStream outToServer=new DataOutputStream(clientSocket.getOutputStream());
            BufferedReader inFromServer = new BufferedReader (new InputStreamReader(clientSocket.getInputStream()));
            //send to server
            outToServer.writeBytes(obj.toJSONString()+"\n");
            System.out.println("Credentials  Sent!");

            outToServer.writeBytes(obj.toJSONString() + '\n');
            outToServer.flush();
            System.out.println("Credentials  Sent!");
            //read from server
            String message = inFromServer.readLine();
            JSONObject obj1 = (JSONObject) JSONValue.parse(message);
            String flag = obj1.get("flag").toString();	
            String image = obj1.get("image").toString();   
            System.out.println(image.length());
            
            //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
            // String message = inFromServer.readLine();
            // JSONObject obj1 = (JSONObject) JSONValue.parse(message);
            // JSONObject obj1 = (JSONObject) ois.readObject();
            // String flag = obj1.get("flag").toString();
            // String image = obj1.get("image").toString();
            // byte[] image = (byte[])ois.readObject();
            //decode image
            if(flag == "true") {
            byte[] imageByteArray = decodeImage(image);
            System.out.println(imageByteArray.length);
            String name = "clientoutput.png";
            
            //write image
            FileOutputStream imageOutFile = new FileOutputStream(name);
            imageOutFile.write(imageByteArray);
            imageOutFile.close();}
            else {
            	System.out.println("Invalid!!...");
            }
            System.out.println("Image Written Manipulated!");
            clientSocket.close();

        } catch (FileNotFoundException e) {
            System.out.println("Image not found" + e);
        } catch (IOException ioe) {
            System.out.println("Exception while reading the Image " + ioe);
        }  

    }

}