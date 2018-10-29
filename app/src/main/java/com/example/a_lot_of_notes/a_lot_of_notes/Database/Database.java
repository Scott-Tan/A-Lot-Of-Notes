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
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.a_lot_of_notes.a_lot_of_notes.model.Directories;
import com.example.a_lot_of_notes.a_lot_of_notes.model.Notes;
import com.example.a_lot_of_notes.a_lot_of_notes.model.Projects;

public class Database extends SQLiteOpenHelper{
    // TAG for debugging
    private static final String TAG = "Database";

    // Variables for database
    private static final String DB_NAME = "notes_db";
    private static final int DB_VERSION = 1;

    Context ctx;


    public Database(Context context){
        super(context, DB_NAME, null, DB_VERSION);
        ctx = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate: starting database creation");

        // Exec SQL database
        db.execSQL(Directories.Directories_Entry.CREATE_DIRECTORIES_TABLE);
        db.execSQL(Projects.Projects_Entry.CREATE_PROJECTS_TABLE);
        db.execSQL(Notes.NotesEntry.CREATE_NOTES_TABLE);

        Log.d(TAG, "onCreate: ending database creation");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists " + Directories.Directories_Entry.TABLE_NAME);
        db.execSQL("drop table if exists " + Projects.Projects_Entry.TABLE_NAME);
        db.execSQL("drop table if exists " + Notes.NotesEntry.TABLE_NAME);

        onCreate(db);
    }

    public boolean insertDirectory(){
        Log.d(TAG, "insertDirectory: starting");
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();


        Log.d(TAG, "insertDirectory: ending");
        return true;
    }

    public boolean insertNote(String title, String content){
        Log.d(TAG, "insertData: starting");
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(Notes.NotesEntry.COLUMN_NOTES_TITLE, title);
        cv.put(Notes.NotesEntry.COLUMN_NOTES_CONTENT, content);


        Log.d(TAG, "insertData: before inserting content values into database");
        db.insert(Notes.NotesEntry.TABLE_NAME, null, cv);
        Log.d(TAG, "insertData: after inserting content values into database");

        Log.d(TAG, "insertData: ending");
        return true;
    }


    // A query to get all notes. May need separate queries to get notes in specific directory, or
    //  from a specific project.
    public Cursor getAllNotes(){
        Log.d(TAG, "getAllNotes: starting");
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + Notes.NotesEntry.TABLE_NAME;
        Cursor data = db.rawQuery(query, null);

        Log.d(TAG, "getAllNotes: ending");
        return data;
    }


    // define queries to get needs
    public Cursor getAllDirectories(){
        Log.d(TAG, "getNotesFromDirectory: starting");
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + Directories.Directories_Entry.TABLE_NAME;
        Cursor data = db.rawQuery(query, null);

        Log.d(TAG, "getNotesFromDirectory: ending");
        return data;
    }

    // define queries to get needs
    public Cursor getAllProjects(){
        Log.d(TAG, "getProjects: starting");
        SQLiteDatabase db = getReadableDatabase();
        String query = "";
        Cursor data = db.rawQuery(query, null);

        Log.d(TAG, "getProjects: ending");
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


    // methods to implement:
    // update directories/note content
    // delete items
    // ...and?

    public void deleteSingleDirectory(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Directories.Directories_Entry.TABLE_NAME,
                Directories.Directories_Entry._ID + "=?", new String[]{id});

    }

    public void deleteAllNotes(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Notes.NotesEntry.TABLE_NAME, null, null);
    }
    public void deleteSingleNote(String id){
        SQLiteDatabase db = this.getWritableDatabase();
    }

    public void deleteSingleProject(String id){
        SQLiteDatabase db = this.getWritableDatabase();
    }
}
