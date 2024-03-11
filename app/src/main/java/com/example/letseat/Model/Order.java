package com.example.letseat.Model;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private int id;
    private long mobileNo;
    private int state;
    private List<OrderProduct> orderProducts=new ArrayList<>();
    private int rating;
    private String date;
    private String address;
    private int deliveryBoyId;
    private double finalPayment;

    public Order() {
    }

    public double getFinalPayment() {
        return finalPayment;
    }

    public void setFinalPayment(double finalPayment) {
        this.finalPayment = finalPayment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(long mobileNo) {
        this.mobileNo = mobileNo;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public List<OrderProduct> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(List<OrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getDeliveryBoyId() {
        return deliveryBoyId;
    }

    public void setDeliveryBoyId(int deliveryBoyId) {
        this.deliveryBoyId = deliveryBoyId;
    }
}
