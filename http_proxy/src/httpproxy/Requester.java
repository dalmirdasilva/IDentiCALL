package httpproxy;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Requester {

    Socket socket = null;
    BufferedWriter out = null;
    BufferedReader in = null;
    String host = null;
    String path = null;

    Requester(String host, String path) {
        this.host = host;
        this.path = path;
    }

    public void request() throws IOException {
        try {
            socket = new Socket(host, 80);
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            System.err.println(e);
            System.exit(1);
        }
        out.write("GET " + path + " HTTP/1.1\n");
        System.out.println("GET " + path + " HTTP/1.1\n");
        out.write("Host: " + host + "\n\n");
        System.out.println("Host: " + host + "\n\n");
        out.flush();
    }
    
    public BufferedReader getReader() {
        return in;
    }
    
    public BufferedWriter getWriter() {
        return out;
    }

    public Socket getSocket() {
        return socket;
    }
}
