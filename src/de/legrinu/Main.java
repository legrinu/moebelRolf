package de.legrinu;

import de.legrinu.datamanagement.FileManager;
import de.legrinu.gui.MainFrameNew;

import java.util.ArrayList;

public class Main {

    private static HardwareStore mainStore = new HardwareStore("Main Store");
    private static FileManager fileManager = new FileManager();
    private static ArrayList<HardwareStore> hardwareStores = new ArrayList<>();

    public static void main(String[] args) {
        fileManager.readFromFiles();
        hardwareStores.add(mainStore);

        MainFrameNew mainFrameNew = new MainFrameNew();
        mainFrameNew.setVisible(true);
    }

    public static HardwareStore getMainStore() {
        return mainStore;
    }

    public static void setMainStore(HardwareStore mainStore) {
        Main.mainStore = mainStore;
    }

    public static FileManager getFileManager(){
        return fileManager;
    }

    public static HardwareStore getHardwareStoreByName(String pName){
        for(HardwareStore getStore : hardwareStores){
            if(getStore.getName().equalsIgnoreCase(pName)){
                return getStore;
            }
        }
        return null;
    }
}
