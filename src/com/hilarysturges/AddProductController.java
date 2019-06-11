/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hilarysturges;

import static com.hilarysturges.FxmlGUIController.partslist;
import java.io.IOException;
import java.math.RoundingMode;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

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
    
    private Product selectedProduct;
    
    @FXML public TextField searchBarParts;
    
    @FXML public TableView<Part> partTable1;
    @FXML public TableView<Part> partTable2;
    
    @FXML public TableColumn<Part, Integer> id1Column;
    @FXML public TableColumn<Part, String> name1Column;
    @FXML public TableColumn<Part, Integer> inventory1Column;
    @FXML public TableColumn price1Column;
    
    @FXML public TableColumn<Part, Integer> id2Column;
    @FXML public TableColumn<Part, String> name2Column;
    @FXML public TableColumn<Part, Integer> inventory2Column;
    @FXML public TableColumn price2Column;
    
    static public ObservableList<Part> parts1=FXCollections.observableArrayList();
    static public ObservableList<Part> parts2=FXCollections.observableArrayList();
    
    static int counter=4;
    
    public void searchPartsButtonPushed() {
        FilteredList<Part> filteredParts = new FilteredList<>(FxmlGUIController.partslist, p -> true);
        searchBarParts.textProperty().addListener((observable, oldValue, newValue)-> {
            filteredParts.setPredicate(part -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (part.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });
        SortedList<Part> sortedParts = new SortedList<>(filteredParts);
        sortedParts.comparatorProperty().bind(partTable1.comparatorProperty());
        partTable1.setItems(sortedParts);
    }
    
    public void deletePartButtonPushed() {
        ObservableList<Part> selectedRows, allParts;
        allParts = partTable2.getItems();
        selectedRows = partTable2.getSelectionModel().getSelectedItems();
        //alert window    
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initModality(Modality.NONE);
            alert.setTitle("Delete Confirmation");
            alert.setHeaderText("Delete data");
            alert.setContentText("Are you sure you want to proceed?");
            //add if statement
            
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    for (Part part : selectedRows) {
                    allParts.remove(part);
                    }
                }
            });
    }
    
    public void addButtonPushed(ActionEvent event) { 
        addData(partTable1.getSelectionModel().getSelectedItem());   
    }
    
    public void addData(Part part) {
        TablePosition pos = partTable1.getSelectionModel().getSelectedCells().get(0);
        int row = pos.getRow();
        parts2 = partTable2.getItems();
        parts2.add((partTable1.getItems().get(row)));
    }
    
    public void saveButtonPushed(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("fxmlGUI.fxml"));
        Parent tableViewParent = loader.load();
        Scene tableViewScene = new Scene(tableViewParent);
        FxmlGUIController controller = loader.getController();
        Product product = new Product(counter,
                             nameTF.getText(),
                             Double.parseDouble(priceTF.getText()),
                             Integer.parseInt(inventoryTF.getText()),
                             Integer.parseInt(maxTF.getText()),
                             Integer.parseInt(minTF.getText()));
        counter++;
        
          if (Integer.parseInt(minTF.getText()) > Integer.parseInt(maxTF.getText())) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initModality(Modality.NONE);
            alert.setTitle("Minimum > Maximum");
            alert.setHeaderText("Minimum is greater than maximum");
            alert.setContentText("Data will not be added");
            alert.showAndWait();
        } else {
        controller.addProducts(product);
        
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show(); }
        
    }

    public void cancelButtonPushed(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("fxmlGUI.fxml"));        
        //alert window    
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initModality(Modality.NONE);
            alert.setTitle("Delete Confirmation");
            alert.setHeaderText("Cancel");
            alert.setContentText("Are you sure you want to proceed?");
            //add if statement
            
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    Scene tableViewScene = new Scene(tableViewParent);
                    Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                    window.setScene(tableViewScene);
                    window.show();
                }
            });
    }

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //parts
        id1Column.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        name1Column.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getName()));
        inventory1Column.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getStock()).asObject());
        
        price1Column.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
            price1Column.setCellFactory(new Callback<TableColumn, TableCell>() {
            public TableCell call(TableColumn p) {
                TableCell cell = new TableCell<Part, Double>() {
                    @Override
                    public void updateItem(Double item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(empty ? null : getString());
                        setGraphic(null);
                    }

                    private String getString() {
                        String ret = "$";
                        if (getItem() != null) {
                            String gi = getItem().toString();
                            NumberFormat df = DecimalFormat.getInstance();
                            df.setMinimumFractionDigits(2);
                            df.setRoundingMode(RoundingMode.DOWN);

                            ret += df.format(Double.parseDouble(gi));
                        } else {
                            ret = "$0.00";
                        }
                        return ret;
                    }
                };
                return cell;
            }
        });
        
        partTable1.setItems(FxmlGUIController.partslist);
        name1Column.setCellFactory(TextFieldTableCell.forTableColumn());
        partTable1.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        
        //products
        id2Column.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        name2Column.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getName()));
        inventory2Column.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getStock()).asObject());
        
        price2Column.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
            price2Column.setCellFactory(new Callback<TableColumn, TableCell>() {
            public TableCell call(TableColumn p) {
                TableCell cell = new TableCell<Part, Double>() {
                    @Override
                    public void updateItem(Double item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(empty ? null : getString());
                        setGraphic(null);
                    }

                    private String getString() {
                        String ret = "$";
                        if (getItem() != null) {
                            String gi = getItem().toString();
                            NumberFormat df = DecimalFormat.getInstance();
                            df.setMinimumFractionDigits(2);
                            df.setRoundingMode(RoundingMode.DOWN);

                            ret += df.format(Double.parseDouble(gi));
                        } else {
                            ret = "$0.00";
                        }
                        return ret;
                    }
                };
                return cell;
            }
        });
    }    
    
}
