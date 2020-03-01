package de.legrinu.gui;

import de.legrinu.Main;
import de.legrinu.classes.Furniture;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;

public class ChangeStockFrame extends JDialog implements ActionListener {

    private JLabel label;
    private MainFrame mainFrame;
    private JButton change;
    private JPanel topPanel;
    private JPanel midPanel;
    private JTextField input;
    private JComboBox furnitureBox;
    private JPanel masterPanel;
    private ArrayList<String> furnitureArrayList;

    public ChangeStockFrame(){
        label = new JLabel();
        change = new JButton();
        topPanel = new JPanel();
        midPanel = new JPanel();
        masterPanel = new JPanel();
        input = new JTextField();
        furnitureArrayList = new ArrayList<>();

        for(Map.Entry<Integer, Furniture> entry : Main.getHardwareStore().entrySet()){
            furnitureArrayList.add(entry.getValue().getName());
        }

        Object furnitureArray[] = furnitureArrayList.toArray();
        furnitureBox = new JComboBox(furnitureArray);

        change.setText("Change");
        change.addActionListener(this);

        input.setToolTipText("Stock to change");
        input.setSize(96, 96);

        label.setText("Change Stock");

        masterPanel.setLayout(new BorderLayout());
        topPanel.setLayout(new BorderLayout());
        midPanel.setLayout(new BorderLayout());

        topPanel.add(label, BorderLayout.PAGE_END);

        midPanel.add(furnitureBox, BorderLayout.LINE_START);
        midPanel.add(input, BorderLayout.CENTER);
        midPanel.add(change, BorderLayout.PAGE_END);

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

        if(actionEvent.getSource() == change){
            String furnitureName = (String) furnitureBox.getSelectedItem();
            int changeBy = Integer.parseInt(input.getText());

            for(Map.Entry<Integer, Furniture> entry : Main.getHardwareStore().entrySet()){
                Furniture furniture = entry.getValue();
                if(furniture.getName().contains(furnitureName)){
                    furniture.setStock(furniture.getStock() + changeBy);
                    label.setText("Stock changed to " + furniture.getStock());
                }
            }
        }
    }
}
