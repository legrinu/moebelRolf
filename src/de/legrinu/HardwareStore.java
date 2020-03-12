package de.legrinu;

import de.legrinu.Utils.MathUtils;
import de.legrinu.classes.Area;
import de.legrinu.classes.Category;
import de.legrinu.classes.Furniture;

import java.util.*;

public class HardwareStore {

    private static HashMap<Integer, Furniture> hardwareStoreMap = new HashMap<>();
    private static ArrayList<Area> areaList = new ArrayList<>();
    private static ArrayList<Category> categoryList = new ArrayList<>();

    public HardwareStore(){
    }

    public double totalStockPrice(){
        double totalPrice = 0;
        for(Map.Entry<Integer, Furniture> entry : hardwareStoreMap.entrySet()){
            Furniture furniture = entry.getValue();
            totalPrice += furniture.getDiscountStockPrice();
        }
        return totalPrice;
    }

    public double totalAreaPrice(Area pArea){
        double totalAreaPrice = 0;
        for(Map.Entry<Integer, Furniture> entry : hardwareStoreMap.entrySet()){
            Furniture furniture = entry.getValue();
            if(furniture.getArea().equals(pArea)){
                totalAreaPrice += furniture.getDiscountStockPrice();
            }
        }
        return totalAreaPrice;
    }

    public double totalCategoryPrice(Category pCategory){
        double totalCategoryPrice = 0;
        for(Map.Entry<Integer, Furniture> entry : hardwareStoreMap.entrySet()){
            Furniture furniture = entry.getValue();
            if(furniture.getCategory().equals(pCategory)){
                totalCategoryPrice += furniture.getDiscountStockPrice();
            }
        }
        return totalCategoryPrice;
    }

    public Area areaHighestTotalPrice(){
        for(Map.Entry<Integer, Furniture> entry : hardwareStoreMap.entrySet()) {
            Furniture furniture = entry.getValue();
            furniture.getArea().setTotalPrice(furniture.getArea().getTotalPrice() + furniture.getDiscountStockPrice());
        }

        Area totalHighestPrice = null;
        for(Area area : areaList){
            if(totalHighestPrice == null){
                totalHighestPrice = area;
            }else if(area.getTotalPrice() > totalHighestPrice.getTotalPrice()){
                totalHighestPrice = area;
            }
        }
        return  totalHighestPrice;
    }

    public ArrayList<Furniture> furnitureGivenCategoryList(Category pCategory){
        ArrayList<Furniture> furnitureArrayList = new ArrayList<>();
        for(int i = 1; i < this.getHardwareStoreMap().size() + 1; i++){
            Category furnitureCategory = this.getHardwareStoreMap().get(i).getCategory();
            if(pCategory == furnitureCategory){
                furnitureArrayList.add(this.getHardwareStoreMap().get(i));
            }
        }
        return furnitureArrayList;
    }

    public ArrayList<Furniture> furnitureGivenAreaList(Area pArea){
        ArrayList<Furniture> furnitureArrayList = new ArrayList<>();
        for(int i = 1; i < this.getHardwareStoreMap().size() + 1; i++){
            Area furnitureArea = this.getHardwareStoreMap().get(i).getArea();
            if(pArea == furnitureArea){
                furnitureArrayList.add(this.getHardwareStoreMap().get(i));
            }
        }
        return furnitureArrayList;
    }

    public String[] suggestionShoppingCartArray(double pShoppingCartValue){
        Object[] suggestedArray = suggestionShoppingCart(pShoppingCartValue);
        ArrayList<Object> suggestionShoppingCartObject = (ArrayList<Object>) suggestedArray[0];
        ArrayList<Furniture> suggestionShoppingCartFurniture = new ArrayList<>();
        String[] returnValue = new String[suggestionShoppingCartObject.size()+1];

        for(Object obj : suggestionShoppingCartObject){
            suggestionShoppingCartFurniture.add((Furniture) obj);
        }

        for(int i = 0; i < suggestionShoppingCartFurniture.size(); i++){
            returnValue[i] = suggestionShoppingCartFurniture.get(i).getName();
        }

        returnValue[returnValue.length - 1] = "Remaining price: " + MathUtils.round((Double) suggestedArray[1], 2);

        return returnValue;
    }

