module pos {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires java.sql;
    requires mysql.connector.java;

    opens com.khoders.pos;
    opens com.khoders.pos.controller;
    opens com.khoders.pos.dto;
}