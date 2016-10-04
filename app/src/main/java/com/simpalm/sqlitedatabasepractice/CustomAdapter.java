package com.simpalm.sqlitedatabasepractice;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Simpalm on 7/17/16.
 */


public class CustomAdapter extends BaseAdapter {

    Context mContext;
    ArrayList<UserInformation> mUserInformation;

    public CustomAdapter(Context context, ArrayList<UserInformation> userInformations) {

        mContext = context;
        mUserInformation = userInformations;


    }


    @Override
    public int getCount() {


        return mUserInformation.size();
    }

    @Override
    public Object getItem(int position) {


        return mUserInformation.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mUserInformation.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Viewholder viewholder = null;

        final LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_layout, null);
            viewholder = new Viewholder();

            viewholder.mFirstNameTv = (TextView) convertView.findViewById(R.id.firstNameTv);

            viewholder.mLastNameTv = (TextView) convertView.findViewById(R.id.lastNameTv);

            viewholder.mEmailTv = (TextView) convertView.findViewById(R.id.emailTv);

            viewholder.mUpdateBtn = (Button) convertView.findViewById(R.id.updateBtn);

            // this is to set a tag to view so that we can get it anytime.

            convertView.setTag(viewholder);


        } else {

            viewholder = (Viewholder) convertView.getTag();


        }


       /* viewholder.mImageview.setImageResource(R.drawable.download);
        viewholder.mDescription.setText("This is my contact description");
        viewholder.mTitle.setText("My contact name");
*/
        final UserInformation userInformation = mUserInformation.get(position);
        viewholder.mFirstNameTv.setText(" " + userInformation.getFirstName());
        viewholder.mLastNameTv.setText(" " + userInformation.getLastName());
        viewholder.mEmailTv.setText(" " + userInformation.getEmailAddress());

        viewholder.mUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EmployeeDataSource employeeDataSource = new EmployeeDataSource(mContext);

                employeeDataSource.open();
                UserInformation userData = employeeDataSource.getSingleEmployee(userInformation.getId());
                int id = userInformation.getId();
                String firstname = userData.getFirstName();
                String lastname = userData.getLastName();
                String email = userData.getEmailAddress();
                String dob = userData.getDob();
                String phonenumber = userData.getPhoneNumber();


                employeeDataSource.closeDatabase();

                Intent intent = new Intent(mContext, SignupActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("firstname", firstname);
                intent.putExtra("lastname", lastname);
                intent.putExtra("dob", dob);
                intent.putExtra("email", email);
                intent.putExtra("phone", phonenumber);

                mContext.startActivity(intent);


            }
        });


        return convertView;


    }

    private class Viewholder {


        TextView mFirstNameTv, mLastNameTv, mEmailTv;
        Button mUpdateBtn;


    }
}
