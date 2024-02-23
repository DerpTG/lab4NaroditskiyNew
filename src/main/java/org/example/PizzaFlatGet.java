package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PizzaFlatGet {
    public static List<String> getPizzaData(String filename) {
        List<String> pizzasJson = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                pizzasJson.add(line);
            }
            System.out.println("Pizza data received successfully from file: " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pizzasJson;
    }

    public static void printPizzaData(List<String> pizzasJson) {
        for (String pizzaJson : pizzasJson) {
            System.out.println(pizzaJson);
        }
    }
}
