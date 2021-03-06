package de.legrinu;

import de.legrinu.Utils.MathUtils;
import de.legrinu.classes.Area;
import de.legrinu.classes.Category;
import de.legrinu.classes.Furniture;

import java.util.*;

import static de.legrinu.Utils.ArrayListUtils.countFurniture;

/**
 *  Die Klasse "HardwareStore" modelliert einen Moeblemarkt.
 */

public class HardwareStore {
    private static HashMap<Integer, Furniture> hardwareStoreMap = new HashMap<>();
    private static ArrayList<Area> areaList = new ArrayList<>();
    private static ArrayList<Category> categoryList = new ArrayList<>();
    private static String name;

    /**
     *
     * @param pName Name des Moebelmarktes
     */
    public HardwareStore(String pName){
        this.name = pName;
    }

    /**
     * Berechnet den Gesamt-Verkaufspreis aller Moebel im Lager.
     * @return Gesamt-Verkaufspreis aller Moebel
     */
    public double totalStockPrice(){
        double totalPrice = 0;
        //Every Furniture in hardwareStoreMap
        for(Map.Entry<Integer, Furniture> entry : hardwareStoreMap.entrySet()){
            //Get current Furniture
            Furniture furniture = entry.getValue();
            //Calculate totalPrice
            totalPrice += furniture.getDiscountStockPrice();
        }
        return totalPrice;
    }

    /**
     * Berechnet den Gesamtpreis aller Moebel eines Bereichs
     * @param pArea Bereich von dem der Gesamtpreis berechnet werden soll
     * @return Gesamtpreis aller Moebel eines Bereichs
     */
    public double totalAreaPrice(Area pArea){
        double totalAreaPrice = 0;
        //Every Furniture in hardwareStoreMap
        for(Map.Entry<Integer, Furniture> entry : hardwareStoreMap.entrySet()){
            //Get current Furniture
            Furniture furniture = entry.getValue();
            if(furniture.getArea().equals(pArea)){
                totalAreaPrice += furniture.getDiscountStockPrice();
            }
        }
        return totalAreaPrice;
    }

    /**
     * Berechnet den Gesamtpreis aller Moebel einer Kategorie
     * @param pCategory Kategorie von der der Gesamtpreis berechnet werden soll
     * @return Gesamtpreis aller Moebel eines Kategorie
     */
    public double totalCategoryPrice(Category pCategory){
        double totalCategoryPrice = 0;
        //Every Furniture in hardwareStoreMap
        for(Map.Entry<Integer, Furniture> entry : hardwareStoreMap.entrySet()){
            //Get current Furniture
            Furniture furniture = entry.getValue();
            if(furniture.getCategory().equals(pCategory)){
                totalCategoryPrice += furniture.getDiscountStockPrice();
            }
        }
        return totalCategoryPrice;
    }

    /**
     * Ermittelt den Bereich mit der hoechsten Gesamtpreis aller Moebel.
     * @return Bereich mit der hoechsten Gesamtpreis aller Moebel
     */
    public Area areaHighestTotalPrice(){
        Area totalHighestPrice = null;
        //Every Area
        for(Area area : this.getAreaList()){
            //Update totalPrice of Area
            area.setTotalPrice(totalAreaPrice(area));
            //If curent area TotalPrice is higher as curent totalHighestPrice or totalHighestPrice is not defined
            if((totalHighestPrice == null) || (area.getTotalPrice() > totalHighestPrice.getTotalPrice())) {
                totalHighestPrice = area;
            }
        }
        return  totalHighestPrice;
    }

    /**
     * Diese Methode erstellt auf Grundlage "pShoppingCartValue" eine Liste von Moebeln, die man fuer diesen Betrag und verfuegbar(auf Lager)sind, erhalten kann.
     * @param pShoppingCartValue Betrag fuer die Erstellung eine Liste von Moebeln
     * @return Array[0] - ArrayListe mit den vorzuschlagenden Moebeln, Array[1] - Differenz(Restguthaben) von "pShoppingCartValue" und dem Warenwert der vorzuschlagenden Liste
     */
    public Object[] suggestionShoppingCart(double pShoppingCartValue){

        return suggestionShoppingCart(new ArrayList<Furniture>(), pShoppingCartValue);
    }

