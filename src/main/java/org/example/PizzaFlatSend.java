package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class PizzaFlatSend {
    public static void sendPizzaData(String pizzaJson, String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println(pizzaJson);
            System.out.println("Pizza data sent successfully to file: " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
