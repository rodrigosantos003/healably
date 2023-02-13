package com.example.healably.accounts.views;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.healably.accounts.controller.UserController;
import com.example.healably.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SignupActivity extends AppCompatActivity {

    EditText editName;
    EditText editDateOfBirth;
    EditText editEmail;
    EditText editPassword;

    final Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        editName = (EditText) findViewById(R.id.signup_edit_name);
        editDateOfBirth = (EditText) findViewById(R.id.signup_edit_date_of_birth);
        editEmail = (EditText) findViewById(R.id.signup_edit_email);
        editPassword = (EditText) findViewById(R.id.signup_edit_password);

        editDateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(SignupActivity.this, pickedDateOfBirth(), calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        ((Button) findViewById(R.id.signup_bt_signup)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editName.getText().toString();
                String gender = selectedGender();
                String dateOfBirth = editDateOfBirth.getText().toString();
                String email = editEmail.getText().toString();
                String password = editPassword.getText().toString();

                UserController userController = new UserController(SignupActivity.this);

                try {
                    if (userController.signupUser(name, gender, dateOfBirth, email, password)) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
                        builder.setMessage(R.string.signedup_successfully)
                                .setTitle(R.string.success)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        editEmail.setText("");
                                        editPassword.setText("");
                                        finish();
                                    }
                                });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                } catch (Exception e) {
                    Log.d("EXCEPTION", e.getMessage());
                }
            }
        });
    }

    private DatePickerDialog.OnDateSetListener pickedDateOfBirth() {
        return new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateDateOfBirth();
            }
        };
    }

    private void updateDateOfBirth() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        editDateOfBirth.setText(dateFormat.format(calendar.getTime()));
    }

    private String selectedGender() {
        int selectedId = ((RadioGroup) findViewById(R.id.signup_rg_gender)).getCheckedRadioButtonId();
        if (selectedId != -1)
            return ((RadioButton) findViewById(selectedId)).getText().toString();

        return "";
    }
}