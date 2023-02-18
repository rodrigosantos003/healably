package com.example.healably.user_profile.views;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.healably.R;
import com.example.healably.user_profile.controller.UserDataController;

public class BloodPressureFragment extends Fragment {

    UserDataController userDataController;
    EditText sysText;
    EditText diaText;
    EditText hrText;

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

        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.lefttoright);
        view.startAnimation(animation);

        userDataController = new UserDataController(getContext(), view);
        userDataController.setUserText(getActivity());
        userDataController.showBloodPressure();

        ((Button) view.findViewById(R.id.bloodPressure_btn_addValue)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = onCreateDialog();
                dialog.show();
            }
        });
    }

    public AlertDialog onCreateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.dialog_insert_blood_pressure, null))
                .setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        double sysBloodPressure = Double.parseDouble(sysText.getText().toString());
                        double diaBloodPressure = Double.parseDouble(diaText.getText().toString());
                        double heartRate = Double.parseDouble(hrText.getText().toString());

                        userDataController.addValue(UserDataController.SYS_BLOOD_PRESSURE, sysBloodPressure);
                        userDataController.addValue(UserDataController.DIA_BLOOD_PRESSURE, diaBloodPressure);
                        userDataController.addValue(UserDataController.HEART_RATE, heartRate);

                        userDataController.showBloodPressure();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
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

                sysText = (EditText) dialog.findViewById(R.id.edit_sys_bp);
                diaText = (EditText) dialog.findViewById(R.id.edit_dia_bp);
                hrText = (EditText) dialog.findViewById(R.id.edit_hr);
            }
        });

        return dialog;
    }
}