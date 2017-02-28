package com.example.shrestha.myscanner;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Button btnqr,btnbar;
    DBhelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnqr=(Button)findViewById(R.id.buttonqr);
        btnbar=(Button)findViewById(R.id.buttonbar);
        mydb = new DBhelper(this);
        scanqr();
        scanbar();

       Intent extrasIntent = getIntent();
        if(extrasIntent.hasExtra("itemName") && extrasIntent.hasExtra("itemCost") && extrasIntent.hasExtra("itemMfdate"))
        {
            String id = extrasIntent.getExtras().getString("id");
            String name = extrasIntent.getExtras().getString("itemName");
            int cost = extrasIntent.getExtras().getInt("itemCost");
            String mfdate = extrasIntent.getExtras().getString("itemMfdate");

            addingproduct(id,name,cost,mfdate);
        }

    }

    public void scanqr(){
        btnqr.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
                        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                        integrator.setPrompt("Place a Qr code inside the rectangle to scan it");
                        integrator.setCameraId(0);
                        integrator.setBeepEnabled(true);
                        integrator.setBarcodeImageEnabled(true);
                        integrator.initiateScan();
                    }
                }
        );
    }

    public void scanbar(){
        btnbar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
                        integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
                        integrator.setPrompt("Place a Bar code inside the rectangle to scan it");
                        integrator.setCameraId(0);
                        integrator.setBeepEnabled(true);
                        integrator.setBarcodeImageEnabled(true);
                        integrator.initiateScan();
                    }
                }
        );
    }

    @Override
    protected void onActivityResult(int requestcode, int resultcode, Intent data){
        IntentResult result = IntentIntegrator.parseActivityResult(requestcode,resultcode,data);

        if(result!=null){

            if(result.getContents()==null)
                Toast.makeText(this, "You cancelled the scanning", Toast.LENGTH_LONG).show();
            else
            { //Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                final String id = result.getContents();
                Cursor rslt = mydb.viewproduct(result.getContents());
               // Cursor r = mydb.viewAll();

                if(rslt.getCount()==0)
                {
                    AlertDialog.Builder a_builder=new AlertDialog.Builder(this);
                    a_builder.setTitle("Sorry!");
                    a_builder.setMessage("Product id :" +result.getContents() + "\nThe product is not present in our database.\n Want to add the product in the database ? ")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(MainActivity.this,SecondActivity.class);
                                    intent.putExtra("id",id);
                                    startActivity(intent);

                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert=a_builder.create();
                    alert.show();
                }
                else {
                    StringBuffer buffer = new StringBuffer();

                    while (rslt.moveToNext()) {

                        buffer.append("ID :" + rslt.getString(0) + "\n");
                        buffer.append("NAME :" + rslt.getString(1) + "\n");
                        buffer.append("COST :" + rslt.getString(2) + "\n");
                        buffer.append("MFDATE :" + rslt.getString(3) + "\n");
                        buffer.append("LAST SACN DATE & TIME :" + rslt.getString(4) + "\n");
                        buffer.append("LAST SCAN LOCATION :" + rslt.getString(5) + "\n\n");
                    }

                    showMessage("PRODUCT DETAILS :", buffer.toString());
                }

              /*  StringBuffer buffer = new StringBuffer();

                while (r.moveToNext()) {

                    buffer.append("ID :" + r.getString(0) + "\n");
                    buffer.append("NAME :" + r.getString(1) + "\n");
                    buffer.append("COST :" + r.getString(2) + "\n");
                    buffer.append("MFDATE :" + r.getString(3) + "\n");
                    buffer.append("LAST SACN DATE & TIME :" + r.getString(4) + "\n");
                    buffer.append("LAST SCAN LOCATION :" + r.getString(5) + "\n\n");
                }

                showMessage("PRODUCT DETAILS :", buffer.toString());
                */

            }


        }
    }

    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

   protected void addingproduct(String id,String name,int cost,String mfdate){

       // String query = "Insert into "+ TablesDB.Table1.TABLE_NAME + " values( " + name + "," + cost + "," + mfdate + "," + "'2000','2000','indore');" ;
       String currentDateTimeString = Calendar.getInstance().getTime().toString();

       boolean rslt = mydb.insertData(id,name,cost,mfdate,currentDateTimeString,"indore");

        if(rslt)
            Toast.makeText(this,"Data inserted into the database",Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this,"Data not inserted into the database",Toast.LENGTH_LONG).show();

       finish();
    }


}
