package com.example.letseat.Model;

public class Payment {
    String Product,Total,MobileNumber,Date,PaymentStatus,Method;

    public Payment() {
    }

    public Payment(String product, String total, String mobileNumber, String date, String paymentStatus, String method) {
        Product = product;
        Total = total;
        MobileNumber = mobileNumber;
        Date = date;
        PaymentStatus = paymentStatus;
        Method = method;
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

    public String getPaymentStatus() {
        return PaymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        PaymentStatus = paymentStatus;
    }

    public String getMethod() {
        return Method;
    }

    public void setMethod(String method) {
        Method = method;
    }
}
