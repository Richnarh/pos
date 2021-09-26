package com.khoders.pos.services;

import com.khoders.pos.dto.ProductPackage;
import com.khoders.pos.utils.DateUtils;
import com.khoders.pos.utils.DbUtils;
import com.khoders.pos.utils.SystemUtils;

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

    public ResultSet productList()
    {
        try
        {
            preparedStatement = conn.prepareStatement(DbUtils.selectQry("product"));
            resultSet = preparedStatement.executeQuery();

        }catch (Exception e){
            e.printStackTrace();
        }

        return resultSet;
    }

    public ResultSet unitMeasurementList()
    {
        try
        {
            preparedStatement = conn.prepareStatement(DbUtils.selectQry("unit_measurement"));
            resultSet = preparedStatement.executeQuery();

        }catch (Exception e){
            e.printStackTrace();
        }

        return resultSet;
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

    public ResultSet getProductPackageList(){
        try {
            preparedStatement = conn.prepareStatement(DbUtils.SELECT_PRODUCT_PACKAGE_QRY);
            resultSet = preparedStatement.executeQuery();
        }catch (Exception e){
            e.printStackTrace();
        }
        return resultSet;
    }
}
