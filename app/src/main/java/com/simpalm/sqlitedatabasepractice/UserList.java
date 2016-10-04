package com.simpalm.sqlitedatabasepractice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class UserList extends AppCompatActivity {

    ListView mListView;
    ArrayAdapter adapter;

    ArrayList<UserInformation> userInformationArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        mListView = (ListView) findViewById(R.id.listview);

        EmployeeDataSource employeeDataSource = new EmployeeDataSource(this);
        employeeDataSource.open();
        userInformationArrayList = employeeDataSource.getEmployee();

        mListView.setAdapter(new CustomAdapter(this, userInformationArrayList));


    }
}
