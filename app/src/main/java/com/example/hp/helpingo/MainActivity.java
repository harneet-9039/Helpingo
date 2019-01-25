package com.example.hp.helpingo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import common.singleton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText UserNameText;
    EditText PasswordText;
    String UserName;
    String Password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button b = findViewById(R.id.submit);
        b.setOnClickListener(this);
        UserNameText = findViewById(R.id.id);
    }

    private void getDetails()
    {
        UserName = UserNameText.getText().toString();

    }
    @Override
    public void onClick(View view) {
        getDetails();
        try {
            StringRequest request = new StringRequest(Request.Method.POST, "http://172.31.128.15/helpingo/check.php", new Response.
                    Listener<String>() {

                @Override
                public void onResponse(String s) {

                    Log.d("HAR", s);



                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {

                    Log.d("HAR", volleyError.toString());
                    Log.d("HAR", "Error");
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> parameters = new HashMap<>();
                    parameters.put("first_name", UserName);
                    return parameters;
                }
            };
            singleton.getInstance(this).addToRequestQueue(request);


        } catch (Exception ex) {

        }

    }
}
