package de.legrinu.gui;

import de.legrinu.Main;
import de.legrinu.classes.Furniture;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;

public class GetInformationFrame extends JDialog implements ActionListener {

    private JLabel label;
    private MainFrame mainFrame;
    private JButton ok;
    private JPanel topPanel;
    private JPanel midPanel;
    private JComboBox furnitureComboBox;
    private JPanel masterPanel;
    private ArrayList<String> furnitureList;

    public GetInformationFrame(){
        label = new JLabel();
        ok = new JButton();
        topPanel = new JPanel();
        midPanel = new JPanel();
        masterPanel = new JPanel();
        furnitureList = new ArrayList<String>();

        for(Map.Entry<Integer, Furniture> entry : Main.getHardwareStore().entrySet()){
            furnitureList.add(entry.getValue().getName());
        }

        furnitureComboBox = new JComboBox(furnitureList.toArray());
        furnitureComboBox.addActionListener(this);

        ok.setText("OK");
        ok.addActionListener(this);

        label.setText("Get Informationen of...");

        masterPanel.setLayout(new BorderLayout());
        topPanel.setLayout(new BorderLayout());
        midPanel.setLayout(new BorderLayout());

        topPanel.add(label, BorderLayout.PAGE_START);

        midPanel.add(furnitureComboBox, BorderLayout.LINE_START);
        midPanel.add(ok, BorderLayout.PAGE_END);

        masterPanel.add(topPanel, BorderLayout.PAGE_START);
        masterPanel.add(midPanel, BorderLayout.CENTER);

        this.setSize(512, 512);
        this.setTitle("HSMS by BACreations");
        this.setMinimumSize(new Dimension(512, 512));
        this.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        this.add(masterPanel);
        this.pack();
        this.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == ok) {
            String furnitureName = (String) furnitureComboBox.getSelectedItem();

            for (Map.Entry<Integer, Furniture> entry : Main.getHardwareStore().entrySet()) {
                Furniture furniture = entry.getValue();
                if (furniture.getName().contains(furnitureName)) {
                    label.setText("Name: " + furniture.getName() + " | Price (Without Discount): " + furniture.getOriginalPrice()
                            + " | Price (With Discount): " + furniture.getDiscountPrice() + " | Stock: " + furniture.getStock()
                            + " | Area: " + furniture.getArea().getAreaName()
                            + " | Category: " + furniture.getCategory().getCategoryName()
                            + " | Stockprice: " + furniture.getOriginalStockPrice()
                            + " | Discount Stockprice: " + furniture.getDiscountStockPrice());
                }
            }
        }
    }
}
