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
        UserDataController userDataController = new UserDataController(getContext(), view);
        userDataController.setUserText(getActivity());

        ((Button) view.findViewById(R.id.reports_btnStructure)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<UserData> userData = ListInitializer.getLista();
                Collections.sort(userData);
                RecyclerView recyclerView = view.findViewById(R.id.reports_rv_history);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                ReportsListAdapter adapter = new ReportsListAdapter(userData);
                recyclerView.setAdapter(adapter);
            }
        });

    }

}