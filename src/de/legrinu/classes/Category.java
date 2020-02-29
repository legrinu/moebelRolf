package de.legrinu.classes;

public class Category extends Groups{

    private String category;

    public Category(String pCategory) {
        this.category = pCategory;
    }

    public Category(String pCategory, double pDiscount){
        super(pDiscount);
        this.category = pCategory;
    }

    public String getCategoryName() {
        return category;
    }

    public void setCategoryName(String category) {
        this.category = category;
    }
}
