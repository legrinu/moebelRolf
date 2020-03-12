package de.legrinu.classes;

import de.legrinu.Utils.MathUtils;

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
        return MathUtils.round(totalPrice, 2);
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getAreaName() {
        return area;
    }

    public void setAreaName(String area) {
        this.area = area;
    }
}
