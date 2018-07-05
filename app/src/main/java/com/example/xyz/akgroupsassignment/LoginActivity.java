package com.example.xyz.akgroupsassignment;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    EditText emailEditText,passwordEditText;
    ProgressBar progressBar;
    String emailid,password;
    AppCompatButton loginButton;
    TextView linkToSignUp;
    final static String LOGMESSAGE="loginActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(SharedPrefManager.getInstance(getApplicationContext()).isUserLoggedIn()){
            finish();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            return;
        }

        emailEditText=findViewById(R.id.input_email);
        passwordEditText=findViewById(R.id.input_password);
        loginButton=findViewById(R.id.btn_login);
        linkToSignUp=findViewById(R.id.link_signup);
        progressBar=findViewById(R.id.login_progress_bar);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        linkToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,SignUpActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
            }
        });

    }

    private void login() {
        emailid=emailEditText.getText().toString();
        password=passwordEditText.getText().toString();

        if(emailid.isEmpty()){
            emailEditText.setError("Email Id Is Compulsory!!");
        }else if(!Patterns.EMAIL_ADDRESS.matcher(emailid).matches()){
            emailEditText.setError("Invalid Email Address");
        }else if(password.isEmpty()){
            passwordEditText.setError("Password is compulsory");
        }else if(password.length()<8){
            passwordEditText.setError("Yout password is greater the 8 characters");
        }else{
            progressBar.setIndeterminate(true);
            progressBar.setVisibility(View.VISIBLE);

            StringRequest stringRequest=new StringRequest(Request.Method.POST, Constants.LOGIN_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject obj=new JSONObject(response);
                        if(obj.getBoolean("error")){
                            Toast.makeText(LoginActivity.this, obj.getString("message"), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }else{
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(LoginActivity.this, "User Logged in!!", Toast.LENGTH_SHORT).show();
                            SharedPrefManager.getInstance(getApplicationContext()).userLogin(obj);
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(LoginActivity.this, "Error:"+error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> param=new HashMap<>();
                    param.put("email_id",emailid);
                    param.put("password",password);
                    return  param;
                }
            };
            RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
        }
    }

}
