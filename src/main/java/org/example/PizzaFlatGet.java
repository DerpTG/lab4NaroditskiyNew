/** Project: Lab 4 Systems Integration Pizza Shop
 * Purpose Details: System Integration Using Flat Files, RabbitMQ, and Web Service/JSON
 * Course: IST 242
 * Author: Felix Naroditskiy
 * Date Developed: 2/21/2024
 * Last Date Changed: 3/4/2024
 * Rev: 1.0

 */

package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PizzaFlatGet {

    /**
     * Converts a single line from a CSV file into a Pizza object.
     * The expected format is: PizzaID,Price,Size,SauceType,MainTopping,CrustThickness.
     * @param line A string representing a line from a CSV file.
     * @return A Pizza object with attributes set from the CSV line.
     * @throws IllegalArgumentException If the CSV line does not have exactly six parts.
     */
    public static Pizza fromDelimitedString(String line) {
        String[] parts = line.split(",");
        if (parts.length == 6) {
            return new Pizza(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]);
        } else {
            throw new IllegalArgumentException("Invalid CSV format: " + line);
        }
    }

    /**
     * Reads pizza data from a specified CSV file and converts each line into a Pizza object.
     * This method assumes that each line in the file represents one pizza entry in the expected format.
     * @param filename The path to the CSV file containing pizza data.
     * @return A list of Pizza objects created from the CSV file data.
     */
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

    /**
     * Prints the details of each Pizza object in a list to the standard output.
     *
     * @param pizzas A list of Pizza objects to be printed.
     */
    public static void printPizzaData(List<Pizza> pizzas) {
        for (Pizza pizza : pizzas) {
            System.out.println("PizzaID: " + pizza.getPizzaID() +", Price: " + pizza.getPrice() +", Size: " + pizza.getSize() + ", SauceType: " + pizza.getSauceType() +
                    ", MainTopping: " + pizza.getMainTopping() + ", CrustThickness: " + pizza.getCrustThickness());
        }
    }
}
