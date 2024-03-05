/** Project: Lab 4 Systems Integration Pizza Shop
 * Purpose Details: System Integration Using Flat Files, RabbitMQ, and Web Service/JSON
 * Course: IST 242
 * Author: Felix Naroditskiy
 * Date Developed: 2/21/2024
 * Last Date Changed: 3/4/2024
 * Rev: 1.0

 */

package org.example;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;


public class PizzaWebGet {
    /**
     * @param endpoint The endpoint URL to which the HTTP GET request is sent.
     * @param gson Gson instance for converting JSON strings into Pizza objects.
     */
    private String endpoint;
    private final Gson gson = new Gson();

    /**
     * Constructs a new PizzaWebGet instance with the specified endpoint.
     *
     * @param endpoint The endpoint URL segment for the web service call.
     */
    public PizzaWebGet(String endpoint) {
        this.endpoint = endpoint;
    }

    /**
     * Sends an HTTP GET request to the specified endpoint, parses the response,
     * and displays the details of the Pizza object(s) created from the response data.
     * The method handles both JSON and CSV response formats, depending on the endpoint.
     */
    public void callWebService() {
        try {
            URL obj = new URL("http://localhost:8000/" + endpoint);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");

            int responseCode = con.getResponseCode();
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            if (endpoint.equals("pizza")) {
                Pizza pizza = gson.fromJson(response.toString(), Pizza.class);
                pizza.displayPizzaDetails();
            } else if (endpoint.equals("csv")) {
                String[] lines = response.toString().split("\n");
                for (String line : lines) {
                    String[] values = line.split(",");
                    // Assuming the CSV format matches the Pizza constructor order
                    if (values.length == 6) {
                        Pizza pizza = new Pizza(values[0], values[1], values[2], values[3], values[4], values[5]);
                        pizza.displayPizzaDetails();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the current endpoint URL segment.
     *
     * @return The current endpoint URL segment.
     */
    public String getEndpoint() {return endpoint;}

    /**
     * Updates the endpoint URL segment for the web service call.
     *
     * @param endpoint The new endpoint URL segment.
     */
    public void setEndpoint(String endpoint) {this.endpoint = endpoint;}
}

