package de.legrinu.classes;

import de.legrinu.Utils.MathUtils;

import java.util.*;

/**
 * Die Klasse "Area" modeliert ein Bereich.
 */
public class Area extends Groups{

    private String areaName;
    private String storageLocation;
    private double totalPrice;

    /**
     *
     * @param pAreaName Name des Breiches
     */
    public Area(String pAreaName) {
        this.areaName = pAreaName;
    }

    /**
     *
     * @param pAreaName Name des Breiches
     * @param pDiscount Rabatt des Bereiches, als Dezemalzahl angegeben -> 10% Rabatt = 0.1
     */
    public Area(String pAreaName, double pDiscount){
        super(pDiscount);
        this.areaName = pAreaName;
    }

    /**
     *
     * @param pAreaName Name des Breiches
     * @param pStorageLocation Lagerort des Bereiches
     * @param pDiscount Rabatt des Bereiches, als Dezemalzahl angegeben -> 10% Rabatt = 0.1
     */
    public Area(String pAreaName,String pStorageLocation, double pDiscount){
        super(pDiscount);
        this.areaName = pAreaName;
        this.storageLocation = pStorageLocation;
    }

    /**
     * Es wird eine Referenz auf den Gesamtpreis zurueckgegeben.
     * @return totalPrice des Bereiches
     */
    public double getTotalPrice() {
        return MathUtils.round(this.totalPrice, 2);
    }

    /**
     * Es wird der Gesamtpreis des Bereiches gesetzt.
     * @param pTotalPrice Gesamtpreis des Bereiches
     */
    public void setTotalPrice(double pTotalPrice) {
        this.totalPrice = pTotalPrice;
    }

    /**
     * Es wird eine Referenz auf den Namen des Bereiches zurueckgegeben.
     * @return Namen des Bereiches
     */
    public String getAreaName() {
        return this.areaName;
    }

    /**
     * Es wird der Name des Bereiches gesetzt.
     * @param pAreaName Name des Bereiches
     */
    public void setAreaName(String pAreaName) {
        this.areaName = pAreaName;
    }

    //Future
    /**
     * Es wird eine Referenz auf den Lagerort des Bereiches zurueckgegeben
     * @return Lagerort des Bereiches
     */
    public String getStorageLocation(){
        return this.storageLocation;
    }

    /**
     * Es wird der Lagerort des Bereiches gesetzt.
     * @param pStorageLocation Lagerort des Bereiches
     */
    public void setStorageLocation(String pStorageLocation){
        this.storageLocation = pStorageLocation;
    }
}
