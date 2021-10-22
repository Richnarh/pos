package com.khoders.pos.services;

import com.khoders.pos.dto.Product;
import com.khoders.pos.dto.ProductPackage;
import com.khoders.pos.dto.UnitMeasurement;
import com.khoders.pos.utils.DateUtils;
import com.khoders.pos.utils.DbUtils;
import com.khoders.pos.utils.SystemUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ProductPackageService {
    Connection conn = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    public ProductPackageService(){
        conn = DbUtils.init();
    }

    public PreparedStatement saveUnits(String value) {
        try
        {
            preparedStatement = conn.prepareStatement(DbUtils.SAVE_UNIT_MEASUREMENT_QRY);
            preparedStatement.setString(1, SystemUtils.genId());
            preparedStatement.setString(2, value);
            preparedStatement.setDate(3, DateUtils.sqlDate());
            preparedStatement.execute();
        }catch (Exception e){
            e.printStackTrace();
        }
        return preparedStatement;
    }

    public ObservableList<Product> productList()
    {
        ObservableList<Product> products = FXCollections.observableArrayList();
        try
        {
            preparedStatement = conn.prepareStatement(DbUtils.selectQry("product"));
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                String id = resultSet.getString("id");
                String productName = resultSet.getString("product_name");
                products.add(new Product(id,null, productName,null));
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return products;
    }

    public ObservableList<UnitMeasurement> unitMeasurementList()
    {
        ObservableList<UnitMeasurement> observableList = FXCollections.observableArrayList();
        try
        {
            preparedStatement = conn.prepareStatement(DbUtils.selectQry("unit_measurement"));
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                String id = resultSet.getString("id");
                String units = resultSet.getString("units");
                observableList.add(new UnitMeasurement(id,units));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return observableList;
    }

    public PreparedStatement saveProductPackage(ProductPackage productPackage) {
        try
        {
            preparedStatement = conn.prepareStatement(DbUtils.SAVE_PRODUCT_PACKAGE_QRY);
            preparedStatement.setString(1, SystemUtils.genId());
            preparedStatement.setString(2, productPackage.getProductId());
            preparedStatement.setString(3, productPackage.getUnitId());
            preparedStatement.setString(4, productPackage.getPackageFactor());
            preparedStatement.setString(5, productPackage.getPackagePrice());
            preparedStatement.setDate(6, DateUtils.sqlDate());
            preparedStatement.execute();

        }catch (Exception e){
            e.printStackTrace();
        }
        return preparedStatement;
    }

    public ObservableList<ProductPackage> getProductPackageList(){
        ObservableList<ProductPackage> productPackageObservableList = FXCollections.observableArrayList();
        try {
            preparedStatement = conn.prepareStatement(DbUtils.SELECT_PRODUCT_PACKAGE_QRY);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                productPackageObservableList.add(new ProductPackage(
                        null,
                        resultSet.getString("product_name"),
                        resultSet.getString("units"),
                        resultSet.getString("package_factor"),
                        resultSet.getString("package_price")
                ));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return productPackageObservableList;
    }
}
