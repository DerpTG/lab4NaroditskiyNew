package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PizzaFlatGet {

    // Method to convert a CSV line into a Pizza object
    public static Pizza fromDelimitedString(String line) {
        String[] parts = line.split(",");
        if (parts.length == 4) {
            return new Pizza(parts[0], parts[1], parts[2], parts[3]);
        } else {
            throw new IllegalArgumentException("Invalid CSV format: " + line);
        }
    }

    public static List<Pizza> getPizzaData(String filename) {
        List<Pizza> pizzas = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                pizzas.add(fromDelimitedString(line));
            }
            System.out.println("Pizza data received successfully from file: " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pizzas;
    }

    public static void printPizzaData(List<Pizza> pizzas) {
        for (Pizza pizza : pizzas) {
            System.out.println("Size: " + pizza.getSize() + ", SauceType: " + pizza.getSauceType() +
                    ", MainTopping: " + pizza.getMainTopping() + ", CrustThickness: " + pizza.getCrustThickness());
        }
    }
}
