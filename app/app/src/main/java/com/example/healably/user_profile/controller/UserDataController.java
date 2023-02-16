package com.example.healably.user_profile.controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.healably.R;
import com.example.healably.accounts.model.User;
import com.example.healably.data.HealablySQLiteHelper;
import com.example.healably.user_profile.model.UserData;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

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
    public static final String HEART_RATE = "HEART RATE";

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
    private UserData getDataOfType(String valueType) {
        for (UserData item : getData()) {
            if (item.getValueType().equals(valueType)) {
                return item;
            }
        }

        return null;
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
    public void showBodyStructure() {
        UserData weight = getDataOfType(WEIGHT);
        UserData height = getDataOfType(HEIGHT);
        UserData abdominalPerimeter = getDataOfType(ABDOMINAL_PERIMETER);

        double weightValue = weight != null ? weight.getValue() : 0.0;
        double heightValue = height != null ? height.getValue() : 0.0;
        double abdominalPerimeterValue = abdominalPerimeter != null ? abdominalPerimeter.getValue() : 0.0;
        double bmi = calculateBMI(weightValue, heightValue);

        TextView bmiValueText = view.findViewById(R.id.bmi_value);
        TextView weightValueText = view.findViewById(R.id.bodyStructure_weight_value);
        TextView heightValueText = view.findViewById(R.id.bodyStructure_height_value);
        TextView abdominalPerimeterValueText = view.findViewById(R.id.bodyStructure_abdominalPerimeter_value);
        TextView weightDateText = view.findViewById(R.id.bodyStructure_weight_date);
        TextView heightDateText = view.findViewById(R.id.bodyStructure_height_date);
        TextView abdominalPerimeterDateText = view.findViewById(R.id.bodyStructure_abdominalPerimeter_date);

        showBMIResult(bmi, bmiValueText);

        if(weightValue > 0.0){
            String text = String.format("%.2f", weightValue) + " " + context.getString(R.string.kg);
            weightValueText.setText(text);
            weightDateText.setText(weight.getDate());
        } else{
            weightValueText.setText("0.0");
            weightDateText.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        }

        if(heightValue > 0.0){
            String text = String.format("%.2f", heightValue) + " " + context.getString(R.string.m);
            heightValueText.setText(text);
            heightDateText.setText(height.getDate());
        } else{
            heightValueText.setText("0.0");
            heightDateText.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        }

        if(abdominalPerimeterValue > 0.0){
            String text = String.format("%.2f", abdominalPerimeterValue) + context.getString(R.string.cm);
            abdominalPerimeterValueText.setText(text);
            abdominalPerimeterDateText.setText(abdominalPerimeter.getDate());
        } else{
            abdominalPerimeterValueText.setText("0.0");
            abdominalPerimeterDateText.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        }
    }

    private void showBMIResult(double bmi, TextView tv){
        if(bmi > 0.0){
            if (bmi < LOW_BMI) {
                tv.setTextColor(context.getResources().getColor(R.color.powder_blue, null));
            } else if (bmi <= NORMAL_BMI) {
                tv.setTextColor(context.getResources().getColor(R.color.yellow, null));
            } else if (bmi >= HIGH_BMI) {
                tv.setTextColor(context.getResources().getColor(R.color.red, null));
            }

            String text = String.format("%.2f", bmi) + " " + context.getString(R.string.kg_m2);
            tv.setText(text);

        } else {
            tv.setText(context.getString(R.string.no_data_available));
        }
    }

    /**
     * Calcula o IMC atual
     *
     * @return Valor do IMC
     */
    private double calculateBMI(double weight, double height) {
        if(weight > 0.0 && height > 0.0)
            return weight / (height * height);

        return 0.0;
    }

    public void showBloodSugar(){
        UserData bloodSugar = getDataOfType(BLOOD_SUGAR);

        double bloodSugarValue = bloodSugar != null ? bloodSugar.getValue() : 0.0;
        double hba1c = calculateHbA1c();

        TextView hba1cText = (TextView) view.findViewById(R.id.hba1c_value);
        TextView bloodSugarValueText = (TextView) view.findViewById(R.id.bloodSugar_value);
        TextView bloodSugarDateText = (TextView) view.findViewById(R.id.bloodSugar_date);

        showHbA1cResult(hba1c, hba1cText);

        if(bloodSugarValue > 0.0){
            String text = String.format("%.2f", bloodSugarValue) + " " + context.getString(R.string.mg_dl);
            bloodSugarValueText.setText(text);
            bloodSugarDateText.setText(bloodSugar.getDate());
        }else{
            bloodSugarValueText.setText("0.0");
            bloodSugarDateText.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        }
    }

    private double calculateHbA1c(){
        List<UserData> bloodSugarValues = getListOfValues(BLOOD_SUGAR);

        double average = 0.0;
        double value = 0.0;

        for (UserData bloodSugar : bloodSugarValues) {
            average += bloodSugar.getValue();
        }
        average /= bloodSugarValues.size();

        if(average >= NORMAL_BLOOD_SUGAR && average < HIGH_BLOOD_SUGAR)
            value = 4;
        else if(average >= HIGH_BLOOD_SUGAR && average < 140)
            value = 6;
        else if(average >= 140 && average < 154)
            value = 6.5;
        else if(average >= 154 && average < 169)
            value = 7;
        else if(average >= 169 && average < 183)
            value = 7.5;
        else if(average >= 183 && average < 197)
            value = 8;
        else if(average >= 197 && average < 212)
            value = 8.5;
        else if(average >= 212 && average < 226)
            value = 9;
        else if(average >= 226 && average < 240)
            value = 9.5;
        else if(average >= 240)
            value = 10;

        return value;
    }

    private void showHbA1cResult(double hba1c, TextView tv){
        if(hba1c > 0.0){
            String text = String.format("%.2f", hba1c) + " %";
            tv.setText(text);
        } else {
            tv.setText(context.getString(R.string.no_data_available));
        }
    }

    public void showBloodPressure(){
        UserData sysBloodPressure = getDataOfType(SYS_BLOOD_PRESSURE);
        UserData diaBloodPressure = getDataOfType(DIA_BLOOD_PRESSURE);

        double sysBloodPressureValue = sysBloodPressure != null ? sysBloodPressure.getValue() : 0.0;
        double diaBloodPressureValue = diaBloodPressure != null ? diaBloodPressure.getValue() : 0.0;


        TextView bloodPressureValueText = (TextView) view.findViewById(R.id.bloodPressure_value);
        TextView bloodPressureDateText = (TextView) view.findViewById(R.id.bloodPressure_date);

        if(sysBloodPressureValue > 0.0 && diaBloodPressureValue > 0.0){
            String sysText = String.format("%.0f", sysBloodPressureValue);
            String diaText = String.format("%.0f", diaBloodPressureValue);
            String text = sysText + "/" + diaText + context.getString(R.string.mm_hg);
            bloodPressureValueText.setText(text);
            bloodPressureDateText.setText(sysBloodPressure.getDate());
        }else {
            bloodPressureValueText.setText("0.0");
            bloodPressureDateText.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        }
    }

    public void bodyStructureReport() {
        UserData weight = getDataOfType(WEIGHT);
        UserData height = getDataOfType(HEIGHT);
        UserData abdominalPerimeter = getDataOfType(ABDOMINAL_PERIMETER);

        double weightValue = weight != null ? weight.getValue() : 0.0;
        double heightValue = height != null ? height.getValue() : 0.0;
        double bmi = calculateBMI(weightValue, heightValue);
        double abdominalPerimeterValue = abdominalPerimeter != null ? abdominalPerimeter.getValue() : 0.0;

        String title = "";
        String text = "";

        if (bmi < LOW_BMI) {
            title = context.getString(R.string.low_bmi);
            text = context.getString(R.string.low_bmi_description);
        } else if (bmi >= LOW_BMI && bmi <= NORMAL_BMI) {
            title = context.getString(R.string.normal_bmi);
            text = context.getString(R.string.normal_bmi_description);
        } else if (bmi >= HIGH_BMI) {
            title = context.getString(R.string.high_bmi);
            text = context.getString(R.string.high_bmi_description);
        }

        switch (user.getGender()) {
            case "MALE":
                maleAbdominalPerimeterAnalysis(abdominalPerimeterValue);
                break;
            case "FEMALE":
                femaleAbdominalPerimeterAnalysis(abdominalPerimeterValue);
                break;
            case "OTHER":
                neutralAbdominalPerimeterAnalysis();
                break;
        }
    }

    private void maleAbdominalPerimeterAnalysis(double abdominalPerimeter) {
        String title = "";
        String text = "";

        if (abdominalPerimeter <= MALE_ABDOMINAL_PERIMETER) {
            title = context.getString(R.string.normal_ap);
            text = context.getString(R.string.normal_ap_description);
        } else if (abdominalPerimeter > MALE_ABDOMINAL_PERIMETER && abdominalPerimeter < HIGH_MALE_ABDOMINAL_PERIMETER) {
            title = context.getString(R.string.augmented_ap);
            text = context.getString(R.string.augmented_ap_description);
        } else if (abdominalPerimeter >= HIGH_MALE_ABDOMINAL_PERIMETER) {
            title = context.getString(R.string.high_ap);
            text = context.getString(R.string.high_ap_description);
        }
    }

    private void femaleAbdominalPerimeterAnalysis(double abdominalPerimeter) {
        String title = "";
        String text = "";

        if (abdominalPerimeter <= FEMALE_ABDOMINAL_PERIMETER) {
            title = context.getString(R.string.normal_ap);
            text = context.getString(R.string.normal_ap_description);
        } else if (abdominalPerimeter > FEMALE_ABDOMINAL_PERIMETER && abdominalPerimeter < HIGH_FEMALE_ABDOMINAL_PERIMETER) {
            title = context.getString(R.string.augmented_ap);
            text = context.getString(R.string.augmented_ap_description);
        } else if (abdominalPerimeter >= HIGH_FEMALE_ABDOMINAL_PERIMETER) {
            title = context.getString(R.string.high_ap);
            text = context.getString(R.string.high_ap_description);
        }
    }

    private void neutralAbdominalPerimeterAnalysis(){
        String title = context.getString(R.string.abdominal_perimeter);
        String text = context.getString(R.string.neutral_ap_description);
    }

    public void bloodSugarReport() {
        List<UserData> bloodSugarValues = getListOfValues(BLOOD_SUGAR);

        double average = 0.0;
        double value = 0.0;

        for (UserData bloodSugar : bloodSugarValues) {
            average += bloodSugar.getValue();
        }
        average /= bloodSugarValues.size();

        String title = "";
        String text = "";

        if (average < NORMAL_BLOOD_SUGAR) {
            title = context.getString(R.string.low_bs);
            text = context.getString(R.string.low_bs_description);
        } else if (average >= NORMAL_BLOOD_SUGAR && average < HIGH_BLOOD_SUGAR) {
            title = context.getString(R.string.normal_bs);
            text = context.getString(R.string.normal_bs_description);
        } else {
            title = context.getString(R.string.high_bs);
            text = context.getString(R.string.high_bs_description);
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

        String title = "";
        String text = "";

        if (userAge() <= 80) {
            if (sysAverage < NORMAL_SYS_BP && diaAverage < NORMAL_DIA_BP) {
                title = context.getString(R.string.low_bp);
                text = context.getString(R.string.low_bp_description);
            } else if (sysAverage >= NORMAL_SYS_BP && sysAverage < HIGH_BLOOD_SUGAR) {
                if (diaAverage >= NORMAL_DIA_BP && diaAverage < HIGH_DIA_BP) {
                    title = context.getString(R.string.normal_bp);
                    text = context.getString(R.string.normal_bp_description);
                }
            } else if (sysAverage >= HIGH_SYS_BP && diaAverage >= HIGH_DIA_BP) {
                title = context.getString(R.string.high_bp);
                text = context.getString(R.string.high_bp_description);
            }
        } else{
            if(sysAverage < ELDERLY_SYS_BP && diaAverage <= NORMAL_DIA_BP){
                title = context.getString(R.string.normal_bp);
                text = context.getString(R.string.normal_bp_description);
            } else{
                title = context.getString(R.string.high_bp);
                text = context.getString(R.string.high_bp_description);
            }
        }
    }

    public void heartRateReport(){
        List<UserData> heartRateValues = getListOfValues(HEART_RATE);

        double average = 0.0;

        for(UserData heartRate : heartRateValues){
            average += heartRate.getValue();
        }

        average /= heartRateValues.size();

        //Fórmula de Tanaka
        double maximumHeartRate = 208 - (0.7 * userAge());

        String title = "";
        String text = "";

        if(average < 60){
            title = context.getString(R.string.low_hr);
            text = context.getString(R.string.low_hr_description);
        } else if(average >= 60 && average <= 100){
            title = context.getString(R.string.normal_hr);
            text = context.getString(R.string.normal_hr_description);
        } else{
            title = context.getString(R.string.high_hr);
            text = context.getString(R.string.high_hr_description);
        }

        if(average >= maximumHeartRate){
            title = context.getString(R.string.extreme_hr);
            text = context.getString(R.string.extreme_hr_description);
        }
    }
}
