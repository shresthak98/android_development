package com.example.shrestha.myscanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    EditText editname,editcost,editmfdate;
    Button btnadd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        editname = (EditText)findViewById(R.id.editname);
        editcost = (EditText)findViewById(R.id.editcost);
        editmfdate = (EditText)findViewById(R.id.editmfdate);
        btnadd = (Button)findViewById(R.id.buttonadd);

        Intent extrasIntent = getIntent();
        final String id1 = extrasIntent.getExtras().getString("id");
        addData(id1);
    }

    public void addData(final String id1){
        btnadd.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(SecondActivity.this,MainActivity.class);
                        intent.putExtra("id",id1);
                        intent.putExtra("itemName",editname.getText().toString());
                        intent.putExtra("itemCost",Integer.parseInt(editcost.getText().toString()));
                        intent.putExtra("itemMfdate",editmfdate.getText().toString());
                        setResult(RESULT_OK,intent);
                        //Toast.makeText(this,"fuck",Toast.LENGTH_LONG).show();
                        //Log.e("data","fuck");
                        startActivity(intent);
                        finish();
                    }
                }
        );
    }
}
