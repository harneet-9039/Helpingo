package common;



import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alimuzaffar.lib.pin.PinEntryEditText;

import java.util.ArrayList;
import java.util.List;




public class Permissions implements ActivityCompat.OnRequestPermissionsResultCallback{

    private static Permissions permissions;
    private Context context;
    private int REQUEST_ID_MULTIPLE_PERMISSIONS;
    PinEntryEditText OTPTEXT;
    String OTP_Received;
    View view;
    private Permissions(Context context)
    {
        this.context=context;
    }

    public static Permissions getInstance(Context context)
    {
        if (permissions == null)
            permissions = new Permissions(context);
        return permissions;
    }

    void checkpermissions(Context context, int REQUEST_ID_MULTIPLE_PERMISSIONS, View view) {

        this.view = view;
        int receiveSMS = ContextCompat.checkSelfPermission(context, Manifest.permission.RECEIVE_SMS);
        int readSMS = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_SMS);
        this.REQUEST_ID_MULTIPLE_PERMISSIONS = REQUEST_ID_MULTIPLE_PERMISSIONS;
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (receiveSMS != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.RECEIVE_MMS);
        }

        if (readSMS != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_SMS);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions((Activity) context,
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),
                    REQUEST_ID_MULTIPLE_PERMISSIONS);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d("HAR","Permission callback: "+requestCode);
        if (requestCode == REQUEST_ID_MULTIPLE_PERMISSIONS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("HAR", "Permission granted");


            }
            else {

                //Toast.makeText(this,"Permission Denied", Toast.LENGTH_LONG).show();

            }
        }
    }

}

