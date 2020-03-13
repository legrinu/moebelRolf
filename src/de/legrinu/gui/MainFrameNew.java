/*
 * Created by JFormDesigner on Tue Mar 10 14:05:25 CET 2020
 */

package de.legrinu.gui;

import java.awt.*;
import javax.swing.event.*;
import de.legrinu.Main;
import de.legrinu.Utils.MathUtils;
import de.legrinu.classes.Area;
import de.legrinu.classes.Category;
import de.legrinu.classes.Furniture;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import javax.swing.*;
import javax.swing.GroupLayout;

/**
 * @author BACreations
 */
public class MainFrameNew extends JFrame {

    DefaultListModel listModel = new DefaultListModel();
    Furniture currentActiveFurniture = null;
    String selectedItemInList = null;

    public MainFrameNew() {
        initComponents();
    }

    private void change_stockActionPerformed(ActionEvent e) {
        if(currentActiveFurniture != null) {
            current_stock.setModel(new SpinnerNumberModel(currentActiveFurniture.getStock(), 0, 999, 1));
            current_furniture.setText(currentActiveFurniture.getName());
            change_stock_dialog.setIconImage(new ImageIcon("C:\\Users\\legri\\Documents\\BauMarkt\\ressources\\BAC_transparent.png").getImage());
            change_stock_dialog.setVisible(true);
        }
    }

    private void change_priceActionPerformed(ActionEvent e) {
        if(currentActiveFurniture != null) {
            current_furniture_price.setText(currentActiveFurniture.getName());
            price_to_change.setText(currentActiveFurniture.getOriginalPrice() + "");
            change_price_dialog.setIconImage(new ImageIcon("C:\\Users\\legri\\Documents\\BauMarkt\\ressources\\BAC_transparent.png").getImage());
            change_price_dialog.setVisible(true);
        }
    }

    private void createUIComponents() {
        // TODO: add custom component creation code here
    }

    private void product_listValueChanged(ListSelectionEvent e) {
        int selectedIndex = product_list.getSelectedIndex() + 1;
        String selectedItemText = (String) product_list.getSelectedValue();

        if(selectedIndex > 0) {

            for(int i = 1; i < Main.getMainStore().getHardwareStoreMap().size()+1; i++) {
                Furniture selectedFurniture = Main.getMainStore().getHardwareStoreMap().get(i);

                if(selectedFurniture.getName().contains(selectedItemText)) {
                    product_name.setText(selectedFurniture.getName());
                    product_area.setText(selectedFurniture.getArea().getAreaName() + " | " + selectedFurniture.getCategory().getCategoryName());
                    op_changer.setText(selectedFurniture.getOriginalPrice() + " €");
                    dp_changer.setText(selectedFurniture.getDiscountPrice() + " €");
                    s_changer.setText(selectedFurniture.getStock() + "");

                    currentActiveFurniture = selectedFurniture;
                    selectedItemInList = selectedItemText;
                }
            }
        }else{
            product_name.setText("");
            product_area.setText("");
            op_changer.setText("");
            dp_changer.setText("");
            s_changer.setText("");
        }
    }

