package com.example.shrestha.myscanner;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class ConnectivityChangedReceiver extends BroadcastReceiver {
    public ConnectivityChangedReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("global","connectiviy changed");
        if(isNetworkAvailable(context))
        {
            Log.d("global","connect to interntet");
            context.startService(new Intent(context,UploadDataService.class));
        }

    }
    private boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
