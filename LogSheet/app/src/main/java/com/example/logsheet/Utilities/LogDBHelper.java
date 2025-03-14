package com.example.logsheet.Utilities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LogDBHelper extends SQLiteOpenHelper {
    // Database Name and Version
    private static final String DATABASE_NAME = "LogSheet.db";
    private static final int DATABASE_VERSION = 1;

    // Table Name
    private static final String TABLE_LOGS = "Logs";

    // Column Names
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USER_ID = "userId";
    private static final String COLUMN_FEELING = "feeling";
    private static final String COLUMN_ACTIVITY_DESC = "activityDesc";
    private static final String COLUMN_HOUR_DURATION = "hourDuration";
    private static final String COLUMN_MINUTE_DURATION = "minuteDuration";
    private static final String COLUMN_INTENSITY = "intensity";
    private static final String COLUMN_Q1 = "questionAnswer1";
    private static final String COLUMN_Q2 = "questionAnswer2";
    private static final String COLUMN_Q3 = "questionAnswer3";
    private static final String COLUMN_Q4 = "questionAnswer4";
    private static final String COLUMN_Q5 = "questionAnswer5";
    private static final String COLUMN_Q6 = "questionAnswer6";
    private static final String COLUMN_Q7 = "questionAnswer7";
    private static final String COLUMN_MONTH = "month";
    private static final String COLUMN_YEAR = "year";
    private static final String COLUMN_MONTH_WEEK = "monthWeek";
    private static final String COLUMN_DAY = "day";
    private static final String COLUMN_DATE_ADDED = "dateAdded";

    private static final String CREATE_TABLE_LOGS =
            "CREATE TABLE IF NOT EXISTS " + TABLE_LOGS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USER_ID + " INTEGER, " +
                    COLUMN_FEELING + " TEXT, " +
                    COLUMN_ACTIVITY_DESC + " TEXT, " +
                    COLUMN_HOUR_DURATION + " INTEGER, " +
                    COLUMN_MINUTE_DURATION + " INTEGER, " +
                    COLUMN_INTENSITY + " TEXT, " +
                    COLUMN_Q1 + " TEXT, " +
                    COLUMN_Q2 + " TEXT, " +
                    COLUMN_Q3 + " TEXT, " +
                    COLUMN_Q4 + " TEXT, " +
                    COLUMN_Q5 + " TEXT, " +
                    COLUMN_Q6 + " TEXT, " +
                    COLUMN_Q7 + " TEXT, " +
                    COLUMN_MONTH + " INTEGER, " +
                    COLUMN_YEAR + " INTEGER, " +
                    COLUMN_MONTH_WEEK + " INTEGER, " +
                    COLUMN_DAY + " INTEGER, " +
                    COLUMN_DATE_ADDED + " TEXT" +
                    ");";

    public LogDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public long insertLog(int userId, String feeling, String activityDesc, int hourDuration, int minuteDuration, String intensity,
                             String questionAnswer1, String questionAnswer2, String questionAnswer3,
                             String questionAnswer4, String questionAnswer5, String questionAnswer6,
                             String questionAnswer7, int month, int year, int monthWeek, int day, String dateAdded) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_USER_ID, userId);
        values.put("feeling", feeling);
        values.put("activityDesc", activityDesc);
        values.put("hourDuration", hourDuration);
        values.put("minuteDuration", minuteDuration);
        values.put("intensity", intensity);
        values.put("questionAnswer1", questionAnswer1);
        values.put("questionAnswer2", questionAnswer2);
        values.put("questionAnswer3", questionAnswer3);
        values.put("questionAnswer4", questionAnswer4);
        values.put("questionAnswer5", questionAnswer5);
        values.put("questionAnswer6", questionAnswer6);
        values.put("questionAnswer7", questionAnswer7);
        values.put("month", month);
        values.put("year", year);
        values.put("monthWeek", monthWeek);
        values.put("day", day);
        values.put("dateAdded", dateAdded);

        long result = db.insert("Logs", null, values);
        db.close();

        return result;
    }

    public ArrayList<HashMap<String, String>> getUniqueDates(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<HashMap<String, String>> uniqueDates = new ArrayList<>();

        String query = "SELECT DISTINCT year, month, monthWeek FROM Logs WHERE userId = ? ORDER BY year DESC, month DESC, monthWeek DESC";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});

        if (cursor != null && cursor.moveToFirst()) {
            do {
                HashMap<String, String> dateMap = new HashMap<>();
                dateMap.put("year", cursor.getString(0));  // Year
                dateMap.put("month", cursor.getString(1)); // Month
                dateMap.put("week", cursor.getString(2)); // Week

                uniqueDates.add(dateMap);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return uniqueDates;
    }

    public String getActivityLevel(int userId, int year, int month, int week) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT COUNT(DISTINCT day) AS daysLogged, " +
                "IFNULL(SUM(hourDuration), 0) AS totalHours, " +
                "COUNT(*) AS totalActivities " +
                "FROM Logs " +
                "WHERE userId = ? AND year = ? AND month = ? AND monthWeek = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId), String.valueOf(year), String.valueOf(month), String.valueOf(week)});

        int daysLogged = 0;
        int totalActivities = 0;
        double totalHours = 0.0;

        if (cursor != null && cursor.moveToFirst()) {
            int daysLoggedIndex = cursor.getColumnIndex("daysLogged");
            int totalHoursIndex = cursor.getColumnIndex("totalHours");
            int totalActivitiesIndex = cursor.getColumnIndex("totalActivities");

            if (daysLoggedIndex != -1) {
                daysLogged = cursor.getInt(daysLoggedIndex);
            }
            if (totalHoursIndex != -1) {
                totalHours = cursor.getDouble(totalHoursIndex);
            }
            if (totalActivitiesIndex != -1) {
                totalActivities = cursor.getInt(totalActivitiesIndex);
            }
        }

        cursor.close();
        db.close();

        // Determine activity level based on the hybrid approach
        if (daysLogged >= 6 && totalHours >= 14 && totalActivities >= 25) {
            return "Highly Active";
        } else if (daysLogged >= 4 && totalHours >= 7 && totalActivities >= 15) {
            return "Moderate Activity";
        } else if (daysLogged >= 1 && totalHours >= 1 && totalActivities >= 5) {
            return "Low Activity";
        } else {
            return "Inactive";
        }
    }

    public boolean deleteLogs(int userId, int year, int month, int monthWeek) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Define WHERE clause
        String whereClause = "userId = ? AND year = ? AND month = ? AND monthWeek = ?";
        String[] whereArgs = new String[]{String.valueOf(userId), String.valueOf(year), String.valueOf(month), String.valueOf(monthWeek)};

        // Perform deletion
        int rowsDeleted = db.delete(TABLE_LOGS, whereClause, whereArgs);

        db.close();

        // Return true if at least one row was deleted, otherwise return false
        return rowsDeleted > 0;
    }


    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);

        db.execSQL(CREATE_TABLE_LOGS);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_LOGS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGS);
        onCreate(db);
    }
}
