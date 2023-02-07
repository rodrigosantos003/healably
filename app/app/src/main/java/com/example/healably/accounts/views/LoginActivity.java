package com.example.healably.accounts.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
                    //TODO: Query DB to find user and create a new object with the retrieved data
                    user.setAuthenticated(true);
                    Toast.makeText(LoginActivity.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                    //TODO: Login user
                } else{
                    Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                    editEmail.setText("");
                    editPassword.setText("");
                }
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