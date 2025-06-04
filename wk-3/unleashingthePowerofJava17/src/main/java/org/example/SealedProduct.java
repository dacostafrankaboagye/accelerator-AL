package org.example;

public sealed abstract class SealedProduct permits ElectronicsProduct, ClothingProduct {
    private final String name;
    private final double price;

    public SealedProduct(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String name() {
        return name;
    }

    public double price() {
        return price;
    }

    public abstract String category();
}
