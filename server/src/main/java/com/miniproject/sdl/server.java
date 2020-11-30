package com.miniproject.sdl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;


public class server {

    public static byte[] decodeImage(String imageDataString) {
        return Base64.getDecoder().decode(imageDataString);
    }

    public static void main(String[] args) {

        while (true){

            try{  
 
                //socket connection (PORT 7777)
                ServerSocket welcomeSocket=new ServerSocket(7777);
                Socket connectionSocket=welcomeSocket.accept();
                BufferedReader inFromClient = new BufferedReader (new InputStreamReader(connectionSocket.getInputStream()));
                
                //convert received data
                System.out.println("File Received!");
                String message = org.apache.commons.io.IOUtils.toString(inFromClient);
                JSONObject obj1 = (JSONObject) JSONValue.parse(message);
                String name = obj1.get("filename").toString();
                String image = obj1.get("image").toString();      

                //convert from base64 to byte array
                byte[] imageByteArray = decodeImage(image);

                //convert byte array to a file image
                FileOutputStream imageOutFile = new FileOutputStream(name);
                imageOutFile.write(imageByteArray);
                imageOutFile.close();
                System.out.println("Image Successfully Manipulated!");
            } 
            catch (FileNotFoundException e) {
            } 
            catch (IOException e) {
            } 
        }
    }
}