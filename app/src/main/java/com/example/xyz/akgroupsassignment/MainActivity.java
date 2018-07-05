package com.example.xyz.akgroupsassignment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    TextView usernameView,emailidView,fullnameView,createdOnView,genderView,mobileNumberView;
    String username,emailid,fullname,createdOn,gender,mobileNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!SharedPrefManager.getInstance(this).isUserLoggedIn()) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
            return;
        }
        usernameView=findViewById(R.id.username);
        emailidView=findViewById(R.id.email_id_value);
        fullnameView=findViewById(R.id.name);
        createdOnView=findViewById(R.id.created_on_time);
        genderView=findViewById(R.id.gender_value);
        mobileNumberView=findViewById(R.id.mobile_number_value);


        try {
            JSONObject obj=new JSONObject(SharedPrefManager.getInstance(getApplicationContext()).getFullInfo());
            username=obj.getString("username");
            emailid=obj.getString("emailid");
            fullname=obj.getString("first_name")+" "+obj.getString("middle_name")+" "+obj.getString("last_name");
            createdOn=obj.getString("created_on");
            gender=obj.getString("gender");
            mobileNumber=obj.getString("mobilenumber");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        usernameView.setText(username.trim());
        emailidView.setText(emailid.trim());
        fullnameView.setText(fullname.trim());
        createdOnView.setText(createdOn.trim());
        genderView.setText(gender.trim());
        mobileNumberView.setText(mobileNumber.trim());


    }
    public void signout(View view){
        SharedPrefManager.getInstance(getApplicationContext()).logout();
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        finish();
    }

}
