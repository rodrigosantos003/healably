package com.example.healably.user_profile.controller;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.healably.R;
import com.example.healably.accounts.model.User;
import com.example.healably.data.HealablySQLiteHelper;
import com.example.healably.user_profile.model.UserData;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Controlador para a funcionalidade de Perfil de Utilizador
 */
public class UserDataController {
    Context context;
    View view;
    User user;
    HealablySQLiteHelper healablySQLiteHelper;

    //Value Types
    public static final String WEIGHT = "WEIGHT";
    public static final String HEIGHT = "HEIGHT";
    public static final String ABDOMINAL_PERIMETER = "ABDOMINAL PERIMETER";
    public static final String BLOOD_SUGAR = "BLOOD SUGAR";
    public static final String SYS_BLOOD_PRESSURE = "SYSTOLIC BLOOD PRESSURE";
    public static final String DIA_BLOOD_PRESSURE = "DIASTOLIC BLOOD PRESSURE";

    //Valores de Referência
    private static final double LOW_BMI = 18.5;
    private static final double NORMAL_BMI = 24.9;
    private static final double HIGH_BMI = 25.0;
    private static final double MALE_ABDOMINAL_PERIMETER = 94.0;
    private static final double FEMALE_ABDOMINAL_PERIMETER = 80.0;
    private static final double HIGH_MALE_ABDOMINAL_PERIMETER = 102.0;
    private static final double HIGH_FEMALE_ABDOMINAL_PERIMETER = 88.0;
    private static final double NORMAL_BLOOD_SUGAR = 68.0;
    private static final double HIGH_BLOOD_SUGAR = 126.0;
    private static final double NORMAL_SYS_BP = 120.0;
    private static final double HIGH_SYS_BP = 140.0;
    private static final double NORMAL_DIA_BP = 80.0;
    private static final double HIGH_DIA_BP = 90.0;
    private static final double ELDERLY_SYS_BP = 150.0;

    public UserDataController(Context context, View view) {
        this.context = context;
        this.view = view;
        this.healablySQLiteHelper = new HealablySQLiteHelper(this.context);
        this.user = healablySQLiteHelper.getLoggedUser();
    }

    /**
     * Define o texto na TextView, com o nome do utilizador
     */
    public void setUserText() {
        String text = context.getString(R.string.hello) + " " + user.getName();
        TextView tv = (TextView) view.findViewById(R.id.tv_user);
        tv.setText(text);
    }

    /**
     * Obtém os dados do utilizador
     *
     * @return Lista de dados
     */
    private List<UserData> getData() {
        return healablySQLiteHelper.getUserData(user.getId());
    }

    /**
     * Obtém um valor dos dados
     *
     * @param valueType Tipo de dado pretendido
     * @return Valor pretendido
     */
    private double getValue(String valueType) {
        for (UserData item : getData()) {
            if (item.getValueType().equals(valueType)) {
                return item.getValue();
            }
        }

        return 0.0;
    }

    private List<UserData> getListOfValues(String valueType) {
        List<UserData> listOfValues = new LinkedList<UserData>();

        for (UserData item : getData()) {
            if (item.getValueType().equals(valueType)) {
                listOfValues.add(item);
            }
        }

        return listOfValues;
    }

    private int userAge() {
        LocalDate dateOfBirth = LocalDate.parse(user.getDateOfBirth());

        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

    /**
     * Adiciona um valor aos dados
     *
     * @param valueType Tipo de dado a adicionar
     * @param value     Valor a adicionar
     */
    public void addValue(String valueType, double value) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(new Date());
        UserData userData = new UserData(user.getId(), valueType, value, date);

        healablySQLiteHelper.addUserData(userData);
    }

    /**
     * Apresenta o resultado do IMC
     */
    public void showBMIResult() {
        double bmi = calculateBMI();

        TextView bmiValue = view.findViewById(R.id.imc_valor);

        if (bmi < LOW_BMI) {
            bmiValue.setTextColor(context.getResources().getColor(R.color.powder_blue, null));
        } else if (bmi >= LOW_BMI && bmi <= NORMAL_BMI) {
            bmiValue.setTextColor(context.getResources().getColor(R.color.yellow, null));
        } else if (bmi >= HIGH_BMI) {
            bmiValue.setTextColor(context.getResources().getColor(R.color.red, null));
        }

        if(bmi > 0.0){
            bmiValue.setText(String.format("%.2f", bmi));
        } else {
            bmiValue.setText(context.getString(R.string.no_data_available));
        }
    }