    private Object[] suggestionShoppingCart(double pShoppingCartValue){
        double cheapestThingPrice;
        Furniture[] sortedProductArray = null;

        //Create sorted product list
        ArrayList<Furniture> productList = new ArrayList<Furniture>();
        for(Map.Entry<Integer, Furniture> entry : hardwareStoreMap.entrySet()){
            Furniture furniture = entry.getValue();
            //Reject emtpy stock
            if(furniture.getStock() > 0){
                productList.add(furniture);
            }
        }
        Collections.sort(productList);

        sortedProductArray = new Furniture[productList.size()];
        for(int i = 0; i < productList.size(); i++){
            sortedProductArray[i] = productList.get(i);
        }
        cheapestThingPrice = sortedProductArray[0].getDiscountPrice();

        return suggestionShoppingCart2(new ArrayList<Furniture>(), pShoppingCartValue, sortedProductArray, cheapestThingPrice, pShoppingCartValue);
    }

    private Object[] suggestionShoppingCart2(ArrayList<Furniture> pTempSuggestion, double pRemainingAmount, Furniture[] pSortedProductArray, double pCheapestThingPrice, double pSuggestionShoppingCartPrice){
        if(pCheapestThingPrice < pRemainingAmount){
            ArrayList<Object[]> possibleShoppingCart = new ArrayList<Object[]>();
            for (int i = 0; i < pSortedProductArray.length ; i++) {
                if(pSortedProductArray[i].getDiscountPrice() <= pRemainingAmount){
                    double newRemainingAmount = pRemainingAmount - pSortedProductArray[i].getDiscountPrice();
                    ArrayList<Furniture> newSuggestionShoppingCart = (ArrayList<Furniture>)pTempSuggestion.clone();
                    newSuggestionShoppingCart.add(pSortedProductArray[i]);
                    possibleShoppingCart.add(suggestionShoppingCart2(newSuggestionShoppingCart, newRemainingAmount,
                            pSortedProductArray, pCheapestThingPrice, pSuggestionShoppingCartPrice));
                }else{
                    //Cancel, array is sorted
                    i = pSortedProductArray.length;
                }
            }
            //vergleichen welcher beste ist
            Object[] bestShoppingCart = new Object[]{null,null};
            double bestShoppingCartValue = -1;
            for(Object[] currentShoppingCart : possibleShoppingCart){
                if(currentShoppingCart[0] != null) {
                    double currentValueShoppingCart = (double) currentShoppingCart[1];
                    if (bestShoppingCartValue > currentValueShoppingCart || bestShoppingCartValue == -1) {
                        bestShoppingCart = currentShoppingCart;
                        bestShoppingCartValue = currentValueShoppingCart;
                    }
                }
            }
            return bestShoppingCart;
        }else{
            if((pRemainingAmount < pSuggestionShoppingCartPrice) || (pSuggestionShoppingCartPrice == -1)){
                //Search duplicate Products
                ArrayList<Furniture> duplicateProducts = new ArrayList<Furniture>();
                for (int i = 0; i < pTempSuggestion.size(); i++) {
                    Furniture tempFurniture = pTempSuggestion.get(i);
                    if(pTempSuggestion.indexOf(tempFurniture) != pTempSuggestion.lastIndexOf(tempFurniture)){
                        duplicateProducts.add(tempFurniture);
                    }
                }
                if(!duplicateProducts.isEmpty()) {
                    Set<Furniture> duplicateProductsSet = new HashSet<Furniture>(duplicateProducts);
                    Furniture tempDuplicateObject;
                    for (Furniture duplicateProduct : duplicateProductsSet) {
                        tempDuplicateObject = duplicateProduct;
                        while (duplicateProducts.contains(duplicateProduct)) {
                            duplicateProducts.remove(tempDuplicateObject);
                        }
                    }
                    for (Furniture duplicateProduct : duplicateProductsSet) {
                        int counter = 0;
                        for (Furniture everyProduct : pTempSuggestion) {
                            if (duplicateProduct.equals(everyProduct)) {
                                counter += 1;
                            }
                        }
                        if (duplicateProduct.getStock() <= counter) {
                            return new Object[]{null, null};
                        }
                    }
                }
                return new Object[]{pTempSuggestion, pRemainingAmount};
            }
        }
        return new Object[]{null,null};
    }

    public HashMap<Integer, Furniture> getHardwareStoreMap() {
        return hardwareStoreMap;
    }

    public void setHardwareStore(HashMap<Integer, Furniture> hardwareStore) {
        HardwareStore.hardwareStoreMap = hardwareStore;
    }

    public ArrayList<Area> getAreaList() {
        return areaList;
    }

    public void setAreaList(ArrayList<Area> areaList) {
        HardwareStore.areaList = areaList;
    }

    public ArrayList<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(ArrayList<Category> categoryList) {
        HardwareStore.categoryList = categoryList;
    }
}
