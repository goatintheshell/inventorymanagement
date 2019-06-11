/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hilarysturges;

import static com.hilarysturges.ModifyPartController.deletedPart;
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
public class ModifyProductController implements Initializable {

    @FXML public Button cancelButton;
    
    private Product selectedProduct;
    
    @FXML public TextField idProductTF;
    @FXML public TextField nameTF;
    @FXML public TextField priceTF;
    @FXML public TextField inventoryTF;
    @FXML public TextField maxTF;
    @FXML public TextField minTF;
    
    @FXML public Button saveButton;
    
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
    
    //static Product[] deletedProduct = new Product[1];
    
    static public ObservableList<Part> parts1=FXCollections.observableArrayList();
    static public ObservableList<Part> parts2=FXCollections.observableArrayList();
    
    static public ObservableList<Part> deletedParts=FXCollections.observableArrayList();
    
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
//        ObservableList<Part> selectedRows, allParts;
//        allParts = partTable2.getItems();
//        selectedRows = partTable2.getSelectionModel().getSelectedItems();
        //alert window    
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initModality(Modality.NONE);
            alert.setTitle("Delete Confirmation");
            alert.setHeaderText("Delete data");
            alert.setContentText("Are you sure you want to proceed?");
            //add if statement
            
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
//                    for (Part part : selectedRows) {
//                        
//                    allParts.remove(part);
//                    }
                    parts2.remove(partTable2.getSelectionModel().getSelectedItem());
                    deletedParts.add(partTable2.getSelectionModel().getSelectedItem());
//                    partTable2.setItems(selectedProduct.getAllAssociatedParts());
                    System.out.println("deleted");
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
        //selectedProduct.addAssociatedPart(part);
    }
    
    public void saveButtonPushed(ActionEvent event) throws IOException {
        for (int i=0 ; i < partTable2.getItems().size() ; i++){
            selectedProduct.addAssociatedPart(partTable2.getItems().get(i));
            //System.out.print("saved");
        }
        for (int i=0 ; i < deletedParts.size() ; i++) {
            selectedProduct.deleteAssociatedPart(deletedParts.get(i));
        }
        System.out.println(selectedProduct.getAllAssociatedParts());
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("fxmlGUI.fxml"));
        Parent tableViewParent = loader.load();
        Scene tableViewScene = new Scene(tableViewParent);
        FxmlGUIController controller = loader.getController();
        if (Integer.parseInt(minTF.getText()) > Integer.parseInt(maxTF.getText())) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initModality(Modality.NONE);
            alert.setTitle("Minimum > Maximum");
            alert.setHeaderText("Minimum is greater than maximum");
            alert.setContentText("Data will not be added");
            alert.showAndWait();
        } else {
        selectedProduct.setId(Integer.parseInt(idProductTF.getText()));
        selectedProduct.setName(nameTF.getText()); 
        selectedProduct.setPrice(Double.parseDouble(priceTF.getText()));
        selectedProduct.setStock(Integer.parseInt(inventoryTF.getText()));
        selectedProduct.setMax(Integer.parseInt(maxTF.getText()));
        selectedProduct.setMin(Integer.parseInt(minTF.getText()));
        

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show(); }
        
    }
    
    
    public void initData(Product product) {
        selectedProduct = product;
        idProductTF.setText(Integer.toString(selectedProduct.getId()));
        nameTF.setText(selectedProduct.getName());
        priceTF.setText(Double.toString(selectedProduct.getPrice()));
        inventoryTF.setText(Integer.toString(selectedProduct.getStock()));
        maxTF.setText(Integer.toString(selectedProduct.getMax()));
        minTF.setText(Integer.toString(selectedProduct.getMin()));
        //System.out.println(selectedProduct.getAllAssociatedParts());
        if (!selectedProduct.getAllAssociatedParts().isEmpty()) {
            partTable2.setItems(selectedProduct.getAllAssociatedParts());
            parts2 = partTable2.getItems();
            System.out.println(selectedProduct.getAllAssociatedParts());
        };
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
            //add if statement
            
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    //Scene tableViewScene = new Scene(tableViewParent);
                    Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                    window.setScene(tableViewScene);
                    window.show();
                }
            });
            //controller.addProducts(deletedProduct[0]);
    }
    
//    public void deletedAndModified(Product product) {
//        deletedProduct[0] = product;
//    }
    
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
        //partTable2.setItems();
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
