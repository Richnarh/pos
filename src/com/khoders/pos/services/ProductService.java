package com.khoders.pos.services;

import com.khoders.pos.dto.Product;
import com.khoders.pos.dto.ProductType;
import com.khoders.pos.utils.DateUtils;
import com.khoders.pos.utils.DbUtils;
import com.khoders.pos.utils.SystemUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ProductService {
    Connection conn = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    public ProductService(){
        conn = DbUtils.init();
    }

    public ObservableList<ProductType> productTypeList()
    {
        ObservableList<ProductType> productTypes = FXCollections.observableArrayList();
        try
        {
            preparedStatement = conn.prepareStatement(DbUtils.selectQry("product_type"));
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                String id = resultSet.getString("id");
                String productType = resultSet.getString("product_type_name");
                productTypes.add(new ProductType(id, productType));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return productTypes;
    }

    public PreparedStatement saveProductType(String value)
    {
        try
        {
            preparedStatement = conn.prepareStatement(DbUtils.SAVE_PRODUCT_TYPE_QRY);
            preparedStatement.setString(1, SystemUtils.genId());
            preparedStatement.setString(2, value);
            preparedStatement.setDate(3, DateUtils.sqlDate());
            preparedStatement.execute();
        }catch (Exception e){
            e.printStackTrace();
        }

        return preparedStatement;
    }

    public PreparedStatement saveProduct(Product product) {
        try
        {
            preparedStatement = conn.prepareStatement(DbUtils.SAVE_PRODUCT_QRY);
            preparedStatement.setString(1, SystemUtils.genId());
            preparedStatement.setString(2, SystemUtils.generateCode());
            preparedStatement.setString(3, product.getProductName());
            preparedStatement.setString(4, product.getProductType());
            preparedStatement.setDate(5, DateUtils.sqlDate());
            preparedStatement.execute();

        }catch (Exception e){
            e.printStackTrace();
        }
        return preparedStatement;
    }

    public ObservableList<Product> getProductList(){
        ObservableList<Product> observableList = FXCollections.observableArrayList();
        try {
            preparedStatement = conn.prepareStatement(DbUtils.SELECT_PRODUCT_QRY);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                observableList.add(new Product(
                        null,
                        resultSet.getString("product_id"),
                        resultSet.getString("product_name"),
                        resultSet.getString("product_type_name")
                ));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return observableList;
    }
}
