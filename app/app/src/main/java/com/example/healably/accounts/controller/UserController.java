package com.example.healably.accounts.controller;

import android.content.Context;
import android.database.CursorIndexOutOfBoundsException;

import com.example.healably.R;
import com.example.healably.accounts.model.User;
import com.example.healably.data.HealablySQLiteHelper;

import java.util.List;

/**
 * Controlador para funcionalidade de Contas
 * */
public class UserController {

    Context context;
    HealablySQLiteHelper healablySQLiteHelper;
    String invalidDataText;

    public UserController(Context context) {
        this.context = context;
        this.healablySQLiteHelper = new HealablySQLiteHelper(this.context);
        this.invalidDataText = "";
    }

    /**
     * Realiza o login de utilizadores
     * @param email Email do utilizador
     * @param password Password do utilizador
     * @return True se o login ocorreu com sucesso, False caso contrário
     * @throws CursorIndexOutOfBoundsException Se não for encontrado o utilizador pretendido*/
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

    /**
     * Realiza o registo de utilizadores
     * @param name Nome do utilizador
     * @param gender Género do utilizador
     * @param dateOfBirth Data de nascimento do utilizador
     * @param email Email do utilizador
     * @param password Password do utilizador
     * @return True se o registo ocorreu com sucesso, False caso contrário*/
    public boolean signupUser(String name, String gender, String dateOfBirth, String email, String password){
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
            setInvalidDataText(validName, validGender, validDateOfBirth, validEmail, validPassword);
        }

        return false;
    }

    /**
     * Valida se um utilizador já existe
     * @param user Utilizador a validar
     * @return True se o utilizador existir, False caso contrário
     * */
    private boolean userExists(User user){
        List<User> users = healablySQLiteHelper.getAllUsers();
        for(User item : users){
            if (item.getEmail().equals(user.getEmail())) {
                return true;
            }
        }

        return false;
    }

    public String getInvalidDataText() {
        return invalidDataText;
    }

    /**
     * Constrói o texto de dados inválidos, com base na validação dos campos introduzidos
     * @param validName Nome válido
     * @param validGender Género válido
     * @param validDateOfBirth Data de nascimento válida
     * @param validEmail Email válido
     * @param validPassword Password válida*/
    private void setInvalidDataText(boolean validName, boolean validGender, boolean validDateOfBirth, boolean validEmail, boolean validPassword) {

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

        invalidDataText = output;
    }
}
