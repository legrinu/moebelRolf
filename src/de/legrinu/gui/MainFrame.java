package de.legrinu.gui;

import de.legrinu.datamanagement.FileManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame implements ActionListener {

    private JComboBox comboBox;
    private JPanel panel;
    private String[] activities;
    private JLabel label;

    public MainFrame(){

        activities = new String[]{"Get Information", "Change Stock", "Change Price", "Get Total Price", "Set Discount",
                "Get Furniture for specified price"};
        comboBox = new JComboBox(activities);
        panel = new JPanel();
        label = new JLabel();

        label.setText("Please choose the action to perform.");

        comboBox.addActionListener(this);

        panel.add(comboBox);
        panel.add(label);

        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                FileManager.saveFiles();
                System.exit(0);
            }});

        this.setSize(512, 512);
        this.setTitle("HSMS by BACreations");
        this.setMinimumSize(new Dimension(512, 512));
        this.add(panel);
        this.pack();
        this.setLocationRelativeTo(null);
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(actionEvent.getSource() == this.comboBox){
            String selectedItem = (String) comboBox.getSelectedItem();

            switch(selectedItem){
                case "Change Stock":
                    ChangeStockFrame changeStockFrame = new ChangeStockFrame();
                    changeStockFrame.setVisible(true);
                    break;
                case "Change Price":
                    ChangePriceFrame changePriceFrame = new ChangePriceFrame();
                    changePriceFrame.setVisible(true);
                    break;
                case "Get Total Price":
                    TotalPriceFrame totalPriceFrame = new TotalPriceFrame();
                    totalPriceFrame.setVisible(true);
                    break;
                case "Get Furniture for specified price":
                    GetForMoneyFrame getForMoneyFrame = new GetForMoneyFrame();
                    getForMoneyFrame.setVisible(true);
                    break;
                case "Get Information":
                    GetInformationFrame getInformationFrame = new GetInformationFrame();
                    getInformationFrame.setVisible(true);
                    break;
                case "Set Discount":
                    SetDisountFrame setDisountFrame = new SetDisountFrame();
                    setDisountFrame.setVisible(true);
                    break;
                default:
                    label.setText("Some serious issues happened. Please contact your local IT guy.");
            }
        }
    }

    public JComboBox getComboBox() {
        return comboBox;
    }

    public void setComboBox(JComboBox comboBox) {
        this.comboBox = comboBox;
    }
}
