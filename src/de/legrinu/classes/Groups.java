package de.legrinu.classes;

import java.util.ArrayList;
import java.util.List;

public abstract class Groups {

    private double discount;     //Der Prozentsatz, der abgezogen wird. Bei -19% wird 19% angeben und in Furniture verrechnet, da Rabatt
                                // Preisaufschlag: Negativer Wert

    public Groups() {
        this(0);
    }

    public Groups(double pDiscount){
        this.discount = pDiscount;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

}
