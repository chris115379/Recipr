package de.androidbytes.recipr.domain.model;

public class Ingredient {

    private long id;
    private String name;
    private String quantity;
    private String unit;

    public Ingredient(long id, String name, String quantity, String unit) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getUnit() {
        return unit;
    }
}
