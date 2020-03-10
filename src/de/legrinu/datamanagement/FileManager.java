package de.legrinu.datamanagement;

import de.legrinu.HardwareStore;
import de.legrinu.Main;
import de.legrinu.classes.Area;
import de.legrinu.classes.Category;
import de.legrinu.classes.Furniture;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FileManager {

    public File stockFile = new File(System.getProperty("user.dir") + File.separator + "stock.csv");
    public File areaFile = new File(System.getProperty("user.dir") + File.separator + "area.csv");
    public File categoryFile = new File(System.getProperty("user.dir") + File.separator + "category.csv");
    private FileReader fileReader;
    private BufferedReader bufferedReader;
    private FileWriter fileWriter;
    private BufferedWriter bufferedWriter;
    private HardwareStore hardwareStore = Main.getHardwareStore();

    public static void checkFile(File file){
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void stringToFile(ArrayList<String> input, File output){
        this.checkFile(output);
        try {
            this.fileWriter = new FileWriter(output);
            this.bufferedWriter = new BufferedWriter(this.fileWriter);

            for(String stringFromInput : input) {
                this.bufferedWriter.write(stringFromInput);
            }

            this.bufferedWriter.flush();
            this.bufferedWriter.close();
            this.fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<String> fileToString(File input){
        this.checkFile(input);
        ArrayList<String> output = new ArrayList<String>();
        try {
            this.fileReader = new FileReader(input);
            this.bufferedReader = new BufferedReader(this.fileReader);

            int lines = 0;
            while(this.bufferedReader.readLine() != null){
                lines++;
            }
            this.bufferedReader.close();
            this.fileReader.close();

            this.fileReader = new FileReader(input);
            this.bufferedReader = new BufferedReader(this.fileReader);

            for(int i = 0; i < lines; i++){
                output.add(this.bufferedReader.readLine());
            }

            this.bufferedReader.close();
            this.fileReader.close();

            return output;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    //Area File Stuff

    private void writeAreaFile(){
        ArrayList<Area> areaList = hardwareStore.getAreaList();
        ArrayList<String> areaToStringList = new ArrayList<>();

        for(Area area : areaList){
            String areaToString = area.getAreaName() + ";" + area.getDiscount();
            areaToStringList.add(areaToString);
        }

        this.stringToFile(areaToStringList, this.areaFile);
    }

    private void readAreaFile(){
        ArrayList<Area> fromFile = new ArrayList<>();
        hardwareStore.setAreaList(null);

        for(String output : this.fileToString(this.areaFile)) {
            String[] stringFromFile = output.split(";");
            String name = stringFromFile[0];
            Double discount = Double.parseDouble(stringFromFile[1]);
            Area areaFromFile = null;
            if (discount == 0) {
                areaFromFile = new Area(name);
            } else {
                areaFromFile = new Area(name, discount);
            }

            fromFile.add(areaFromFile);
        }

        hardwareStore.setAreaList(fromFile);
    }

    private Area getAreaFromString(String pName){
        for(Area areaFromList : hardwareStore.getAreaList()){
            if(areaFromList.getAreaName().contains(pName)){
                return areaFromList;
            }
        }
        return null;
    }

    //Category File Stuff

    private void writeCategoryFile(){
        ArrayList<Category> categoryList = hardwareStore.getCategoryList();
        ArrayList<String> categoryToStringList = new ArrayList<>();

        for(Category category : categoryList){
            String categoryToString = category.getCategoryName() + ";" + category.getDiscount();
            categoryToStringList.add(categoryToString);
        }

        this.stringToFile(categoryToStringList, this.categoryFile);
    }

    private void readCategoryFile(){
        ArrayList<Category> fromFile = new ArrayList<>();
        hardwareStore.setCategoryList(null);

        for(String output : this.fileToString(this.categoryFile)) {
            String[] stringFromFile = output.split(";");
            String name = stringFromFile[0];
            Double discount = Double.parseDouble(stringFromFile[1]);
            Category categoryFromFile = null;
            if (discount == 0) {
                categoryFromFile = new Category(name);
            } else {
                categoryFromFile = new Category(name, discount);
            }

            fromFile.add(categoryFromFile);
        }
        hardwareStore.setCategoryList(fromFile);
    }

    private Category getCategoryFromString(String pName){
        for(Category categoryFromList : hardwareStore.getCategoryList()){
            if(categoryFromList.getCategoryName().contains(pName)){
                return categoryFromList;
            }
        }
        return null;
    }

    //Stock File Stuff

    private void writeStockFile(){
        HashMap<Integer, Furniture> hardwareStoreMap = hardwareStore.getHardwareStoreMap();
        ArrayList<String> furnitureArray = new ArrayList<>();

        for (Map.Entry<Integer, Furniture> entry : hardwareStoreMap.entrySet()) {
            Furniture furnitureValue = entry.getValue();
            String furnitureString = entry.getKey() + ";" + furnitureValue.getName() + ";" + furnitureValue.getArea().getAreaName()
                    + ";" + furnitureValue.getCategory().getCategoryName() + ";" + furnitureValue.getOriginalPrice()
                    + ";" + furnitureValue.getStock();
            furnitureArray.add(furnitureString);
        }

        this.stringToFile(furnitureArray, this.stockFile);
    }

    private void readStockFile(){
        HashMap<Integer, Furniture> newMap = new HashMap<>();
        hardwareStore.setHardwareStore(null);
        FileManager.checkFile(stockFile);
        ArrayList<String> fromFile = this.fileToString(this.stockFile);

        for(String output : fromFile){
            String[] buildString = output.split(";");

            int key = Integer.parseInt(buildString[0]);
            String name = buildString[1];
            Area area = this.getAreaFromString(buildString[2]);
            Category category = this.getCategoryFromString(buildString[3]);
            Double price = Double.parseDouble(buildString[4]);
            int stock = Integer.parseInt(buildString[5]);
            Furniture furnitureFromFile = new Furniture(name, area, category, price, stock);

            newMap.put(key, furnitureFromFile);
        }

        hardwareStore.setHardwareStore(newMap);
    }

    public void saveFiles(){
        this.writeAreaFile();
        this.writeCategoryFile();
        this.writeStockFile();
    }

    public void readFromFiles(){
        this.readAreaFile();
        this.readCategoryFile();
        this.readStockFile();
    }

}
