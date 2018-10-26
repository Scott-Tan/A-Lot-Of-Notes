package com.example.a_lot_of_notes.a_lot_of_notes.Database;


/*
*       Database.java
*       Implement SQLite database to store user notes/folders/etc.
*       May need other .java files to handle queries/sorting/etc.
*
*
*
* */

import android.content.ContentValues;
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

    // Variables for database
    private static final String DB_NAME = "notes_db";
    private static final int DB_VERSION = 1;
    // possibly replace name defined in Notes.class
    // these columns are just dummies, replace with definitions in Note.class
    private static final String DB_TABLE_NAME = "table_name";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_DIRECTORY = "directory";
    private static final String COLUMN_PROJECT = "project";
    private static final String COLUMN_NOTES = "notes";
    private static final String COLUMN_NOTES_TTLE = "notes_title";
    private static final String COLUMN_NOTES_CONTENT = "notes_content";


    Context cntxt;

    public Database(Context context){
        super(context, DB_NAME, null, DB_VERSION);
        cntxt = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate: starting database creation");

        // String to make define database
        // First string to initialize a database with the dummy columns
        //  When creating db with Notes.class, may need some revision.
        String CREATE_TABLE = "CREATE TABLE " + DB_TABLE_NAME + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_DIRECTORY+ " TEXT, "
                + COLUMN_PROJECT + " TEXT, "
                + COLUMN_NOTES + " TEXT, "
                + COLUMN_NOTES_TTLE + " TEXT, "
                + COLUMN_NOTES_CONTENT + " TEXT" + ")";

        // Exec SQL database
        db.execSQL(CREATE_TABLE);

        Log.d(TAG, "onCreate: ending database creation");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists " + DB_TABLE_NAME);
        onCreate(db);
    }
    
    public boolean insertData(String notes, String title, String content){
        Log.d(TAG, "insertData: starting");
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        // what should we cv put?
        // How to handle different additions to database? whether a new directory or note?

        // maybe start by assuming that only notes will be add first before directories
        cv.put(COLUMN_NOTES, notes);
        cv.put(COLUMN_NOTES_TTLE, title);
        cv.put(COLUMN_NOTES_CONTENT, content);

        Log.d(TAG, "insertData: before inserting content values into database");
        db.insert(DB_TABLE_NAME, null, cv);
        Log.d(TAG, "insertData: after inserting content values into database");

        Log.d(TAG, "insertData: ending");
        return true;
    }


    // A query to get all notes. May need separate queries to get notes in specific directory, or
    //  from a specific project.
    public Cursor getAllNotes(){
        Log.d(TAG, "getData: starting");
        SQLiteDatabase db = getReadableDatabase();
        String query = "";
        Cursor data = db.rawQuery(query, null);

        Log.d(TAG, "getData: ending");
        return data;
    }


    // define queries to get needs
    public Cursor getNotesFromDirectory(){
        Log.d(TAG, "getNotesFromDirectory: starting");
        SQLiteDatabase db = getReadableDatabase();
        String query = "";
        Cursor data = db.rawQuery(query, null);

        Log.d(TAG, "getNotesFromDirectory: ending");
        return data;
    }


    // define query to meet needs
    public Cursor getNotesFromProject(){
        Log.d(TAG, "getNotesFromProject: starting");
        SQLiteDatabase db = getReadableDatabase();
        String query = "";
        Cursor data = db.rawQuery(query, null);

        Log.d(TAG, "getNotesFromProject: ending");
        return data;
    }
}
