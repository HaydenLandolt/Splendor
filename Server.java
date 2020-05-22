import java.io.*;
import java.net.*;

public class Server
{
 
    public static void main(String[] args){
        try{
        ServerSocket myServerSocket = new ServerSocket(9999);
        
        System.out.println("Sever waiting for incoming connection on host="
                            + InetAddress.getLocalHost().getCanonicalHostName()
                            + " port=" + myServerSocket.getLocalPort());
        
        Socket skt = myServerSocket.accept();
        
        BufferedReader myInput = new BufferedReader(new InputStreamReader(skt.getInputStream()));
        PrintStream myOutput = new PrintStream(skt.getOutputStream());
        
        String buf = myInput.readLine();
        
        if(buf != null){
         System.out.println("Server read: [" + buf + "]");
         myOutput.print("got it");
        }
        
        skt.close();
    }
    catch(Exception e){
     e.printStackTrace();   
    }
    }
}
