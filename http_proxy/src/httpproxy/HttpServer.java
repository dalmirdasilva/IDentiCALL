package httpproxy;


import httpproxy.ConnectionHandler;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author dalmir
 */
public class HttpServer implements Runnable {

    private ServerSocket server;

    public static void main(String[] args) throws IOException {
        HttpServer s = new HttpServer();
        s.run();
    }

    public HttpServer() throws IOException {
        server = new ServerSocket(8080);
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket socket = server.accept();
                ConnectionHandler ch = new ConnectionHandler(socket);
                ch.run();
            } catch (IOException ex) {
                System.out.println(ex);
                System.exit(1);
            }            
        }
    }
}
