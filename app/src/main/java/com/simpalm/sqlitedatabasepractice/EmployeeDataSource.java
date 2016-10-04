package com.simpalm.sqlitedatabasepractice;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Simpalm on 9/1/16.
 */

public class EmployeeDataSource {


    SqliteHelper sqliteHelper;
    SQLiteDatabase sqLiteDatabase;


    //String[] columnNames = {SqliteHelper.COLUMN_NAME_ID, SqliteHelper.COLUMN_NAME_EMPLOYEE};

    String[] columnNames = {SqliteHelper.COLUMN_NAME_ID, SqliteHelper.EMPLOYEE_FIRST_NAME, SqliteHelper.EMPLOYEE_LAST_NAME, SqliteHelper.EMPLOYEE_PASSWORD, SqliteHelper.EMPLOYEE_EMAIL, SqliteHelper.EMPLOYEE_DOB, SqliteHelper.EMPLOYEE_PHONE};

    public EmployeeDataSource(Context context) {

        sqliteHelper = new SqliteHelper(context);


    }

    public void open() {

        sqLiteDatabase = sqliteHelper.getWritableDatabase();


    }

    public void insertEmployeeName(String firstName, String lastName, String password, String confirmPassword, String email, String phone, String dob) {

        ContentValues contentValues = new ContentValues();

        contentValues.put(SqliteHelper.EMPLOYEE_FIRST_NAME, firstName);
        contentValues.put(SqliteHelper.EMPLOYEE_LAST_NAME, lastName);

        contentValues.put(SqliteHelper.EMPLOYEE_PASSWORD, password);


        contentValues.put(SqliteHelper.EMPLOYEE_EMAIL, email);

        contentValues.put(SqliteHelper.EMPLOYEE_PHONE, phone);

        contentValues.put(SqliteHelper.EMPLOYEE_DOB, dob);


        sqLiteDatabase.insert(SqliteHelper.EMPLOYEE_DATA_TABLE, null, contentValues);
        Log.d("Sign Up Data", "insertEmployeeName");


    }


    public ArrayList<UserInformation> getEmployee() {
        ArrayList<UserInformation> userInformationArrayList = new ArrayList<>();

        Cursor cursor = sqLiteDatabase.query(SqliteHelper.EMPLOYEE_DATA_TABLE, columnNames, null, null, null, null, null);

        cursor.moveToFirst();
        Log.d("ArrayList", "size of cursor" + cursor.getCount());

        do {

            UserInformation userInformation = new UserInformation();
            userInformation.setId(cursor.getInt(0));
            userInformation.setFirstName(cursor.getString(1));
            userInformation.setLastName(cursor.getString(2));
            userInformation.setEmailAddress(cursor.getString(4));

            userInformation.setDob((cursor.getString(5)));
            userInformation.setPhoneNumber((cursor.getString(6)));
            Log.d("ArrayList", "First name" + cursor.getString(1));
            Log.d("ArrayList", "Last Name" + cursor.getString(2));
            Log.d("ArrayList", "Email" + cursor.getString(4));

            userInformationArrayList.add(userInformation);

        }

        // cursor.movetoNext will move to next row until finished
        while (cursor.moveToNext());


        cursor.close();
        return userInformationArrayList;
    }

    public boolean checkUserExist(String firstName) {

        String[] column = new String[]{
                SqliteHelper.EMPLOYEE_FIRST_NAME
        };

        Cursor cursor = sqLiteDatabase.query(SqliteHelper.EMPLOYEE_DATA_TABLE, column, SqliteHelper.EMPLOYEE_FIRST_NAME + " = " + "'" + firstName + "'"
                , null, null, null, null);

        Integer count = cursor.getCount();
        if (count > 0) {

            return true;
        }
        return false;
    }

    public UserInformation getSingleEmployee(int id) {

        Cursor cursor = sqLiteDatabase.query(SqliteHelper.EMPLOYEE_DATA_TABLE, columnNames, SqliteHelper.COLUMN_NAME_ID + " = " + id, null, null, null, null);

        cursor.moveToFirst();

        UserInformation userInformation = new UserInformation();
        userInformation.setFirstName(cursor.getString(1));
        userInformation.setLastName(cursor.getString(2));
        userInformation.setEmailAddress(cursor.getString(4));

        userInformation.setDob((cursor.getString(5)));
        userInformation.setPhoneNumber((cursor.getString(6)));
        Log.d("ArrayList", "First name" + cursor.getString(1));
        Log.d("ArrayList", "Last Name" + cursor.getString(2));
        Log.d("ArrayList", "Email" + cursor.getString(4));
        cursor.close();

        return userInformation;


    }

    public void updateEmployeeData(int id, ContentValues contentValues) {

        sqLiteDatabase.update(SqliteHelper.EMPLOYEE_DATA_TABLE, contentValues, SqliteHelper.COLUMN_NAME_ID + " = " + id, null);


    }

    public void closeDatabase() {

        sqLiteDatabase.close();
    }

    public void deleteEmployeeData(int id) {
        sqLiteDatabase.delete(SqliteHelper.EMPLOYEE_DATA_TABLE, SqliteHelper.COLUMN_NAME_ID + " = " + id, null);

    }


}
