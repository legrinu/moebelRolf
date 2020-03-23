package de.legrinu.classes;

import de.legrinu.Utils.MathUtils;

public class Furniture implements Comparable{

    private String name;
    private Area area;
    private Category category;
    private double originalPrice;
    private int stock;

    /**
     *
     * @param pName - The Name of the Furniture
     * @param pArea - The Area of the Furniture (For the program to work this area needs to be added in the arraylist)
     * @param pCategory - The Category of the Furniture (For the program to work this category needs to be added in the arraylist)
     * @param pOriginalPrice - The Original Price (Without Discount) of the Furniture
     * @param pStock - The Stock of the Furniture
     */
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

    /**
     * Returns the original price times the highest discount
     * @return
     */
    public double getDiscountPrice(){
        double discountPrice = this.getOriginalPrice();

        if(this.getArea().getDiscount() >= this.getCategory().getDiscount()){
            discountPrice = discountPrice * (1 - this.getArea().getDiscount());
        }else if(this.getArea().getDiscount() < this.getCategory().getDiscount()){
            discountPrice = discountPrice * (1 - this.getCategory().getDiscount());
        }

        double round = MathUtils.round(discountPrice, 2);
        return round;
    }

    public double getDiscountStockPrice(){
        double discountStockPrice = this.getDiscountPrice() * this.getStock();
        double round = MathUtils.round(discountStockPrice, 2);
        return round;
    }

    public double getOriginalStockPrice(){
        double stockPrice = this.getOriginalPrice() * this.getStock();
        double round = MathUtils.round(stockPrice, 2);
        return round;
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

    public String getName() {
        return name;
    }

    //Used for sorting
    @Override
    public int compareTo(Object o) {
        Furniture other = (Furniture) o;
        return Double.compare(this.getDiscountPrice(),other.getDiscountPrice());
    }
}
