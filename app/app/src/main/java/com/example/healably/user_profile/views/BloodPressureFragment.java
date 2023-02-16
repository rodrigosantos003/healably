package com.example.healably.user_profile.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.healably.R;
import com.example.healably.user_profile.controller.UserDataController;

public class BloodPressureFragment extends Fragment {

    public static BloodPressureFragment newInstance() {
        return new BloodPressureFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_blood_pressure, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        UserDataController userDataController = new UserDataController(getContext(), view);
        userDataController.setUserText();
        userDataController.showBloodPressure();
    }
}