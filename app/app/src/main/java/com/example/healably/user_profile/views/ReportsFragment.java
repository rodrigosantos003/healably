package com.example.healably.user_profile.views;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.example.healably.ListInitializer;
import com.example.healably.R;
import com.example.healably.ReportsListAdapter;
import com.example.healably.user_profile.controller.UserDataController;
import com.example.healably.user_profile.model.UserData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReportsFragment extends Fragment {

    UserDataController userDataController;

    public static ReportsFragment newInstance() {
        return new ReportsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_reports, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.lefttoright);
        view.startAnimation(animation);

        userDataController = new UserDataController(getContext(), view);
        userDataController.setUserText(getActivity());


        ((Button) view.findViewById(R.id.reports_btnStructure)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get data from DB
                List<UserData> weightList = userDataController.getListOfValues(UserDataController.WEIGHT);
                List<UserData> heightList = userDataController.getListOfValues(UserDataController.HEIGHT);
                List<UserData> abdominalPerimeterList = userDataController.getListOfValues(UserDataController.ABDOMINAL_PERIMETER);

                //Add to a list
                List<UserData> userData = new ArrayList<UserData>();
                userData.addAll(weightList);
                userData.addAll(heightList);
                userData.addAll(abdominalPerimeterList);

                //Sort the list
                Collections.sort(userData);

                //Put on the recyclerview
                RecyclerView recyclerView = view.findViewById(R.id.reports_rv_history);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                ReportsListAdapter adapter = new ReportsListAdapter(userData);
                recyclerView.setAdapter(adapter);

                userDataController.bodyStructureReport();
            }
        });


        ((Button) view.findViewById(R.id.reports_btnSugar)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get data from DB
                List<UserData> bloodSugarList = userDataController.getListOfValues(UserDataController.BLOOD_SUGAR);

                //Sort the list
                Collections.sort(bloodSugarList);

                //Put on the recyclerview
                RecyclerView recyclerView = view.findViewById(R.id.reports_rv_history);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                ReportsListAdapter adapter = new ReportsListAdapter(bloodSugarList);
                recyclerView.setAdapter(adapter);

                userDataController.bloodSugarReport();
            }
        });

        ((Button) view.findViewById(R.id.reports_btnPressure)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get data from DB
                List<UserData> sysBloodPressureList= userDataController.getListOfValues(UserDataController.SYS_BLOOD_PRESSURE);
                List<UserData> diaBloodPressureList = userDataController.getListOfValues(UserDataController.DIA_BLOOD_PRESSURE);
                List<UserData> heartRateList = userDataController.getListOfValues(UserDataController.HEART_RATE);

                //Add to a list
                List<UserData> userData = new ArrayList<UserData>();
                userData.addAll(sysBloodPressureList);
                userData.addAll(diaBloodPressureList);
                userData.addAll(heartRateList);

                //Sort the list
                Collections.sort(userData);

                //Put on the recyclerview
                RecyclerView recyclerView = view.findViewById(R.id.reports_rv_history);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                ReportsListAdapter adapter = new ReportsListAdapter(userData);
                recyclerView.setAdapter(adapter);

                userDataController.bloodPressureReport();
            }
        });

    }

}