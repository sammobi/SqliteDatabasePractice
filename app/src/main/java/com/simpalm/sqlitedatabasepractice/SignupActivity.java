package com.simpalm.sqlitedatabasepractice;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;


public class SignupActivity extends AppCompatActivity {
    EmployeeDataSource employeeDataSource;

    EditText mFirstNameEt, mLastNameEt, mPasswordEt, mConfirmPasswordEt, mEmailEt, mPhoneEt, mDobEt;
    Button mSubmitBtn, mDeleteBtn, mUpdateBtn;
    String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    //String regEx1 = "^(0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])[- /.](19|20)\\d{2}$";
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mFirstNameEt = (EditText) findViewById(R.id.nameEt);
        mLastNameEt = (EditText) findViewById(R.id.lastNameEt);
        mPasswordEt = (EditText) findViewById(R.id.passwordEt);
        mConfirmPasswordEt = (EditText) findViewById(R.id.confirmPasswordEt);
        mEmailEt = (EditText) findViewById(R.id.emailAddressEt);
        mPhoneEt = (EditText) findViewById(R.id.phoneEt);
        mDobEt = (EditText) findViewById(R.id.dobEt);
        mDobEt.setInputType(InputType.TYPE_NULL);
        mSubmitBtn = (Button) findViewById(R.id.submitbtn);
        mDeleteBtn = (Button) findViewById(R.id.deletebtn);
        mUpdateBtn = (Button) findViewById(R.id.updatebtn);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String firstname = bundle.getString("firstname");
            id = bundle.getInt("id");
            String lastname = bundle.getString("lastname");
            String dob = bundle.getString("dob");
            String email = bundle.getString("email");
            String phone = bundle.getString("phone");
            Log.d("Bundle", id + firstname + " :" + lastname + " :" + dob + " :" + email + " :" + phone);

            mFirstNameEt.setText(firstname);
            mLastNameEt.setText(lastname);
            mDobEt.setText(dob);
            mEmailEt.setText(email);
            mPhoneEt.setText(phone);

            mSubmitBtn.setVisibility(View.GONE);
            mDeleteBtn.setVisibility(View.GONE);


        }
        employeeDataSource = new EmployeeDataSource(SignupActivity.this);


        mDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                employeeDataSource.open();

                employeeDataSource.deleteEmployeeData(29);
                employeeDataSource.closeDatabase();
            }
        });

        mUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("onclick", "onclick");

                ContentValues contentValues = new ContentValues();
                contentValues.put(SqliteHelper.EMPLOYEE_FIRST_NAME, mFirstNameEt.getText().toString());
                contentValues.put(SqliteHelper.EMPLOYEE_LAST_NAME, mLastNameEt.getText().toString());

                contentValues.put(SqliteHelper.EMPLOYEE_PASSWORD, mPasswordEt.getText().toString());


                contentValues.put(SqliteHelper.EMPLOYEE_EMAIL, mEmailEt.getText().toString());

                contentValues.put(SqliteHelper.EMPLOYEE_PHONE, mPhoneEt.getText().toString());

                contentValues.put(SqliteHelper.EMPLOYEE_DOB, mDobEt.getText().toString());

                employeeDataSource.open();

                Log.d("SAM", "id:" + id);

                employeeDataSource.updateEmployeeData(id, contentValues);

                employeeDataSource.closeDatabase();

                finish();
                Intent intent = new Intent(SignupActivity.this, UserList.class);
                startActivity(intent);


            }
        });
        mDobEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog.OnDateSetListener dpd = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {

                        int s = monthOfYear + 1;
                        String a = s + "/" + dayOfMonth + "/" + year;
                        mDobEt.setText("" + a);
                    }
                };

                Time date = new Time();
                DatePickerDialog d = new DatePickerDialog(SignupActivity.this, dpd, date.year, date.month, date.monthDay);
                d.show();
            }
        });


        mSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("Sign Up Data", "onclick");

                String firstName = mFirstNameEt.getText().toString();
                String lastName = mLastNameEt.getText().toString();
                String password = mPasswordEt.getText().toString();
                String confirmPassword = mConfirmPasswordEt.getText().toString();
                String email = mEmailEt.getText().toString();
                String phone = mPhoneEt.getText().toString();
                String dob = mDobEt.getText().toString();

                if (validateFields()) {

                    employeeDataSource.open();

                    employeeDataSource.insertEmployeeName(firstName, lastName, password, confirmPassword, email, phone, dob);

                    employeeDataSource.closeDatabase();
                    startActivity(new Intent(SignupActivity.this, UserList.class));

                }


            }
        });
    }

    public boolean validateFields() {

        employeeDataSource.open();
        if (mFirstNameEt.getText().toString().length() == 0) {
            Toast.makeText(this, "First name cannot be blank", Toast.LENGTH_SHORT).show();
            return false;
        } else if (mLastNameEt.getText().toString().length() == 0) {
            Toast.makeText(this, "Last name cannot be blank", Toast.LENGTH_SHORT).show();
            return false;
        } else if (mPasswordEt.getText().toString().length() == 0) {
            Toast.makeText(this, "Password cannot be blank", Toast.LENGTH_SHORT).show();
            return false;
        } else if (mConfirmPasswordEt.getText().toString().length() == 0) {
            Toast.makeText(this, " Confirm Password cannot be blank", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!mPasswordEt.getText().toString().equals(mConfirmPasswordEt.getText().toString())) {
            Toast.makeText(this, "Password and confirm password does not match", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!mEmailEt.getText().toString().matches(regex)) {
            Toast.makeText(this, " Email is invalid", Toast.LENGTH_SHORT).show();
            return false;
        } else if (mPhoneEt.getText().toString().length() == 0) {
            Toast.makeText(this, " Phone number cannot be blank", Toast.LENGTH_SHORT).show();
            return false;
        } else if (mDobEt.getText().toString().length() == 0) {
            Toast.makeText(this, " Date of Birth cannot be blank", Toast.LENGTH_SHORT).show();
            return false;
        } else if (employeeDataSource.checkUserExist(mFirstNameEt.getText().toString())) {
            Toast.makeText(this, "Email address already exist", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;

    }
}

