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

/**
 * Controlador para a funcionalidade de Perfil de Utilizador*/
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

    /**
     * Define o texto na TextView, com o nome do utilizador*/
    public void setUserText() {
        String text = context.getString(R.string.hello) + " " + user.getName();
        TextView tv = (TextView) view.findViewById(R.id.tv_user);
        tv.setText(text);
    }

    /**
     * Obtém os dados do utilizador
     * @return Lista de dados*/
    public List<UserData> getData(){
        return healablySQLiteHelper.getUserData(user.getId());
    }

    /**
     * Adiciona um valor aos dados
     * @param valueType Tipo de dado a adicionar
     * @param value Valor a adicionar*/
    public void addValue(String valueType, double value){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(new Date());
        UserData userData = new UserData(user.getId(), valueType.toUpperCase(), value, date);

        healablySQLiteHelper.addUserData(userData);
    }

    /**
     * Obtém um valor dos dados
     * @param valueType Tipo de dado pretendido
     * @return Valor pretendido*/
    public double getValue(String valueType){
        for(UserData item : getData()){
            if(item.getValueType().equals(valueType)){
                return item.getValue();
            }
        }

        return 0.0;
    }

    /**
     * Calcula o IMC atual
     * @return Valor do IMC*/
    private double calculateBMI(){
        double weight = getValue("WEIGHT");
        double height = getValue("HEIGHT");

        return weight / (height * height);
    }

    /**
     * Apresenta o resultado do IMC*/
    public void showBMIResult(){
        double bmi = calculateBMI();

        TextView bmiValue = view.findViewById(R.id.imc_valor);

        if(bmi < 18.5){
            bmiValue.setTextColor(context.getResources().getColor(R.color.powder_blue, null));
        } else if(bmi >= 18.5 && bmi <= 24.9){
            bmiValue.setTextColor(context.getResources().getColor(R.color.yellow, null));
        } else if(bmi > 24.9){
            bmiValue.setTextColor(context.getResources().getColor(R.color.red, null));
        }

        bmiValue.setText(String.format("%.2f", bmi));
    }
}
