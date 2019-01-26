package com.example.hp.helpingo;



import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import common.CheckConnection;
import common.GlobalMethods;
import common.MyDialog;
import common.singleton;
import common.OTP_Generator;
import common.OTP_Reader;

public class Register extends AppCompatActivity implements View.OnClickListener {


    LinearLayout signup;
    EditText UserName,UserContact;
    String name,contact;
    String url = GlobalMethods.getURL() + "public_signup.php";
    int validContact=0,empty=0,eName=0,eContact=0;
    String StatusCode;
    ProgressBar progressBar;

    common.MyDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar=findViewById(R.id.Tbar1);
        toolbar.setTitle("Register");
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressBar = findViewById(R.id.Register_Progress_Bar);
        progressBar.setIndeterminate(true);
        getDetails();
        signup.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            default:
                return true;
        }
    }

    private void getDetails() {
        UserName =findViewById(R.id.Full_Name);
        UserContact=findViewById(R.id.Mob_No);
        signup=findViewById(R.id.Btn_Signup);
        focusChangeListeners();

    }
    public void callLogin(View view)
    {
       // Intent intent=new Intent(this,Login.class);
        //startActivity(intent);
    }


    private void focusChangeListeners()
    {

        UserContact.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                {
                    contact=UserContact.getText().toString();
                    if(!(contact.length()==10))
                    {
                        UserContact.setError("Invalid Contact");
                        validContact = 0;
                    }
                    else
                        validContact = 1;

                }
            }
        });

    }

    private void clearfocuses() {

        UserName.clearFocus();
        UserContact.clearFocus();
    }

    private void setDetails()
    {
        name=UserName.getText().toString();
        contact=UserContact.getText().toString();


        if(name.length()==0)
        {
            UserName.setError("This field is required");
            eName = 0;
        }
        else eName = 1;

        if(contact.length()==0)
        {
            UserContact.setError("This field is required");
            eContact = 0;
        }
        else eContact = 1;

        if(eName == 0 || eContact == 0  ||
                validContact == 0)
        {
            empty = 1;
        }
        else
            empty = 0;

    }
    @Override
    public void onClick(View view) {

        clearfocuses();
        setDetails();

        if(empty == 0) {

            if (!CheckConnection.getInstance(this).getNetworkStatus()) {





            } else {
                //internet is connected
                progressBar.setVisibility(View.VISIBLE);
                callService(view);
            }
        }
    }
    private void callService(final View view)
    {
        try {
            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.
                    Listener<String>() {

                @Override
                public void onResponse(String s) {
                    // progressBar.setActivated(false);
                    progressBar.setVisibility(View.GONE);

                    Log.d("HAR", s);
                    // ********************************************************stop progress bar*************************

                    if (s.contains("1")) {

                        boolean flag;
                        //generating OTP
                        String OTP = OTP_Generator.getInstance(Register.this).Generate();
                        //sending sms
                        flag = OTP_Generator.getInstance(Register.this).sendMessage(OTP, contact);
                        if (flag)
                            Log.d("HAR", "OTP generated and sent succesfully " + OTP);
                        else
                            Log.d("HAR", "OTP generated but not sent, contact:" + contact);


                        Intent intent = new Intent(Register.this, OTP_Reader.class);
                        intent.putExtra("Name", name);
                        intent.putExtra("Contact", contact);
                        intent.putExtra("OTP", OTP);
                        startActivity(intent);
                    } else {

                        startActivity(new Intent(Register.this, HomeScreen.class));


                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    // setProgressBarIndeterminate(false);
                    progressBar.setVisibility(View.GONE);
                    //progressBar.setActivated(false);
                    Log.d("HAR", volleyError.toString());
                    Log.d("HAR", "Error");
                    //***************************************Stop Progress Bar********************************




                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> parameters = new HashMap<>();
                    parameters.put("mobile", contact);
                    parameters.put("name", name);

                    // if(Name != null)
                    //   parameters.put("Name",Name);
                    return parameters;
                }
            };
            singleton.getInstance(this).addToRequestQueue(request);
            Log.d("HAR", "Service ab return kr ri hai");

        } catch (Exception ex) {

        }
    }

    /*@Override
    public void onBackPressed() {

        Intent ForgotPassword = new Intent(Register.this, ScreenSlideActivity.class);
        ForgotPassword.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP   );
        startActivity(ForgotPassword);
    }*/


}

