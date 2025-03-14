package com.example.logsheet.Utilities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.logsheet.Models.User;

public class UserDBHelper extends SQLiteOpenHelper {
    // Database Name and Version
    private static final String DATABASE_NAME = "LogSheet.db";
    private static final int DATABASE_VERSION = 1;

    // Table Name
    private static final String TABLE_USER = "User";

    // Column Names
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_GENDER = "gender";
    private static final String COLUMN_AGE = "age";
    private static final String COLUMN_HEIGHT = "height";
    private static final String COLUMN_WEIGHT = "weight";

    // Create Table SQL Query
    private static final String CREATE_TABLE_USER =
            "CREATE TABLE " + TABLE_USER + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USERNAME + " TEXT NOT NULL, " +
                    COLUMN_PASSWORD + " TEXT NOT NULL, " +
                    COLUMN_GENDER + " TEXT, " +
                    COLUMN_AGE + " INTEGER, " +
                    COLUMN_HEIGHT + " REAL, " +
                    COLUMN_WEIGHT + " REAL" +
                    ");";

    public UserDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public long insertUser(String username, String password, String gender, int age, float height, float weight) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", password);
        values.put("gender", gender);
        values.put("age", age);
        values.put("height", height);
        values.put("weight", weight);

        // Insert the row into the table
        long result = db.insert("User", null, values);
        db.close();

        return result; // Returns the row ID if successful, -1 if failed
    }

    public boolean isUsernameExists(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM User WHERE username = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username});

        boolean exists = (cursor.getCount() > 0); // If count > 0, username exists

        cursor.close();
        db.close();

        return exists;
    }

    public User logInUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        User user = null;

        String query = "SELECT * FROM User WHERE username = ? AND password = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username, password});

        if (cursor.moveToFirst()) {
            // Retrieve user details from the cursor
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String retrievedUsername = cursor.getString(cursor.getColumnIndexOrThrow("username"));
            String retrievedPassword = cursor.getString(cursor.getColumnIndexOrThrow("password"));
            String gender = cursor.getString(cursor.getColumnIndexOrThrow("gender"));
            int age = cursor.getInt(cursor.getColumnIndexOrThrow("age"));
            float height = cursor.getFloat(cursor.getColumnIndexOrThrow("height"));
            float weight = cursor.getFloat(cursor.getColumnIndexOrThrow("weight"));

            // Create a User object
            user = new User(id, retrievedUsername, retrievedPassword, gender, age, height, weight);
        }

        cursor.close();
        db.close();
        return user; // Returns null if login fails
    }

    public User getUserById(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        User user = null;

        // Query to fetch user details
        String query = "SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});

        if (cursor != null && cursor.moveToFirst()) {
            // Create User object from retrieved data
            user = new User(
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERNAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GENDER)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_AGE)),
                    cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_HEIGHT)),
                    cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_WEIGHT))
            );
            cursor.close();
        }

        db.close();
        return user; // Returns the user object or null if not found
    }

    public boolean updateUserById(int userId, String username, String gender, int age, float height, float weight) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_GENDER, gender);
        values.put(COLUMN_AGE, age);
        values.put(COLUMN_HEIGHT, height);
        values.put(COLUMN_WEIGHT, weight);

        // Update user record
        int rowsAffected = db.update(TABLE_USER, values, COLUMN_ID + " = ?", new String[]{String.valueOf(userId)});

        db.close();

        // Return true if at least one row was updated, otherwise false
        return rowsAffected > 0;
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }
}