    private void CategoryAreaActionPerformed(ActionEvent e) { //TODO: SMall fixes for beauty
        listModel.clear();

        ArrayList<Component> selectedItemsList = new ArrayList<>();
        selectedItemsList.addAll(Arrays.asList(Area.getMenuComponents()));
        selectedItemsList.addAll(Arrays.asList(Category.getMenuComponents()));

        ArrayList<Furniture> intersectionArea = new ArrayList<>();
        ArrayList<Furniture> intersectionCategory = new ArrayList<>();

        int areaLength = Area.getMenuComponentCount();
        int categoryLength = Category.getMenuComponentCount();

        Object[] selectedItems = selectedItemsList.toArray();
        double totalAreaValue = 0;
        double totalCategoryValue = 0;

        int selectedAreaCounter = 0;
        int selectedCategoryCounter = 0;
        int selectedArea = 0;
        int selectedCategory = 0;

        for(Object obj : Area.getMenuComponents()){
            JCheckBoxMenuItem checkBoxMenuItem = (JCheckBoxMenuItem) obj;
            if(checkBoxMenuItem.getState() == true){
                selectedArea++;
            }
        }

        for(Object obj : Category.getMenuComponents()){
            JCheckBoxMenuItem checkBoxMenuItem = (JCheckBoxMenuItem) obj;
            if(checkBoxMenuItem.getState() == true){
                selectedCategory++;
            }
        }

        for(Object obj : selectedItems){
            JCheckBoxMenuItem checkBox = (JCheckBoxMenuItem) obj;

            //Area Check
            for(Area areaToFind : Main.getMainStore().getAreaList()){
                if(areaToFind.getAreaName().contains(checkBox.getText())){
                    if(checkBox.getState() == true){
                        //Part 1: Show Value
                        totalAreaValue += Main.getMainStore().totalAreaPrice(areaToFind);

                        //Part 2: Add to ListModel
                        for(Furniture furnitureFromList : Main.getMainStore().furnitureGivenAreaList(areaToFind)){
                            if(!listModel.contains(furnitureFromList.getName())) {
                                intersectionArea.add(furnitureFromList);
                            }
                        }
                    }else{
                        selectedAreaCounter++;
                    }
                }
            }

            //Category Check
            for(Category categoryToFind : Main.getMainStore().getCategoryList()){
                if(categoryToFind.getCategoryName().contains(checkBox.getText())){
                    if(checkBox.getState() == true){
                        //Part 1: Show Value
                        totalCategoryValue += Main.getMainStore().totalCategoryPrice(categoryToFind);

                        //Part 2: Add to ListModel
                        for(Furniture furnitureFromList : Main.getMainStore().furnitureGivenCategoryList(categoryToFind)){
                            if(!listModel.contains(furnitureFromList.getName())) {
                                intersectionCategory.add(furnitureFromList);
                            }
                        }
                    }else{
                        selectedCategoryCounter++;
                    }
                }
            }
        }

        if(selectedArea >= 1 && selectedCategory >= 1) {

            //Part 1
            price_selected_area.setVisible(true);
            selected_area_price.setText(MathUtils.round(totalAreaValue, 2) + "€");
            selected_area_price.setVisible(true);

            price_selected_category.setVisible(true);
            selected_category_price.setText(MathUtils.round(totalCategoryValue, 2) + "€");
            selected_category_price.setVisible(true);

            //Part 2
            if (selectedAreaCounter < areaLength && selectedCategoryCounter < categoryLength) {
                for (final Furniture intersectArea : intersectionArea) {
                    for (final Furniture intersectCategory : intersectionCategory) {
                        if (intersectArea.equals(intersectCategory)) {
                            listModel.addElement(intersectArea.getName());
                        }
                    }
                }
            }
        }else if(selectedArea >= 1 && selectedAreaCounter < areaLength) {
            //Part 1
            price_selected_area.setVisible(true);
            selected_area_price.setText(MathUtils.round(totalAreaValue, 2) + "€");
            selected_area_price.setVisible(true);

            //Part 2
            for(Furniture furniture : intersectionArea){
                if(!listModel.contains(furniture.getName())) {
                    listModel.addElement(furniture.getName());
                }
            }
            product_list.updateUI();
        }else if(selectedCategory >= 1 && selectedCategoryCounter < categoryLength){
            //Part 1
            price_selected_category.setVisible(true);
            selected_category_price.setText(MathUtils.round(totalCategoryValue, 2) + "€");
            selected_category_price.setVisible(true);

            //Part 2
            for(Furniture furniture : intersectionCategory){
                if(!listModel.contains(furniture.getName())) {
                    listModel.addElement(furniture.getName());
                }
            }
            product_list.updateUI();
        }else{
            //Part 1
            price_selected_area.setVisible(false);
            selected_area_price.setVisible(false);
            price_selected_category.setVisible(false);
            selected_category_price.setVisible(false);

            //Part 2
            String[] stringArray = new String[Main.getMainStore().getHardwareStoreMap().size()];
            for(int i = 0; i < Main.getMainStore().getHardwareStoreMap().size(); i++){
                Furniture furniture = Main.getMainStore().getHardwareStoreMap().get(i+1);
                listModel.addElement(furniture.getName());
            }
            product_list.updateUI();
        }
    }

    private void current_stockStateChanged(ChangeEvent e) {
        int currentValue = (int) current_stock.getValue();
        currentActiveFurniture.setStock(currentValue);

        for(int i = 1; i < Main.getMainStore().getHardwareStoreMap().size()+1; i++) {
            Furniture selectedFurniture = Main.getMainStore().getHardwareStoreMap().get(i);

            if(selectedFurniture.getName().contains(selectedItemInList)) {
                s_changer.setText(selectedFurniture.getStock() + "");
            }
        }
    }

    private void mainWindowClosing(WindowEvent e) {
        Main.getFileManager().saveFiles();
    }

