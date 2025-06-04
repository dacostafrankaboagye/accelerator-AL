package org.example;

public final class ClothingProduct extends SealedProduct {
    public ClothingProduct(String name, double price) {
        super(name, price);
    }

    @Override
    public String category() {
        return "Clothing";
    }
}