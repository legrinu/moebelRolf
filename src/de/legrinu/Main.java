package de.legrinu;

import de.legrinu.classes.Area;
import de.legrinu.classes.Category;
import de.legrinu.classes.Furniture;

import java.util.HashMap;

public class Main {

    HashMap<Integer, Furniture> hardwareStore = new HashMap<>();

    public static void main(String[] args) {
        Furniture kitchen_Chair = new Furniture("Küchenstuhl", new Area("Küche"),
                new Category("Sitzmöbel", 0.1), 49.99, 5);

        System.out.println(kitchen_Chair.getOriginalPrice());
        System.out.println(kitchen_Chair.getDiscountPrice());
        System.out.println(kitchen_Chair.getOriginalStockPrice());
        kitchen_Chair.setStock(kitchen_Chair.getStock() - 2);
        System.out.println(kitchen_Chair.getStock());
        System.out.println(kitchen_Chair.getOriginalStockPrice());
    }
}
