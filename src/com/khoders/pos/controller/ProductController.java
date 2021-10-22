package com.khoders.pos.controller;

import com.khoders.pos.dto.Product;
import com.khoders.pos.dto.ProductType;
import com.khoders.pos.services.ProductService;
import com.khoders.pos.utils.SystemUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.ResourceBundle;

public class ProductController implements Initializable {
    @FXML private TextField txtProductNameField;
    @FXML private ComboBox<ProductType> comboBoxProductType;
    @FXML private TableView<Product> tableProduct;
    @FXML private TableColumn<Product, String> colProductId;
    @FXML private TableColumn<Product, String> colProductName;
    @FXML private TableColumn<Product, String> colProductType;

    ObservableList<ProductType> productTypeList = FXCollections.observableArrayList();
    ObservableList<Product> productList = FXCollections.observableArrayList();
    ProductService productService = new ProductService();
    PreparedStatement preparedStatement=null;

    @FXML public void saveProduct() {
        Product product = new Product(null,null,
                txtProductNameField.getText(),
                comboBoxProductType.getSelectionModel().getSelectedItem().getId()
        );
        preparedStatement = productService.saveProduct(product);
        if(preparedStatement != null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Product saved!");
            alert.showAndWait();

            productList.add(new Product(null,
                    SystemUtils.generateCode(),
                    txtProductNameField.getText(),
                    comboBoxProductType.getSelectionModel().getSelectedItem().getProductTypeName()
            ));
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Oops! could not save product, please try again.");
            alert.showAndWait();
        }
    }

    @FXML public void clearProduct() {
        txtProductNameField.clear();
    }

    @FXML public void AddProductType() {
        TextInputDialog textInputDialog = new TextInputDialog();
        textInputDialog.setTitle("Product Type");
        textInputDialog.setContentText("Enter product type");

        Optional<String> optional = textInputDialog.showAndWait();
        if(optional.isPresent())
        {
            preparedStatement = productService.saveProductType(optional.get());
            try {
                if(preparedStatement != null){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("Product Type saved!");
                    alert.showAndWait();
                loadProductType();
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Oops! could not save product type, please try again.");
                    alert.showAndWait();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadTableData();
        loadProductType();
    }

    private void loadProductType() {
        comboBoxProductType.getItems().clear();
        try
        {
            productTypeList = productService.productTypeList();

            comboBoxProductType.setItems(productTypeList);
            comboBoxProductType.setConverter(new StringConverter<>() {

                @Override
                public String toString(ProductType productType) {
                    return productType.getProductTypeName();
                }

                @Override
                public ProductType fromString(String string) {
                    return comboBoxProductType.getItems().stream().filter(item ->
                            item.getProductTypeName().equals(string)).findFirst().orElse(null);
                }
            });

        }catch (Exception e)
        {
           e.printStackTrace();
        }
    }

    private void loadTableData() {
        fetchProducts();

        colProductId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        colProductType.setCellValueFactory(new PropertyValueFactory<>("productType"));
    }

    private void fetchProducts() {
        productList.clear();
        productList = productService.getProductList();
        tableProduct.setItems(productList);
    }
}
