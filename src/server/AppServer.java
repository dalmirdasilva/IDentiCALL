package server;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import incomingcallnotification.IncomingCallDescriptor;
import incomingcallnotification.IncomingCallListener;
import com.sun.net.httpserver.HttpServer;
import entity.Customer;
import helper.Formater;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AppServer implements IncomingCallListener, HttpHandler {

    private static AppServer instance;
    private IncomingCallDescriptor lastIncomingCallDescriptor;
    private HttpServer server;
    private static int HTTP_SERVER_PORT = 8000;

    static {
        try {
            instance = new AppServer();
            instance.start();
        } catch (IOException ex) {
            Logger.getLogger(AppServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private AppServer() throws IOException {
    }
    
    public void handleCrossOriginResourceSharing(HttpExchange exchange) throws IOException {
        String response = "";
        Headers responseHeaders = exchange.getResponseHeaders();
        responseHeaders.set("Access-Control-Allow-Origin", "*");
        responseHeaders.set("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
        responseHeaders.set("Access-Control-Allow-Headers", "X-Requested-With, Content-Type, Content-Length");
        exchange.sendResponseHeaders(200, 0);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (exchange.getRequestMethod().startsWith("OPTIONS")) {
            handleCrossOriginResourceSharing(exchange);
            return;
        }
        if (exchange.getHttpContext().getPath().startsWith("/last_call")) {
            handleLastCall(exchange);
        } else {
            handleIndex(exchange);
        }
    }

    private void handleIndex(HttpExchange exchange) throws IOException {
        String response = generateIndexResponse();
        Headers responseHeaders = exchange.getResponseHeaders();
        responseHeaders.set("Content-Type", "text/html");
        responseHeaders.set("Access-Control-Allow-Origin", "*");
        exchange.sendResponseHeaders(200, response.getBytes().length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }

    private void handleLastCall(HttpExchange exchange) throws IOException {
        String response = generateLastCallResponse();
        Headers responseHeaders = exchange.getResponseHeaders();
        responseHeaders.set("Content-Type", "application/json");
        responseHeaders.set("Access-Control-Allow-Origin", "*");
        exchange.sendResponseHeaders(200, response.getBytes().length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }

    private String generateIndexResponse() throws FileNotFoundException, IOException {
        Path path = Paths.get(Paths.get("").toAbsolutePath() + "/src/server/index.html");
        byte[] encoded = Files.readAllBytes(path);
        return Charset.defaultCharset().decode(ByteBuffer.wrap(encoded)).toString();
    }

    private String generateLastCallResponse() {
        if (lastIncomingCallDescriptor == null) {
            return "{"
                + "\"phone\": \"---\","
                + "\"name\" : \"<Cliente não encontrado>\","
                + "\"problem\": \"N\","
                + "\"CPF_CNPJ\": \"---\""
                + "}";
        }
        List<Customer> customers = lastIncomingCallDescriptor.getCustomers();
        Customer customer = (customers != null && customers.size() > 0) ? customers.get(0) : null;
        String response = "{"
                + "\"phone\": \"" + Formater.formatPhone(lastIncomingCallDescriptor.getPhone()) + "\","
                + "\"name\" : \"" + (customer != null ? customer.getName() : "<Cliente não encontrado>") + "\","
                + "\"problem\": \"" + (customer != null ? customer.getProblems().toString() : "N") + "\","
                + "\"CPF_CNPJ\": \"" + (customer != null ? Formater.formatCpfCnpj(customer.getCpfCnpj()) : "---") + "\""
                + "}";
        return response;
    }

    public void start() throws IOException {
        server = HttpServer.create(new InetSocketAddress(HTTP_SERVER_PORT), 0);
        server.createContext("/last_call", instance);
        server.createContext("/", instance);
        server.setExecutor(null);
        server.start();
    }

    public static AppServer getInstance() {
        return instance;
    }

    @Override
    public void incomingCall(IncomingCallDescriptor descriptor) {
        lastIncomingCallDescriptor = descriptor;
    }
}