    private void price_to_changeActionPerformed(ActionEvent e) {
        Double priceToChange = Double.parseDouble(price_to_change.getText());
        currentActiveFurniture.setOriginalPrice(priceToChange);

        for(int i = 1; i < Main.getMainStore().getHardwareStoreMap().size()+1; i++) {
            Furniture selectedFurniture = Main.getMainStore().getHardwareStoreMap().get(i);

            if(selectedFurniture.getName().contains(selectedItemInList)) {
                op_changer.setText(selectedFurniture.getOriginalPrice() + "€");
                dp_changer.setText(selectedFurniture.getDiscountPrice() + "€");
            }
        }
    }

    private void readActionPerformed(ActionEvent e) {
        Main.getFileManager().readFromFiles();
    }

    private void writeActionPerformed(ActionEvent e) {
        Main.getFileManager().saveFiles();
    }

    private void resetActionPerformed(ActionEvent e) {
        listModel.clear();
        for(Map.Entry<Integer, Furniture> entry : Main.getMainStore().getHardwareStoreMap().entrySet()){
            String furnitureName = entry.getValue().getName();
            listModel.addElement(furnitureName);
        }

        for(Component areaObj : Area.getMenuComponents()){
            JCheckBoxMenuItem areaCheckBox = (JCheckBoxMenuItem) areaObj;
            areaCheckBox.setState(false);
        }

        for(Component categoryObj : Category.getMenuComponents()){
            JCheckBoxMenuItem categoryCheckBox = (JCheckBoxMenuItem) categoryObj;
            categoryCheckBox.setState(false);
        }

        price_selected_area.setVisible(false);
        selected_area_price.setVisible(false);
        price_selected_category.setVisible(false);
        selected_category_price.setVisible(false);

        product_list.updateUI();
    }

    private void cart_valueActionPerformed(ActionEvent e) {
        listModel.clear();
        Double input = Double.parseDouble(cart_value.getText());
        String[] suggestedCart = Main.getMainStore().suggestionShoppingCartArray(input);

        for(int i = 0; i < suggestedCart.length - 1; i++){
            listModel.addElement(suggestedCart[i]);
        }
        product_list.updateUI();

        remaining_value.setText(suggestedCart[suggestedCart.length-1]);
        remaining_value_dialog.setIconImage(new ImageIcon("C:\\Users\\legri\\Documents\\BauMarkt\\ressources\\BAC_transparent.png").getImage());
        remaining_value_dialog.setVisible(true);
    }

    private void suggested_cartActionPerformed(ActionEvent e) {
        suggested_cart_dialog.setIconImage(new ImageIcon("C:\\Users\\legri\\Documents\\BauMarkt\\ressources\\BAC_transparent.png").getImage());
        suggested_cart_dialog.setVisible(true);
    }

    private void set_discountActionPerformed(ActionEvent e) {
        DefaultListModel discountModel = new DefaultListModel();

        discountModel.addElement("---Area---");
        for(Area area : Main.getMainStore().getAreaList()){
            discountModel.addElement(area.getAreaName());
        }

        discountModel.addElement("---Category---");
        for(Category category : Main.getMainStore().getCategoryList()){
            discountModel.addElement(category.getCategoryName());
        }

        discount_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        discount_list.setModel(discountModel);
        discount_list.updateUI();

        discount_dialog.setIconImage(new ImageIcon("C:\\Users\\legri\\Documents\\BauMarkt\\ressources\\BAC_transparent.png").getImage());
        discount_dialog.setVisible(true);
    }

    private void setDiscount_buttonActionPerformed(ActionEvent e) {
        String selectedItem = (String) discount_list.getSelectedValue();
        Double input = Double.parseDouble(discount_input.getText());
        boolean finished = false;

        for(Area area : Main.getMainStore().getAreaList()){
            if(!finished){
                if(area.getAreaName().contains(selectedItem)){
                    area.setDiscount(input);
                    finished = true;
                }
            }else{
                return;
            }
        }

        for(Category category : Main.getMainStore().getCategoryList()){
            if(!finished){
                if(category.getCategoryName().contains(selectedItem)){
                    category.setDiscount(input);
                    finished = true;
                }
            }else{
                return;
            }
        }
    }

    private void discount_listValueChanged(ListSelectionEvent e) {
        boolean finished = false;

        for(Area area : Main.getMainStore().getAreaList()){
            if(!finished){
                discount_input.setText(area.getDiscount() + "");
                finished = true;
            }else {
                return;
            }
        }

        for(Category category : Main.getMainStore().getCategoryList()){
            if(!finished){
                discount_input.setText(category.getDiscount() + "");
                finished = true;
            }else{
                return;
            }
        }
    }

