import java.io.*;
import java.net.*;
public class Client
{
    public static void main(String[] args){
        String host = "192.168.0.16";
        int port = 9999;
       
        /*
        if(args.length == 0){
         host = "localhost";
         port = 9999;
        }
        else{
         host = args[0];
         String portStr = args[1];
         try{
         port = Integer.parseInt(portStr);
        }
        catch(NumberFormatException e){
            e.printStackTrace();
            port = 9999;
        }
            
        }
        */
        
        try{
            System.out.println("Client attempting to connect to server at host=" + host + " port=" + port + ".");
            Socket skt = new Socket(host, port);
            
            BufferedReader myInput = new BufferedReader(new InputStreamReader(skt.getInputStream()));
            PrintStream myOutput = new PrintStream(skt.getOutputStream());
            
            myOutput.println("Hello Server");
            
            String buf = myInput.readLine();
            if(buf != null){
             System.out.println("Client received [" + buf + "] from the server!");   
            }
            
            skt.close();
        }
        catch(Exception e){
         e.printStackTrace();   
        }
    }
}
