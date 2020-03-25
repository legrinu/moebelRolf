package de.legrinu.classes;

import de.legrinu.Utils.MathUtils;

/**
 * Die Klasse "Furniture" modelliert einen Moebelstueck.
 */
public class Furniture{

    private String name;
    private Area area;
    private Category category;
    private double originalPrice;
    private int stock;

    /**
     *
     * @param pName Name des Moebelstuekes
     * @param pArea Bereich des Moebelstuekes
     * @param pCategory Kategorie des Moebelstuekes
     * @param pOriginalPrice Preis des Moebelstuekes ohne Rabatt
     * @param pStock Lagerbestand des Moebelstuekes
     */
    public Furniture(String pName, Area pArea, Category pCategory, double pOriginalPrice, int pStock){
        this.name = pName;
        this.area = pArea;
        this.category = pCategory;
        this.originalPrice = pOriginalPrice;
        this.stock = pStock;
    }

    /**
     * Es wird eine Referenz auf den Namen des Moebelstuekes zurueckgegeben.
     * @return Name des Moebelstuekes
     */
    public String getName() {
        return this.name;
    }

    /**
     * Es wird eine Referenz auf den Bereich des Moebelstuekes zurueckgegeben.
     * @return Bereich des Moebelstuekes
     */
    public Area getArea(){
        return this.area;
    }

    /**
     * Es wird eine Referenz auf den Kategorie des Moebelstuekes zurueckgegeben.
     * @return Kategorie des Moebelstuekes
     */
    public Category getCategory(){
        return this.category;
    }

    /**
     * Es wird der Orginalpreis des Moebelstuekes ohne Rabatt zurueckgegeben.
     * @return Orginalpreis des Moebelstuekes ohne Rabatt
     */
    public double getOriginalPrice(){
        return this.originalPrice;
    }

    /**
     * Es wird der Preis des Moebelstueckes mit Verrechnung des hoesten Rabatt zurueckgegeben.
     * @return Preis des Moebelstueckes mit Verrechnung des hoesten Rabatt
     */
    public double getDiscountPrice(){
        double discountPrice = this.getOriginalPrice();
        //Choose which discount is higher
        if(this.getArea().getDiscount() >= this.getCategory().getDiscount()){
            discountPrice = discountPrice * (1 - this.getArea().getDiscount());
        }else if(this.getArea().getDiscount() < this.getCategory().getDiscount()){
            discountPrice = discountPrice * (1 - this.getCategory().getDiscount());
        }

        double round = MathUtils.round(discountPrice, 2);
        return round;
    }

    /**
     * Es wird der Warenwert der gelagerten Moebelstuecke mit Verrechnung des hoechsten Rabatt zurueckgegeben.
     * @return Warenwert der gelagerten Moebelstuecke mit Verrechnung des hoechsten Rabatt
     */
    public double getDiscountStockPrice(){
        double discountStockPrice = this.getDiscountPrice() * this.getStock();
        double round = MathUtils.round(discountStockPrice, 2);
        return round;
    }

    /**
     * Es wird der Warenwert der gelagerten Moebelstuceke ohne Rabatt zurueckgegeben.
     * @return Warenwert der gelagerten Moebelstuecke ohne Rabatt
     */
    public double getOriginalStockPrice(){
        double stockPrice = this.getOriginalPrice() * this.getStock();
        double round = MathUtils.round(stockPrice, 2);
        return round;
    }

    /**
     * Es wird der Lagerbestand zurueckgegeben.
     * @return Lagerbestand des Moebelstueckes
     */
    public int getStock() {
        return stock;
    }

    /**
     * Der Lagerbestand wird auf pStock angepasst.
     * @param pStock Lagerbestand des Moebelstueckes
     */
    public void setStock(int pStock) {
        this.stock = pStock;
    }

    /**
     * Der Name wird auf pName geaendert.
     * @param pName Neuer Name des Moebelstueckes
     */
    public void setName(String pName) {
        this.name = pName;
    }

    /**
     * Der Bereich wird auf pArea geaendert.
     * @param pArea Neuer Bereich des Moebelstueckes
     */
    public void setArea(Area pArea) {
        this.area = pArea;
    }

    /**
     * Die Kategorie wird auf pCategory geaendert.
     * @param pCategory Neue Kategorie des Moebelstueckes
     */
    public void setCategory(Category pCategory) {
        this.category = pCategory;
    }

    /**
     * Der Orginalpreis vor Rabatt wird auf pPrice geaendert.
     * @param pPrice Neuer Orginalpreis des Moebelstueckes
     */
    public void setOriginalPrice(double pPrice) {
        this.originalPrice = pPrice;
    }
}
