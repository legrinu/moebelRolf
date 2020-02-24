package de.legrinu;

import de.legrinu.classes.Area;
import de.legrinu.classes.Category;
import de.legrinu.classes.Furniture;

import java.util.HashMap;

public class Main {

    HashMap<Integer, Furniture> hardwareStore = new HashMap<>();

    public static void main(String[] args) {
        Furniture kitchenChair = new Furniture("Küchenstuhl", new Area("Küche"),
                new Category("Sitzmöbel", 0.1), 49.99, 5);

        System.out.println(kitchenChair.getOriginalPrice());
        System.out.println(kitchenChair.getDiscountPrice());
        System.out.println(kitchenChair.getOriginalStockPrice());
        kitchenChair.setStock(kitchenChair.getStock() - 2);
        System.out.println(kitchenChair.getStock());
        System.out.println(kitchenChair.getOriginalStockPrice());
    }
}
