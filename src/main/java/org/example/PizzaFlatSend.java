package org.example;

import com.google.gson.Gson;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class PizzaFlatSend {
    // Method to convert a Pizza object to a delimited string for CSV writing
    public static String toDelimitedString(Pizza pizza) {
        return String.format("%s,%s,%s,%s,%s,%s",
                pizza.getPizzaID(),
                pizza.getPrice(),
                pizza.getSize(),
                pizza.getSauceType(),
                pizza.getMainTopping(),
                pizza.getCrustThickness());
    }

    // Method to write a single Pizza object data as a CSV line in the file
    public static void sendPizzaData(String pizzaJson, String filename) {
        Gson gson = new Gson();
        Pizza pizza = gson.fromJson(pizzaJson, Pizza.class);

        String csvLine = toDelimitedString(pizza);

        // Write the CSV line to the file
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename, true))) {
            writer.println(csvLine);
            System.out.println("Pizza data sent successfully to file: " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

