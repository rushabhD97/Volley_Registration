package com.example.xyz.akgroupsassignment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.Authenticator;
import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    EditText emailidEditText,usernameEditText,passwordEditText,phoneNumberEditText,fnameEditText,mnameEditText,lnameEditText;
    String username,password,emailid,phoneNumber,fname,mname,lname,gender="Male";
    AppCompatButton signupButton;
    TextView linkToSignIn;
    RadioGroup genderGroup;
    RadioButton maleButton,femaleButton;
    ProgressBar progressBar;
    final static String LOGMESSAGE="SignUpActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        if(SharedPrefManager.getInstance(getApplicationContext()).isUserLoggedIn()){
            finish();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            return;
        }

        emailidEditText=findViewById(R.id.input_email);
        passwordEditText=findViewById(R.id.input_password);
        usernameEditText=findViewById(R.id.input_name);
        fnameEditText=findViewById(R.id.input_fname);
        mnameEditText=findViewById(R.id.input_mname);
        lnameEditText=findViewById(R.id.input_lname);
        phoneNumberEditText=findViewById(R.id.input_phone_number);
        signupButton=findViewById(R.id.btn_signup);
        linkToSignIn=findViewById(R.id.link_login);
        genderGroup=findViewById(R.id.gender);
        maleButton=findViewById(R.id.rb_male);
        femaleButton=findViewById(R.id.rb_female);
        progressBar=findViewById(R.id.signup_progress_bar);

        genderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.rb_male:
                        gender="Male";
                        break;
                    case R.id.rb_female:
                        gender="Female";
                        break;
                    default:
                        break;

                }
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup();
            }
        });
        linkToSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this,LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                finish();
            }
        });

    }

    private void signup() {

        username=usernameEditText.getText().toString();
        password=passwordEditText.getText().toString();
        emailid=emailidEditText.getText().toString();
        fname=fnameEditText.getText().toString();
        mname=mnameEditText.getText().toString();
        lname=lnameEditText.getText().toString();
        phoneNumber=phoneNumberEditText.getText().toString();

        if(username.isEmpty()){
            usernameEditText.setError("Username should not be empty");
        }else if(emailid.isEmpty()){
            emailidEditText.setError("Email Is is compulsory");
        }else if(!Patterns.EMAIL_ADDRESS.matcher(emailid).matches()){
            emailidEditText.setError("Invalid Emailid");
        }else if(password.isEmpty()){
            passwordEditText.setError("Password should not be empty");
        }else if(password.length()<8){
            passwordEditText.setError("Length of password should be greater tha 8");
        }else if(fname.isEmpty()){
            fnameEditText.setError("Fill The First name");
        }else if(phoneNumber.isEmpty()){
            phoneNumberEditText.setError("Enter the phone number");
        }else if(!Patterns.PHONE.matcher(phoneNumber).matches()|| phoneNumber.length()<8 || phoneNumber.length()>13){
            phoneNumberEditText.setError("Invalid Phone Number");
        }else{
            progressBar.setIndeterminate(true);
            progressBar.setVisibility(View.VISIBLE);
            StringRequest stringRequest=new StringRequest(Request.Method.POST, Constants.REG_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressBar.setVisibility(View.INVISIBLE);
                            try {
                                JSONObject jsonObject=new JSONObject(response);
                                Toast.makeText(SignUpActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                SharedPrefManager.getInstance(getApplicationContext()).userLogin(jsonObject);
                                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                finish();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(SignUpActivity.this, "Error "+error.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String>param=new HashMap<>();
                    param.put("username",username);
                    param.put("email_id",emailid);
                    param.put("password",password);
                    param.put("first_name",fname);

                    //Similarly Get Userinput
                    param.put("middle_name",mname);
                    param.put("last_name",lname);
                    param.put("gender",gender);
                    param.put("mobile_number",phoneNumber);
                    return  param;
                }
            };
            RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
        }

    }
}
