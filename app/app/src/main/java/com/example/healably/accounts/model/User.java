package com.example.healably.accounts.model;

import android.util.Patterns;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Modelo de dados de Utilizador*/
public class User {
    int id;
    String name;
    String gender;
    String dateOfBirth;
    String email;
    String password;

    //Recebe id (usado na leitura da bd)
    public User(int id, String name, String gender, String dateOfBirth, String email, String password) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.password = password;
    }

    //Não recebe id (usado na escrita da bd, o id é criado atraves da bd)
    public User(String name, String gender, String dateOfBirth, String email, String password) {
        this.name = name;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.password = password;
    }

    //Usado no login
    public User(String email, String password){
        this.email = email;
        this.password = password;
    }

    //Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    //Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Valida se o nome do utilizador é válido
     * @return True se o nome for válido, False caso contrário*/
    public boolean isNameValid(){
        if(name.isEmpty())
            return false;

        char[] chars = name.toCharArray();
        StringBuilder sb = new StringBuilder();
        for(char c : chars){
            if(Character.isDigit(c))
                return false;
        }

        return true;
    }

    /**
     * Valida se o email do utilizador é válido
     * @return True se o email for válido, False caso contrário*/
    public boolean isEmailValid() {
        return (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    /**
     * Valida se a password do utilizador é válido
     * @return True se a password for válida, False caso contrário*/
    public boolean isPasswordValid() {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@!#$%&/=._])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return (matcher.matches() && password.length() >= 8 && password.length() <= 14);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        final User other = (User) obj;

        if (!this.email.equals(other.email)) {
            return false;
        }

        return true;
    }
}
