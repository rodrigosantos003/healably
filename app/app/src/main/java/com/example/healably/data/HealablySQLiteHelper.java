package com.example.healably.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.healably.accounts.model.User;
import com.example.healably.user_profile.model.UserData;

import java.util.LinkedList;
import java.util.List;

/**
 * SQLiteHelper da aplicação*/
public class HealablySQLiteHelper extends SQLiteOpenHelper {

    //DB Info
    private static final String DATABASE_NAME = "HealablyDB";
    private static final int DATABASE_VERSION = 1;

    //Table Names
    private static final String TABLE_USER = "user";
    private static final String TABLE_LOGGED_USER = "logged_user";
    private static final String TABLE_USER_DATA = "user_data";

    //Column names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_GENDER = "gender";
    private static final String KEY_DATEOFBIRTH = "dateOfBirth";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_VALUE_TYPE = "value_type";
    private static final String KEY_VALUE = "value";
    private static final String KEY_DATE = "date";

    //Columns
    private static final String[] TABLE_USER_COLUMNS = {KEY_ID, KEY_NAME, KEY_GENDER, KEY_DATEOFBIRTH, KEY_EMAIL, KEY_PASSWORD};
    private static final String[] TABLE_USER_DATA_COLUMNS = {KEY_ID, KEY_USER_ID, KEY_VALUE_TYPE, KEY_VALUE, KEY_DATE};

    public HealablySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE Table user ( " +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_NAME + " TEXT, " +
                KEY_GENDER + " TEXT, " +
                KEY_DATEOFBIRTH + " TEXT, " +
                KEY_EMAIL + " TEXT, " +
                KEY_PASSWORD + " TEXT)";

        String CREATE_LOGGED_USER_TABLE = "CREATE Table logged_user ( " +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_USER_ID + " INTEGER, " +
                KEY_NAME + " TEXT, " +
                KEY_GENDER + " TEXT, " +
                KEY_DATEOFBIRTH + " TEXT, " +
                KEY_EMAIL + " TEXT, " +
                KEY_PASSWORD + " TEXT)";

        String CREATE_USER_DATA_TABLE = "CREATE Table user_data ( " +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_USER_ID + " INTEGER, " +
                KEY_VALUE_TYPE + " TEXT, " +
                KEY_VALUE + " DECIMAL(10, 5), " +
                KEY_DATE + " TEXT)";

        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_LOGGED_USER_TABLE);
        db.execSQL(CREATE_USER_DATA_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF exists user");
        db.execSQL("DROP TABLE IF exists logged_user");
        db.execSQL("DROP TABLE IF exists user_data");
        this.onCreate(db);
    }

    /**
     * Adiciona um utilizador à BD
     * @param user Utilizador a adicionar*/
    public void addUser(User user) {
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, user.getName());
        values.put(KEY_GENDER, user.getGender());
        values.put(KEY_DATEOFBIRTH, user.getDateOfBirth());
        values.put(KEY_EMAIL, user.getEmail());
        values.put(KEY_PASSWORD, user.getPassword());

