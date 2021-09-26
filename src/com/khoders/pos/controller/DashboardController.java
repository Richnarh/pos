package com.khoders.pos.controller;

import com.khoders.pos.utils.FxmlView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    @FXML private MenuItem productMenu;
    @FXML private MenuItem homeMenu;
    @FXML private BorderPane borderPane;
    @FXML private MenuItem posMenu;

    @FXML public void HomePage(ActionEvent event) { loadPage("home"); }
    @FXML public void productPage(ActionEvent event) { loadPage("product");}
    @FXML public void posPage(ActionEvent event) { loadPage("pos"); }
    @FXML public void productPackagePage(ActionEvent event) { loadPage("productPackage"); }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private void loadPage(String fxmlFileName){
        try
        {
           Parent root = FXMLLoader.load(Objects.requireNonNull(FxmlView.getFxml(fxmlFileName)));
            if(root != null)
            {
                borderPane.setCenter(root);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
