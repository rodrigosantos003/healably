package com.example.healably;

import com.example.healably.user_profile.model.UserData;

import java.util.ArrayList;
import java.util.List;

public class ListInitializer {
    public static List<UserData> getLista() {
        List<UserData> list = new ArrayList<>();

        list.add(new UserData(1, 1, "WEIGHT", 0, "01/01/2000"));
        list.add(new UserData(2, 1, "HEIGHT", 0, "03/01/2000"));
        list.add(new UserData(3, 1, "ABDOMINAL PERIMETER", 0, "01/02/2000"));
        list.add(new UserData(4, 1, "BLOOD SUGAR", 0, "01/01/2023"));
        list.add(new UserData(5, 1, "SYSTOLIC BLOOD PRESSURE", 0, "01/01/2000"));
        list.add(new UserData(6, 1, "DIASTOLIC BLOOD PRESSURE", 0, "07/01/2000"));
        list.add(new UserData(7, 1, "HEART RATE", 0, "01/09/2000"));
        return list;
    }
}
