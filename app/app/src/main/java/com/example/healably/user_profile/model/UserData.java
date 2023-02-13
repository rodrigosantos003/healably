package com.example.healably.user_profile.model;

/**
 * Modelo de dados de Dados de Utilizador*/
public class UserData {
    int id;
    int userId;
    String valueType;
    double value;
    String date;

    public UserData(int id, int userId, String valueType, double value, String date) {
        this.id = id;
        this.userId = userId;
        this.valueType = valueType;
        this.value = value;
        this.date = date;
    }

    public UserData(int userId, String valueType, double value, String date) {
        this.userId = userId;
        this.valueType = valueType;
        this.value = value;
        this.date = date;
    }

    //Getters
    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getValueType() {
        return valueType.toString();
    }

    public double getValue() {
        return value;
    }

    public String getDate() {
        return date;
    }

    //Setters
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "UserData{" +
                "id=" + id +
                ", userId=" + userId +
                ", type=" + valueType +
                ", value=" + value +
                '}';
    }
}
