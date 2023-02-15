package com.example.healably.user_profile.views.blood_sugar;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.healably.R;
import com.example.healably.user_profile.controller.UserDataController;

public class BloodSugarFragment extends Fragment {

    private BloodSugarViewModel mViewModel;

    public static BloodSugarFragment newInstance() {
        return new BloodSugarFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_blood_sugar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        UserDataController userDataController = new UserDataController(getContext(), view);
        userDataController.setUserText();
        userDataController.showBloodSugar();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(BloodSugarViewModel.class);
        // TODO: Use the ViewModel
    }

}