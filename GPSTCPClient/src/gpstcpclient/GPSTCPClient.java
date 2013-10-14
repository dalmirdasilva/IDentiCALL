package gpstcpclient;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class GPSTCPClient {

    public static void main(String[] args) throws UnknownHostException, IOException {
        int i;
        System.out.println("Begin...");
        try (Socket clientSocket = new Socket("localhost", 2947)) {
            System.out.println("clientSocket: " + clientSocket);
            InputStream is = clientSocket.getInputStream();
            while((i = is.read()) != -1) {            
                System.out.println("Server: " + i);
            }
        }
    }
}


