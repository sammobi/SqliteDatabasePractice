package com.simpalm.sqlitedatabasepractice;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Simpalm on 9/2/16.
 */

public class SqliteHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "Testdatabase.db";
    private static final int DATABASE_VERSION = 2;
    public static final String TABLE_NAME = "Employee";
    public static final String EMPLOYEE_DATA_TABLE = "EmployeeData";
    public static final String EMPLOYEE_FIRST_NAME = "FirstName";
    public static final String EMPLOYEE_LAST_NAME = "LastName";
    public static final String EMPLOYEE_PASSWORD = "Password";
    public static final String EMPLOYEE_EMAIL = "Email";
    public static final String EMPLOYEE_DOB = "Dob";
    public static final String EMPLOYEE_PHONE = "PhoneNumber";
    public static final String COLUMN_NAME_ID = "_id";
    public static final String COLUMN_NAME_EMPLOYEE = "EmployeeName";
    // private static final String DATABASE_CREATE_QUERY = "create table " + TABLE_NAME + " (" + COLUMN_NAME_ID + " integer primary key autoincrement, " + COLUMN_NAME_EMPLOYEE + " text not null);";
    private static final String DATABASE_CREATE_EMPLOYEE_TABLE = "create table " + EMPLOYEE_DATA_TABLE + " (" + COLUMN_NAME_ID + " integer primary key autoincrement, " + EMPLOYEE_FIRST_NAME + " text not null, " + EMPLOYEE_LAST_NAME + " text not null, " +
            EMPLOYEE_PASSWORD + " text not null, " + EMPLOYEE_EMAIL + " text not null, " +
            EMPLOYEE_PHONE + " text not null, " + EMPLOYEE_DOB + " text not null);";
    private static final String DATABASE_DELETE_QUERY = "drop table if exists " + EMPLOYEE_DATA_TABLE;


    public SqliteHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);


    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DATABASE_CREATE_EMPLOYEE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DATABASE_DELETE_QUERY);
        onCreate(sqLiteDatabase);
    }


}
