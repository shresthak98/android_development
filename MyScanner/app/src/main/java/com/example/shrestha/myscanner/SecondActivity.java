package com.example.shrestha.myscanner;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SecondActivity extends AppCompatActivity {

    EditText editname, editcost, editmfdate;
    Button btnadd;
    int start_yr, start_month, start_day;

    String eve_start_date = "yo";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Calendar today = Calendar.getInstance();//today contains current date and time when event is being created
        start_yr = today.get(Calendar.YEAR);  //yr initialized
        start_month = today.get(Calendar.MONTH);  //month initialized
        start_day = today.get(Calendar.DAY_OF_MONTH);  //date initialized


        editname = (EditText) findViewById(R.id.editname);
        editcost = (EditText) findViewById(R.id.editcost);
        editmfdate = (EditText) findViewById(R.id.editmfdate);
        btnadd = (Button) findViewById(R.id.buttonadd);

        Intent extrasIntent = getIntent();
        final String id1 = extrasIntent.getExtras().getString("id");
        addData(id1);


        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
        Date d1 = new Date(start_yr - 1900, start_month, start_day, 0, 0);
        eve_start_date = sdf1.format(d1);
        editmfdate.setText(eve_start_date);

    }

    public void addData(final String id1) {
        btnadd.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(SecondActivity.this, MainActivity.class);
                        intent.putExtra("id", id1);
                        intent.putExtra("itemName", editname.getText().toString());
                        intent.putExtra("itemCost", Integer.parseInt(editcost.getText().toString()));
                        intent.putExtra("itemMfdate", eve_start_date);
                        setResult(RESULT_OK, intent);
                        startActivity(intent);
                        finish();
                    }
                }
        );
    }

    //opens the dialog box for setting date of the event
    public void set_date(View view) {
        InputMethodManager im = (InputMethodManager) getSystemService(getBaseContext().INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(editmfdate.getWindowToken(), 0);
        showDialog(1);

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {

            //shows dialog box for date
            case 1:
                return new DatePickerDialog(
                        this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        start_yr = year;
                        start_month = monthOfYear;
                        start_day = dayOfMonth;
                        //way of formatting date doesn't create any problem
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        Date date = new Date(start_yr - 1900, start_month, start_day, 0, 0);
                        eve_start_date = dateFormat.format(date);

                        editmfdate.setText(eve_start_date);

                    }
                }
                        , start_yr, start_month, start_day);
        }

        return null;
    }




}