package org.example;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class PizzaWebSend {
    private String pizzaJson;
    private HttpServer server;

    public PizzaWebSend(String pizzaJson) {
        this.pizzaJson = pizzaJson;
    }

    public void startServer() throws IOException {
        server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/csv", new CSVHandler()); // Handler for CSV
        server.createContext("/pizza", new PizzaHandler()); // Handler for Pizza JSON
        server.setExecutor(null); // creates a default executor
        server.start();
        System.out.println("Server started on port 8000");
    }

    public void stopServer() {
        if (server != null) {
            server.stop(0);
            System.out.println("Server stopped.");
        }
    }

    // Handler for serving the Pizza csv
    class CSVHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String csvFilePath = "pizza_data.csv";
            File file = new File(csvFilePath);
            if (file.exists()) {
                exchange.sendResponseHeaders(200, file.length());
                try (OutputStream os = exchange.getResponseBody()) {
                    Files.copy(file.toPath(), os);
                }
            } else {
                String response = "CSV file not found.";
                exchange.sendResponseHeaders(404, response.length());
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
            }
        }
    }

    // Handler for serving the Pizza JSON
    class PizzaHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            exchange.sendResponseHeaders(200, pizzaJson.getBytes(StandardCharsets.UTF_8).length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(pizzaJson.getBytes(StandardCharsets.UTF_8));
            }
        }
    }

    // Getter for pizzaJson
    public String getPizzaJson() {return pizzaJson;}

    // Setter for pizzaJson
    public void setPizzaJson(String pizzaJson) {this.pizzaJson = pizzaJson;}
}
