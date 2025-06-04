package org.example;

public final class ElectronicsProduct extends SealedProduct {
    public ElectronicsProduct(String name, double price) {
        super(name, price);
    }

    @Override
    public String category() {
        return "Electronics";
    }
}
