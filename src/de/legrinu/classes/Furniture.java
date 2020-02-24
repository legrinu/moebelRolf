package de.legrinu.classes;

public class Furniture {

    private String name;
    private Area area;
    private Category category;
    private double originalPrice;
    private int stock;

    public Furniture(String pName, Area pArea, Category pCategory, double pOriginalPrice, int pStock){
        this.name = pName;
        this.area = pArea;
        this.category = pCategory;
        this.originalPrice = pOriginalPrice;
        this.stock = pStock;
    }

    public Area getArea(){
        return this.area;
    }

    public Category getCategory(){
        return this.category;
    }

    public double getOriginalPrice(){
        return this.originalPrice;
    }

    public double getDiscountPrice(){
        return this.originalPrice * (1 - this.getArea().getDiscount()) * (1 - this.getCategory().getDiscount());
        //TODO:Gelten beide Rabatte oder nur der größere?
    }

    public double getDiscountStockPrice(){
        return this.getDiscountPrice() * this.getStock();
    }

    public double getOriginalStockPrice(){
        return this.getOriginalPrice() * this.getStock();
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setOriginalPrice(double price) {
        this.originalPrice = price;
    }
}
