package com.khoders.pos.config;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    public Connection connection=null;

    private final DbParams dbParams = new DbParams();

    public Connection getConnectionInstance()
    {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(dbParams.getDatabaseUrl(), dbParams.getDatabaseUser(), dbParams.getDatabasePassword());
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return connection;
    }
}
