package com.simpalm.sqlitedatabasepractice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login extends AppCompatActivity {

    EditText mEmailIDEt, mPasswordEt;
    Button mSignupBtn;
    EmployeeDataSource employeeDataSource;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmailIDEt = (EditText) findViewById(R.id.email_id_et);
        mPasswordEt = (EditText) findViewById(R.id.passowrd_et);
        mSignupBtn = (Button) findViewById(R.id.sign_up_btn);


        mSignupBtn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                employeeDataSource = new EmployeeDataSource(Login.this);


                final String email = mEmailIDEt.getText().toString();
                if (!isValidEmail(email)) {
                    mEmailIDEt.setError("Invalid Email");
                }

                final String pass = mPasswordEt.getText().toString();
                if (!isValidPassword(pass)) {
                    mPasswordEt.setError("Invalid Password");
                }




            }
        });


    }

    // validating email id
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // validating password with retype password
    private boolean isValidPassword(String pass) {
        if (pass != null && pass.length() > 8) {
            return true;
        }
        return false;
    }
}
