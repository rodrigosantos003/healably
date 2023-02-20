package com.example.healably.user_profile.model;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Modelo de dados de Dados de Utilizador*/
public class UserData implements Comparable<UserData>{
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

    @Override
    public int compareTo(UserData userData) {
        Date thisDate = new Date();
        Date otherDate = new Date();
        try {
            thisDate = new SimpleDateFormat("dd/MM/yyyy").parse(getDate());
            otherDate = new SimpleDateFormat("dd/MM/yyyy").parse(userData.getDate());
        } catch (ParseException e) {
            Log.d("EXCEPTION", e.getMessage());
        };

        if (thisDate == null || otherDate == null) {
            return 0;
        }
        return thisDate.compareTo(otherDate);
    }
}
