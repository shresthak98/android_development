package com.example.shrestha.myscanner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by shrestha on 28/1/17.
 */

public class DBhelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "products.db";
    public static final int VERSION = 2;

    public static final String i = "4995";
    public static final String n = "book";
    public static final int c = 1234;
    public static final String mf = "2012/12/12";
    public static final String dt = "2016/01/28";
    public static final String l = "indore";


    public DBhelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        Log.e("Database operations","Database created");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("Create table "+ TablesDB.Table1.TABLE_NAME + "( "+ TablesDB.Table1.COL_1 + " Text, "+ TablesDB.Table1.COL_2 +
        " Text," + TablesDB.Table1.COL_3 + " int," + TablesDB.Table1.COL_4 + " Text," + TablesDB.Table1.COL_5 + " Text," + TablesDB.Table1.COL_6 + " Text);");

        Log.e("Database operation","Table created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+ TablesDB.Table1.TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String id , String name , int cost , String mfdate , String date_time , String location){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TablesDB.Table1.COL_1,id);
        contentValues.put(TablesDB.Table1.COL_2,name);
        contentValues.put(TablesDB.Table1.COL_3,cost);
        contentValues.put(TablesDB.Table1.COL_4,mfdate);
        contentValues.put(TablesDB.Table1.COL_5,date_time);
        contentValues.put(TablesDB.Table1.COL_6,location);

        long rslt = db.insert(TablesDB.Table1.TABLE_NAME,null,contentValues);

        if(rslt == -1)
            return false;
        else
            return true;
    }

    public Cursor viewproduct (String id){
        SQLiteDatabase db = this.getWritableDatabase();
       //db.execSQL("Insert into " + TablesDB.Table1.TABLE_NAME + " values('4995','Book',1234,'2012','2016','indore');");
        //Log.e("Database operation","One row added");
        Cursor res = db.rawQuery("Select * from "+ TablesDB.Table1.TABLE_NAME + " where " + TablesDB.Table1.COL_1 + " = '" + id + "';",null);
        return res;
    }

    public Cursor viewAll(){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor res = db.rawQuery("Select * from "+ TablesDB.Table1.TABLE_NAME,null);
        return res;
    }

}
