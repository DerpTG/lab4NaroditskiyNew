/** Project: Lab 4 Systems Integration Pizza Shop
 * Purpose Details: System Integration Using Flat Files, RabbitMQ, and Web Service/JSON
 * Course: IST 242
 * Author: Felix Naroditskiy
 * Date Developed: 2/21/2024
 * Last Date Changed: 3/4/2024
 * Rev: 1.0

 */

package org.example;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class PizzaWebSend {
    /**
     * @param pizzaJson The JSON string representation of a pizza object to be served at the "/pizza" endpoint.
     * @param server The instance of HttpServer used to manage HTTP requests and responses.
     */
    private String pizzaJson;
    private HttpServer server;

    /**
     * Constructs a new PizzaWebSend instance with the specified pizza JSON string.
     *
     * @param pizzaJson The JSON string representation of pizza data.
     */
    public PizzaWebSend(String pizzaJson) {
        this.pizzaJson = pizzaJson;
    }

    /**
     * Initializes and starts the HTTP server on port 8000. It sets up handlers for serving
     * pizza data as CSV and JSON through designated endpoints.
     *
     * @throws IOException If an I/O error occurs when starting the server or creating a context.
     */
    public void startServer() throws IOException {
        server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/csv", new CSVHandler()); // Handler for CSV
        server.createContext("/pizza", new PizzaHandler()); // Handler for Pizza JSON
        server.setExecutor(null); // creates a default executor
        server.start();
        System.out.println("Server started on port 8000");
    }

    /**
     * Stops the HTTP server if it's running. This method ensures the server is gracefully shut down.
     */
    public void stopServer() {
        if (server != null) {
            server.stop(0);
            System.out.println("Server stopped.");
        }
    }

    /**
     * HttpHandler implementation for serving the pizza data CSV file at the "/csv" endpoint.
     */
    class CSVHandler implements HttpHandler {
        /**
         * Handles the HTTP request by serving a CSV file containing pizza data.
         *
         * @param exchange The HttpExchange object representing the HTTP request and response.
         * @throws IOException If an I/O error occurs.
         */
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

    /**
     * HttpHandler implementation for serving the pizza data in JSON format at the "/pizza" endpoint.
     */
    class PizzaHandler implements HttpHandler {
        /**
         * Handles the HTTP request by serving the pizza data in JSON format.
         *
         * @param exchange The HttpExchange object representing the HTTP request and response.
         * @throws IOException If an I/O error occurs.
         */
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            exchange.sendResponseHeaders(200, pizzaJson.getBytes(StandardCharsets.UTF_8).length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(pizzaJson.getBytes(StandardCharsets.UTF_8));
            }
        }
    }

    /**
     * Returns the current pizza JSON string.
     *
     * @return The current pizza JSON string.
     */
    public String getPizzaJson() {return pizzaJson;}

    /**
     * Returns the current pizza JSON string.
     *
     * @return The current pizza JSON string.
     */
    public void setPizzaJson(String pizzaJson) {this.pizzaJson = pizzaJson;}
}
