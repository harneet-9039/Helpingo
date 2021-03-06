package com.example.hp.helpingo;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import common.GlobalMethods;
import common.singleton;

public class ChoiceActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private String URL = GlobalMethods.getURL() + "fetch_detail.php";
    private Spinner s1, s2;
    private Button b;
    private ProgressDialog Progress;
     ArrayList<String> Name, TempArr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        s1 = findViewById(R.id.spinner);
        s2 = findViewById(R.id.spinner2);
        b = findViewById(R.id.button);
        Progress = new ProgressDialog(this);
        b.setOnClickListener(this);
        s1.setOnItemSelectedListener(this);
        s2.setOnItemSelectedListener(this);
        Name=new ArrayList<>();
        TempArr = new ArrayList<>();
        Progress.show();
        FillCity();
    }


    @Override
    public void onClick(View view) {



    }

    private void FillCity()
    {


        try {

            StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Log.d("HAR", response);
                    String[] temp = response.split(",");
                    for(int i=0;i<temp.length;i++) {
                        Name.add(temp[i]);
                        Log.d("HAR", temp[i].toString());
                    }
                        s1.setAdapter(new ArrayAdapter<String>(ChoiceActivity.this, android.R.layout.simple_spinner_dropdown_item, Name));
                        Progress.dismiss();

                    // s1.setAdapter(new ArrayAdapter<String>(ChoiceActivity.this, android.R.layout.simple_spinner_dropdown_item, CountryName));
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    Log.d("HAR", error.toString());
                    Progress.dismiss();

                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> parameters = new HashMap<>();
                    parameters.put("value", "true");
                    return parameters;
                }
            };

            singleton.getInstance(this).addToRequestQueue(stringRequest);


        } catch (Exception ex) {
            Log.d("HAR", ex.toString());
            Progress.dismiss();

            Log.d("HAR", "Catch Error");

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(adapterView.getId() == R.id.spinner) {
            Progress.show();
            String City = (String) adapterView.getItemAtPosition(i);
            getEvents(City);
        }
        else if(adapterView.getId() == R.id.spinner2)
        {
            String Event1 = (String) adapterView.getItemAtPosition(i);
            getPinCode(Event1);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void getPinCode(final String Event1)
    {
        try {

            StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {



                    Log.d("HAR", response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    Log.d("HAR", error.toString());
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> parameters = new HashMap<>();
                    parameters.put("value", "true3");
                    parameters.put("event_name", Event1);
                    return parameters;
                }
            };

            singleton.getInstance(this).addToRequestQueue(stringRequest);


        } catch (Exception ex) {
            Log.d("HAR", "errrrrr");

        }
    }
    private void getEvents(final String city)
    {
        try {

            StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    TempArr.clear();

                    String[] temp = response.split(",");
                    for(int i=0;i<temp.length;i++) {
                        TempArr.add(temp[i]);
                        Log.d("HAR", temp[i].toString());
                    }
                    s2.setAdapter(new ArrayAdapter<String>(ChoiceActivity.this, android.R.layout.simple_spinner_dropdown_item, TempArr));
                    Progress.dismiss();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    Log.d("HAR", error.toString());
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> parameters = new HashMap<>();
                    parameters.put("value", "true2");
                    parameters.put("city_name", city);
                    return parameters;
                }
            };

            singleton.getInstance(this).addToRequestQueue(stringRequest);


        } catch (Exception ex) {
            Log.d("HAR", "errrrrr");

        }
    }
}
