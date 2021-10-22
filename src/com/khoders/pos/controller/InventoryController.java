package com.khoders.pos.controller;

import com.khoders.pos.dto.Inventory;
import com.khoders.pos.dto.Product;
import com.khoders.pos.services.InventoryService;
import com.khoders.pos.services.ProductPackageService;
import com.khoders.pos.utils.SystemUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class InventoryController implements Initializable {
    @FXML private TextArea txtDescriptionField;
    @FXML private TableView<Inventory> tableInventory;
    @FXML private TableColumn<Inventory, Integer> colQuantity;
    @FXML private TableColumn<Inventory, Double> colCostPrice;
    @FXML private TableColumn<Inventory, String> colInventoryId;
    @FXML private TableColumn<Inventory, Double> colSellingPrice;
    @FXML private TableColumn<Inventory, LocalDate> colPurchasedDate;
    @FXML private TableColumn<Inventory, String> colProductName;
    @FXML private TextField txtSellingPriceField;
    @FXML private TextField txtCostPriceField;
    @FXML private ComboBox<Product> comboBoxProduct;
    @FXML private DatePicker purchasedDate;
    @FXML private TextField txtQuantityField;

    ObservableList<Product> productList = FXCollections.observableArrayList();
    ObservableList<Inventory> inventoryList = FXCollections.observableArrayList();
    PreparedStatement preparedStatement=null;
    InventoryService inventoryService = new InventoryService();
    ProductPackageService packageService = new ProductPackageService();

    @FXML private TableColumn<Inventory, String> id;
    private String editingId;
    private String inventoryId;
    private String product;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadProducts();
        loadTableData();
        getValueFromTable();
    }

    @FXML
    public void saveInventory() {
        System.out.println("Editing ID => "+editingId);

        System.out.println("selected product => "+comboBoxProduct.getSelectionModel().getSelectedItem().getProductId());
        Inventory inventory = new Inventory(
                editingId,
                inventoryId,
                comboBoxProduct.getSelectionModel().getSelectedItem().getId(),
                Integer.parseInt(txtQuantityField.getText()),
                java.sql.Date.valueOf(purchasedDate.getValue()),
                Double.parseDouble(txtCostPriceField.getText()),
                Double.parseDouble(txtSellingPriceField.getText()),
                txtDescriptionField.getText()
        );
        preparedStatement = inventoryService.saveInventory(inventory);

        try {
            if(preparedStatement != null)
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Action executed successfully!");
                alert.showAndWait();

                fetchInventory();
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Oops! could not save inventory, please try again.");
                alert.showAndWait();
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    public void clearInventory() {
        txtCostPriceField.clear();
        txtDescriptionField.clear();
        txtQuantityField.clear();
        txtSellingPriceField.clear();
        editingId=null;

        loadTableData();
    }
    private void loadProducts() {
        comboBoxProduct.getItems().clear();
        productList = packageService.productList();
        comboBoxProduct.setItems(productList);
        comboBoxProduct.setConverter(new StringConverter<>() {

            @Override
            public String toString(Product product) {
                return product.getProductName();
            }

            @Override
            public Product fromString(String string) {
                return comboBoxProduct.getItems().stream().filter(item ->
                        item.getProductName().equals(string)).findFirst().orElse(null);
            }
        });
    }
    private void loadTableData() {
        fetchInventory();

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        colInventoryId.setCellValueFactory(new PropertyValueFactory<>("inventoryId"));
        colProductName.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colPurchasedDate.setCellValueFactory(new PropertyValueFactory<>("purchasedDate"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colCostPrice.setCellValueFactory(new PropertyValueFactory<>("costPrice"));
        colSellingPrice.setCellValueFactory(new PropertyValueFactory<>("sellingPrice"));
    }
    private void fetchInventory() {
        inventoryList.clear();
        inventoryList = inventoryService.getInventoryList();
        tableInventory.setItems(inventoryList);

    }

    public void getValueFromTable() {
        tableInventory.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Inventory inventory = tableInventory.getItems().get(tableInventory.getSelectionModel().getSelectedIndex());
                editingId = inventory.getId();
                inventoryId=inventory.getInventoryId();
                comboBoxProduct.setValue(new Product(inventory.getProductId(), null, inventory.getProductId(), null));
                txtQuantityField.setText(String.valueOf(inventory.getQuantity()));
                txtCostPriceField.setText(String.valueOf(inventory.getCostPrice()));
                txtSellingPriceField.setText(String.valueOf(inventory.getSellingPrice()));
                txtDescriptionField.setText(inventory.getDescription());
                purchasedDate.setValue(inventory.getPurchasedDate().toLocalDate());

                System.out.println("Inventory Id => "+inventoryId);
                System.out.println("Product Id => "+inventory.getProductId());
            }
        });
    }
}
