package common;


import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by TUSHAR on 11-03-18.
 */

public class singleton {

    private static singleton mySingleton;
    private RequestQueue requestQueue;
    private Context context;


    private singleton(Context context)
    {
        this.context = context;
        requestQueue = getRequestQueue();
    }

    public RequestQueue getRequestQueue()
    {
        if(requestQueue==null)
        {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }
    public static synchronized singleton getInstance(Context context)
    {
        if(mySingleton==null)
            mySingleton = new singleton(context);
        return mySingleton;
    }
    public void addToRequestQueue(StringRequest request)
    {

        requestQueue.add(request);

    }
    public void addToJsonRequestQueue(JsonArrayRequest jsonArrayRequest)
    {
        requestQueue.add(jsonArrayRequest);
    }
}

