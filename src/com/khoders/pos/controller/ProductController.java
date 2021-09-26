package com.khoders.pos.controller;

import com.khoders.pos.dto.Product;
import com.khoders.pos.dto.ProductType;
import com.khoders.pos.services.ProductService;
import com.khoders.pos.utils.SystemUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
    @FXML private Button buttonSaveProduct;
    @FXML private Button buttonClear;
    @FXML private ComboBox<ProductType> comboBoxProductType;
    @FXML private TableView<Product> tableProduct;
    @FXML private TableColumn<Product, String> colProductId;
    @FXML private TableColumn<Product, String> colProductName;
    @FXML private TableColumn<Product, String> colProductType;

    ObservableList<ProductType> productTypeList = FXCollections.observableArrayList();
    ObservableList<Product> productList = FXCollections.observableArrayList();
    ProductService productService = new ProductService();
    PreparedStatement preparedStatement=null;

    @FXML public void saveProduct(ActionEvent event) {
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
    }

    @FXML public void clearProduct(ActionEvent event) {
        txtProductNameField.clear();
    }

    @FXML public void AddProductType(ActionEvent event) {
        TextInputDialog textInputDialog = new TextInputDialog();
        textInputDialog.setTitle("Product Type");
        textInputDialog.setContentText("Enter product type");

        Optional<String> optional = textInputDialog.showAndWait();
        if(optional.isPresent())
        {
            preparedStatement = productService.saveProductType(optional.get());
            if(preparedStatement != null){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Product Type saved!");
                alert.showAndWait();
            }
            loadProductType();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setTableData();
        loadProductType();
    }

    private void loadProductType() {
        comboBoxProductType.getItems().clear();
        try
        {
            ResultSet resultSet = productService.productTypeList();
            while (resultSet.next())
            {
                String id = resultSet.getString("id");
                String productType = resultSet.getString("product_type_name");
                productTypeList.add(new ProductType(id, productType));
            }
            comboBoxProductType.setItems(productTypeList);
            comboBoxProductType.setConverter(new StringConverter<ProductType>() {

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

    private void setTableData() {
        fetchProducts();

        colProductId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        colProductType.setCellValueFactory(new PropertyValueFactory<>("productType"));
    }

    private void fetchProducts() {
        productList.clear();
        try{
            ResultSet resultSet = productService.getProductList();
            while (resultSet.next()){
                productList.add(new Product(
                        null,
                        resultSet.getString("product_id"),
                        resultSet.getString("product_name"),
                        resultSet.getString("product_type_name")
                ));
                tableProduct.setItems(productList);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
