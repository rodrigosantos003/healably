package com.example.healably.user_profile.views;

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

public class BodyStructureFragment extends Fragment {

    UserDataController userDataController;
    EditText valueText;
    String valueType;

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

        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.lefttoright);
        view.startAnimation(animation);

        userDataController = new UserDataController(getContext(), view);
        userDataController.setUserText(getActivity());
        userDataController.showBodyStructure();

        ((Button) view.findViewById(R.id.bodyStructure_btn_add_value)).setOnClickListener(new View.OnClickListener() {
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
        builder.setView(inflater.inflate(R.layout.dialog_insert_body_structure, null))
                .setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        double value = Double.parseDouble(valueText.getText().toString());

                        switch (valueType) {
                            case UserDataController.WEIGHT:
                                userDataController.addValue(valueType, value);
                                break;
                            case UserDataController.HEIGHT:
                                userDataController.addValue(valueType, value);
                                break;
                            case UserDataController.ABDOMINAL_PERIMETER:
                                userDataController.addValue(valueType, value);
                                break;
                        }

                        userDataController.showBodyStructure();
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
                valueText = (EditText) dialog.findViewById(R.id.bodyStructure_edit_value);
                ((RadioGroup) dialog.findViewById(R.id.rg_valueType)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId){
                            case R.id.rb_weight:
                                valueType = UserDataController.WEIGHT;
                                break;
                            case R.id.rb_height:
                                valueType = UserDataController.HEIGHT;
                                break;
                            case R.id.rb_abdominal_perimeter:
                                valueType = UserDataController.ABDOMINAL_PERIMETER;
                                break;
                            default:
                                valueType = "";
                                break;
                        }
                    }
                });
            }
        });

        return dialog;
    }
}