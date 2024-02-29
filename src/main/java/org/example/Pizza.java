package org.example;

public class Pizza {
    private String size;
    private String sauceType;
    private String mainTopping;
    private String crustThickness;
    private String pizzaID;
    private String price;

    // Default constructor required for Gson serialization/deserialization
    public Pizza() {}

    // Parameterized constructor
    public Pizza(String pizzaID, String price, String size, String sauceType, String mainTopping, String crustThickness) {
        this.pizzaID = pizzaID;
        this.price = price;
        this.size = size;
        this.sauceType = sauceType;
        this.mainTopping = mainTopping;
        this.crustThickness = crustThickness;
    }

    // Getters
    public String getSize() { return size; }
    public String getSauceType() { return sauceType; }
    public String getMainTopping() { return mainTopping; }
    public String getCrustThickness() { return crustThickness; }
    public String getPizzaID() { return pizzaID; }
    public String getPrice() { return price; }

    // Setters
    public void setSize(String size) { this.size = size; }
    public void setSauceType(String sauceType) { this.sauceType = sauceType; }
    public void setMainTopping(String mainTopping) { this.mainTopping = mainTopping; }
    public void setCrustThickness(String crustThickness) { this.crustThickness = crustThickness; }
    public void setPizzaID(String pizzaID) { this.pizzaID = pizzaID; }
    public void setPrice(String price) { this.price = price; }

    // Method to display pizza details
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
