package com.khoders.pos.services;

import com.khoders.pos.dto.Inventory;
import com.khoders.pos.utils.DateUtils;
import com.khoders.pos.utils.DbUtils;
import com.khoders.pos.utils.SystemUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class InventoryService {
    Connection conn;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    public InventoryService(){ conn = DbUtils.init(); }

    public PreparedStatement saveInventory(Inventory inventory) {
        System.out.println("inventory Id => "+inventory.getInventoryId());
        System.out.println("ProductId => "+inventory.getProductId());
        try
        {
            if(inventory.getId() != null){
                String query = "UPDATE inventory SET id=?, inventory_id=?, product=?, quantity=?, purchased_date=?, cost_price=?, selling_price=?, description=?, last_modified_date=? WHERE id='"+inventory.getId()+"'";
                preparedStatement = conn.prepareStatement(query);
                preparedStatement.setString(1, inventory.getId());
                preparedStatement.setString(2, inventory.getInventoryId());
                System.out.println("over here");
            }
            else
            {
                System.out.println("saving inventory");
                preparedStatement = conn.prepareStatement(DbUtils.SAVE_INVENTORY_QRY);
                preparedStatement.setString(1, SystemUtils.genId());
                preparedStatement.setString(2, SystemUtils.generateCode());
            }

            System.out.println("continuing from here");
            preparedStatement.setString(3, inventory.getProductId());
            preparedStatement.setInt(4, inventory.getQuantity());
            preparedStatement.setDate(5, inventory.getPurchasedDate());
            preparedStatement.setDouble(6, inventory.getCostPrice());
            preparedStatement.setDouble(7, inventory.getSellingPrice());
            preparedStatement.setString(8, inventory.getDescription());
            preparedStatement.setDate(9, DateUtils.sqlDate());
            preparedStatement.execute();
        }catch (Exception e){
            e.printStackTrace();
        }
        return preparedStatement;
    }
    public ObservableList<Inventory> getInventoryList(){
        ObservableList<Inventory> inventories = FXCollections.observableArrayList();
        try {
            preparedStatement = conn.prepareStatement(DbUtils.SELECT_INVENTORY_QRY);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                resultSet.getString("product");
                inventories.add(new Inventory(
                        resultSet.getString("id"),
                        resultSet.getString("inventory_id"),
                        resultSet.getString("product_name"),
                        resultSet.getInt("quantity"),
                        resultSet.getDate("purchased_date"),
                        resultSet.getDouble("cost_price"),
                        resultSet.getDouble("selling_price"),
                        resultSet.getString("description")
                ));
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return inventories;
    }
}
