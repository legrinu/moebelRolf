package de.legrinu.classes;

public class Area extends Groups{

    String area;

    public Area(String pArea) {
        this.area = pArea;
    }

    public Area(String pArea, double pDiscount){
        super(pDiscount);
        this.area = pArea;
    }

}
