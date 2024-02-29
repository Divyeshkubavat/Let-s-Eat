package com.example.letseat.Model;

public class ITEM {
    String NAME,PRICE,IMAGE;

    ITEM(){

    }

    public ITEM(String NAME, String PRICE, String IMAGE) {
        this.NAME = NAME;
        this.PRICE = PRICE;
        this.IMAGE = IMAGE;
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
}
