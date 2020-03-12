package de.legrinu;

import de.legrinu.datamanagement.FileManager;
import de.legrinu.gui.MainFrameNew;

public class Main {

    private static HardwareStore hardwareStore = new HardwareStore();
    private static FileManager fileManager = new FileManager();

    public static void main(String[] args) {
        fileManager.readFromFiles();

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
