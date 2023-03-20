package com.example.cemenuta;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddClient {
    @FXML
    private TextField clientName;
    @FXML
    private TextField clientEmail;
    @FXML
    private TextField clientPhone;
    @FXML
    private Button AddButton;
    @FXML
    private Text successfulAddiction;

    DataBaseConnection coni = new DataBaseConnection();
    Connection con = coni.connect();

    public void switchToMain(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoad = new FXMLLoader(HelloController.class.getResource("clientsBase.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoad.load());
        stage.setScene(scene);
        stage.show();
    }

    public void DBAddClient(ActionEvent event) {
        String cName = clientName.getText();
        String cEmail = clientEmail.getText();
        String cPhone = clientPhone.getText();
        if(cName.length() > 0 & cEmail.length() > 0 & cPhone.length() > 0){
            String sql = String.format("INSERT INTO clients(client_name, client_email, client_phone_number)" +
                    " VALUES  ('%s', '%s', '%s')", cName, cEmail, cPhone);
            try {
                successfulAddiction.setText("Клиент " + cName + " успешно добавлен в базу данных");
                PreparedStatement stm = con.prepareStatement(sql);
                stm.execute();
            } catch (SQLException e) {
                successfulAddiction.setText("Произошла ошибка!");
                throw new RuntimeException(e);
            }
            clientName.setText("");
            clientEmail.setText("");
            clientPhone.setText("");
        }else{
            successfulAddiction.setText("Корректно заполните все поля!");
        }
    }
}
