package com.khoders.pos.utils;

import com.khoders.pos.config.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DbUtils {
   static DatabaseConnection databaseConnection = null;
   static Connection conn =null;

    public DbUtils(){

    }

    public static Connection init(){
        databaseConnection = new DatabaseConnection();
        conn = databaseConnection.getConnectionInstance();
        return conn;
    }

    public static String selectQry(String tableName)
    {
        return "SELECT * FROM "+tableName;
    }

    public static final String LOGIN_QRY = "SELECT * FROM user_account WHERE username = ? AND password = ?";
    public static final String SAVE_PRODUCT_PACKAGE_QRY = "INSERT INTO product_package(id, product, unit_measurement, package_factor, package_price, value_date) VALUES(?,?,?,?,?,?)";
    public static final String SAVE_UNIT_MEASUREMENT_QRY = "INSERT INTO unit_measurement(id, units, value_date) VALUES(?,?,?)";
    public static final String SELECT_PRODUCT_PACKAGE_QRY = "SELECT p.`product_name`, pp.`package_factor`, pp.`package_price`, u.`units` FROM `product_package` pp JOIN `product` p ON pp.`product`=p.`id` JOIN `unit_measurement` u ON pp.`unit_measurement`=u.`id`";
    public static final String SAVE_PRODUCT_QRY = "INSERT INTO product(id, product_id, product_name, product_type, value_date) VALUES(?,?,?,?,?)";
    public static final String SELECT_PRODUCT_QRY = "SELECT p.`product_id`, p.`product_name`, pt.`product_type_name` FROM `product` p JOIN `product_type` pt ON p.`product_type`=pt.`id`";
    public static final String SAVE_PRODUCT_TYPE_QRY = "INSERT INTO product_type(id, product_type_name, value_date) VALUES(?,?,?)";
}
