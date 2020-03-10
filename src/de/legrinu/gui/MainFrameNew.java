/*
 * Created by JFormDesigner on Tue Mar 10 14:05:25 CET 2020
 */

package de.legrinu.gui;

import java.awt.*;
import javax.swing.event.*;
import de.legrinu.Main;
import de.legrinu.classes.Area;
import de.legrinu.classes.Category;
import de.legrinu.classes.Furniture;

import java.awt.event.*;
import java.util.Map;
import javax.swing.*;
import javax.swing.GroupLayout;

/**
 * @author unknown
 */
public class MainFrameNew extends JFrame {
    public MainFrameNew() {
        initComponents();
        //initShit();
    }

    private void change_stockActionPerformed(ActionEvent e) {
        //TODO: Create JDialog
    }

    private void change_priceActionPerformed(ActionEvent e) {
        //TODO: Create JDIalog
    }

    private void createUIComponents() {
        // TODO: add custom component creation code here
    }

    private void product_listValueChanged(ListSelectionEvent e) {
        int selectedIndex = product_list.getSelectedIndex();
        Furniture selectedFurniture = Main.getHardwareStore().getHardwareStoreMap().get(selectedIndex + 1);

        product_name.setText(selectedFurniture.getName());
        product_area.setText(selectedFurniture.getArea().getAreaName() + " | " + selectedFurniture.getCategory().getCategoryName());
        op_changer.setText(selectedFurniture.getOriginalPrice() + " €");
        dp_changer.setText(selectedFurniture.getDiscountPrice() + " €");
        s_changer.setText(selectedFurniture.getStock() + "");
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
        scrollPane1 = new JScrollPane();
        String[] stringArray = new String[Main.getHardwareStore().getHardwareStoreMap().size()];
                            for(int i = 0; i < Main.getHardwareStore().getHardwareStoreMap().size(); i++){
                                    Furniture furniture = Main.getHardwareStore().getHardwareStoreMap().get(i+1);
                                    stringArray[i] = furniture.getName();
                            }
        product_list = new JList(stringArray);
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

        //======== this ========
        setTitle("HSMS by BACreations");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setIconImage(new ImageIcon("C:\\Users\\legri\\Documents\\BauMarkt\\ressources\\BAC_transparent.png").getImage());
        var contentPane = getContentPane();

        //======== menuBar ========
        {

            //======== file ========
            {
                file.setText("File");

                //---- read ----
                read.setText("Read");
                file.add(read);

                //---- write ----
                write.setText("Write");
                file.add(write);
            }
            menuBar.add(file);

            //======== Area ========
            {
                Area.setText("Area");
                for(de.legrinu.classes.Area area : Main.getHardwareStore().getAreaList()){
                            Area.add(new JCheckBoxMenuItem(area.getAreaName()));
                        }
            }
            menuBar.add(Area);

            //======== Category ========
            {
                Category.setText("Category");
                for(de.legrinu.classes.Category category : Main.getHardwareStore().getCategoryList()){
                            Category.add(new JCheckBoxMenuItem(category.getCategoryName()));
                        }
            }
            menuBar.add(Category);

            //======== etc ========
            {
                etc.setText("Etc.");
            }
            menuBar.add(etc);
        }
        setJMenuBar(menuBar);

        //======== scrollPane1 ========
        {

            //---- product_list ----
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
        price_selected_area.setText("Price of selected Area:");

        //---- price_selected_category ----
        price_selected_category.setText("Price of selected Category: ");

        //---- dateLabel ----
        dateLabel.setText("Alea iacta est");
        dateLabel.setHorizontalAlignment(SwingConstants.RIGHT);

                dateLabel.setText("" + java.time.LocalDate.now());

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addGap(83, 83, 83)
                    .addComponent(price_selected_area)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
                    .addComponent(price_selected_category)
                    .addGap(37, 37, 37))
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addGap(17, 17, 17)
                    .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 137, GroupLayout.PREFERRED_SIZE)
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
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
                            .addGap(29, 29, 29)
                            .addComponent(change_stock)
                            .addGap(18, 18, 18)
                            .addComponent(change_price)
                            .addContainerGap(28, Short.MAX_VALUE))))
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addComponent(footer, GroupLayout.PREFERRED_SIZE, 217, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(dateLabel, GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE))
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
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(price_selected_area)
                        .addComponent(price_selected_category))
                    .addGap(18, 18, 18)
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addComponent(footer)
                        .addComponent(dateLabel)))
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }


    private void initShit(){
        //======== product_list ========

        {
            for(Map.Entry<Integer, Furniture> entry : Main.getHardwareStore().getHardwareStoreMap().entrySet()){
                Furniture furniture = entry.getValue();
                product_list.add(new JLabel(furniture.getName()));
            }

            scrollPane1.add(product_list);
        }
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
    // JFormDesigner - End of variables declaration  //GEN-END:variables


}
