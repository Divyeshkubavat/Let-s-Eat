package com.example.letseat.Model;

public class CartItem {
    String NAME,PRICE,IMAGE,QTY;

    public CartItem() {
    }

    public CartItem(String NAME, String PRICE, String IMAGE, String QTY) {
        this.NAME = NAME;
        this.PRICE = PRICE;
        this.IMAGE = IMAGE;
        this.QTY = QTY;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getPRICE() {
        return PRICE;
    }

    public void setPRICE(String PRICE) {
        this.PRICE = PRICE;
    }

    public String getIMAGE() {
        return IMAGE;
    }

    public void setIMAGE(String IMAGE) {
        this.IMAGE = IMAGE;
    }

    public String getQTY() {
        return QTY;
    }

    public void setQTY(String QTY) {
        this.QTY = QTY;
    }
}
