package de.legrinu.datamanagement;

import de.legrinu.Main;
import de.legrinu.classes.Area;
import de.legrinu.classes.Category;
import de.legrinu.classes.Furniture;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/*
Design der Datei:
Nummer;Name;Area;Category;Price;Stock
 */

public class StockFileEdit {

    private static File stockFile = FileManager.getStockFile();
    private static HashMap<Integer, Furniture> hardwareStore = Main.getHardwareStore();

    public static void saveStockHashMap(){
        ArrayList<String> furnitureArray = new ArrayList<>();
        FileManager.checkFile(stockFile);

        for (Map.Entry<Integer, Furniture> entry : hardwareStore.entrySet()) {
            Furniture furnitureValue = entry.getValue();
            String furnitureString = entry.getKey() + ";" + furnitureValue.getName() + ";" + furnitureValue.getArea().getAreaName()
                    + ";" + furnitureValue.getCategory().getCategoryName() + ";" + furnitureValue.getOriginalPrice()
                    + ";" + furnitureValue.getStock();
            furnitureArray.add(furnitureString);
        }

        try {
            FileWriter stock_writer = new FileWriter(stockFile);
            BufferedWriter stock_buffered_writer = new BufferedWriter(stock_writer);

            for(String furniture : furnitureArray){
                stock_buffered_writer.write(furniture);
                stock_buffered_writer.newLine();
            }

            stock_buffered_writer.flush();
            stock_buffered_writer.close();
            stock_writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setHardWareStoreFromFile(){
        HashMap<Integer, Furniture> fromFile = new HashMap<>();
        Main.setHardwareStore(null);
        FileManager.checkFile(stockFile);

        try {
            FileReader stock_reader = new FileReader(stockFile);
            BufferedReader buffered_stock_reader = new BufferedReader(stock_reader);
            int lines = 0;

            while(buffered_stock_reader.readLine() != null){
                lines++;
            }

            buffered_stock_reader.close();
            stock_reader.close();
            stock_reader = new FileReader(stockFile);
            buffered_stock_reader = new BufferedReader(stock_reader);

            while(lines != 0){
                lines--;
                String[] stringFromFile = buffered_stock_reader.readLine().split(";");
                int key = Integer.parseInt(stringFromFile[0]);
                String name = stringFromFile[1];
                Area area = AreaFileEdit.getAreaFromString(stringFromFile[2]);
                Category category = CategoryFileEdit.getCategoryFromString(stringFromFile[3]);
                Double price = Double.parseDouble(stringFromFile[4]);
                int stock = Integer.parseInt(stringFromFile[5]);
                Furniture furnitureFromFile = new Furniture(name, area, category, price, stock);

                fromFile.put(key, furnitureFromFile);
            }

            buffered_stock_reader.close();
            stock_reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Main.setHardwareStore(fromFile);
    }
}
