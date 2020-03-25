package de.legrinu.Utils;

import de.legrinu.classes.Furniture;

import java.util.ArrayList;

/**
 * Diese Klasse stellt ArrayListTools zu Verfuegung.
 */
public class ArrayListUtils {
    /**
     * Diese Methode gibt die Anzahl, wie oft ein Moebelstueck in einer ArrayListe enthalten ist, zurueck
     * @param pFurnitureList ArrayList in der gezaehlt werden soll
     * @param pFurniture Moebelstueck, was gezaehlt werden soll
     * @return Anzahl,wie das Moebelstueck enthalten ist ist
     */
    public static int countFurniture(ArrayList<Furniture> pFurnitureList, Furniture pFurniture){
        //Create a workcopy, becouse referenzing
        ArrayList<Furniture> workcopy = (ArrayList<Furniture>) pFurnitureList.clone();
        //Remove all pFurniture of the workcopy
        while (workcopy.contains(pFurniture)){
            workcopy.remove(pFurniture);
        }
        return pFurnitureList.size() - workcopy.size();
    }
}
