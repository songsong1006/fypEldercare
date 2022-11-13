package com.example.bottomnavigation;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDbHelper extends SQLiteOpenHelper {

    public MyDbHelper(@Nullable Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create table on that db
        db.execSQL(Constants.CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //update database (if any changed)

        //drop older table
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME);

        //create table again
        onCreate(db);

    }

    //insert record to db
    public long insertRecord(String name, String image, String tablets, String times, String foods,
                             String addedTime, String updatedTime){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Constants.C_NAME, name);
        values.put(Constants.C_IMAGE, image);
        values.put(Constants.C_TABLETS, tablets);
        values.put(Constants.C_TIMES, times);
        values.put(Constants.C_FOOD, foods);
        values.put(Constants.C_ADDED_TIMESTAMP, addedTime);
        values.put(Constants.C_UPDATED_TIMESTAMP, updatedTime);

        long id = db.insert(Constants.TABLE_NAME, null, values);

        //close db connection
        db.close();

        //return id
        return id;

    }
}
