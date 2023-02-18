package com.example.healably.user_profile.views;

import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

public class BloodSugarFragment extends Fragment {

    UserDataController userDataController;
    EditText valueText;

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

        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.lefttoright);
        view.startAnimation(animation);

        userDataController = new UserDataController(getContext(), view);
        userDataController.setUserText(getActivity());
        userDataController.showBloodSugar();

        ((Button) view.findViewById(R.id.bloodSugar_btn_addValue)).setOnClickListener(new View.OnClickListener() {
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
        builder.setView(inflater.inflate(R.layout.dialog_insert_blood_sugar, null))
                .setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        double value = Double.parseDouble(valueText.getText().toString());
                        userDataController.addValue(UserDataController.BLOOD_SUGAR, value);
                        userDataController.showBloodSugar();
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

                valueText = (EditText) dialog.findViewById(R.id.bloodSugar_edit_value);
            }
        });

        return dialog;
    }
}