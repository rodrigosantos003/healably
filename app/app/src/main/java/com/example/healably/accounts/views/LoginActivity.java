package com.example.healably.accounts.views;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.healably.MainActivity;
import com.example.healably.accounts.controller.UserController;
import com.example.healably.R;

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

                UserController userController = new UserController(LoginActivity.this);

                try {
                    if (userController.loginUser(email, password)) {
                        Toast.makeText(LoginActivity.this, R.string.logged_in_successfully, Toast.LENGTH_SHORT).show();
                        Intent it = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(it);
                    } else{
                        showErrorMessage(getString(R.string.invalid_credentials));
                    }
                } catch (CursorIndexOutOfBoundsException e){
                    showErrorMessage(getString(R.string.invalid_credentials));
                } catch (Exception e){
                    showErrorMessage(e.getMessage());
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

    private void showErrorMessage(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setMessage(message)
                .setTitle(R.string.error)
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