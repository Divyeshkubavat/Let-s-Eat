package com.example.letseat.Model;

public class Order_Payement_model {
    String Product,Total,MobileNumber,Date,OrderStatus,PaymentStatus;
    public Order_Payement_model() {
    }

    public Order_Payement_model(String product, String total, String mobileNumber, String date, String orderStatus, String paymentStatus) {
        Product = product;
        Total = total;
        MobileNumber = mobileNumber;
        Date = date;
        OrderStatus = orderStatus;
        PaymentStatus = paymentStatus;
    }

    public String getProduct() {
        return Product;
    }

    public void setProduct(String product) {
        Product = product;
    }

    public String getTotal() {
        return Total;
    }

    public void setTotal(String total) {
        Total = total;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getOrderStatus() {
        return OrderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        OrderStatus = orderStatus;
    }

    public String getPaymentStatus() {
        return PaymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        PaymentStatus = paymentStatus;
    }
}
