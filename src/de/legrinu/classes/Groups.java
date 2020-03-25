package de.legrinu.classes;

import java.util.ArrayList;
import java.util.List;

/**
 *  Diese Klasse bildet die Oberklasse und stellt Grundlagen zur Verfuegung.
 */
public abstract class Groups {

    private double discount;

    /**
     *  Erstellt eine Gruppe mit keinem Rabatt.
     */
    public Groups() {
        this(0);
    }

    /**
     *
     * @param pDiscount Rabatt der Gruppe, als Dezimalzahl angegeben 10% Rabatt = 0.1
     */
    public Groups(double pDiscount){
        this.discount = pDiscount;
    }

    /**
     * Es wird der Rabatt der Gruppe zurueckgegeben.
     * @return Rabatt der Gruppe, als Dezimalzahl angegeben 10% Rabatt = 0.1
     */
    public double getDiscount() {
        return discount;
    }

    /**
     * Setzt den Rabatt der Gruppe.
     * @param discount Rabatt der Gruppe, als Dezimalzahl angegeben 10% Rabatt = 0.1, negative Werte sind Preisaufschlaege
     */
    public void setDiscount(double discount) {
        this.discount = discount;
    }

}
