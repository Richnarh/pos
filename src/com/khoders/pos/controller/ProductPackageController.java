package com.khoders.pos.controller;

import com.khoders.pos.dto.Product;
import com.khoders.pos.dto.ProductPackage;
import com.khoders.pos.dto.UnitMeasurement;
import com.khoders.pos.services.ProductPackageService;
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

public class ProductPackageController implements Initializable {
    @FXML private TextField txtPackagePriceField;
    @FXML private TextField txtPackageFactorField;
    @FXML private ComboBox<UnitMeasurement> comboBoxUnitMeasurement;
    @FXML private ComboBox<Product> comboBoxProduct;
    @FXML private TableView<ProductPackage> tableProductPackage;
    @FXML private TableColumn<ProductPackage, String> colPackageFactor;
    @FXML private TableColumn<ProductPackage, Double> colPackagePrice;
    @FXML private TableColumn<ProductPackage, String> colUnits;
    @FXML private TableColumn<ProductPackage, String> colProduct;
    @FXML private Button buttonSavePackage;
    @FXML private Button buttonAddUnits;
    @FXML private Button buttonReset;

    ObservableList<Product>  productList = FXCollections.observableArrayList();
    ObservableList<ProductPackage>  productPackageList = FXCollections.observableArrayList();
    ObservableList<UnitMeasurement>  unitMeasurementList = FXCollections.observableArrayList();
    ProductPackageService packageService = new ProductPackageService();
    PreparedStatement preparedStatement=null;

    @FXML
    public void saveProductPackage(ActionEvent event) {
        ProductPackage productPackage = new ProductPackage(
                null,
                comboBoxProduct.getSelectionModel().getSelectedItem().getId(),
                comboBoxUnitMeasurement.getSelectionModel().getSelectedItem().getId(),
                txtPackageFactorField.getText(),
                txtPackagePriceField.getText()
        );
        preparedStatement = packageService.saveProductPackage(productPackage);

        if(preparedStatement != null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Product package saved!");
            alert.showAndWait();

        productPackageList.add(new ProductPackage(
                null,
                comboBoxProduct.getSelectionModel().getSelectedItem().getProductName(),
                comboBoxUnitMeasurement.getSelectionModel().getSelectedItem().getUnits(),
                txtPackageFactorField.getText(),
                txtPackagePriceField.getText()
        ));
        }

    }

    @FXML
    public void resetPackage(ActionEvent event) {
        txtPackageFactorField.clear();
        txtPackagePriceField.clear();
    }

    @FXML
    public void addUniteMeasurement(ActionEvent event) {
        TextInputDialog textInputDialog = new TextInputDialog();
        textInputDialog.setTitle("Units");
        textInputDialog.setContentText("Enter Units");

        Optional<String> optional = textInputDialog.showAndWait();
        if(optional.isPresent())
        {
            preparedStatement = packageService.saveUnits(optional.get());
            try {
                if(preparedStatement != null)
                {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("Units saved successfully!");
                    alert.showAndWait();
                    loadUnitsMeasurement();
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Oops! could not save units measurement, please try again.");
                    alert.showAndWait();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadProducts();
        loadUnitsMeasurement();
        loadTableData();
    }

    private void loadProducts() {
        comboBoxProduct.getItems().clear();
        productList = packageService.productList();
        comboBoxProduct.setItems(productList);
        comboBoxProduct.setConverter(new StringConverter<Product>() {

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
    private void loadUnitsMeasurement() {
        comboBoxUnitMeasurement.getItems().clear();
        unitMeasurementList = packageService.unitMeasurementList();
        comboBoxUnitMeasurement.setItems(unitMeasurementList);
        comboBoxUnitMeasurement.setConverter(new StringConverter<UnitMeasurement>() {

            @Override
            public String toString(UnitMeasurement measurement) {
                return measurement.getUnits();
            }

            @Override
            public UnitMeasurement fromString(String string) {
                return comboBoxUnitMeasurement.getItems().stream().filter(item ->
                        item.getUnits().equals(string)).findFirst().orElse(null);
            }
        });
    }

    private void loadTableData() {
        fetchProductPackages();

        colProduct.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colPackageFactor.setCellValueFactory(new PropertyValueFactory<>("packageFactor"));
        colPackagePrice.setCellValueFactory(new PropertyValueFactory<>("packagePrice"));
        colUnits.setCellValueFactory(new PropertyValueFactory<>("unitId"));
    }

    private void fetchProductPackages() {
        productPackageList.clear();
        productPackageList = packageService.getProductPackageList();
        tableProductPackage.setItems(productPackageList);
    }

    public void selectedProductAction(ActionEvent event) {
    }
}
