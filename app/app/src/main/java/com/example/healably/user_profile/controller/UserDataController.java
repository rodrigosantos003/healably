package com.example.healably.user_profile.controller;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.healably.R;
import com.example.healably.accounts.model.User;
import com.example.healably.data.HealablySQLiteHelper;
import com.example.healably.user_profile.model.UserData;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class UserDataController {
    Context context;
    View view;
    User user;
    HealablySQLiteHelper healablySQLiteHelper;

    public UserDataController(Context context, View view) {
        this.context = context;
        this.view = view;
        this.healablySQLiteHelper = new HealablySQLiteHelper(this.context);
        this.user = healablySQLiteHelper.getLoggedUser();
    }

    //Set the text on the TextView with the name of the logged user
    public void setUserText() {
        String text = context.getString(R.string.hello) + " " + user.getName();
        TextView tv = (TextView) view.findViewById(R.id.tv_user);
        tv.setText(text);
    }

    //Get user data from DB
    public List<UserData> getData(){
        return healablySQLiteHelper.getUserData(user.getId());
    }

    //Add a user data value on DB
    public void addValue(String valueType, double value){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(new Date());
        UserData userData = new UserData(user.getId(), valueType.toUpperCase(), value, date);

        healablySQLiteHelper.addUserData(userData);
    }

    //Get a user data value from DB
    public double getValue(String valueType){
        for(UserData item : getData()){
            if(item.getValueType().equals(valueType)){
                return item.getValue();
            }
        }

        return 0.0;
    }

    //Calculate current BMI
    public double calculateBMI(){
        double weight = getValue("WEIGHT");
        double height = getValue("HEIGHT");

        return weight / (height * height);
    }
}