    /**
     * Diese Methode erstellt auf Grundlage "pShoppingCartValue" eine Liste von Moebeln, die man fuer diesen Betrag und verfuegbar(auf Lager)sind, erhalten kann.
     * @param pTempSuggestion Bisheriger vorzuschlagende Liste
     * @param pRemainingAmount Restguthaben, der fuer die Erstellung der Liste zur Verfuegung steht
     * @return Array[0] - ArrayListe mit den vorzuschlagenden Moebeln, Array[1] - Differenz(Restguthaben) von "pShoppingCartValue" und dem Warenwert der vorzuschlagenden Liste
     */
    private Object[] suggestionShoppingCart(ArrayList<Furniture> pTempSuggestion, double pRemainingAmount){
        //Add possibel furniture
        ArrayList<Object[]> possibleShoppingCart = new ArrayList<Object[]>();
        //Try every furniture
        for(Map.Entry<Integer, Furniture> entry : hardwareStoreMap.entrySet()){
            Furniture furniture = entry.getValue();
            //Add only if DiscountPrice lower or equal RemainingAmount and the Stock is filled
            if ((furniture.getDiscountPrice() <= pRemainingAmount) && (furniture.getStock() > countFurniture(pTempSuggestion,furniture))) {
                //Calculate new RemainingAmount
                double newRemainingAmount = pRemainingAmount - furniture.getDiscountPrice();
                //Clone ArrayList because it is only referenz
                ArrayList<Furniture> newSuggestionShoppingCart = (ArrayList<Furniture>) pTempSuggestion.clone();
                newSuggestionShoppingCart.add(furniture);
                //recursion, add possibilty to ArrayList possibleShoppingCart
                possibleShoppingCart.add(suggestionShoppingCart(newSuggestionShoppingCart, newRemainingAmount));
            }
        }
        //Compare result, only parent(all possiblitys are like a tree)
        if(possibleShoppingCart.size() != 0){
            //Compare
            Object[] bestShoppingCart = new Object[]{};
            //-1 means no bestShoppingCart, becouse bestShoppingCart can 0
            double bestShoppingCartValue = -1;
            //Every possibleShoppingCart
            for(Object[] currentShoppingCart : possibleShoppingCart){
                //If there are a possibleShoppingCart
                if(currentShoppingCart[0] != null) {
                    double currentValueShoppingCart = (double) currentShoppingCart[1];
                    //If bestShoppingCartValue is higher as the currentValueShoppingCart or there is no bestShoppingCart
                    if (bestShoppingCartValue > currentValueShoppingCart || bestShoppingCartValue == -1) {
                        bestShoppingCart = currentShoppingCart;
                        bestShoppingCartValue = currentValueShoppingCart;
                    }
                }
            }
            return bestShoppingCart;
        }
        //If recursion at lowest point, leaf (tree), return himself
        else{
            return new Object[]{pTempSuggestion, pRemainingAmount};
        }
    }

    /**
     * Es wird eine Referenz auf die HashMap hardwareStoreMap zurueckgegeben.
     * @return hardwareStoreMap
     */
    public HashMap<Integer, Furniture> getHardwareStoreMap() {
        return hardwareStoreMap;
    }

    /**
     * Diese Methode referenziert den pHardwareStore zur hardwareStoreMap.
     * @param pHardwareStore Neue HashMap(Integer, Furniture) fuer den Moebelmarkt
     */
    public void setHardwareStore(HashMap<Integer, Furniture> pHardwareStore) {
        HardwareStore.hardwareStoreMap = pHardwareStore;
    }

    /**
     * Es wird eine Referenz auf AreaList zurueckgegeben.
     * @return AreaList
     */
    public ArrayList<Area> getAreaList() {
        return areaList;
    }

