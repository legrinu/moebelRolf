package de.legrinu.classes;

public class Category extends Groups{

    String category;

    public Category(String pCategory) {
        this.category = pCategory;
    }

    public Category(String pCategory, double pDiscount){
        super(pDiscount);
        this.category = pCategory;
    }
}
