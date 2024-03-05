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
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class PizzaFlatSend {

    /**
     * Converts a Pizza object into a delimited string suitable for CSV writing.
     *
     * @param pizza The Pizza object to be converted into a delimited string.
     * @return A string representing the Pizza object in a CSV-compatible format.
     */
    public static String toDelimitedString(Pizza pizza) {
        return String.format("%s,%s,%s,%s,%s,%s",
                pizza.getPizzaID(),
                pizza.getPrice(),
                pizza.getSize(),
                pizza.getSauceType(),
                pizza.getMainTopping(),
                pizza.getCrustThickness());
    }

    /**
     * Converts a JSON representation of a Pizza object into a Pizza object,
     * creates a delimited string from it, and appends this string as a new line
     * in a specified CSV file.
     *
     * @param pizzaJson The JSON string representation of a Pizza object.
     * @param filename The path to the CSV file where the pizza data will be written.
     */
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

