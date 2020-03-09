package de.legrinu.datamanagement;

import de.legrinu.HardwareStore;
import de.legrinu.Main;

import java.io.File;
import java.io.IOException;

public class FileManager {

    private static File stock;
    private static File area;
    private static File category;
    private static HardwareStore hardwareStore = Main.getHardwareStore();

    public static File getStockFile(){
        return stock = new File(System.getProperty("user.dir") + File.separator + "stock.csv");
    }

    public static File getAreaFile(){
        return area = new File(System.getProperty("user.dir") + File.separator + "area.csv");
    }

    public static File getCategoryFile(){
        return area = new File(System.getProperty("user.dir") + File.separator + "category.csv");
    }

    public static void checkFile(File file){
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void saveFiles(){
        AreaFileEdit.saveAreaList();
        CategoryFileEdit.saveCategoryList();
        StockFileEdit.saveStockHashMap();
    }

    public static void readFromFiles(){
        AreaFileEdit.setAreaListFromFile();
        CategoryFileEdit.setCategoryListFromFile();
        StockFileEdit.setHardWareStoreFromFile();
    }

    public static HardwareStore getHardwareStore(){
        return hardwareStore;
    }

}
