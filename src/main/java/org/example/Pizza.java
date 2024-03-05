/** Project: Lab 4 Systems Integration Pizza Shop
 * Purpose Details: System Integration Using Flat Files, RabbitMQ, and Web Service/JSON
 * Course: IST 242
 * Author: Felix Naroditskiy
 * Date Developed: 2/21/2024
 * Last Date Changed: 3/4/2024
 * Rev: 1.0

 */

package org.example;

public class Pizza {
    private String size;
    private String sauceType;
    private String mainTopping;
    private String crustThickness;
    private String pizzaID;
    private String price;

    /**
     * Default constructor required for Gson serialization/deserialization.
     */
    public Pizza() {}

    /**
     * Constructs a new Pizza instance with specified attributes.
     *
     * @param pizzaID The unique identifier for the pizza.
     * @param price The price of the pizza.
     * @param size The size of the pizza.
     * @param sauceType The type of sauce used on the pizza.
     * @param mainTopping The main topping on the pizza.
     * @param crustThickness The thickness of the pizza's crust.
     */
    public Pizza(String pizzaID, String price, String size, String sauceType, String mainTopping, String crustThickness) {
        this.pizzaID = pizzaID;
        this.price = price;
        this.size = size;
        this.sauceType = sauceType;
        this.mainTopping = mainTopping;
        this.crustThickness = crustThickness;
    }

    // Getters and Setters for each field with @return tag for getters and @param tag for setters.

    /**
     * Returns the size of the pizza.
     * @return The size of the pizza.
     */
    public String getSize() { return size; }

    /**
     * Returns the type of sauce on the pizza.
     * @return The sauce type of the pizza.
     */
    public String getSauceType() { return sauceType; }

    /**
     * Returns the main topping of the pizza.
     * @return The main topping on the pizza.
     */
    public String getMainTopping() { return mainTopping; }

    /**
     * Returns the crust thickness of the pizza.
     * @return The crust thickness of the pizza.
     */
    public String getCrustThickness() { return crustThickness; }

    /**
     * Returns the ID of the pizza.
     * @return The unique identifier of the pizza.
     */
    public String getPizzaID() { return pizzaID; }

    /**
     * Returns the price of the pizza.
     * @return The price of the pizza.
     */
    public String getPrice() { return price; }

    /**
     * Updates the size of the pizza.
     * @param size The new size of the pizza.
     */
    public void setSize(String size) { this.size = size; }

    /**
     * Updates the sauce type of the pizza.
     * @param sauceType The new sauce type of the pizza.
     */
    public void setSauceType(String sauceType) { this.sauceType = sauceType; }

    /**
     * Updates the main topping of the pizza.
     * @param mainTopping The new main topping of the pizza.
     */
    public void setMainTopping(String mainTopping) { this.mainTopping = mainTopping; }

    /**
     * Updates the crust thickness of the pizza.
     * @param crustThickness The new crust thickness of the pizza.
     */
    public void setCrustThickness(String crustThickness) { this.crustThickness = crustThickness; }

    /**
     * Updates the ID of the pizza.
     * @param pizzaID The new unique identifier of the pizza.
     */
    public void setPizzaID(String pizzaID) { this.pizzaID = pizzaID; }

    /**
     * Updates the price of the pizza.
     * @param price The new price of the pizza.
     */
    public void setPrice(String price) { this.price = price; }


    /**
     * Displays the details of the pizza, including its ID, size, sauce type, main topping,
     * crust thickness, and price.
     */
    public void displayPizzaDetails() {
        System.out.println("Pizza Details:");
        System.out.println("ID: " + pizzaID);
        System.out.println("Size: " + size);
        System.out.println("Sauce Type: " + sauceType);
        System.out.println("Main Topping: " + mainTopping);
        System.out.println("Crust Thickness: " + crustThickness);
        System.out.println("Price: " + price);
    }
}
