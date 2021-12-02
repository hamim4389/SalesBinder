package com.example.salesbinder;

public class UserModel {
    String NAME, NUMBER, PASSWORD;

    public UserModel(){

    }

    public UserModel(String NAME, String NUMBER, String PASSWORD) {
        this.NAME = NAME;
        this.NUMBER = NUMBER;
        this.PASSWORD = PASSWORD;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getNUMBER() {
        return NUMBER;
    }

    public void setNUMBER(String NUMBER) {
        this.NUMBER = NUMBER;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }
}
