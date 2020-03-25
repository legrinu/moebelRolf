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

/**
 * Die Klasse "FileManager" stellt Methoden fuer die Verarbeitung der Dateiablage.
 */
public class FileManager {

    public File stockFile = new File(System.getProperty("user.dir") + File.separator + "stock.csv");
    public File areaFile = new File(System.getProperty("user.dir") + File.separator + "area.csv");
    public File categoryFile = new File(System.getProperty("user.dir") + File.separator + "category.csv");
    private FileReader fileReader;
    private BufferedReader bufferedReader;
    private FileWriter fileWriter;
    private BufferedWriter bufferedWriter;
    private HardwareStore hardwareStore = Main.getMainStore();

    /**
     * Die Methode ueberprueft ob die Datei pFile vorhanden ist, sonst wird diese erstellt.
     * @param pFile Datei zu ueberpruefen
     */
    private static void checkFile(File pFile){
        if(!pFile.exists()){
            try {
                pFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Die Methode speichert die ArrayList pInput in die Datei pOutput.
     * @param pInput Zuspeichern ArrayList
     * @param pOutput Datei, Speicherort
     */
    private void stringToFile(ArrayList<String> pInput, File pOutput){
        this.checkFile(pOutput);
        try {
            this.fileWriter = new FileWriter(pOutput);
            this.bufferedWriter = new BufferedWriter(this.fileWriter);

            //Every String write
            for(String stringFromInput : pInput) {
                this.bufferedWriter.write(stringFromInput);
            }

            this.bufferedWriter.flush();
            this.bufferedWriter.close();
            this.fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Die Methode liest die Datei pInput ein und gibt den Inhalt als ArrayList zurueck.
     * @param pInput Einzulesende Datei
     * @return Inhalt der Datei
     */
    private ArrayList<String> fileToString(File pInput){
        this.checkFile(pInput);
        ArrayList<String> output = new ArrayList<String>();
        try {
            this.fileReader = new FileReader(pInput);
            this.bufferedReader = new BufferedReader(this.fileReader);

            //Count lines in File
            int lines = 0;
            while(this.bufferedReader.readLine() != null){
                lines++;
            }
            this.bufferedReader.close();
            this.fileReader.close();

            this.fileReader = new FileReader(pInput);
            this.bufferedReader = new BufferedReader(this.fileReader);

            //Read all Lines
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

    /**
     * Die Methode schreibt alle Bereiche in eine Datei.
     */
    private void writeAreaFile(){
        ArrayList<Area> areaList = hardwareStore.getAreaList();
        ArrayList<String> areaToStringList = new ArrayList<>();
        //Every Area
        for(Area area : areaList){
            //Build String
            String areaToString = area.getAreaName() + ";" + area.getDiscount() + "\n";
            areaToStringList.add(areaToString);
        }

        this.stringToFile(areaToStringList, this.areaFile);
    }

    /**
     * Die Methode liest alle Bereiche aus einer Datei und speichert diese im Moebelmarkt.
     */
    private void readAreaFile(){
        ArrayList<Area> fromFile = new ArrayList<>();
        hardwareStore.setAreaList(null);
        //Every line in File
        for(String output : this.fileToString(this.areaFile)) {
            //Parse Input
            String[] stringFromFile = output.split(";");
            String name = stringFromFile[0];
            Double discount = Double.parseDouble(stringFromFile[1]);
            //Create Area and add
            Area areaFromFile = new Area(name, discount);
            fromFile.add(areaFromFile);
        }

        hardwareStore.setAreaList(fromFile);
    }

    //Category File Stuff

    /**
     * Die Methode schreibt alle Kategorien in eine Datei.
     */
    private void writeCategoryFile(){
        ArrayList<Category> categoryList = hardwareStore.getCategoryList();
        ArrayList<String> categoryToStringList = new ArrayList<>();
        //Every Category
        for(Category category : categoryList){
            //Build String
            String categoryToString = category.getCategoryName() + ";" + category.getDiscount() + "\n";
            categoryToStringList.add(categoryToString);
        }

        this.stringToFile(categoryToStringList, this.categoryFile);
    }

    /**
     * Die Methode liest alle Kategorien aus einer Datei und speichert diese im Moebelmarkt.
     */
    private void readCategoryFile(){
        ArrayList<Category> fromFile = new ArrayList<>();
        hardwareStore.setCategoryList(null);
        //Every line in File
        for(String output : this.fileToString(this.categoryFile)) {
            //Parse Input
            String[] stringFromFile = output.split(";");
            String name = stringFromFile[0];
            Double discount = Double.parseDouble(stringFromFile[1]);
            //Create Category and add
            Category categoryFromFile = new Category(name, discount);;
            fromFile.add(categoryFromFile);
        }
        hardwareStore.setCategoryList(fromFile);
    }

    //Stock File Stuff

    /**
     * Die Methode schreibt alle Moebelstuecke in eine Datei.
     */
    private void writeStockFile(){
        HashMap<Integer, Furniture> hardwareStoreMap = hardwareStore.getHardwareStoreMap();
        ArrayList<String> furnitureArray = new ArrayList<>();
        //Every Furniture
        for (Map.Entry<Integer, Furniture> entry : hardwareStoreMap.entrySet()) {
            Furniture furnitureValue = entry.getValue();
            //Build String
            String furnitureString = entry.getKey() + ";" + furnitureValue.getName() + ";" + furnitureValue.getArea().getAreaName()
                    + ";" + furnitureValue.getCategory().getCategoryName() + ";" + furnitureValue.getOriginalPrice()
                    + ";" + furnitureValue.getStock() + "\n";
            furnitureArray.add(furnitureString);
        }
        //Write
        this.stringToFile(furnitureArray, this.stockFile);
    }

    /**
     * Die Methode liest alle Moebelstuecke aus einer Datei und speichert diese im Moebelmarkt.
     */
    private void readStockFile(){
        HashMap<Integer, Furniture> newMap = new HashMap<>();
        hardwareStore.setHardwareStore(null);
        FileManager.checkFile(stockFile);
        ArrayList<String> fromFile = this.fileToString(this.stockFile);

        for(String output : fromFile){
            String[] buildString = output.split(";");

            int key = Integer.parseInt(buildString[0]);
            String name = buildString[1];
            Area area = this.hardwareStore.getAreaFromString(buildString[2]);
            Category category = this.hardwareStore.getCategoryFromString(buildString[3]);
            Double price = Double.parseDouble(buildString[4]);
            int stock = Integer.parseInt(buildString[5]);
            Furniture furnitureFromFile = new Furniture(name, area, category, price, stock);

            newMap.put(key, furnitureFromFile);
        }

        hardwareStore.setHardwareStore(newMap);
    }

    /**
     * Die Methode speichert den Moebelmarkt in Dateien.
     */
    public void saveFiles(){
        this.writeAreaFile();
        this.writeCategoryFile();
        this.writeStockFile();
    }

    /**
     * Die Methode liest den Moebelmarkt von Dateien.
     */
    public void readFromFiles(){
        this.readAreaFile();
        this.readCategoryFile();
        this.readStockFile();
    }

}
