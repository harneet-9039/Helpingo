package com.example.hp.helpingo;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.provider.Settings;


import common.CheckConnection;
import common.GlobalMethods;

public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 2000;

    // String url = "172.31.143.34:3000//Login";
    String StatusCode;
    private String UserName,Password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getNetworkState();
                //startActivity(new Intent(SplashActivity.this,ScreenSlideActivity.class));
                finish();
            }
        },SPLASH_TIME_OUT);


    }
    private void getNetworkState()
    {
          Boolean State = CheckConnection.getInstance(this).getNetworkStatus();
        if(State)  //internet is connected
        {
            startActivity(new Intent(SplashActivity.this, ChoiceActivity.class));
           // CheckUserCredentials();

        }
        else
        {
            GlobalMethods.print(this,"Please open internet connection");
            Intent callGPSSettingIntent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
            startActivity(callGPSSettingIntent);
        }
    }

   /* public void CheckUserCredentials()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("logDetails",
                Context.MODE_PRIVATE);
        UserName = sharedPreferences.getString("UserName",null);
        Password = sharedPreferences.getString("Password",null);
        Log.d("HAR","Username:"+UserName+" Password:"+Password);
        if(UserName != null && Password != null) {
            // Services.getInstance(context).Validate(UserName,Password);
            startActivity(new Intent(SplashActivity.this, Restaurant_Main_Page.class));

        }
        else
        {
            Log.d("HAR","Shared preference not found");
            startActivity(new Intent(SplashActivity.this, ScreenSlideActivity.class));
        }
    }*/
}
