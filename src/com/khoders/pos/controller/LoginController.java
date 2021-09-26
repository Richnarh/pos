package com.khoders.pos.controller;

import com.khoders.pos.dto.User;
import com.khoders.pos.services.AccountService;
import com.khoders.pos.utils.FxmlView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Objects;

public class LoginController {
    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Button buttonCancel;

    @FXML
    private Button buttonLogin;

    @FXML
    void cancelAction(ActionEvent event) {
        System.exit(0);
    }

    AccountService accountService = new AccountService();

    public LoginController(){}

    @FXML
    void loginAction(ActionEvent event) {
        User user = new User();

        if(txtUsername.getText().equals("") && txtPassword.getText().equals(""))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Username and password cannot be empty!");
            alert.showAndWait();
            return;
        }

        user.setUsername(txtUsername.getText());
        user.setPassword(txtPassword.getText());

        ResultSet resultSet = accountService.login(user);

        try
        {
            if(resultSet.next())
            {
                Stage stage = new Stage();

                try {
                    buttonLogin.getScene().getWindow().hide();

                    Parent root = FXMLLoader.load(Objects.requireNonNull(FxmlView.getFxml("dashboard")));
                    stage.setTitle("Dashboard");
                    stage.setScene(new Scene(root));
                    stage.show();

                }catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Login failed");
                alert.showAndWait();

                txtUsername.setText("");
                txtPassword.setText("");
                txtUsername.requestFocus();
            }

        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

}
