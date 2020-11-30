package com.miniproject.sdl;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

//import org.json.simple.JSONArray;
//import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
//import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
//import com.google.zxing.client.j2se.MatrixToImageWriter;
//import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class server {

    public static byte[] decodeImage(String imageDataString) {
        return Base64.getDecoder().decode(imageDataString);
    }
    
    public static void connectToDb() {
    	
    }
    public static String encodeImage(byte[] imageByteArray) {
        return Base64.getEncoder().encodeToString(imageByteArray);
    }

    @SuppressWarnings("deprecation")
//	public static void createQR(String data, String path,
//            String charset, Map hashMap,
//            int height, int width)
//            throws WriterException, IOException
//    	{
//
//		BitMatrix matrix = new MultiFormatWriter().encode(
//		new String(data.getBytes(charset), charset),
//		BarcodeFormat.QR_CODE, width, height);
//		
//		MatrixToImageWriter.writeToFile(
//		matrix,
//		path.substring(path.lastIndexOf('.') + 1),
//		new File(path));
//		}
    public static void main(String[] args)
    		throws WriterException, IOException,
            NotFoundException{
    	
        while (true){

            try{  
 
                //socket connection (PORT 7777)
                ServerSocket welcomeSocket=new ServerSocket(7777);
                Socket connectionSocket=welcomeSocket.accept();
                BufferedReader inFromClient = new BufferedReader (new InputStreamReader(connectionSocket.getInputStream()));
                DataOutputStream outToClient=new DataOutputStream(connectionSocket.getOutputStream());
                
                //convert received data
                System.out.println("Credentials Received--->>!");
                String message = org.apache.commons.io.IOUtils.toString(inFromClient);
                JSONObject obj1 = (JSONObject) JSONValue.parse(message);
                String name = obj1.get("username").toString();
                String image = obj1.get("password").toString();      

                //validate credentials
                db obj = new db();
                //boolean flag = obj.checkCredentials();
                boolean flag = true;
                if (flag == true) {
                	genrateQrCode qr = new genrateQrCode();
                	JSONObject userobj = new JSONObject();
                	userobj.put("id", "C2K18105812");
                	userobj.put("first_name", "Sanket");
                	userobj.put("last-name", "Varpe");
                	userobj.put("div", "Te-2");
                	userobj.put("yearofstudy", "3rd");
                	userobj.put("rollno", "31268");
                	String data = userobj.toJSONString();
               	 
                   // The path where the image will get saved
                	String path = "output.png";
                   // Encoding charset
                	String charset = "UTF-8";
                	Map<EncodeHintType, ErrorCorrectionLevel> hashMap
                       = new HashMap<EncodeHintType,ErrorCorrectionLevel>();
                	hashMap.put(EncodeHintType.ERROR_CORRECTION,ErrorCorrectionLevel.L);
                	//genrate qr code
                	
                	qr.createQR(data, path, charset, hashMap, 200, 200);
                	
                	//read image genrated
                	String file = "output.png";
                	FileInputStream imageInFile = new FileInputStream(file);
                    byte imageData[] = new byte[(int) file.length()];
                    imageInFile.read(imageData);
                    
                    //encode image
                    String imageDataString = encodeImage(imageData);
                    imageInFile.close();
                    System.out.println("Image Successfully Manipulated!");
                    //response
                    
                    JSONObject responseObj = new JSONObject();
                    //string obteined by the conversion of the image
                    responseObj.put("image",imageDataString );
                    responseObj.put("flag",flag );
                    //send response
                    
                    outToClient.writeBytes(responseObj.toJSONString());
                    System.out.println("File Sent!");
                //convert byte array to a file image
//                FileOutputStream imageOutFile = new FileOutputStream(name);
//                imageOutFile.write(imageByteArray);
//                imageOutFile.close();}
//                System.out.println("Image Successfully Manipulated!");
            }else{
            	JSONObject responseObj = new JSONObject();
                //string obteined by the conversion of the image
                responseObj.put("image","empty" );
                responseObj.put("flag",flag );
                //send response
                
                outToClient.writeBytes(responseObj.toJSONString());
                System.out.println("File Sent!");
                }
            }
            catch (FileNotFoundException e) {
            } 
            catch (IOException e) {
            } 
        }
//    	JSONObject obj = new JSONObject();
//    	obj.put("id", "C2K18105812");
//    	obj.put("name", "Sanket");
//    	obj.put("last-name", "Varpe");
//    	
//    	String data = obj.toJSONString();
//    	 
//        // The path where the image will get saved
//        String path = "demo1.png";
// 
//        // Encoding charset
//        String charset = "UTF-8";
// 
//        Map<EncodeHintType, ErrorCorrectionLevel> hashMap
//            = new HashMap<EncodeHintType,
//                          ErrorCorrectionLevel>();
// 
//        hashMap.put(EncodeHintType.ERROR_CORRECTION,
//                    ErrorCorrectionLevel.L);
// 
//        // Create the QR code and save
//        // in the specified folder
//        // as a jpg file
//        createQR(data, path, charset, hashMap, 200, 200);
//        System.out.println("QR Code Generated!!! ");
    }
}