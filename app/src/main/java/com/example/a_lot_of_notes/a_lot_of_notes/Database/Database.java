package com.example.a_lot_of_notes.a_lot_of_notes.Database;


/*
*       Database.java
*       Implement SQLite database to store user notes/folders/etc.
*       May need other .java files to handle queries/sorting/etc.
*
*
*
* */

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class Database extends SQLiteOpenHelper{
    // TAG for debugging
    private static final String TAG = "Database";

    private static final String DB_NAME = "notes_db";
    private static final int DB_VERSION = 1;
    // possibly replace name defined in Notes.class
    // these columns are just dummies, replace with definitions in Note.class
    private static final String DB_TABLE_NAME = "table_name";
    private static final String COLUMN_DIRECTORY = "directory";
    private static final String COLUMN_PROJECT = "project";
    private static final String COLUMN_NOTES = "notes";


    Context cntxt;

    public Database(Context context){
        super(context, DB_NAME, null, DB_VERSION);
        cntxt = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d(TAG, "onCreate: starting database creation");


        Log.d(TAG, "onCreate: ending database creation");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists " + DB_TABLE_NAME);
        onCreate(db);
    }
    
    public void insertData(){
        Log.d(TAG, "insertData: starting");

        Log.d(TAG, "insertData: ending");
    }
    
    public Cursor getData(){
        Log.d(TAG, "getData: starting");
        SQLiteDatabase db = getReadableDatabase();
        String query = "";
        Cursor data = db.rawQuery(query, null);

        Log.d(TAG, "getData: ending");
        return data;
    }
}
