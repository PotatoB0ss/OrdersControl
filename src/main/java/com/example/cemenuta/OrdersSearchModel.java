package com.example.cemenuta;

public class OrdersSearchModel {

    Integer orderID;
    String orderName;
    String orderCheckoutDateTime;
    String orderBuildStartTime;
    String orderBuildEndTime;
    String orderPickUpDateTime;
    Integer orderQuantity;
    Integer orderClientID;
    String orderStatus;

    public OrdersSearchModel(Integer orderID, String orderName, String orderCheckoutDateTime, String orderBuildStartTime,
                             String orderBuildEndTime, String orderPickUpDateTime, Integer orderQuantity,
                             Integer orderClientID, String orderStatus) {
        this.orderID = orderID;
        this.orderName = orderName;
        this.orderCheckoutDateTime = orderCheckoutDateTime;
        this.orderBuildStartTime = orderBuildStartTime;
        this.orderBuildEndTime = orderBuildEndTime;
        this.orderPickUpDateTime = orderPickUpDateTime;
        this.orderQuantity = orderQuantity;
        this.orderClientID = orderClientID;
        this.orderStatus = orderStatus;
    }

    public Integer getOrderID() {
        return orderID;
    }

    public void setOrderID(Integer orderID) {
        this.orderID = orderID;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getOrderCheckoutDateTime() {
        return orderCheckoutDateTime;
    }

    public void setOrderCheckoutDateTime(String orderCheckoutDateTime) {
        this.orderCheckoutDateTime = orderCheckoutDateTime;
    }

    public String getOrderBuildStartTime() {
        return orderBuildStartTime;
    }

    public void setOrderBuildStartTime(String orderBuildStartTime) {
        this.orderBuildStartTime = orderBuildStartTime;
    }

    public String getOrderBuildEndTime() {
        return orderBuildEndTime;
    }

    public void setOrderBuildEndTime(String orderBuildEndTime) {
        this.orderBuildEndTime = orderBuildEndTime;
    }

    public String getOrderPickUpDateTime() {
        return orderPickUpDateTime;
    }

    public void setOrderPickUpDateTime(String orderPickUpDateTime) {
        this.orderPickUpDateTime = orderPickUpDateTime;
    }

    public Integer getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(Integer orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public Integer getOrderClientID() {
        return orderClientID;
    }

    public void setOrderClientID(Integer orderClientID) {
        this.orderClientID = orderClientID;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