        // 3. insert
        db.insert(TABLE_USER, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

    /**
     * Obtém um utilizador da BD, dado o seu ID
     * @param id ID do utilizador
     * @return Utilizador pretendido*/
    public User getUserById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor =
                db.query(TABLE_USER, // a. table
                        TABLE_USER_COLUMNS, // b. column names
                        " id = ?", // c. selections
                        new String[]{String.valueOf(id)}, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        if (cursor != null) {
            cursor.moveToFirst();

            User user = new User(
                    cursor.getInt(0) /*Id*/,
                    cursor.getString(1) /*Name*/,
                    cursor.getString(2) /*Gender*/,
                    cursor.getString(3) /*DateOfBirth*/,
                    cursor.getString(4) /*Email*/,
                    cursor.getString(5) /*Password*/
            );

            return user;
        }

        return null;
    }

    /**
     * Obtém a lista de todos os utilizadores da BD
     * @return Lista de utilizadores*/
    public List<User> getAllUsers() {
        List<User> users = new LinkedList<User>();

        // 1. build the query
        String query = "SELECT * FROM " + TABLE_USER;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build user and add it to list
        User user = null;
        if (cursor.moveToFirst()) {
            do {
                user = new User(
                        cursor.getInt(0) /*Id*/,
                        cursor.getString(1) /*Name*/,
                        cursor.getString(2) /*Gender*/,
                        cursor.getString(3) /*DateOfBirth*/,
                        cursor.getString(4) /*Email*/,
                        cursor.getString(5) /*Password*/
                );

                // Add user to users
                users.add(user);
            } while (cursor.moveToNext());
        }

        // return users
        return users;
    }

    /**
     * Obtém um uitlizador da BD, dado os seus dados de login
     * @param email Email do utilizador
     * @param password Password do utilizador
     * @return Utilizador pretendido*/
    public User getUserByLogin(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor =
                db.query(TABLE_USER, // a. table
                        TABLE_USER_COLUMNS, // b. column names
                        " email = ? and password = ?", // c. selections
                        new String[]{email, password}, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        if (cursor != null) {
            cursor.moveToFirst();

            User user = new User(
                    cursor.getInt(0) /*Id*/,
                    cursor.getString(1) /*Name*/,
                    cursor.getString(2) /*Gender*/,
                    cursor.getString(3) /*DateOfBirth*/,
                    cursor.getString(4) /*Email*/,
                    cursor.getString(5) /*Password*/
            );

            return user;
        }

        return null;
    }

    public void updateUserInfo(User user){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NAME, user.getName());
        contentValues.put(KEY_GENDER, user.getGender());
        contentValues.put(KEY_DATEOFBIRTH, user.getDateOfBirth());
        contentValues.put(KEY_EMAIL, user.getEmail());
        contentValues.put(KEY_PASSWORD, user.getPassword());

        db.update(TABLE_USER,
                contentValues,
                "id = ?",
                new String[]{String.valueOf(user.getId())});

        db.update(TABLE_LOGGED_USER,
                contentValues,
                "user_id = ?",
                new String[]{String.valueOf(user.getId())});

        db.close();
    }

    /**
     * Regista um utilizador na BD como loggado
     * @param user Utilizador a loggar*/
    public void loginUser(User user) {
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, user.getName());
        values.put(KEY_USER_ID, user.getId());
        values.put(KEY_GENDER, user.getGender());
        values.put(KEY_DATEOFBIRTH, user.getDateOfBirth());
        values.put(KEY_EMAIL, user.getEmail());
        values.put(KEY_PASSWORD, user.getPassword());

        // 3. insert
        db.insert(TABLE_LOGGED_USER, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

    /**
     * Obtém o utilizador loggado
     * @return Utilizador loggado*/
    public User getLoggedUser() {
        // 1. build the query
        String query = "SELECT * FROM " + TABLE_LOGGED_USER;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build user and add it to list
        User user = null;
        if (cursor.moveToFirst()) {

            user = new User(
                    cursor.getInt(1) /*User Id*/,
                    cursor.getString(2) /*Name*/,
                    cursor.getString(3) /*Gender*/,
                    cursor.getString(4) /*DateOfBirth*/,
                    cursor.getString(5) /*Email*/,
                    cursor.getString(6) /*Password*/
            );

            //return User
            return user;
        }

        return null;
    }

    /**
     * Remove o utilizador loggado da BD*/
    public void logoutUser(){
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("delete from " + TABLE_LOGGED_USER);
        db.close();
    }

    /**
     * Adiciona dados do utilizador a BD
     * @param userData Dados a introduzir*/
    public void addUserData(UserData userData){
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_USER_ID, userData.getUserId());
        values.put(KEY_VALUE_TYPE, userData.getValueType());
        values.put(KEY_VALUE, userData.getValue());
        values.put(KEY_DATE, userData.getDate());

        // 3. insert
        db.insert(TABLE_USER_DATA, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

    /**
     * Obtém a lista de dados de um utilizador da BD
     * @param userId ID do utilizador
     * @return Lista de dados*/
    public List<UserData> getUserData(int userId){
        List<UserData> userDataList = new LinkedList<UserData>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor =
                db.query(TABLE_USER_DATA, // a. table
                        TABLE_USER_DATA_COLUMNS, // b. column names
                        " user_id = ?", // c. selections
                        new String[]{String.valueOf(userId)}, // d. selections args
                        null, // e. group by
                        null, // f. having
                        "id DESC", // g. order by
                        null); // h. limit

        UserData userData = null;
        if (cursor.moveToFirst()) {
            do {
                userData = new UserData(
                        cursor.getInt(0), /*ID*/
                        cursor.getInt(1), /*User ID*/
                        cursor.getString(2), /*Value Type*/
                        cursor.getDouble(3), /*Value*/
                        cursor.getString(4) /*Date*/
                );

                userDataList.add(userData);
            } while (cursor.moveToNext());
        }

        return userDataList;
    }
}
