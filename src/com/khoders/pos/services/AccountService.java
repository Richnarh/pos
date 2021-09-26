package com.khoders.pos.services;

import com.khoders.pos.dto.User;
import com.khoders.pos.utils.DbUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AccountService
{
    Connection conn = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    public AccountService() { conn = DbUtils.init(); }

    public ResultSet login(User user)
    {
        try
        {
            preparedStatement = conn.prepareStatement(DbUtils.LOGIN_QRY);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            resultSet = preparedStatement.executeQuery();

        }catch (Exception e){
            e.printStackTrace();
        }

        return resultSet;
    }
}
