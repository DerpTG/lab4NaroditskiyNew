package org.example;

public class Pizza {
    private String size;
    private String sauceType;
    private String mainTopping;
    private String crustThickness;
    // You can add more fields as needed

    public Pizza() {
        // Default constructor required for Gson serialization/deserialization
    }

    public Pizza(String size, String sauceType, String mainTopping, String crustThickness) {
        this.size = size;
        this.sauceType = sauceType;
        this.mainTopping = mainTopping;
        this.crustThickness = crustThickness;
    }

    // Getter and setter methods for all fields
    public String getSize() {return size;}
    public void setSize(String size) {this.size = size;}
    public String getSauceType() {return sauceType;}
    public void setSauceType(String sauceType) {this.sauceType = sauceType;}
    public String getMainTopping() {return mainTopping;}
    public void setMainTopping(String mainTopping) {this.mainTopping = mainTopping;}
    public String getCrustThickness() {return crustThickness;}
    public void setCrustThickness(String crustThickness) {this.crustThickness = crustThickness;}

    // Method to display pizza details
    public void displayPizzaDetails() {
        System.out.println("Pizza Details:");
        System.out.println("Size: " + size);
        System.out.println("Sauce Type: " + sauceType);
        System.out.println("Main Topping: " + mainTopping);
        System.out.println("Crust Thickness: " + crustThickness);
    }
}
