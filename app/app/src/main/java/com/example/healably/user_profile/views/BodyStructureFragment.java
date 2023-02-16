package com.example.healably.user_profile.views;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.healably.R;
import com.example.healably.user_profile.controller.UserDataController;

public class BodyStructureFragment extends Fragment {

    public static BodyStructureFragment newInstance() {
        return new BodyStructureFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_body_structure, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        UserDataController userDataController = new UserDataController(getContext(), view);
        userDataController.setUserText();
        userDataController.showBodyStructure();

        ((Button) view.findViewById(R.id.bodyStructure_btn_add_value)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Show activity to add a value
            }
        });
    }
}