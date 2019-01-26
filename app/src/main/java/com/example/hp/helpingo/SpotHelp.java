package com.example.hp.helpingo;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import common.GlobalMethods;
import common.MyDialog;
import common.singleton;

/**
 * Location sample.
 * <p>
 * Demonstrates use of the Location API to retrieve the last known location for a device.
 */
public class SpotHelp extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = SpotHelp.class.getSimpleName();

    private Spinner spinner, spinner_new;


    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    private static final double x1 = 25.437470;
    private static final double y1 = 81.891546;
    private static final double x2 = 25.423312;
    private static final double y2 = 81.868389;
    private static final double x3 = 25.405511;
    private static final double y3 = 81.884976;
    private static final double x4 = 25.412736;
    private static final double y4 = 81.907159;
    private static final double midx = 25.4214905;
    private static final double midy = 81.888261;
    private int flag=0;
    private ProgressDialog progressBar;
    private EditText mob;
    MyDialog dialog;
    /**
     * Provides the entry point to the Fused Location Provider API.
     */
    private FusedLocationProviderClient mFusedLocationClient;

    /**
     * Represents a geographical location.
     */
    protected Location mLastLocation;

    private Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);



        spinner = (Spinner) findViewById(R.id.spinner);
        spinner_new = (Spinner) findViewById(R.id.spinner2);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        b = findViewById(R.id.button);
        progressBar = new ProgressDialog(this);
        b.setOnClickListener(this);
        mob = findViewById(R.id.Mob_No);
        IniailizeSpinner();
    }




    private void IniailizeSpinner()
    {
        List<String> categories = new ArrayList<String>();
        categories.add(0,"---select one---");
        categories.add("Fire");
        categories.add("Excessive Crowd Rush");
        categories.add("Fight");
        categories.add("Health Issue");
        categories.add("Management Problem");

        List<String> Severity = new ArrayList<String>();
        Severity.add(0,"---select one---");
        Severity.add("Normal");
        Severity.add("Intermediate");
        Severity.add("Crucial");
        Severity.add("Extreme Priority Issue");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        ArrayAdapter<String> dataAdapter_new = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Severity);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter_new.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        spinner_new.setAdapter(dataAdapter_new);

        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> parent, View view, int pos,
                                       long id) {
                ((TextView) view).setTextColor(Color.RED);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });

    }



    @Override
    public void onStart() {
        super.onStart();

        if (!checkPermissions()) {
            requestPermissions();
        } else {
            getLastLocation();
        }
    }


    @SuppressWarnings("MissingPermission")
    private void getLastLocation() {
        mFusedLocationClient.getLastLocation()
                .addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            mLastLocation = task.getResult();

                          /*  mLatitudeText.setText(String.format(Locale.ENGLISH, "%s: %f",
                                    mLatitudeLabel,
                                    mLastLocation.getLatitude()));
                            mLongitudeText.setText(String.format(Locale.ENGLISH, "%s: %f",
                                    mLongitudeLabel,
                                    mLastLocation.getLongitude()));*/

                            //mLatitudeText.setVisibility(View.INVISIBLE);
                            //mLatitudeText.setVisibility(View.INVISIBLE);
                            CheckSegment(  mLastLocation.getLatitude(), mLastLocation.getLongitude());
                        } else {
                            Log.w(TAG, "getLastLocation:exception", task.getException());
                            showSnackbar(getString(R.string.no_location_detected));
                        }
                    }
                });
    }

    /**
     * Shows a {@link Snackbar} using {@code text}.
     *
     * @param text The Snackbar text.
     */
    private void showSnackbar(final String text) {
        View container = findViewById(R.id.main_activity_container);
        if (container != null) {
            Snackbar.make(container, text, Snackbar.LENGTH_LONG).show();
        }
    }

    private static double area(double x1, double y1, double x2, double y2,
                               double x3, double y3)
    {
        return Math.abs((x1*(y2-y3) + x2*(y3-y1)+
                x3*(y1-y2))/2.0);
    }

    static boolean isInside(double x1, double y1, double x2,
                            double y2, double x3, double y3, double x, double y)
    {
        /* Calculate area of triangle ABC */
        double A = area (x1, y1, x2, y2, x3, y3);

        /* Calculate area of triangle PBC */
        double A1 = area (x, y, x2, y2, x3, y3);

        /* Calculate area of triangle PAC */
        double A2 = area (x1, y1, x, y, x3, y3);

        /* Calculate area of triangle PAB */
        double A3 = area (x1, y1, x2, y2, x, y);

        /* Check if sum of A1, A2 and A3 is same as A */
        return (A >= A1 + A2 + A3);
    }

    private void CheckSegment(double lat, double lon)
    {

        for(int i=0;i<4;i++) {
            if(isInside(x1,y1,x2,y2,midx,midy,lat,lon)) {
                flag = 1;
                break;
            }
            else if(isInside(x2,y2,x3,y3,midx,midy,lat,lon)) {
                flag=2;
                break;
            }
            else if(isInside(x3,y3,x4,y4,midx,midy,lat,lon)) {
                flag=3;
                break;
            }
            else if(isInside(x1,y1,x4,y4,midx,midy,lat,lon)) {
                flag=4;
                break;
            }
        }



    }



    private void showSnackbar(final int mainTextStringId, final int actionStringId,
                              View.OnClickListener listener) {
        Snackbar.make(findViewById(android.R.id.content),
                getString(mainTextStringId),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(actionStringId), listener).show();
    }

    /**
     * Return the current state of the permissions needed.
     */
    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(SpotHelp.this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                REQUEST_PERMISSIONS_REQUEST_CODE);
    }


    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION);

        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.");

            showSnackbar(R.string.permission_rationale, android.R.string.ok,
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Request permission
                            startLocationPermissionRequest();
                        }
                    });

        } else {
            Log.i(TAG, "Requesting permission");
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            startLocationPermissionRequest();
        }
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Log.i(TAG, "onRequestPermissionResult");
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                // If user interaction was interrupted, the permission request is cancelled and you
                // receive empty arrays.
                Log.i(TAG, "User interaction was cancelled.");
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted.
                getLastLocation();
            } else {
                // Permission denied.

                // Notify the user via a SnackBar that they have rejected a core permission for the
                // app, which makes the Activity useless. In a real app, core permissions would
                // typically be best requested during a welcome-screen flow.

                // Additionally, it is important to remember that a permission might have been
                // rejected without asking the user for permission (device policy or "Never ask
                // again" prompts). Therefore, a user interface affordance is typically implemented
                // when permissions are denied. Otherwise, your app could appear unresponsive to
                // touches or interactions which have required permissions.
                showSnackbar(R.string.permission_denied_explanation, R.string.settings,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // Build intent that displays the App settings screen.
                                Intent intent = new Intent();
                                intent.setAction(
                                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package",
                                        BuildConfig.APPLICATION_ID, null);
                                intent.setData(uri);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        });
            }
        }
    }
    int t=0;
    @Override
    public void onClick(View view) {

        /*if(flag==0) {
            showSnackbar("You are not in range of the gathering");
            return;
        }*/

        progressBar.show();
        Log.d("HAR",mob.getText().toString()+String.valueOf(flag)+spinner.getSelectedItem()+spinner_new.getSelectedItem());
        String url = GlobalMethods.getURL() + "public_gathering.php";
        try {
            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.
                    Listener<String>() {

                @Override
                public void onResponse(String s) {
                    progressBar.dismiss();
                    Log.d("HAR",s);
                    dialog = new MyDialog(SpotHelp.this, "Thank You for your valuable feedback","OK");
                 /*  if(s.contains("1")) {
                       t=1;
                   }
                   else
                   {
                       showSnackbar("Sorry, the notification for this event is already being posted");
                   }*/


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    // setProgressBarIndeterminate(false);
                    progressBar.dismiss();
                    //progressBar.setActivated(false);
                    Log.d("HAR", volleyError.toString());
                    Log.d("HAR", "Error");
                    //***************************************Stop Progress Bar********************************




                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> parameters = new HashMap<>();
                    parameters.put("mobile", mob.getText().toString());
                    parameters.put("region", "S"+String.valueOf(flag));
                    parameters.put("situation_type", spinner.getSelectedItem().toString());
                    parameters.put("types_of_problem", spinner_new.getSelectedItem().toString());
                    return parameters;
                }
            };
            singleton.getInstance(this).addToRequestQueue(request);


        } catch (Exception ex) {

        }
    }
}