package com.example.kursovaoop;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "users.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_USERS = "users";
    private static final String COLUMN_ID = "id";
    public static final String COLUMN_FIRST_NAME = "first_name";
    public static final String COLUMN_LAST_NAME = "last_name";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_POSITION = "position";
    public static final String COLUMN_GRADE = "grade";

    public static final String TABLE_SALARIES = "salaries";
    private static final String COLUMN_SALARY_ID = "id";
    public static final String COLUMN_SALARY_AMOUNT = "amount";
    private static final String COLUMN_SALARY_DATE = "date";

    private static final String TABLE_USERS_CREATE =
            "CREATE TABLE " + TABLE_USERS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_FIRST_NAME + " TEXT, " +
                    COLUMN_LAST_NAME + " TEXT, " +
                    COLUMN_EMAIL + " TEXT, " +
                    COLUMN_PASSWORD + " TEXT, " +
                    COLUMN_POSITION + " TEXT, " +
                    COLUMN_GRADE + " TEXT) ";

    private static final String TABLE_SALARIES_CREATE =
            "CREATE TABLE " + TABLE_SALARIES + " (" +
                    COLUMN_SALARY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_SALARY_AMOUNT + " TEXT, " +
                    COLUMN_SALARY_DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP)";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_USERS_CREATE);
        db.execSQL(TABLE_SALARIES_CREATE);
    }

    public long addUser(String firstName, String lastName, String email, String password, String position, String grade) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FIRST_NAME, firstName);
        values.put(COLUMN_LAST_NAME, lastName);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_POSITION, position);
        values.put(COLUMN_GRADE, grade);
        long newRowId = db.insert(TABLE_USERS, null, values);
        db.close();
        return newRowId;
    }

    public boolean userExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_EMAIL + "=?", new String[]{email});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }

    public boolean authenticateUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_EMAIL + "=? AND " + COLUMN_PASSWORD + "=?", new String[]{email, password});

        boolean isAuthenticated = cursor.moveToFirst();
        cursor.close();
        db.close();

        return isAuthenticated;
    }

    public User getUserByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        User user = null;

        Cursor cursor = db.query(TABLE_USERS, new String[]{COLUMN_FIRST_NAME, COLUMN_LAST_NAME, COLUMN_EMAIL, COLUMN_GRADE, COLUMN_POSITION},
                COLUMN_EMAIL + "=?", new String[]{email}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") String firstName = cursor.getString(cursor.getColumnIndex(COLUMN_FIRST_NAME));
            @SuppressLint("Range") String lastName = cursor.getString(cursor.getColumnIndex(COLUMN_LAST_NAME));
            @SuppressLint("Range") String grade = cursor.getString(cursor.getColumnIndex(COLUMN_GRADE));
            @SuppressLint("Range") String position = cursor.getString(cursor.getColumnIndex(COLUMN_POSITION));

            // Перевіряємо, чи є поле position null, і замінюємо його пустою строкою, якщо це так
            if (position == null) {
                position = "";
            }

            Log.d("DatabaseHelper", "First Name: " + firstName);
            Log.d("DatabaseHelper", "Last Name: " + lastName);
            Log.d("DatabaseHelper", "Grade: " + grade);
            Log.d("DatabaseHelper", "Position: " + position);

            user = new User(firstName + " " + lastName, position, grade, email);
        }

        if (cursor != null) {
            cursor.close();
        }
        db.close();

        return user;
    }

    public void addSalary(String amount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SALARY_AMOUNT, amount);
        db.insert(TABLE_SALARIES, null, values);
        db.close();
    }

    public List<String> getAllSalaries() {
        List<String> salaries = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_SALARIES, new String[]{COLUMN_SALARY_AMOUNT}, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String amount = cursor.getString(cursor.getColumnIndex(COLUMN_SALARY_AMOUNT));
                salaries.add(amount);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return salaries;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SALARIES);
        onCreate(db);
    }
}
