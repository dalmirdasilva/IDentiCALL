package httpproxy;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ConnectionHandler implements Runnable {

    private Socket socket;
    BufferedWriter out = null;
    BufferedReader in = null;

    public ConnectionHandler(Socket socket) throws IOException {
        this.socket = socket;
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    @SuppressWarnings("empty-statement")
    public void run() {
        String line;
        try {
            line = in.readLine();
            String[] parts = line.split("/?q=");
            parts = parts[1].split(" ");
            parts = parts[0].split("//");
            Requester req = new Requester(parts[1], "/");
            req.request();
            InputStream ips = req.getSocket().getInputStream();
            int i;
            BufferedReader br = new BufferedReader(new InputStreamReader(ips));
            i = ips.read();
            System.out.println(i);
            out.write(i);
            while (ips.available() > 0) {
                i = ips.read();
                out.write(i);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
