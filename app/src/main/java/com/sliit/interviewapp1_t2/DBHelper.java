package com.sliit.interviewapp1_t2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {


    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table userdetails(name TEXT primary key,contact TEXT, birthday TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
       DB.execSQL("drop Table if exists userDetails");

    }

    public boolean insertUserData(String name, String contact, String birthday){
        // implement insert function to inset details to the database
        SQLiteDatabase DB=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();


        long results=0;

        if(results==-1){
            return false;
        }
        else{
            return true;
        }
    }





    public Cursor getUserData(String name){


        SQLiteDatabase DB=this.getWritableDatabase();



// implement query to  serch the provided name in the 'name filed of the table
        Cursor cursor=null;
        return cursor;

    }



    public DBHelper(Context context) {
        super(context, "userData.db", null, 1);
    }
}
