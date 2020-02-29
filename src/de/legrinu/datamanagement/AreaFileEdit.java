package de.legrinu.datamanagement;

import de.legrinu.Main;
import de.legrinu.classes.Area;

import java.io.*;
import java.util.ArrayList;

/*
Aufbau der Datei:
Name;Discount
 */

public class AreaFileEdit {

    private static File areaFile = FileManager.getAreaFile();
    private static ArrayList<Area> areaList = Main.getAreaList();

    public static void saveAreaList(){
        ArrayList<String> areaToStringList = new ArrayList<>();

        FileManager.checkFile(areaFile);

        for(Area area : areaList){
            String areaToString = area.getAreaName() + ";" + area.getDiscount();
            areaToStringList.add(areaToString);
        }

        try {
            FileWriter area_writer = new FileWriter(areaFile);
            BufferedWriter buffered_area_writer = new BufferedWriter(area_writer);

            for(String area : areaToStringList){
                buffered_area_writer.write(area);
                buffered_area_writer.newLine();
            }

            buffered_area_writer.flush();
            buffered_area_writer.close();
            area_writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setAreaListFromFile(){
        ArrayList<Area> fromFile = new ArrayList<>();
        Main.setAreaList(null);

        FileManager.checkFile(areaFile);

        try {
            FileReader area_Reader = new FileReader(areaFile);
            BufferedReader buffered_area_reader = new BufferedReader(area_Reader);
            int lines = 0;

            while(buffered_area_reader.readLine() != null){
                lines++;
            }

            buffered_area_reader.close();
            area_Reader.close();
            area_Reader = new FileReader(areaFile);
            buffered_area_reader = new BufferedReader(area_Reader);

            for(int i = 0; i < lines; i++){
                String[] stringFromFile = buffered_area_reader.readLine().split(";");
                String name = stringFromFile[0];
                Double discount = Double.parseDouble(stringFromFile[1]);
                Area areaFromFile = null;
                if(discount == 0) {
                    areaFromFile = new Area(name);
                }else{
                    areaFromFile = new Area(name, discount);
                }

                fromFile.add(areaFromFile);
            }

            buffered_area_reader.close();
            area_Reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Main.setAreaList(fromFile);
    }

    public static Area getAreaFromString(String pName){
        for(Area areaFromList : Main.getAreaList()){
            if(areaFromList.getAreaName().contains(pName)){
                return areaFromList;
            }
        }
        return null;
    }
}
