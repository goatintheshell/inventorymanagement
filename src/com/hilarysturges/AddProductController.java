/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hilarysturges;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Hilary
 */
public class AddProductController implements Initializable {
    
    @FXML public Button cancelButton;
    
    @FXML public TextField idTF;
    @FXML public TextField nameTF;
    @FXML public TextField priceTF;
    @FXML public TextField inventoryTF;
    @FXML public TextField minTF;
    @FXML public TextField maxTF;
    @FXML public Button saveButton;
    
    public void saveButtonPushed(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("fxmlGUI.fxml"));
        Parent tableViewParent = loader.load();
        Scene tableViewScene = new Scene(tableViewParent);
        FxmlGUIController controller = loader.getController();
        Product product = new Product(Integer.parseInt(idTF.getText()),
                             nameTF.getText(),
                             Double.parseDouble(priceTF.getText()),
                             Integer.parseInt(inventoryTF.getText()),
                             Integer.parseInt(maxTF.getText()),
                             Integer.parseInt(minTF.getText()));
        controller.addProducts(product);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    public void cancelButtonPushed(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("fxmlGUI.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
