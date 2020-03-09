package de.legrinu.gui;

import de.legrinu.HardwareStore;
import de.legrinu.classes.Area;
import de.legrinu.classes.Category;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class TotalPriceFrame extends JDialog implements ActionListener {

    private JLabel label;
    private MainFrame mainFrame;
    private JButton ok;
    private JPanel topPanel;
    private JPanel midPanel;
    private JComboBox chooseBox;
    private JComboBox areaContentBox;
    private JComboBox categoryContentBox;
    private JPanel masterPanel;
    private ArrayList<String> areaList;
    private ArrayList<String> categoryList;
    private HardwareStore hardwareStore = MainFrame.getHardwareStore();

    public TotalPriceFrame(){
        label = new JLabel();
        ok = new JButton();
        topPanel = new JPanel();
        midPanel = new JPanel();
        masterPanel = new JPanel();
        areaList = new ArrayList<String>();
        categoryList = new ArrayList<String>();

        for(Area area : hardwareStore.getAreaList()){
            areaList.add(area.getAreaName());
        }
        for(Category category : hardwareStore.getCategoryList()){
            categoryList.add(category.getCategoryName());
        }

        areaContentBox = new JComboBox(areaList.toArray());
        areaContentBox.addActionListener(this);

        categoryContentBox = new JComboBox(categoryList.toArray());
        categoryContentBox.addActionListener(this);

        chooseBox = new JComboBox(new String[]{"Stock", "Area", "Category"});
        chooseBox.addActionListener(this);

        ok.setText("OK");
        ok.addActionListener(this);

        label.setText("Get total price of...");

        masterPanel.setLayout(new BorderLayout());
        topPanel.setLayout(new BorderLayout());
        midPanel.setLayout(new BorderLayout());

        topPanel.add(label, BorderLayout.PAGE_START);

        midPanel.add(chooseBox, BorderLayout.PAGE_START);
        midPanel.add(areaContentBox, BorderLayout.LINE_START);
        midPanel.add(categoryContentBox, BorderLayout.LINE_END);
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

        if(actionEvent.getSource() == ok) {
            String selectedChooseItem = (String) chooseBox.getSelectedItem();

            if (selectedChooseItem.equals("Area")) {
                String selectedAreaItem = (String) areaContentBox.getSelectedItem();

                for(Area area : hardwareStore.getAreaList()){
                    if(area.getAreaName().contains(selectedAreaItem)){
                        label.setText("Total area price of area " + area.getAreaName() + ": " + hardwareStore.totalAreaPrice(area));
                    }
                }
            } else if (selectedChooseItem.equals("Category")) {
                String selectedCategoryItem = (String) categoryContentBox.getSelectedItem();

                for(Category category : hardwareStore.getCategoryList()){
                    if(category.getCategoryName().contains(selectedCategoryItem)){
                        label.setText("Total area price of area " + category.getCategoryName() + ": " +
                                hardwareStore.totalCategoryPrice(category));
                    }
                }
            } else {
                label.setText("Total stock price: " + hardwareStore.totalStockPrice());
            }
        }
    }

}
