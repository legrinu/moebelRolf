package de.legrinu.Utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Diese Klasse stellt Mathetools zu Verfuegung.
 */
public class MathUtils {

    /**
     * Diese Methode rundet dooble auf die angegebene Nachkommerstelle
     * @param pValue Zahl die gerundet werden soll
     * @param pPlaces Anzahl der Nachkommerstellen
     * @return Gerundete Zahl auf die angegebenen Nachkommerstellen
     */
    public static double round(double pValue, int pPlaces) {
        //Cast to BigDecimal
        BigDecimal bd = BigDecimal.valueOf(pValue);
        //Round
        bd = bd.setScale(pPlaces, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
