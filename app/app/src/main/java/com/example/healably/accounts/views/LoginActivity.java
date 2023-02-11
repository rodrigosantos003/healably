package com.example.healably.accounts.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.healably.MainActivity;
import com.example.healably.data.HealablySQLiteHelper;
import com.example.healably.R;
import com.example.healably.accounts.model.User;

public class LoginActivity extends AppCompatActivity {

    EditText editEmail;
    EditText editPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editEmail = (EditText) findViewById(R.id.login_edit_email);
        editPassword = (EditText) findViewById(R.id.login_edit_password);

        ((Button) findViewById(R.id.login_bt_login)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editEmail.getText().toString();
                String password = editPassword.getText().toString();

                User user = new User(email, password);

                if(user.isEmailValid() && user.isPasswordValid()){
                    HealablySQLiteHelper healablySQLiteHelper = new HealablySQLiteHelper(getApplicationContext());

                    try {
                        User loggedUser = healablySQLiteHelper.getUserByLogin(user.getEmail(), user.getPassword());
                        healablySQLiteHelper.logoutUser(); //TODO: Implement this on logout feature (user profile)
                        healablySQLiteHelper.loginUser(loggedUser);
                        Toast.makeText(LoginActivity.this, R.string.logged_in_successfully, Toast.LENGTH_SHORT).show();
                        Intent it = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(it);
                    } catch (CursorIndexOutOfBoundsException e) {
                        Toast.makeText(LoginActivity.this, R.string.invalid_credentials, Toast.LENGTH_SHORT).show();
                    } catch (Exception e){
                        Log.d("EXCEPTION", e.getMessage());
                        Toast.makeText(LoginActivity.this, R.string.error_occurred, Toast.LENGTH_SHORT).show();
                    }
                } else{
                    Toast.makeText(LoginActivity.this, R.string.invalid_credentials, Toast.LENGTH_SHORT).show();
                }

                editEmail.setText("");
                editPassword.setText("");
            }
        });

        ((Button) findViewById(R.id.login_bt_signup)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(it);
            }
        });
    }
}