    private void total_stock_priceActionPerformed(ActionEvent e) {
        total_stock_price_change.setText(Main.getMainStore().totalStockPrice() + "€");
        total_stock_dialog.setIconImage(new ImageIcon("C:\\Users\\legri\\Documents\\BauMarkt\\ressources\\BAC_transparent.png").getImage());
        total_stock_dialog.setVisible(true);
    }

    private void highest_area_priceActionPerformed(ActionEvent e) {
        Area highestPrice = Main.getMainStore().areaHighestTotalPrice();
        area_total_highest_price_change.setText(highestPrice.getAreaName() +
                " | Price: " + highestPrice.getTotalPrice());

        area_total_highest_price_dialog.setIconImage(new ImageIcon("C:\\Users\\legri\\Documents\\BauMarkt\\ressources\\BAC_transparent.png").getImage());
        area_total_highest_price_dialog.setVisible(true);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - unknown
        menuBar = new JMenuBar();
        file = new JMenu();
        read = new JMenuItem();
        write = new JMenuItem();
        Area = new JMenu();
        Category = new JMenu();
        etc = new JMenu();
        set_discount = new JMenuItem();
        suggested_cart = new JMenuItem();
        total_stock_price = new JMenuItem();
        area_total_highest_price = new JMenuItem();
        reset = new JMenuItem();
        scrollPane1 = new JScrollPane();
                            for(int i = 0; i < Main.getMainStore().getHardwareStoreMap().size(); i++){
                                    Furniture furniture = Main.getMainStore().getHardwareStoreMap().get(i+1);
                                    listModel.addElement(furniture.getName());
                            }
        product_list = new JList(listModel);
        product_name = new JLabel();
        product_area = new JLabel();
        original_price = new JLabel();
        discount_price = new JLabel();
        product_stock = new JLabel();
        footer = new JLabel();
        change_stock = new JButton();
        change_price = new JButton();
        price_selected_area = new JLabel();
        price_selected_category = new JLabel();
        op_changer = new JLabel();
        dp_changer = new JLabel();
        s_changer = new JLabel();
        dateLabel = new JLabel();
        selected_area_price = new JLabel();
        selected_category_price = new JLabel();
        change_stock_dialog = new JDialog();
        solid_current_stock = new JLabel();
        current_stock = new JSpinner();
        solid_current_furniture = new JLabel();
        current_furniture = new JLabel();
        change_price_dialog = new JDialog();
        solid_current_furniture_price = new JLabel();
        current_furniture_price = new JLabel();
        current_price = new JLabel();
        price_to_change = new JTextField();
        suggested_cart_dialog = new JDialog();
        solid_cart_value = new JLabel();
        cart_value = new JTextField();
        remaining_value_dialog = new JDialog();
        remaining_value = new JLabel();
        discount_dialog = new JDialog();
        scrollPane2 = new JScrollPane();
        discount_list = new JList();
        discount_input = new JTextField();
        discount_button = new JButton();
        total_stock_dialog = new JDialog();
        solid_total_stock_price = new JLabel();
        total_stock_price_change = new JLabel();
        area_total_highest_price_dialog = new JDialog();
        area_total_highest_price_solid = new JLabel();
        area_total_highest_price_change = new JLabel();

        //======== this ========
        setTitle("HSMS by BACreations");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setIconImage(new ImageIcon("C:\\Users\\legri\\Documents\\BauMarkt\\ressources\\BAC_transparent.png").getImage());
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                mainWindowClosing(e);
            }
        });
        var contentPane = getContentPane();

        //======== menuBar ========
        {

            //======== file ========
            {
                file.setText("File");

                //---- read ----
                read.setText("Read");
                read.addActionListener(e -> readActionPerformed(e));
                file.add(read);

                //---- write ----
                write.setText("Write");
                write.addActionListener(e -> writeActionPerformed(e));
                file.add(write);
            }
            menuBar.add(file);

            //======== Area ========
            {
                Area.setText("Area");
                        for(de.legrinu.classes.Area area : Main.getMainStore().getAreaList()){
                            JCheckBoxMenuItem checkBoxMenuItem = new JCheckBoxMenuItem(area.getAreaName());
                            checkBoxMenuItem.addActionListener(this::CategoryAreaActionPerformed);
                            Area.add(checkBoxMenuItem);
                        }
            }
            menuBar.add(Area);

            //======== Category ========
            {
                Category.setText("Category");
                for(de.legrinu.classes.Category category : Main.getMainStore().getCategoryList()){
                            JCheckBoxMenuItem checkBoxMenuItem = new JCheckBoxMenuItem(category.getCategoryName());
                            checkBoxMenuItem.addActionListener(this::CategoryAreaActionPerformed);
                            Category.add(checkBoxMenuItem);
                        }
            }
            menuBar.add(Category);

            //======== etc ========
            {
                etc.setText("Etc.");

                //---- set_discount ----
                set_discount.setText("Discount");
                set_discount.addActionListener(e -> set_discountActionPerformed(e));
                etc.add(set_discount);

                //---- suggested_cart ----
                suggested_cart.setText("Suggestion");
                suggested_cart.addActionListener(e -> suggested_cartActionPerformed(e));
                etc.add(suggested_cart);

                //---- total_stock_price ----
                total_stock_price.setText("Total Stock Price");
                total_stock_price.addActionListener(e -> total_stock_priceActionPerformed(e));
                etc.add(total_stock_price);

                //---- area_total_highest_price ----
                area_total_highest_price.setText("Area with highest total price");
                area_total_highest_price.addActionListener(e -> highest_area_priceActionPerformed(e));
                etc.add(area_total_highest_price);

                //---- reset ----
                reset.setText("Reset Selection");
                reset.addActionListener(e -> resetActionPerformed(e));
                etc.add(reset);
            }
            menuBar.add(etc);
        }
        setJMenuBar(menuBar);

        //======== scrollPane1 ========
        {

            //---- product_list ----
            product_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            product_list.addListSelectionListener(e -> product_listValueChanged(e));
            scrollPane1.setViewportView(product_list);
        }

        //---- product_name ----
        product_name.setText("Name");
        product_name.setFont(new Font("Segoe UI", Font.BOLD, 12));

        //---- product_area ----
        product_area.setText("Area | Category");
        product_area.setHorizontalAlignment(SwingConstants.CENTER);

        //---- original_price ----
        original_price.setText("Original Price: ");

        //---- discount_price ----
        discount_price.setText("Discount Price: ");

        //---- product_stock ----
        product_stock.setText("Stock: ");

        //---- footer ----
        footer.setText("\u00a9 BACreations");

        //---- change_stock ----
        change_stock.setText("Change Stock");
        change_stock.addActionListener(e -> change_stockActionPerformed(e));

        //---- change_price ----
        change_price.setText("Change Price");
        change_price.addActionListener(e -> change_priceActionPerformed(e));

        //---- price_selected_area ----
        price_selected_area.setText("Price of selected Area: ");
        price_selected_area.setVisible(false);

        //---- price_selected_category ----
        price_selected_category.setText("Price of selected Category: ");
        price_selected_category.setVisible(false);

        //---- dateLabel ----
        dateLabel.setText("Alea iacta est");
        dateLabel.setHorizontalAlignment(SwingConstants.RIGHT);

                dateLabel.setText("" + java.time.LocalDate.now());

        //---- selected_area_price ----
        selected_area_price.setText("0\u20ac");
        selected_area_price.setVisible(false);

        //---- selected_category_price ----
        selected_category_price.setText("0\u20ac");
        selected_category_price.setVisible(false);

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addComponent(footer, GroupLayout.PREFERRED_SIZE, 217, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(dateLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addGap(17, 17, 17)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(price_selected_area)
                        .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 137, GroupLayout.PREFERRED_SIZE))
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addComponent(product_name, GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
                                .addComponent(product_area, GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
                                .addGroup(contentPaneLayout.createSequentialGroup()
                                    .addComponent(product_stock, GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(s_changer))
                                .addGroup(contentPaneLayout.createSequentialGroup()
                                    .addComponent(discount_price, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(dp_changer))
                                .addGroup(contentPaneLayout.createSequentialGroup()
                                    .addComponent(original_price)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(op_changer)))
                            .addGap(59, 59, 59))
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addGroup(contentPaneLayout.createSequentialGroup()
                                    .addGap(29, 29, 29)
                                    .addComponent(change_stock)
                                    .addGap(18, 18, 18)
                                    .addComponent(change_price))
                                .addGroup(contentPaneLayout.createSequentialGroup()
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(selected_area_price, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(price_selected_category)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(selected_category_price, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)))
                            .addContainerGap(28, Short.MAX_VALUE))))
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addGap(22, 22, 22)
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addComponent(product_name)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(product_area)
                            .addGap(18, 18, 18)
                            .addGroup(contentPaneLayout.createParallelGroup()
                                .addComponent(original_price)
                                .addComponent(op_changer))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(discount_price)
                                .addComponent(dp_changer))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(product_stock)
                                .addComponent(s_changer))
                            .addGap(64, 64, 64)
                            .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(change_stock)
                                .addComponent(change_price)))
                        .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 196, GroupLayout.PREFERRED_SIZE))
                    .addGap(14, 14, 14)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(price_selected_area)
                        .addComponent(price_selected_category)
                        .addComponent(selected_area_price)
                        .addComponent(selected_category_price))
                    .addGap(18, 18, 18)
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addComponent(footer)
                        .addComponent(dateLabel)))
        );
        pack();
        setLocationRelativeTo(getOwner());

        //======== change_stock_dialog ========
        {
            change_stock_dialog.setTitle("Change Stock");
            var change_stock_dialogContentPane = change_stock_dialog.getContentPane();

            //---- solid_current_stock ----
            solid_current_stock.setText("Current Stock:");

            //---- current_stock ----
            current_stock.addChangeListener(e -> current_stockStateChanged(e));

            //---- solid_current_furniture ----
            solid_current_furniture.setText("Current Furniture:");

            GroupLayout change_stock_dialogContentPaneLayout = new GroupLayout(change_stock_dialogContentPane);
            change_stock_dialogContentPane.setLayout(change_stock_dialogContentPaneLayout);
            change_stock_dialogContentPaneLayout.setHorizontalGroup(
                change_stock_dialogContentPaneLayout.createParallelGroup()
                    .addGroup(change_stock_dialogContentPaneLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(change_stock_dialogContentPaneLayout.createParallelGroup()
                            .addGroup(change_stock_dialogContentPaneLayout.createSequentialGroup()
                                .addComponent(solid_current_stock)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(current_stock, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addGroup(change_stock_dialogContentPaneLayout.createSequentialGroup()
                                .addComponent(solid_current_furniture)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(current_furniture)))
                        .addContainerGap(145, Short.MAX_VALUE))
            );
            change_stock_dialogContentPaneLayout.setVerticalGroup(
                change_stock_dialogContentPaneLayout.createParallelGroup()
                    .addGroup(change_stock_dialogContentPaneLayout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(change_stock_dialogContentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(solid_current_furniture)
                            .addComponent(current_furniture))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(change_stock_dialogContentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(solid_current_stock)
                            .addComponent(current_stock, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(36, Short.MAX_VALUE))
            );
            change_stock_dialog.pack();
            change_stock_dialog.setLocationRelativeTo(change_stock_dialog.getOwner());
        }

        //======== change_price_dialog ========
        {
            change_price_dialog.setTitle("Change Price");
            var change_price_dialogContentPane = change_price_dialog.getContentPane();

            //---- solid_current_furniture_price ----
            solid_current_furniture_price.setText("Current Furniture:");

            //---- current_price ----
            current_price.setText("Current Price:");

            //---- price_to_change ----
            price_to_change.addActionListener(e -> price_to_changeActionPerformed(e));

            GroupLayout change_price_dialogContentPaneLayout = new GroupLayout(change_price_dialogContentPane);
            change_price_dialogContentPane.setLayout(change_price_dialogContentPaneLayout);
            change_price_dialogContentPaneLayout.setHorizontalGroup(
                change_price_dialogContentPaneLayout.createParallelGroup()
                    .addGroup(change_price_dialogContentPaneLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(change_price_dialogContentPaneLayout.createParallelGroup()
                            .addGroup(change_price_dialogContentPaneLayout.createSequentialGroup()
                                .addComponent(solid_current_furniture_price)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(current_furniture_price))
                            .addGroup(change_price_dialogContentPaneLayout.createSequentialGroup()
                                .addComponent(current_price)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(price_to_change, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                        .addGap(181, 181, 181))
            );
            change_price_dialogContentPaneLayout.setVerticalGroup(
                change_price_dialogContentPaneLayout.createParallelGroup()
                    .addGroup(change_price_dialogContentPaneLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(change_price_dialogContentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(solid_current_furniture_price)
                            .addComponent(current_furniture_price))
                        .addGap(18, 18, 18)
                        .addGroup(change_price_dialogContentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(current_price)
                            .addComponent(price_to_change, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addGap(17, 17, 17))
            );
            change_price_dialog.pack();
            change_price_dialog.setLocationRelativeTo(change_price_dialog.getOwner());
        }

        //======== suggested_cart_dialog ========
        {
            suggested_cart_dialog.setTitle("Suggested Shopping Cart Value");
            var suggested_cart_dialogContentPane = suggested_cart_dialog.getContentPane();

            //---- solid_cart_value ----
            solid_cart_value.setText("Cart Value:");

            //---- cart_value ----
            cart_value.setText("187.69");
            cart_value.addActionListener(e -> cart_valueActionPerformed(e));

            GroupLayout suggested_cart_dialogContentPaneLayout = new GroupLayout(suggested_cart_dialogContentPane);
            suggested_cart_dialogContentPane.setLayout(suggested_cart_dialogContentPaneLayout);
            suggested_cart_dialogContentPaneLayout.setHorizontalGroup(
                suggested_cart_dialogContentPaneLayout.createParallelGroup()
                    .addGroup(suggested_cart_dialogContentPaneLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(solid_cart_value)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cart_value, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(25, Short.MAX_VALUE))
            );
            suggested_cart_dialogContentPaneLayout.setVerticalGroup(
                suggested_cart_dialogContentPaneLayout.createParallelGroup()
                    .addGroup(suggested_cart_dialogContentPaneLayout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(suggested_cart_dialogContentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(solid_cart_value)
                            .addComponent(cart_value, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(17, Short.MAX_VALUE))
            );
            suggested_cart_dialog.pack();
            suggested_cart_dialog.setLocationRelativeTo(suggested_cart_dialog.getOwner());
        }

        //======== remaining_value_dialog ========
        {
            remaining_value_dialog.setTitle("Remaining Value");
            var remaining_value_dialogContentPane = remaining_value_dialog.getContentPane();

            //---- remaining_value ----
            remaining_value.setText("Remaining Value: 10.01");

            GroupLayout remaining_value_dialogContentPaneLayout = new GroupLayout(remaining_value_dialogContentPane);
            remaining_value_dialogContentPane.setLayout(remaining_value_dialogContentPaneLayout);
            remaining_value_dialogContentPaneLayout.setHorizontalGroup(
                remaining_value_dialogContentPaneLayout.createParallelGroup()
                    .addGroup(remaining_value_dialogContentPaneLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(remaining_value, GroupLayout.PREFERRED_SIZE, 162, GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );
            remaining_value_dialogContentPaneLayout.setVerticalGroup(
                remaining_value_dialogContentPaneLayout.createParallelGroup()
                    .addGroup(remaining_value_dialogContentPaneLayout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(remaining_value)
                        .addContainerGap(30, Short.MAX_VALUE))
            );
            remaining_value_dialog.pack();
            remaining_value_dialog.setLocationRelativeTo(remaining_value_dialog.getOwner());
        }

        //======== discount_dialog ========
        {
            discount_dialog.setTitle("Set Discount");
            var discount_dialogContentPane = discount_dialog.getContentPane();

            //======== scrollPane2 ========
            {

                //---- discount_list ----
                discount_list.addListSelectionListener(e -> discount_listValueChanged(e));
                scrollPane2.setViewportView(discount_list);
            }

            //---- discount_input ----
            discount_input.setText("0.00");

            //---- discount_button ----
            discount_button.setText("Set Discount");
            discount_button.addActionListener(e -> setDiscount_buttonActionPerformed(e));

            GroupLayout discount_dialogContentPaneLayout = new GroupLayout(discount_dialogContentPane);
            discount_dialogContentPane.setLayout(discount_dialogContentPaneLayout);
            discount_dialogContentPaneLayout.setHorizontalGroup(
                discount_dialogContentPaneLayout.createParallelGroup()
                    .addGroup(discount_dialogContentPaneLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(scrollPane2, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE)
                        .addGap(56, 56, 56)
                        .addGroup(discount_dialogContentPaneLayout.createParallelGroup()
                            .addComponent(discount_input, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
                            .addComponent(discount_button))
                        .addGap(141, 141, 141))
            );
            discount_dialogContentPaneLayout.setVerticalGroup(
                discount_dialogContentPaneLayout.createParallelGroup()
                    .addGroup(discount_dialogContentPaneLayout.createSequentialGroup()
                        .addGroup(discount_dialogContentPaneLayout.createParallelGroup()
                            .addGroup(discount_dialogContentPaneLayout.createSequentialGroup()
                                .addGap(76, 76, 76)
                                .addComponent(discount_input, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(32, 32, 32)
                                .addComponent(discount_button))
                            .addGroup(discount_dialogContentPaneLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(scrollPane2, GroupLayout.PREFERRED_SIZE, 189, GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(8, Short.MAX_VALUE))
            );
            discount_dialog.pack();
            discount_dialog.setLocationRelativeTo(discount_dialog.getOwner());
        }

        //======== total_stock_dialog ========
        {
            total_stock_dialog.setTitle("Total Stock Price");
            var total_stock_dialogContentPane = total_stock_dialog.getContentPane();

            //---- solid_total_stock_price ----
            solid_total_stock_price.setText("Total Stock Price:");

            GroupLayout total_stock_dialogContentPaneLayout = new GroupLayout(total_stock_dialogContentPane);
            total_stock_dialogContentPane.setLayout(total_stock_dialogContentPaneLayout);
            total_stock_dialogContentPaneLayout.setHorizontalGroup(
                total_stock_dialogContentPaneLayout.createParallelGroup()
                    .addGroup(total_stock_dialogContentPaneLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(solid_total_stock_price)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(total_stock_price_change)
                        .addContainerGap(96, Short.MAX_VALUE))
            );
            total_stock_dialogContentPaneLayout.setVerticalGroup(
                total_stock_dialogContentPaneLayout.createParallelGroup()
                    .addGroup(total_stock_dialogContentPaneLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(total_stock_dialogContentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(solid_total_stock_price)
                            .addComponent(total_stock_price_change))
                        .addContainerGap(23, Short.MAX_VALUE))
            );
            total_stock_dialog.pack();
            total_stock_dialog.setLocationRelativeTo(total_stock_dialog.getOwner());
        }

        //======== area_total_highest_price_dialog ========
        {
            area_total_highest_price_dialog.setTitle("Area with highest total price");
            var area_total_highest_price_dialogContentPane = area_total_highest_price_dialog.getContentPane();

            //---- area_total_highest_price_solid ----
            area_total_highest_price_solid.setText("Area with highest total price:");

            GroupLayout area_total_highest_price_dialogContentPaneLayout = new GroupLayout(area_total_highest_price_dialogContentPane);
            area_total_highest_price_dialogContentPane.setLayout(area_total_highest_price_dialogContentPaneLayout);
            area_total_highest_price_dialogContentPaneLayout.setHorizontalGroup(
                area_total_highest_price_dialogContentPaneLayout.createParallelGroup()
                    .addGroup(area_total_highest_price_dialogContentPaneLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(area_total_highest_price_solid)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(area_total_highest_price_change)
                        .addContainerGap(168, Short.MAX_VALUE))
            );
            area_total_highest_price_dialogContentPaneLayout.setVerticalGroup(
                area_total_highest_price_dialogContentPaneLayout.createParallelGroup()
                    .addGroup(area_total_highest_price_dialogContentPaneLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(area_total_highest_price_dialogContentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(area_total_highest_price_solid)
                            .addComponent(area_total_highest_price_change))
                        .addContainerGap(33, Short.MAX_VALUE))
            );
            area_total_highest_price_dialog.pack();
            area_total_highest_price_dialog.setLocationRelativeTo(area_total_highest_price_dialog.getOwner());
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - unknown
    private JMenuBar menuBar;
    private JMenu file;
    private JMenuItem read;
    private JMenuItem write;
    private JMenu Area;
    private JMenu Category;
    private JMenu etc;
    private JMenuItem set_discount;
    private JMenuItem suggested_cart;
    private JMenuItem total_stock_price;
    private JMenuItem area_total_highest_price;
    private JMenuItem reset;
    private JScrollPane scrollPane1;
    private JList product_list;
    private JLabel product_name;
    private JLabel product_area;
    private JLabel original_price;
    private JLabel discount_price;
    private JLabel product_stock;
    private JLabel footer;
    private JButton change_stock;
    private JButton change_price;
    private JLabel price_selected_area;
    private JLabel price_selected_category;
    private JLabel op_changer;
    private JLabel dp_changer;
    private JLabel s_changer;
    private JLabel dateLabel;
    private JLabel selected_area_price;
    private JLabel selected_category_price;
    private JDialog change_stock_dialog;
    private JLabel solid_current_stock;
    private JSpinner current_stock;
    private JLabel solid_current_furniture;
    private JLabel current_furniture;
    private JDialog change_price_dialog;
    private JLabel solid_current_furniture_price;
    private JLabel current_furniture_price;
    private JLabel current_price;
    private JTextField price_to_change;
    private JDialog suggested_cart_dialog;
    private JLabel solid_cart_value;
    private JTextField cart_value;
    private JDialog remaining_value_dialog;
    private JLabel remaining_value;
    private JDialog discount_dialog;
    private JScrollPane scrollPane2;
    private JList discount_list;
    private JTextField discount_input;
    private JButton discount_button;
    private JDialog total_stock_dialog;
    private JLabel solid_total_stock_price;
    private JLabel total_stock_price_change;
    private JDialog area_total_highest_price_dialog;
    private JLabel area_total_highest_price_solid;
    private JLabel area_total_highest_price_change;
    // JFormDesigner - End of variables declaration  //GEN-END:variables


}
