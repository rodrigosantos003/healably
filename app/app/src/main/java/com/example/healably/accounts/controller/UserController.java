package com.example.healably.accounts.controller;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.CursorIndexOutOfBoundsException;

import com.example.healably.R;
import com.example.healably.accounts.model.User;
import com.example.healably.data.HealablySQLiteHelper;

import java.util.List;

public class UserController {

    Context context;
    HealablySQLiteHelper healablySQLiteHelper;

    public UserController(Context context) {
        this.context = context;
        this.healablySQLiteHelper = new HealablySQLiteHelper(this.context);
    }

    public boolean loginUser(String email, String password) throws CursorIndexOutOfBoundsException {
        User user = new User(email, password);

        if (user.isEmailValid() && user.isPasswordValid()) {
            User userToLogin = healablySQLiteHelper.getUserByLogin(user.getEmail(), user.getPassword());
            healablySQLiteHelper.logoutUser();
            healablySQLiteHelper.loginUser(userToLogin);

            return true;
        }

        return false;
    }

    public boolean signupUser(String name, String gender, String dateOfBirth, String email, String password) throws Exception{
        User user = new User(name, gender, dateOfBirth, email, password);

        boolean validName = user.isNameValid();
        boolean validGender = !gender.isEmpty();
        boolean validDateOfBirth = !dateOfBirth.isEmpty();
        boolean validEmail = user.isEmailValid();
        boolean validPassword = user.isPasswordValid();

        if (validName && validGender && validDateOfBirth && validEmail && validPassword) {
            if(!userExists(user)){
                healablySQLiteHelper.addUser(user);
                return true;
            }
        } else{
            String text = invalidDataText(validName, validGender, validDateOfBirth, validEmail, validPassword);
            showInvalidDataDialog(text);
        }

        return false;
    }

    private boolean userExists(User user){
        List<User> users = healablySQLiteHelper.getAllUsers();
        for(User item : users){
            if (item.getEmail().equals(user.getEmail())) {
                return true;
            }
        }

        return false;
    }

    private void showInvalidDataDialog(String text){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
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

    private String invalidDataText(boolean validName, boolean validGender, boolean validDateOfBirth, boolean validEmail, boolean validPassword) {
        String output = context.getString(R.string.correct_fields) + "\n";

        if (!validName)
            output += "\n" + context.getString(R.string.name) + " - " + context.getString(R.string.cannot_be_empty) + "; " + context.getString(R.string.cannot_contain_digits) + "\n";
        if (!validGender)
            output += "\n" + context.getString(R.string.gender) + " - " + context.getString(R.string.cannot_be_empty) + "\n";
        if (!validDateOfBirth)
            output += "\n" + context.getString(R.string.date_of_birth) + " - " + context.getString(R.string.cannot_be_empty) + "\n";
        if (!validEmail)
            output += "\n" + context.getString(R.string.email) + " - " + context.getString(R.string.must_be_valid_address) + "\n";
        if (!validPassword)
            output += "\n" + context.getString(R.string.password) + " - " + context.getString(R.string.password_requirements);

        return output;
    }
}
