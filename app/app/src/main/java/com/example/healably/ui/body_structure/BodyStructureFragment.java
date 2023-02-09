package com.example.healably.ui.body_structure;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healably.MySQLiteHelper;
import com.example.healably.R;
import com.example.healably.accounts.model.User;

import org.w3c.dom.Text;

public class BodyStructureFragment extends Fragment {

    private BodyStructureViewModel mViewModel;

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

        //TODO: Get logged user
        MySQLiteHelper mySQLiteHelper = new MySQLiteHelper(getContext());
        User user = mySQLiteHelper.getUserById(0);

        String text = getString(R.string.hello) + " " + user.getName();
        TextView tv = (TextView) view.findViewById(R.id.tv_user);
        tv.setText(text);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(BodyStructureViewModel.class);
        // TODO: Use the ViewModel
    }
}