    /**
     * Calcula o IMC atual
     *
     * @return Valor do IMC
     */
    private double calculateBMI() {
        double weight = getValue(WEIGHT);
        double height = getValue(HEIGHT);

        return weight / (height * height);
    }

    public void bodyStructureReport() {
        double bmi = calculateBMI();
        double abdominalPerimeter = getValue(ABDOMINAL_PERIMETER);

        if (bmi < LOW_BMI) {
            //TODO: Write text for low BMI
        } else if (bmi >= LOW_BMI && bmi <= NORMAL_BMI) {
            //TODO: Write text for normal BMI
        } else if (bmi >= HIGH_BMI) {
            //TODO: Write text for high BMI
        }

        switch (user.getGender()) {
            case "MALE":
                maleAbdominalPerimeterAnalysis(abdominalPerimeter);
                break;
            case "FEMALE":
                femaleAbdominalPerimeterAnalysis(abdominalPerimeter);
                break;
            case "OTHER":
                //TODO: Write text explaining abdominal perimeter for both genders
                break;
        }
    }

    private void maleAbdominalPerimeterAnalysis(double abdominalPerimeter) {
        if (abdominalPerimeter <= MALE_ABDOMINAL_PERIMETER) {
            //TODO: Write text for normal abdominal perimeter
        } else if (abdominalPerimeter > MALE_ABDOMINAL_PERIMETER && abdominalPerimeter < HIGH_MALE_ABDOMINAL_PERIMETER) {
            //TODO: Write text for augmented abdominal perimeter
        } else if (abdominalPerimeter >= HIGH_MALE_ABDOMINAL_PERIMETER) {
            //TODO: Write text for concerning abdominal perimeter (look for a doctor)
        }
    }

    private void femaleAbdominalPerimeterAnalysis(double abdominalPerimeter) {
        if (abdominalPerimeter <= FEMALE_ABDOMINAL_PERIMETER) {
            //TODO: Write text for normal abdominal perimeter
        } else if (abdominalPerimeter > FEMALE_ABDOMINAL_PERIMETER && abdominalPerimeter < HIGH_FEMALE_ABDOMINAL_PERIMETER) {
            //TODO: Write text for augmented abdominal perimeter
        } else if (abdominalPerimeter >= HIGH_FEMALE_ABDOMINAL_PERIMETER) {
            //TODO: Write text for concerning abdominal perimeter (look for a doctor)
        }
    }

    public void bloodSugarReport() {
        List<UserData> bloodSugarValues = getListOfValues(BLOOD_SUGAR);

        double average = 0.0;

        for (UserData bloodSugar : bloodSugarValues) {
            average += bloodSugar.getValue();
        }

        average /= bloodSugarValues.size();

        if (average < NORMAL_BLOOD_SUGAR) {
            //TODO: Write text for lower blood sugar than recommended
        } else if (average >= NORMAL_BLOOD_SUGAR && average < HIGH_BLOOD_SUGAR) {
            //TODO: Write text for normal blood sugar
        } else {
            //TODO: Write text for high blood sugar
        }
    }

    public void bloodPressureReport() {
        List<UserData> sysBloodPressureValues = getListOfValues(SYS_BLOOD_PRESSURE);
        List<UserData> diaBloodPressureValues = getListOfValues(DIA_BLOOD_PRESSURE);

        double sysAverage = 0.0;
        double diaAverage = 0.0;

        for (UserData sysBloodPressure : sysBloodPressureValues) {
            sysAverage += sysBloodPressure.getValue();
        }
        sysAverage /= sysBloodPressureValues.size();

        for (UserData diaBloodPressure : diaBloodPressureValues) {
            diaAverage += diaBloodPressure.getValue();
        }
        diaAverage /= diaBloodPressureValues.size();

        if (userAge() <= 80) {
            if (sysAverage < NORMAL_SYS_BP && diaAverage < NORMAL_DIA_BP) {
                //TODO: Write text for low blood pressure
            } else if (sysAverage >= NORMAL_SYS_BP && sysAverage < HIGH_BLOOD_SUGAR) {
                if (diaAverage >= NORMAL_DIA_BP && diaAverage < HIGH_DIA_BP) {
                    //TODO: Write text for normal blood pressure
                }
            } else if (sysAverage >= HIGH_SYS_BP && diaAverage >= HIGH_DIA_BP) {
                //TODO: Write text for high blood pressure
            }
        } else{
            if(sysAverage < ELDERLY_SYS_BP && diaAverage <= NORMAL_DIA_BP){
                //TODO: Write text for normal blood pressure
            } else{
                //TODO: Write text for normal high pressure
            }
        }
    }
}