    /**
     * Die ArrayListe mit dem Namen pAreaList wird die AreaList des Moebelmarktes.
     * @param pAreaList Neue AreaList
     */
    public void setAreaList(ArrayList<Area> pAreaList) {
        HardwareStore.areaList = pAreaList;
    }

    /**
     * Es wird eine Referenz auf CategoryList zurueckgegeben.
     * @return CategoryList
     */
    public ArrayList<Category> getCategoryList() {
        return categoryList;
    }

    /**
     * Die ArrayListe mit dem Namen pCategoryList wird die CategoryList des Moebelmarktes.
     * @param pCategoryList Neue CategoryList
     */
    public void setCategoryList(ArrayList<Category> pCategoryList) {
        HardwareStore.categoryList = pCategoryList;
    }

    /**
     * Es wird eine Referenz des Namens zurueckgegeben.
     * @return Name des Moebelmarkt
     */
    public String getName() {
        return name;
    }

    /**
     * Der String mit dem Namen pName wird der Name des Moebelmarkt.
     * @param pName Neuer Name
     */
    public void setName(String pName) {
        HardwareStore.name = pName;
    }

    /**
     * Es wird eine ArrayList mit allen Moebel einer Kategorie zurueckgegeben. - Hilfsmethode fuer GUI
     * @param pCategory Kategorie von der eine ArrayList mit deren Moebeln zurueckgegeben werden soll
     * @return ArrayList einer Kategorie mit deren Moebeln
     */
    public ArrayList<Furniture> furnitureGivenCategoryList(Category pCategory){
        ArrayList<Furniture> furnitureArrayList = new ArrayList<>();
        //Every Furniture
        for(Map.Entry<Integer, Furniture> entry : hardwareStoreMap.entrySet()){
            //Get from MapEntry Furniture
            Furniture furniture = entry.getValue();
            //If pCategory equal to furnitureCategory
            if(pCategory.equals(furniture.getCategory())){
                furnitureArrayList.add(furniture);
            }
        }
        return furnitureArrayList;
    }

    /**
     * Es wird eine ArrayList mit allen Moebel eines Bereiches zurueckgegeben. - Hilfsmethode fuer GUI
     * @param pArea Bereich von dem eine ArrayList mit deren Moebeln zurueckgegeben werden soll
     * @return ArrayList eines Bereichs mit deren Moebeln
     */
    public ArrayList<Furniture> furnitureGivenAreaList(Area pArea){
        ArrayList<Furniture> furnitureArrayList = new ArrayList<>();
        //Every Furniture
        for(Map.Entry<Integer, Furniture> entry : hardwareStoreMap.entrySet()){
            //Get from MapEntry Furniture
            Furniture furniture = entry.getValue();
            //If pCategory equal to furnitureArea
            if(pArea.equals(furniture.getArea())){
                furnitureArrayList.add(furniture);
            }
        }
        return furnitureArrayList;
    }

    /**
     * Diese Methode ordnet auf Grundlage eines Namens ein Bereich zu. - Hilfsmethode FileManager
     * @param pInput Name des Bereiches als String
     * @return Bereich, die so heisst wie pName
     */
    public Area getAreaFromString(String pInput){
        //Every area
        for(Area area : getAreaList()){
            //If Name equal like AreaName
            if(area.getAreaName().equalsIgnoreCase(pInput)){
                return area;
            }
        }
        return null;
    }

    /**
     * Diese Methode ordnet auf Grundlage eines Namens ein Kategorie zu. - Hilfsmethode FileManager
     * @param pInput Name des Bereiches als String
     * @return Kategorie, die so heisst wie pName
     */
    public Category getCategoryFromString(String pInput){
        //Every area
        for(Category category : getCategoryList()){
            //If Name equal like CategoryName
            if(category.getCategoryName().equalsIgnoreCase(pInput)){
                return category;
            }
        }
        return null;
    }
}
