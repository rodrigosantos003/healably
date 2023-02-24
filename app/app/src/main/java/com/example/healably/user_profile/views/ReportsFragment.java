package com.example.healably.user_profile.views;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.EditText;

import com.example.healably.R;
import com.example.healably.user_profile.adapter.ReportsListAdapter;
import com.example.healably.user_profile.controller.UserDataController;
import com.example.healably.user_profile.model.UserData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReportsFragment extends Fragment {

    UserDataController userDataController;
    RecyclerView recyclerView;
    EditText editValueText;

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

        recyclerView = view.findViewById(R.id.reports_rv_history);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

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
                ReportsListAdapter adapter = new ReportsListAdapter(getContext(), userData, new ReportsListAdapter.OnItemClickListener(){
                    @Override
                    public void onItemClick(Object item) {
                        editHistoryValue((UserData) item);
                    }
                });
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
                ReportsListAdapter adapter = new ReportsListAdapter(getContext(), bloodSugarList, new ReportsListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Object item) {
                        editHistoryValue((UserData) item);
                    }
                });
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
                ReportsListAdapter adapter = new ReportsListAdapter(getContext(), userData, new ReportsListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Object item) {
                        editHistoryValue((UserData) item);
                    }
                });
                recyclerView.setAdapter(adapter);

                userDataController.bloodPressureReport();
            }
        });

    }

    private void editHistoryValue(UserData userData) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.dialog_edit_history, null))
                .setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        double value = Double.parseDouble(editValueText.getText().toString());

                        //Atualiza valor
                        userData.setValue(value);
                        userDataController.updateValue(userData);

                        //Recarrega atividade
                        getActivity().recreate();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                dialog.getWindow().setBackgroundDrawableResource(R.color.gunmetal);

                editValueText = dialog.findViewById(R.id.edit_history_value);
                editValueText.setText(String.valueOf(userData.getValue()));
                ((Button) dialog.findViewById(R.id.editHistory_btDelete)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Elimina valor
                        userDataController.deleteValue(userData);
                        dialog.dismiss();

                        //Recarrega atividade
                        getActivity().recreate();
                    }
                });
            }
        });

        dialog.show();
    }
}