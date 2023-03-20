package com.example.cemenuta;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class AddOrder implements Initializable {
    @FXML
    private TextField orderName;
    @FXML
    private TextField orderCheckoutDT;
    @FXML
    private TextField orderQuantity;
    @FXML
    private TextField orderClientID;
    @FXML
    private Button AddButton;
    @FXML
    private Text successfulAddiction;

    DataBaseConnection coni = new DataBaseConnection();
    Connection con = coni.connect();

    @Override
    public void initialize(URL url, ResourceBundle resource){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime now = LocalDateTime.now();
        orderCheckoutDT.setText(dtf.format(now));
    }

    public void switchToMain(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoad = new FXMLLoader(HelloController.class.getResource("ordersBase.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoad.load());
        stage.setScene(scene);
        stage.show();
    }

    public void DBAddOrder(ActionEvent event) {
        String oName = orderName.getText();
        String oCheckoutDT = orderCheckoutDT.getText();
        Integer oQuantity = Integer.valueOf(orderQuantity.getText());
        Integer oClientId = Integer.valueOf(orderClientID.getText());
        if(oName.length() > 0 & oCheckoutDT.length() > 0 & oQuantity > 0 & oQuantity > 0){
            String sql = String.format("INSERT INTO orders(order_name, ordercheckoutdatetime, order_quantity, client_id)" +
                    " VALUES  ('%s', '%s', %d, %d)", oName, oCheckoutDT, oQuantity, oClientId);
            try {
                successfulAddiction.setText("Заказ " + oName + " успешно добавлен в базу данных");
                PreparedStatement stm = con.prepareStatement(sql);
                stm.execute();
            } catch (SQLException e) {
                successfulAddiction.setText("Произошла ошибка!");
                throw new RuntimeException(e);
            }
            orderName.setText("");
            orderCheckoutDT.setText("");
            orderQuantity.setText("");
            orderClientID.setText("");
        }else{
            successfulAddiction.setText("Корректно заполните все поля!");
        }
    }
}
