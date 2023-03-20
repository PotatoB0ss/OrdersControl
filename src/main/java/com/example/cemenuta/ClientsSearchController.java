package com.example.cemenuta;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientsSearchController implements Initializable {

    public Button addClientButton;
    private Stage stage;
    private Scene scene;
    private FXMLLoader fxmlLoad;
    @FXML
    private TableView<ClientsSearchModel> overheadsTableView;
    @FXML
    private TableColumn<ClientsSearchModel, Integer> clientID;
    @FXML
    private TableColumn<ClientsSearchModel, String> clientName;
    @FXML
    private TableColumn<ClientsSearchModel, String> clientEmail;
    @FXML
    private TableColumn<ClientsSearchModel, String> clientPhoneNumber;

    @FXML
    private TextField searchTextField;

    ObservableList<ClientsSearchModel> clientsSearchModelObservableList = FXCollections.observableArrayList();

    DataBaseConnection coni = new DataBaseConnection();
    Connection con = coni.connect();

    @Override
    public void initialize(URL url, ResourceBundle resource){
        String sql = "SELECT * FROM clients";

        try {
            Statement statement = con.createStatement();
            ResultSet res = statement.executeQuery(sql);

            while(res.next()){
                Integer queryClientID = res.getInt("client_id");
                String queryClientName = res.getString("client_name");
                String queryClientEmail = res.getString("client_email");
                String queryPhoneNumber = res.getString("client_phone_number");
                clientsSearchModelObservableList.add(new ClientsSearchModel(queryClientID, queryClientName, queryClientEmail, queryPhoneNumber));
            }

            clientID.setCellValueFactory(new PropertyValueFactory<>("clientID"));
            clientName.setCellValueFactory(new PropertyValueFactory<>("clientName"));
            clientEmail.setCellValueFactory(new PropertyValueFactory<>("clientEmail"));
            clientPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("clientPhoneNumber"));

            overheadsTableView.setItems(clientsSearchModelObservableList);


            FilteredList<ClientsSearchModel> filteredData = new FilteredList<>(clientsSearchModelObservableList, b-> true);

            searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(clientsSearchModel -> {

                    if (newValue.isEmpty() || newValue.isBlank() || newValue == null){
                        return true;
                    }

                    String searchWord = newValue.toLowerCase();

                    if(clientsSearchModel.getClientName().toLowerCase().contains(searchWord)){
                        return true;
                    } else if (clientsSearchModel.getClientEmail().contains(searchWord)) {
                        return true;
                    } else{
                        return false;
                    }
                });
            });

            SortedList<ClientsSearchModel> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(overheadsTableView.comparatorProperty());
            overheadsTableView.setItems(sortedData);

            overheadsTableView.setEditable(true);
            clientName.setCellFactory(TextFieldTableCell.forTableColumn());



        } catch (SQLException e) {
            Logger.getLogger(ClientsSearchController.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }

    }

    public void onEditName(TableColumn.CellEditEvent<ClientsSearchModel, String> clientsSearchModelStringCellEditEvent) {
        ClientsSearchModel client = overheadsTableView.getSelectionModel().getSelectedItem();
        Integer a = client.getClientID();
        client.setClientName(clientsSearchModelStringCellEditEvent.getNewValue());
        String newName = clientsSearchModelStringCellEditEvent.getNewValue();
        String sql = String.format("UPDATE clients SET client_name = '%s' WHERE client_id = %d ", newName, a);
        try(
            PreparedStatement stm = con.prepareStatement(sql);
        ) {
            stm.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void switchToMainMenu(ActionEvent event) throws IOException {
        fxmlLoad = new FXMLLoader(HelloController.class.getResource("hello-view.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoad.load());
        stage.setTitle("Главное меню");
        stage.setScene(scene);
        stage.show();
    }

    public void switchToAddClient(ActionEvent event) throws IOException {
        fxmlLoad = new FXMLLoader(HelloController.class.getResource("AddClient.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoad.load());
        stage.setTitle("Добавление клиента");
        stage.setScene(scene);
        stage.show();
    }
}
