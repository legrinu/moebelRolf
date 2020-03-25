package de.legrinu.classes;

/**
 * Die Klasse "Category" modelliert eine Kategorie.
 */
public class Category extends Groups{

    private String category;

    /**
     *
     * @param pNameCategory Namen der Kategorie
     */
    public Category(String pNameCategory) {
        this.category = pNameCategory;
    }

    /**
     *
     * @param pCategory Namen der Kategorie
     * @param pDiscount Rabatt des Bereiches, als Dezimalzahl angegeben 10% Rabatt = 0.1
     */
    public Category(String pCategory, double pDiscount){
        super(pDiscount);
        this.category = pCategory;
    }

    /**
     * Es wird eine Referenz auf den Namen der Kategorie zurueckgegeben.
     * @return Namen des Kategorie
     */
    public String getCategoryName() {
        return category;
    }

    /**
     * Es wird der Name der Kategorie gesetzt.
     * @param pNameCategory Name der Kategorie
     */
    public void setCategoryName(String pNameCategory) {
        this.category = pNameCategory;
    }
}
