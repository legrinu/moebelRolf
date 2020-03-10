package de.legrinu;

import de.legrinu.classes.Furniture;
import de.legrinu.datamanagement.FileManager;
import de.legrinu.gui.MainFrame;
import de.legrinu.gui.MainFrameNew;

public class Main {

    private static HardwareStore hardwareStore = new HardwareStore();
    private static FileManager fileManager = new FileManager();

    public static void main(String[] args) {
        fileManager.readFromFiles();
        //MainFrame mainFrame = new MainFrame();
        //mainFrame.setVisible(true);//Hier alle auszuführenden Funktionen eintragen
        //FileManager.saveFiles();

       /* for(String string : hardwareStore.suggestionShoppingCartArray(499.99)){
            System.out.println(string);
        } */

        MainFrameNew mainFrameNew = new MainFrameNew();
        mainFrameNew.setVisible(true);
    }

    public static HardwareStore getHardwareStore() {
        return hardwareStore;
    }

    public static void setHardwareStore(HardwareStore hardwareStore) {
        Main.hardwareStore = hardwareStore;
    }

    public static FileManager getFileManager(){
        return fileManager;
    }
}
