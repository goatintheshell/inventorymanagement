/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hilarysturges;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Hilary
 */
public class ModifyPartController implements Initializable {

    @FXML public Button cancelButton;
    
    @FXML public RadioButton outsourcedButton;
    @FXML public RadioButton inHouseButton;
    @FXML public ToggleGroup radioButtonToggle;
    
    @FXML public Label machineAndCompLabel;
    @FXML public TextField machineAndCompTF;
    
    private Part selectedPart;
    private InHouse selectedInHouse;
    private Outsourced selectedOutsourced;
    
    @FXML public TextField idPartTF;
    @FXML public TextField nameTF;
    @FXML public TextField priceTF;
    @FXML public TextField inventoryTF;
    @FXML public TextField minTF;
    @FXML public TextField maxTF;
    
    @FXML public Button saveButton;
    
    static Part[] deletedPart = new Part[1];
    
    public void saveButtonPushed(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("fxmlGUI.fxml"));
        Parent tableViewParent = loader.load();
        Scene tableViewScene = new Scene(tableViewParent);
        FxmlGUIController controller = loader.getController();
        //alert logic here
          if (Integer.parseInt(minTF.getText()) > Integer.parseInt(maxTF.getText())) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initModality(Modality.NONE);
            alert.setTitle("Minimum > Maximum");
            alert.setHeaderText("Minimum is greater than maximum");
            alert.setContentText("Data will not be added");
            alert.showAndWait();
        } else {
              if (selectedInHouse != null) {
                  if (this.radioButtonToggle.getSelectedToggle().equals(this.outsourcedButton)) {
            Outsourced part = new Outsourced(Integer.parseInt(idPartTF.getText()),
                             nameTF.getText(),
                             Double.parseDouble(priceTF.getText()),
                             Integer.parseInt(inventoryTF.getText()),
                             Integer.parseInt(maxTF.getText()),
                             Integer.parseInt(minTF.getText()),
                             machineAndCompTF.getText());
            controller.addParts(part);
            controller.deleteChangedPart(selectedPart);
                  } else {
            selectedInHouse.setId(Integer.parseInt(idPartTF.getText()));
            selectedInHouse.setName(nameTF.getText()); 
            selectedInHouse.setPrice(Double.parseDouble(priceTF.getText()));
            selectedInHouse.setStock(Integer.parseInt(inventoryTF.getText()));
            selectedInHouse.setMax(Integer.parseInt(maxTF.getText()));
            selectedInHouse.setMin(Integer.parseInt(minTF.getText()));
            selectedInHouse.setMachineId(Integer.parseInt(machineAndCompTF.getText()));
            System.out.println("in house"); 
                  }
        } else {
                 if (this.radioButtonToggle.getSelectedToggle().equals(this.inHouseButton)) {
                     InHouse part = new InHouse(Integer.parseInt(idPartTF.getText()),
                             nameTF.getText(),
                             Double.parseDouble(priceTF.getText()),
                             Integer.parseInt(inventoryTF.getText()),
                             Integer.parseInt(maxTF.getText()),
                             Integer.parseInt(minTF.getText()),
                             Integer.parseInt(machineAndCompTF.getText()));
            controller.addParts(part);
            controller.deleteChangedPart(selectedPart);
                 } else {
            selectedOutsourced.setId(Integer.parseInt(idPartTF.getText()));
            selectedOutsourced.setName(nameTF.getText()); 
            selectedOutsourced.setPrice(Double.parseDouble(priceTF.getText()));
            selectedOutsourced.setStock(Integer.parseInt(inventoryTF.getText()));
            selectedOutsourced.setMax(Integer.parseInt(maxTF.getText()));
            selectedOutsourced.setMin(Integer.parseInt(minTF.getText()));
            selectedOutsourced.setCompanyName(machineAndCompTF.getText());
            System.out.println("outsourced"); }
        }
        
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
        }
    }
    
    
    public void initData(Part part) {
        selectedPart = part;
        idPartTF.setText(Integer.toString(selectedPart.getId()));
        nameTF.setText(selectedPart.getName());
        priceTF.setText(Double.toString(selectedPart.getPrice()));
        inventoryTF.setText(Integer.toString(selectedPart.getStock()));
        minTF.setText(Integer.toString(selectedPart.getMin()));
        maxTF.setText(Integer.toString(selectedPart.getMax()));
        
        if (part instanceof InHouse){
        selectedInHouse = (InHouse)part;
        machineAndCompTF.setVisible(true);
        machineAndCompLabel.setText("Machine ID");
        machineAndCompTF.setText(Integer.toString(selectedInHouse.getMachineId()));
        inHouseButton.setSelected(true);
        }
        else if (part instanceof Outsourced) {
            selectedOutsourced = (Outsourced)part;
            machineAndCompTF.setVisible(true);
            machineAndCompLabel.setText("Company Name");
            machineAndCompTF.setText(selectedOutsourced.getCompanyName());
            outsourcedButton.setSelected(true);
        }
    }

    public void cancelButtonPushed(ActionEvent event) throws IOException {
        //Parent tableViewParent = FXMLLoader.load(getClass().getResource("fxmlGUI.fxml"));
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("fxmlGUI.fxml"));
        Parent tableViewParent = loader.load();
        Scene tableViewScene = new Scene(tableViewParent);
        FxmlGUIController controller = loader.getController();
        //alert window    
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initModality(Modality.NONE);
            alert.setTitle("Delete Confirmation");
            alert.setHeaderText("Cancel");
            alert.setContentText("Are you sure you want to proceed?");
            
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    //Scene tableViewScene = new Scene(tableViewParent);
                    Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                    window.setScene(tableViewScene);
                    window.show();
                }
            });
            //controller.addParts(deletedPart[0]);
    }
    
    public void deletedAndModified(Part part) {
        deletedPart[0] = part;
    }
    
    public void radioButtonSelected() {
        if (this.radioButtonToggle.getSelectedToggle().equals(this.inHouseButton)) {
            //make machine ID visible
            machineAndCompTF.setVisible(true);
            machineAndCompLabel.setText("Machine ID");
            //selectedPart = (InHouse)selectedPart;
            //machineAndCompTF.setText(Integer.toString(selectedInHouse.getMachineId())); 
        }
        if (this.radioButtonToggle.getSelectedToggle().equals(this.outsourcedButton)) {
            //make company name visible
            machineAndCompTF.setVisible(true);
            machineAndCompLabel.setText("Company Name");
            //machineAndCompTF.setText(selectedOutsourced.getCompanyName()); 
        }
    }

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        radioButtonToggle = new ToggleGroup();
        this.inHouseButton.setToggleGroup(radioButtonToggle);
        this.outsourcedButton.setToggleGroup(radioButtonToggle);
    }    
    
}

