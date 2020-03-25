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

    /**
     * Diese Methdoe gibt den Moebelmarkt zurueck.
     * @return Moebelmarkt
     */
    public static HardwareStore getMainStore() {
        return mainStore;
    }

    /**
     * Diese Methode uebernimmt den Moebelmarkt.
     * @param pMainStore Moebelmarkt, der uebernommen werden soll
     */
    public static void setMainStore(HardwareStore pMainStore) {
        Main.mainStore = pMainStore;
    }

    /**
     * Diese Methode gibt den FileManager zurueck.
     * @return FileManager
     */
    public static FileManager getFileManager(){
        return fileManager;
    }

    /**
     * Diese Methode gibt eine Referenz eines Moebelmarktes, der den Namen pName traegt.
     * @param pName Name des Moebelmarkt
     * @return Moebelmarkt mit dem Namen pName
     */
    public static HardwareStore getHardwareStoreByName(String pName){
        //Every HardwareStore
        for(HardwareStore getStore : hardwareStores){
            //If Name equals pName
            if(getStore.getName().equalsIgnoreCase(pName)){
                return getStore;
            }
        }
        return null;
    }
}
