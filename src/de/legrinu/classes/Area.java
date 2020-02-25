package de.legrinu.classes;

import java.util.*;

public class Area extends Groups{

    private String area;
    private double totalPrice;

    public Area(String pArea) {
        this.area = pArea;
    }

    public Area(String pArea, double pDiscount){
        super(pDiscount);
        this.area = pArea;
    }

    public void resetTotalPrice(){
        totalPrice = 0;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
