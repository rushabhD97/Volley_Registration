package com.example.xyz.akgroupsassignment;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

public class SharedPrefManager {

    private static SharedPrefManager mInstance;
    private static Context mContext;
    private static final String SHAREDPREFERNCE_NAME="USER_SP";
    private static final String KEY_USERNAME="username";
    private static final String KEY_EMAILID="email_id";
    private static final String KEY_USERID="id";
    private static final String FULL_INFO="full_info";
    private SharedPrefManager(Context context){
        mContext=context;
    }
    public static synchronized SharedPrefManager getInstance(Context context){
        if(mInstance==null){
            mInstance=new SharedPrefManager(context);
        }
        return mInstance;
    }

    public boolean userLogin(JSONObject obj){
        JSONObject userObj=obj;
        try {
            userObj=obj.getJSONObject("user");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        SharedPreferences sharedPreferences=mContext.getSharedPreferences(SHAREDPREFERNCE_NAME,Context.MODE_PRIVATE);

        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putInt(KEY_USERID,userObj.optInt("id"));
            editor.putString(KEY_USERNAME, userObj.optString("username"));
            editor.putString(KEY_EMAILID, userObj.optString("emailid"));
            editor.putString(FULL_INFO,userObj.toString());

        editor.apply();
        return true;
    }
    public boolean isUserLoggedIn(){
        SharedPreferences sharedPreferences=mContext.getSharedPreferences(SHAREDPREFERNCE_NAME,Context.MODE_PRIVATE);
        return(sharedPreferences.getString(KEY_USERNAME,null)!=null);
    }
    public boolean logout(){
        SharedPreferences sharedPreferences=mContext.getSharedPreferences(SHAREDPREFERNCE_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }
    public String getFullInfo(){
        return mContext.getSharedPreferences(SHAREDPREFERNCE_NAME,Context.MODE_PRIVATE).getString(FULL_INFO,null);
    }
}
