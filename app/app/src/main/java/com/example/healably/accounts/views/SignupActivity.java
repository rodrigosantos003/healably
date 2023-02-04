package com.example.healably.accounts.views;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.healably.R;
import com.example.healably.accounts.model.User;

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

                User user = new User(name, gender, dateOfBirth, email, password);

                if(user.isNameValid() && !gender.isEmpty() && !dateOfBirth.isEmpty() && user.isEmailValid() && user.isPasswordValid()){
                    //TODO: Create user account on DB

                    AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
                    builder.setMessage("Signed up successfully")
                            .setTitle("Success")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else{
                    Toast.makeText(SignupActivity.this, "Invalid data", Toast.LENGTH_SHORT).show();
                    editName.setText("");
                    editEmail.setText("");
                    editPassword.setText("");
                }
            }
        });
    }

    private DatePickerDialog.OnDateSetListener pickedDateOfBirth(){
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

    private void updateDateOfBirth(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        editDateOfBirth.setText(dateFormat.format(calendar.getTime()));
    }

    private String selectedGender(){
        int selectedId = ((RadioGroup) findViewById(R.id.signup_rg_gender)).getCheckedRadioButtonId();
        return ((RadioButton) findViewById(selectedId)).getText().toString();
    }
}