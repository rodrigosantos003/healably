package com.example.healably.accounts.views;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.healably.data.MySQLiteHelper;
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

                boolean validName = user.isNameValid();
                boolean validGender = !gender.isEmpty();
                boolean validDateOfBirth = !dateOfBirth.isEmpty();
                boolean validEmail = user.isEmailValid();
                boolean validPassword = user.isPasswordValid();

                if (validName && validGender && validDateOfBirth && validEmail && validPassword) {
                    MySQLiteHelper mySQLiteHelper = new MySQLiteHelper(getApplicationContext());
                    mySQLiteHelper.addUser(user);

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
                } else {
                    String text = invalidDataText(validName, validGender, validDateOfBirth, validEmail, validPassword);

                    AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
                    builder.setMessage(text)
                            .setTitle(R.string.invalid_data)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();
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
        if(selectedId != -1)
            return ((RadioButton) findViewById(selectedId)).getText().toString();

        return "";
    }

    private String invalidDataText(boolean validName, boolean validGender, boolean validDateOfBirth, boolean validEmail, boolean validPassword) {
        String output = getString(R.string.correct_fields) + "\n";

        if (!validName)
            output += "\n" + getString(R.string.name) + " - " + getString(R.string.cannot_be_empty) + "; " + getString(R.string.cannot_contain_digits) + "\n";
        if (!validGender)
            output += "\n" + getString(R.string.gender) + " - " + getString(R.string.cannot_be_empty) + "\n";
        if (!validDateOfBirth)
            output += "\n" + getString(R.string.date_of_birth) + " - " + getString(R.string.cannot_be_empty) + "\n";
        if (!validEmail)
            output += "\n" + getString(R.string.email) + " - " + getString(R.string.must_be_valid_address) + "\n";
        if (!validPassword)
            output += "\n" + getString(R.string.password) + " - " + getString(R.string.password_requirements);

        return output;
    }
}