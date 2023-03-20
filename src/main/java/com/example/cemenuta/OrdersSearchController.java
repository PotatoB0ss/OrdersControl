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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class OrdersSearchController implements Initializable {
    private Stage stage;
    private Scene scene;
    private FXMLLoader fxmlLoad;


    @FXML
    private TableView<OrdersSearchModel> ordersTableView;
    @FXML
    private TableColumn<OrdersSearchModel, Integer> orderID;
    @FXML
    private TableColumn<OrdersSearchModel, String> orderName;
    @FXML
    private TableColumn<OrdersSearchModel, String> orderCheckoutDateTime;
    @FXML
    private TableColumn<OrdersSearchModel, String> orderBuildStartDateTime;
    @FXML
    private TableColumn<OrdersSearchModel, String> orderBuildEndDateTime;
    @FXML
    private TableColumn<OrdersSearchModel, String> orderPickUpDateTime;
    @FXML
    private TableColumn<OrdersSearchModel, Integer> orderQuantity;
    @FXML
    private TableColumn<OrdersSearchModel, Integer> orderClientID;
    @FXML
    private TableColumn<OrdersSearchModel, String> orderStatus;

    @FXML
    private Label averageTime;

    @FXML
    private TextField searchTextField;

    ObservableList<OrdersSearchModel> ordersSearchModelObservableList = FXCollections.observableArrayList();

    DataBaseConnection coni = new DataBaseConnection();
    Connection con = coni.connect();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        long hoursBetween = 0;
        long averageOrderTime;
        int count = 0;
        String sql = "SELECT * FROM orders";
        String sl = "SELECT ordercheckoutdatetime, orderbuildenddatetime FROM orders\n" +
                "WHERE orderbuildenddatetime IS NOT NULL";

        try {
            Statement st = con.createStatement();
            ResultSet re = st.executeQuery(sl);

            while(re.next()){
                LocalDateTime date1 = LocalDate.parse(re.getString(1), dtf).atStartOfDay();
                LocalDateTime date2 = LocalDate.parse(re.getString(2), dtf).atStartOfDay();
                hoursBetween += Duration.between(date1, date2).toHours();
                count++;
            }
            averageOrderTime = hoursBetween / count;
            averageTime.setText("Среднее время выполнения заказа: " + averageOrderTime + " ч.");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try{
            Statement statement = con.createStatement();
            ResultSet res = statement.executeQuery(sql);

            while(res.next()){
                Integer queryOrderID = res.getInt("order_id");
                String queryOrderName = res.getString("order_name");
                String queryOrderCheckoutDateTime = res.getString("ordercheckoutdatetime");
                String queryOrderBuildStartDateTime = res.getString("orderbuildstartdatetime");
                String queryOrderBuildEndDateTime = res.getString("orderbuildenddatetime");
                String queryOrderPickUpDateTime = res.getString("orderpickupdatetime");
                Integer queryOrderQuantity = res.getInt("order_quantity");
                Integer queryOrderClientID = res.getInt("client_id");
                String queryOrderStatus = res.getString("order_status");
                ordersSearchModelObservableList.add(new OrdersSearchModel(queryOrderID, queryOrderName, queryOrderCheckoutDateTime,
                        queryOrderBuildStartDateTime, queryOrderBuildEndDateTime, queryOrderPickUpDateTime, queryOrderQuantity,
                        queryOrderClientID, queryOrderStatus));
            }

            orderID.setCellValueFactory(new PropertyValueFactory<>("orderID"));
            orderName.setCellValueFactory(new PropertyValueFactory<>("orderName"));
            orderCheckoutDateTime.setCellValueFactory(new PropertyValueFactory<>("orderCheckoutDateTime"));
            orderBuildStartDateTime.setCellValueFactory(new PropertyValueFactory<>("orderBuildStartTime"));
            orderBuildEndDateTime.setCellValueFactory(new PropertyValueFactory<>("orderBuildEndTime"));
            orderPickUpDateTime.setCellValueFactory(new PropertyValueFactory<>("orderPickUpDateTime"));
            orderQuantity.setCellValueFactory(new PropertyValueFactory<>("orderQuantity"));
            orderClientID.setCellValueFactory(new PropertyValueFactory<>("orderClientID"));
            orderStatus.setCellValueFactory(new PropertyValueFactory<>("orderStatus"));

            ordersTableView.setItems(ordersSearchModelObservableList);

            FilteredList<OrdersSearchModel> filteredData = new FilteredList<>(ordersSearchModelObservableList, b-> true);

            searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(ordersSearchModel -> {

                    if (newValue.isEmpty() || newValue.isBlank() || newValue == null){
                        return true;
                    }

                    String searchWord = newValue.toLowerCase();

                    if(ordersSearchModel.getOrderName().toLowerCase().contains(searchWord)){
                        return true;
                    } else{
                        return false;
                    }
                });
            });

            SortedList<OrdersSearchModel> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(ordersTableView.comparatorProperty());
            ordersTableView.setItems(sortedData);

            ordersTableView.setEditable(true);
            orderBuildStartDateTime.setCellFactory(TextFieldTableCell.forTableColumn());
            orderBuildEndDateTime.setCellFactory(TextFieldTableCell.forTableColumn());
            orderPickUpDateTime.setCellFactory(TextFieldTableCell.forTableColumn());
            orderStatus.setCellFactory(TextFieldTableCell.forTableColumn());
            
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void onEditDTStart(TableColumn.CellEditEvent<OrdersSearchModel, String> ordersSearchModelStringCellEditEvent) {
        OrdersSearchModel order = ordersTableView.getSelectionModel().getSelectedItem();
        Integer a = order.getOrderID();
        order.setOrderBuildStartTime(ordersSearchModelStringCellEditEvent.getNewValue());
        String newData = ordersSearchModelStringCellEditEvent.getNewValue();
        String sql = String.format("UPDATE orders SET orderbuildstartdatetime = '%s' WHERE order_id = %d ", newData, a);
        try(
                PreparedStatement stm = con.prepareStatement(sql);
        ) {
            stm.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void onEditDTEnd(TableColumn.CellEditEvent<OrdersSearchModel, String> ordersSearchModelStringCellEditEvent) {
        OrdersSearchModel order = ordersTableView.getSelectionModel().getSelectedItem();
        Integer a = order.getOrderID();
        order.setOrderBuildEndTime(ordersSearchModelStringCellEditEvent.getNewValue());
        String newData = ordersSearchModelStringCellEditEvent.getNewValue();
        String sql = String.format("UPDATE orders SET orderbuildenddatetime = '%s' WHERE order_id = %d ", newData, a);
        try(
                PreparedStatement stm = con.prepareStatement(sql);
        ) {
            stm.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void onEditDTPickUp(TableColumn.CellEditEvent<OrdersSearchModel, String> ordersSearchModelStringCellEditEvent) {
        OrdersSearchModel order = ordersTableView.getSelectionModel().getSelectedItem();
        System.out.println(order.getOrderID());
        System.out.println(order.getOrderStatus());
        Integer a = order.getOrderID();
        order.setOrderPickUpDateTime(ordersSearchModelStringCellEditEvent.getNewValue());
        String newData = ordersSearchModelStringCellEditEvent.getNewValue();
        String sql = String.format("UPDATE orders SET orderpickupdatetime = '%s' WHERE order_id = %d ", newData, a);
        try(
                PreparedStatement stm = con.prepareStatement(sql);
        ) {
            stm.execute();
            if(newData.length() > 9){
                sql = String.format("UPDATE orders SET order_status = '%s' WHERE order_id = %d ", "Выдан", a);
                PreparedStatement stmm = con.prepareStatement(sql);
                stmm.execute();
                order.setOrderStatus("Выдан");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void onEditDTStatus(TableColumn.CellEditEvent<OrdersSearchModel, String> ordersSearchModelStringCellEditEvent) {
        OrdersSearchModel order = ordersTableView.getSelectionModel().getSelectedItem();
        Integer a = order.getOrderID();
        order.setOrderStatus(ordersSearchModelStringCellEditEvent.getNewValue());
        String newData = ordersSearchModelStringCellEditEvent.getNewValue();
        String sql = String.format("UPDATE orders SET order_status = '%s' WHERE order_id = %d ", newData, a);
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

    public void switchToAddOrder(ActionEvent event) throws IOException {
        fxmlLoad = new FXMLLoader(HelloController.class.getResource("AddOrder.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoad.load());
        stage.setTitle("Добавление заказа");
        stage.setScene(scene);
        stage.show();
    }

}
