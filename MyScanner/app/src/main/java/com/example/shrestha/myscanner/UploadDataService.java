package com.example.shrestha.myscanner;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class UploadDataService extends IntentService {

    private final String url = "http://10.100.30.142/p.php";
    private final String Tag = "global";
    String pdata= "";

    public UploadDataService() {
        super("UploadDataService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Log.d(Tag,"Reached in intent service");

        DBhelper mydb = new DBhelper(getApplicationContext());
        if(mydb.getResults(getApplicationContext())!=null&&mydb.getResults(getApplicationContext()).toString().length()!=0)
        {

            pdata = mydb.getResults(getApplicationContext()).toString();
            Log.d(Tag,"Not null"+pdata);
            uploadData();
        }

    }

    private void uploadData()
    {
        StringRequest uploadDataRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(Tag,"got response");
                if(response!=null)
                Log.d(Tag,response);
                else Log.d(Tag,"null response");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(error!=null)
                Log.d(Tag,"Volley error while uploading data"+error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<String, String>();
                param.put("pdata",pdata);
                return param;
            }
        };
        AppController.getInstance().addToRequestQueue(uploadDataRequest);
    }

}
