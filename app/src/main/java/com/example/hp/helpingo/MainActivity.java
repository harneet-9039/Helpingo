package com.example.hp.helpingo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import common.GlobalMethods;
import common.singleton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private String URL = GlobalMethods.getURL() + "fetch_detail.php";
    private Spinner s1, s2;
    private Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        s1 = findViewById(R.id.spinner);
        s2 = findViewById(R.id.spinner2);
        b = findViewById(R.id.button1);
        b.setOnClickListener(this);
        s1.setOnItemSelectedListener(this);

    }


    @Override
    public void onClick(View view) {

        try {

            StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                        /*JSONObject jsonObject=new JSONObject(response);
                        if(jsonObject.getInt("success")==1){
                            JSONArray jsonArray=jsonObject.getJSONArray("Name");
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject1=jsonArray.getJSONObject(i);
                                String country=jsonObject1.getString("Country");
                                CountryName.add(country);
                            }*/
                        Log.d("HAR", response);

                       // s1.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, CountryName));
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

                    parameters.put("value", "1");
                    return parameters;
                }
            };

            singleton.getInstance(this).addToRequestQueue(stringRequest);


        } catch (Exception ex) {
            Log.d("HAR", "errrrrr");

        }

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String City = (String)adapterView.getItemAtPosition(i);
        getEvents(City);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void getEvents(final String city)
    {
        try {

            StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                        /*JSONObject jsonObject=new JSONObject(response);
                        if(jsonObject.getInt("success")==1){
                            JSONArray jsonArray=jsonObject.getJSONArray("Name");
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject1=jsonArray.getJSONObject(i);
                                String country=jsonObject1.getString("Country");
                                CountryName.add(country);
                            }*/
                    Log.d("HAR", response);

                    // s1.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, CountryName));
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
                    parameters.put("value", "2");
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
