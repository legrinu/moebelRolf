package de.legrinu;

import de.legrinu.classes.Area;
import de.legrinu.classes.Category;
import de.legrinu.classes.Furniture;
import de.legrinu.datamanagement.FileManager;
import de.legrinu.gui.MainFrame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {

    static HashMap<Integer, Furniture> hardwareStore = new HashMap<>();
    static ArrayList<Area> areaList = new ArrayList<>();
    static ArrayList<Category> categoryList = new ArrayList<>();
    private static MainFrame mainFrame;

    public static void main(String[] args) {
        //FileManager.readFromFiles();
        debugSpaß();
        mainFrame = new MainFrame();
        mainFrame.setVisible(true);//Hier alle auszuführenden Funktionen eintragen
        //FileManager.saveFiles();
    }

    static void debugSpaß(){ //Super duper DebugSPAß
        Area kitchen = new Area("Kueche");
        Category sitzmöbel = new Category("Sitzmoebel", 0.1);

        Area baum = new Area("baum");
        Category hund = new Category("hund");

        Furniture kitchen_Chair = new Furniture("KitchenChair", baum,  //Debugspaß
                hund, 49.99, 5);
        Furniture basinga = new Furniture("basinga", kitchen,  //Debugspaß
                sitzmöbel, 99.99, 2);
        Furniture bull = new Furniture("bull", kitchen,  //Debugspaß
                sitzmöbel, 9.99, 5);
        Furniture lul = new Furniture("lul", kitchen,  //Debugspaß
                sitzmöbel, 44.99, 5);

        hardwareStore.put(1, kitchen_Chair);
        hardwareStore.put(2, basinga);
        hardwareStore.put(3, bull);
        hardwareStore.put(4, lul);

        areaList.add(kitchen);
        areaList.add(baum);

        categoryList.add(hund);
        categoryList.add(sitzmöbel);


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

    public static HashMap<Integer, Furniture> getHardwareStore() {
        return hardwareStore;
    }

    public static void setHardwareStore(HashMap<Integer, Furniture> hardwareStore) {
        Main.hardwareStore = hardwareStore;
    }

    public static ArrayList<Area> getAreaList() {
        return areaList;
    }

    public static void setAreaList(ArrayList<Area> areaList) {
        Main.areaList = areaList;
    }

    public static ArrayList<Category> getCategoryList() {
        return categoryList;
    }

    public static void setCategoryList(ArrayList<Category> categoryList) {
        Main.categoryList = categoryList;
    }

    public static MainFrame getMainFrame() {
        return mainFrame;
    }

    public static void setMainFrame(MainFrame mainFrame) {
        Main.mainFrame = mainFrame;
    }
}
