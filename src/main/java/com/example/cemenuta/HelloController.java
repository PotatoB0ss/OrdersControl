package com.example.cemenuta;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {

    private Stage stage;
    private Scene scene;
    private FXMLLoader fxmlLoad;
    
    @FXML
    private Button clientsBaseButton;

    public void openClientsBase(ActionEvent event) throws IOException {
        fxmlLoad = new FXMLLoader(HelloController.class.getResource("clientsBase.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoad.load());
        stage.setTitle("База клиентов");
        stage.setScene(scene);
        stage.show();
    }

    public void openOrdersBase(ActionEvent event) throws IOException {
        fxmlLoad = new FXMLLoader(HelloController.class.getResource("ordersBase.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoad.load());
        stage.setTitle("База заказов");
        stage.setScene(scene);
        stage.show();
    }
}