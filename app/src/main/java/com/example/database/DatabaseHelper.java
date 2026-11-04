package com.example.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {
        super(context, "StudentDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE Students (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "sname TEXT, " +
                        "marks TEXT)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,
                          int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Students");
        onCreate(db);
    }

    public boolean addStudent(String name, String marks) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("sname", name);
        values.put("marks", marks);

        long result = db.insert("Students", null, values);

        return result != -1;
    }

    public String getAllStudents() {

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT sname, marks FROM Students", null);

        StringBuilder result = new StringBuilder();

        try {
            while (cursor.moveToNext()) {

                result.append("Student Name: ")
                        .append(cursor.getString(0))
                        .append("\nMarks: ")
                        .append(cursor.getString(1))
                        .append("\n\n");
            }
        } finally {
            cursor.close();
        }

        if (result.length() == 0) {
            return "No students found";
        }

        return result.toString();
    }
}