package com.example.shrestha.myscanner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by shrestha on 28/1/17.
 */

public class DBhelper extends SQLiteOpenHelper{

    private static final String Tag = "global";
    public static final String DATABASE_NAME = "product.db";
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

        db.execSQL("Create table "+ TablesDB.Table2.TABLE_NAME + "( "+ TablesDB.Table2.COL_1 + " Text, "+ TablesDB.Table2.COL_2 +
        " Text," + TablesDB.Table2.COL_3 + " int," + TablesDB.Table2.COL_4 + " Text," + TablesDB.Table2.COL_5 + " Text," + TablesDB.Table2.COL_6 + " Text);");

        Log.e("Database operation","Table created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+ TablesDB.Table2.TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String id , String name , int cost , String mfdate , String date_time , String location){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TablesDB.Table2.COL_1,id);
        contentValues.put(TablesDB.Table2.COL_2,name);
        contentValues.put(TablesDB.Table2.COL_3,cost);
        contentValues.put(TablesDB.Table2.COL_4,mfdate);
        contentValues.put(TablesDB.Table2.COL_5,date_time);
        contentValues.put(TablesDB.Table2.COL_6,location);

        //db.execSQL("Delete * from "+ TablesDB.Table2.TABLE_NAME);
        long rslt = db.insert(TablesDB.Table2.TABLE_NAME,null,contentValues);

        if(rslt == -1)
            return false;
        else
            return true;
    }

    public Cursor viewproduct (String id){
        SQLiteDatabase db = this.getWritableDatabase();
       //db.execSQL("Insert into " + TablesDB.Table2.TABLE_NAME + " values('4995','Book',1234,'2012','2016','indore');");
        //Log.e("Database operation","One row added");
        Cursor res = db.rawQuery("Select * from "+ TablesDB.Table2.TABLE_NAME + " where " + TablesDB.Table2.COL_1 + " = '" + id + "';",null);
        return res;
    }

    public Cursor viewAll(){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor res = db.rawQuery("Select * from "+ TablesDB.Table2.TABLE_NAME,null);
        return res;
    }


    public JSONArray getResults(Context context)
    {

        String myPath = context.getDatabasePath(DATABASE_NAME).toString();

        String myTable = TablesDB.Table2.TABLE_NAME;//Set name of your table

//or you can use `context.getDatabasePath("my_db_test.db")`

        SQLiteDatabase myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

        String searchQuery = "SELECT  * FROM " + myTable;
        Cursor cursor = myDataBase.rawQuery(searchQuery, null );

        JSONArray resultSet     = new JSONArray();

        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {

            int totalColumn = cursor.getColumnCount();
            JSONObject rowObject = new JSONObject();

            for( int i=0 ;  i< totalColumn ; i++ )
            {
                if( cursor.getColumnName(i) != null )
                {
                    try
                    {
                        if( cursor.getString(i) != null )
                        {
                            Log.d("TAG_NAME", cursor.getString(i) );
                            rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                        }
                        else
                        {
                            rowObject.put( cursor.getColumnName(i) ,  "" );
                        }
                    }
                    catch( Exception e )
                    {
                        Log.d("TAG_NAME", e.getMessage()  );
                    }
                }
            }
            resultSet.put(rowObject);
            cursor.moveToNext();
        }
        cursor.close();
       // Log.d(Tag, resultSet.toString() );
        return resultSet;
    }

}
