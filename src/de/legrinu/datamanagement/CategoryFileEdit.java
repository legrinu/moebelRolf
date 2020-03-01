package de.legrinu.datamanagement;

import de.legrinu.Main;
import de.legrinu.classes.Area;
import de.legrinu.classes.Category;

import java.io.*;
import java.util.ArrayList;

public class CategoryFileEdit {

    private static File categoryFile = FileManager.getCategoryFile();
    private static ArrayList<Category> categoryList;

    public static void saveCategoryList(){
        categoryList = Main.getCategoryList();
        ArrayList<String> categoryToStringList = new ArrayList<>();
        FileManager.checkFile(categoryFile);

        for(Category category : categoryList){
            String categoryToString = category.getCategoryName() + ";" + category.getDiscount();
            categoryToStringList.add(categoryToString);
        }

        try {
            FileWriter category_writer = new FileWriter(categoryFile);
            BufferedWriter buffered_category_writer = new BufferedWriter(category_writer);

            for(String category : categoryToStringList){
                buffered_category_writer.write(category);
                buffered_category_writer.newLine();
            }

            buffered_category_writer.flush();
            buffered_category_writer.close();
            category_writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setCategoryListFromFile(){
        ArrayList<Category> fromFile = new ArrayList<>();
        Main.setCategoryList(null);
        FileManager.checkFile(categoryFile);

        try {
            FileReader category_reader = new FileReader(categoryFile);
            BufferedReader buffered_category_reader = new BufferedReader(category_reader);
            int lines = 0;

            while(buffered_category_reader.readLine() != null){
                lines++;
            }

            buffered_category_reader.close();
            category_reader.close();
            category_reader = new FileReader(categoryFile);
            buffered_category_reader = new BufferedReader(category_reader);

            for(int i = 0; i < lines; i++){
                String[] stringFromFile = buffered_category_reader.readLine().split(";");
                String name = stringFromFile[0];
                Double discount = Double.parseDouble(stringFromFile[1]);
                Category categoryFromFile = null;
                if(discount == 0) {
                    categoryFromFile = new Category(name);
                }else{
                    categoryFromFile = new Category(name, discount);
                }
                fromFile.add(categoryFromFile);
            }

            buffered_category_reader.close();
            category_reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Main.setCategoryList(fromFile);
    }

    public static Category getCategoryFromString(String pName){
        for(Category categoryFromList : Main.getCategoryList()){
            if(categoryFromList.getCategoryName().contains(pName)){
                return categoryFromList;
            }
        }
        return null;
    }
}
