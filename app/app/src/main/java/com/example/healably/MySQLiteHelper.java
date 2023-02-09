package com.example.healably;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.healably.accounts.model.User;

import java.util.LinkedList;
import java.util.List;

public class MySQLiteHelper extends SQLiteOpenHelper {

    //DB Info
    private static final String DATABASE_NAME = "HealablyDB";
    private static final int DATABASE_VERSION = 1;


    //Table Users info
    private static final String TABLE_USER = "user";

    // User Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_GENDER = "gender";
    private static final String KEY_DATEOFBIRTH = "dateOfBirth";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";

    //User Table All Columns
    private static final String[] TABLE_USER_COLUMNS = {KEY_ID, KEY_NAME, KEY_GENDER, KEY_DATEOFBIRTH, KEY_EMAIL, KEY_PASSWORD};

    public MySQLiteHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE Table user ( " +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_NAME+ " TEXT, " +
                KEY_GENDER+ " TEXT, " +
                KEY_DATEOFBIRTH+ " TEXT, " +
                KEY_EMAIL+ " TEXT, " +
                KEY_PASSWORD + " TEXT)";

        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF exists user");
        this.onCreate(db);
    }

    public void addUser(User user){
        //apenas para se ver o que acontece
        Log.d("addUser", user.toString());

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_ID, user.getId());
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

    public User getUserById(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor =
                db.query(TABLE_USER, // a. table
                        TABLE_USER_COLUMNS, // b. column names
                        " id = ?", // c. selections
                        new String[] { String.valueOf(id) }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        if(cursor != null){
            cursor.moveToFirst();

            User user = new User(
                    cursor.getInt(0) /*Id*/,
                    cursor.getString(1) /*Name*/,
                    cursor.getString(2) /*Gender*/,
                    cursor.getString(3) /*DateOfBirth*/,
                    cursor.getString(4) /*Email*/,
                    cursor.getString(5) /*Password*/
            );

            Log.d("getUser("+id+")", user.toString());

            return user;
        }

        return null;
    }

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

        Log.d("getAllUsers()", users.toString());

        // return users
        return users;
    }
    public User getUserByLogin(String email, String password){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor =
                db.query(TABLE_USER, // a. table
                        TABLE_USER_COLUMNS, // b. column names
                        " email = ? and password = ?", // c. selections
                        new String[] { email, password }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        if(cursor != null){
            cursor.moveToFirst();

            User user = new User(
                    cursor.getInt(0) /*Id*/,
                    cursor.getString(1) /*Name*/,
                    cursor.getString(2) /*Gender*/,
                    cursor.getString(3) /*DateOfBirth*/,
                    cursor.getString(4) /*Email*/,
                    cursor.getString(5) /*Password*/
            );

            Log.d("getUserByLogin("+ email + "," + password + ")", user.toString());

            return user;
        }

        return null;
    }


}
