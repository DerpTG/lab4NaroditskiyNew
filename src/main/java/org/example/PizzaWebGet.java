package org.example;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class PizzaWebGet {
    private final String endpoint;
    private final Gson gson = new Gson();

    public PizzaWebGet(String endpoint) {
        this.endpoint = endpoint;
    }

    public void callWebService() {
        try {
            URL obj = new URL("http://localhost:8000/" + this.endpoint);
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

            // Determine the type of response based on the endpoint
            if (this.endpoint.equals("pizza")) {
                // Parse JSON response
                Pizza pizza = gson.fromJson(response.toString(), Pizza.class);
                pizza.displayPizzaDetails();
            } else if (this.endpoint.equals("csv")) {
                // Parse CSV response (assuming a simple CSV format)
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
}

