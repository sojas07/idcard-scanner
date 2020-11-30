package com.miniproject.sdl;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.*;
import java.net.Socket;
import java.util.*;
import java.util.Base64;

public class client  {

    public static String encodeImage(byte[] imageByteArray) {
        return Base64.getEncoder().encodeToString(imageByteArray);
    }

    public static byte[] decodeImage(String imageDataString) {
        return Base64.getDecoder().decode(imageDataString);
    }
     public static void main(String[] args) {

         //the file to convert is in the same folder as the source code

        try {            
            Socket clientSocket= new Socket ("localhost", 7777);

            //send username password
            JSONObject obj = new JSONObject();
            obj.put("username","newImage.png");
            obj.put("password","password" );

            //output stream
            DataOutputStream outToServer=new DataOutputStream(clientSocket.getOutputStream());
            
            //send to server
            outToServer.writeBytes(obj.toJSONString());
            System.out.println("Credentials  Sent!");

            //read from server
            BufferedReader inFromServer = new BufferedReader (new InputStreamReader(clientSocket.getInputStream()));
            String message = org.apache.commons.io.IOUtils.toString(inFromServer);
            JSONObject obj1 = (JSONObject) JSONValue.parse(message);
            String flag = obj1.get("flag").toString();
            String image = obj1.get("image").toString();   
            
            //decode image
            byte[] imageByteArray = decodeImage(image);
            string name = "clientoutput.png";
            //write image
            FileOutputStream imageOutFile = new FileOutputStream(name);
            imageOutFile.write(imageByteArray);
            imageOutFile.close();}
            System.out.println("Image Written Manipulated!");
            //Image conversion to byte array
            // FileInputStream imageInFile = new FileInputStream(file);
            // byte imageData[] = new byte[(int) file.length()];
            // imageInFile.read(imageData);

            //Image conversion byte array in Base64 String
            // String imageDataString = encodeImage(imageData);
            // imageInFile.close();
            // System.out.println("Image Successfully Manipulated!");

            // //the object that will be send to Server
            // JSONObject obj = new JSONObject();

            // //name of the image
            // obj.put("filename","newImage.png");
            // //string obteined by the conversion of the image
            // obj.put("image",imageDataString );

            // //connection to erver
            // Socket clientSocket= new Socket ("localhost", 7777);
           
            
            // //send data
            // outToServer.writeBytes(obj.toJSONString());
            // System.out.println("File Sent!");
            
            //closing connection
            clientSocket.close();

        } catch (FileNotFoundException e) {
            System.out.println("Image not found" + e);
        } catch (IOException ioe) {
            System.out.println("Exception while reading the Image " + ioe);
        }  

    }

}