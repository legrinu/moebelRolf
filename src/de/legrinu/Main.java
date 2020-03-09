package de.legrinu;

import de.legrinu.classes.Furniture;
import de.legrinu.datamanagement.FileManager;
import de.legrinu.gui.MainFrame;

public class Main {

    private static HardwareStore hardwareStore = new HardwareStore();

    public static void main(String[] args) {
        FileManager.readFromFiles();
        //MainFrame mainFrame = new MainFrame();
        //mainFrame.setVisible(true);//Hier alle auszuführenden Funktionen eintragen
        //FileManager.saveFiles();

        for(String string : hardwareStore.suggestionShoppingCartArray(600)){
            System.out.println(string);
        }
    }

    public static HardwareStore getHardwareStore() {
        return hardwareStore;
    }

    public static void setHardwareStore(HardwareStore hardwareStore) {
        Main.hardwareStore = hardwareStore;
    }
}
