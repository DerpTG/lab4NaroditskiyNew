package org.example;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class PizzaWebSend {
    private final String pizzaJson;
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
            server.stop(0); // Stop immediately
            System.out.println("Server stopped.");
        }
    }

    class CSVHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // Assuming the CSV file is in the same directory as your source files
            String csvFilePath = "pizza_data.csv"; // Assuming it's in the same directory
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
}
