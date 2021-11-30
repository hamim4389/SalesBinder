package com.example.salesbinder;

public class ItemModel {

    private String NAME, DESC, CATEGORY, EXPIRY_DATE, BAR_CODE;
    private int Qty, daysToExpireAlert, price, minQtyAlert;
    boolean setAlert;

    public ItemModel(){

    }
    public ItemModel(String NAME, String DESC, String CATEGORY, String EXPIRY_DATE, String BAR_CODE,
              int Qty, int daysToExpireAlert, int price, int minQtyAlert, boolean setAlert)
    {
        this.NAME = NAME;
        this.DESC = DESC;
        this.CATEGORY = CATEGORY;
        this.EXPIRY_DATE = EXPIRY_DATE;
        this.BAR_CODE = BAR_CODE;
        this.Qty = Qty;
        this.daysToExpireAlert = daysToExpireAlert;
        this.minQtyAlert = minQtyAlert;
        this.price = price;
        this.setAlert = setAlert;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getDESC() {
        return DESC;
    }

    public void setDESC(String DESC) {
        this.DESC = DESC;
    }

    public String getCATEGORY() {
        return CATEGORY;
    }

    public void setCATEGORY(String CATEGORY) {
        this.CATEGORY = CATEGORY;
    }

    public String getEXPIRY_DATE() {
        return EXPIRY_DATE;
    }

    public void setEXPIRY_DATE(String EXPIRY_DATE) {
        this.EXPIRY_DATE = EXPIRY_DATE;
    }

    public String getBAR_CODE() {
        return BAR_CODE;
    }

    public void setBAR_CODE(String BAR_CODE) {
        this.BAR_CODE = BAR_CODE;
    }

    public int getQty() {
        return Qty;
    }

    public void setQty(int qty) {
        Qty = qty;
    }

    public int getDaysToExpireAlert() {
        return daysToExpireAlert;
    }

    public void setDaysToExpireAlert(int daysToExpireAlert) {
        this.daysToExpireAlert = daysToExpireAlert;
    }

    public int getPrice() {
        return this.price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getMinQtyAlert() {
        return minQtyAlert;
    }

    public void setMinQtyAlert(int minQtyAlert) {
        this.minQtyAlert = minQtyAlert;
    }

    public boolean isSetAlert() {
        return setAlert;
    }

    public void setSetAlert(boolean setAlert) {
        this.setAlert = setAlert;
    }






}
