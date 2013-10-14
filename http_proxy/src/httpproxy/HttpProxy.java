package httpproxy;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class HttpProxy {

    public static void main(String[] args) throws IOException {
        HttpServer hs = new HttpServer();
        hs.run();
        
        if (true) return;

        Socket socket = null;
        BufferedWriter out = null;
        BufferedReader in = null;

        String host = "www.facebook.com";
        String path = "/about/timeline";

        try {
            socket = new Socket(host, 80);
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            System.err.println(e);
            System.exit(1);
        }

        out.write("GET " + path + " HTTP/1.1\n");
        out.write("Host: " + host + "\n\n");
        out.flush();
        
        String line;
        while ((line = in.readLine()) != null) {          
            System.out.println(line);
        }

        out.close();
        in.close();
        socket.close();
    }
}
