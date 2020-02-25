package de.legrinu;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import de.legrinu.classes.Area;
import de.legrinu.classes.Category;
import de.legrinu.classes.Furniture;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {

    static HashMap<Integer, Furniture> hardwareStore = new HashMap<>();
    static ArrayList<Area> areaList = new ArrayList<>();
    static ArrayList<Category> categoryList = new ArrayList<>();

    public static void main(String[] args) { //SUper duper DebugSPAß
        Furniture kitchen_Chair = new Furniture("Küchenstuhl", new Area("Küche"),  //Debugspaß
                new Category("Sitzmöbel", 0.1), 49.99, 5);
        Furniture basinga = new Furniture("Küchenstuhl", new Area("Küche"),  //Debugspaß
                new Category("Sitzmöbel", 0.1), 99.99, 2);
        Furniture bull = new Furniture("Küchenstuhl", new Area("Küche"),  //Debugspaß
                new Category("Sitzmöbel", 0.1), 9.99, 5);
        Furniture lul = new Furniture("Küchenstuhl", new Area("Küche"),  //Debugspaß
                new Category("Sitzmöbel", 0.1), 44.99, 5);

        hardwareStore.put(1, kitchen_Chair);
        hardwareStore.put(2, basinga);
        hardwareStore.put(3, bull);
        hardwareStore.put(4, lul);

        System.out.println(readJSON());

        for(Map.Entry<Integer, Furniture> entry : hardwareStore.entrySet()){
            Furniture furniture = entry.getValue();
            System.out.println(furniture.getName());
        }


 /*       System.out.println(kitchen_Chair.getOriginalPrice());
        System.out.println(kitchen_Chair.getDiscountPrice());
        System.out.println(kitchen_Chair.getOriginalStockPrice());
        kitchen_Chair.setStock(kitchen_Chair.getStock() - 2);
        System.out.println(kitchen_Chair.getStock());
        System.out.println(kitchen_Chair.getOriginalStockPrice()); */
    }

    public static double totalStockPrice(){
        double totalPrice = 0;
        for(Map.Entry<Integer, Furniture> entry : hardwareStore.entrySet()){
            Furniture furniture = entry.getValue();
            totalPrice += furniture.getDiscountStockPrice();
        }
        return totalPrice;
    }

    public static double totalAreaPrice(Area pArea){
        double totalAreaPrice = 0;
        for(Map.Entry<Integer, Furniture> entry : hardwareStore.entrySet()){
            Furniture furniture = entry.getValue();
            if(furniture.getArea().equals(pArea)){
                totalAreaPrice += furniture.getDiscountStockPrice();
            }
        }
        return totalAreaPrice;
    }

    public static double totalCategoryPrice(Category pCategory){
        double totalCategoryPrice = 0;
        for(Map.Entry<Integer, Furniture> entry : hardwareStore.entrySet()){
            Furniture furniture = entry.getValue();
            if(furniture.getCategory().equals(pCategory)){
                totalCategoryPrice += furniture.getDiscountStockPrice();
            }
        }
        return totalCategoryPrice;
    }

    public static Area areaHighestTotalPrice(){
        for(Map.Entry<Integer, Furniture> entry : hardwareStore.entrySet()) {
            Furniture furniture = entry.getValue();
            furniture.getArea().setTotalPrice(furniture.getArea().getTotalPrice() + furniture.getDiscountStockPrice());
        }

        Area totalHighestPrice = null;
        for(Area area : areaList){
            if(area.getTotalPrice() > totalHighestPrice.getTotalPrice() || totalHighestPrice == null){
                totalHighestPrice = area;
            }
        }
        return  totalHighestPrice;
    }

    public static double listForAmount(double pAmount) {
        double workAmount = pAmount;

        double returner = 0;

        for (Map.Entry<Integer, Furniture> entry : hardwareStore.entrySet()) {
            Furniture furniturePre = entry.getValue();
            double stock = 0;
            while (workAmount >= 0) {
                stock += furniturePre.getDiscountPrice();
                workAmount -= furniturePre.getDiscountPrice();
            }
            workAmount = pAmount;
            double stockPricePre = stock;

            for (Map.Entry<Integer, Furniture> e : hardwareStore.entrySet()) {
                Furniture furniturePost = e.getValue();
                stock -= furniturePost.getDiscountPrice();
                stock += furniturePost.getDiscountPrice();

                if (stockPricePre > stock) {
                    stock = stockPricePre;
                }
            }

            if(returner < stock){
                returner = stock;
            }

        }
        return returner;
    }

    public static void generateAreaList(){
        areaList.add(new Area("Kitchen"));
        areaList.add(new Area("Living"));
        areaList.add(new Area("Spleeping"));
        areaList.add(new Area("ETC"));
    }

    public static boolean writeJSON(){

        File stock = new File(System.getProperty("user.dir") + File.separator + "stock.json");

        if(!stock.exists()){
            try {
                stock.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            Gson gson = new Gson();
            gson.toJson(hardwareStore, new FileWriter(stock));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean readJSON(){
        File stock = new File(System.getProperty("user.dir") + File.separator + "stock.json");

        if(!stock.exists()){
            try {
                stock.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            Gson gson = new Gson();
            Furniture fur = gson.fromJson(new FileReader(stock), Furniture.class);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

}
