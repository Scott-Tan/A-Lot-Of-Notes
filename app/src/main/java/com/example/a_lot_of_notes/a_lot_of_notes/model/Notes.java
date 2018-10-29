package com.example.a_lot_of_notes.a_lot_of_notes.model;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

import com.example.a_lot_of_notes.a_lot_of_notes.Database.Database;

public class Notes {
    //debug tag
    private static final String TAG = "Notes";

    //constructor, no instances allowed, only access to entries
    private Notes(){}

    public static final class NotesEntry implements BaseColumns{
        public static final String TABLE_NAME = "notesTable";
        public static final String COLUMN_NOTES_DIRECTORY = "notes_directory";
        public static final String COLUMN_NOTES_PROJECT = "notes_project";
        public static final String COLUMN_NOTES_TITLE = "notes_title";
        public static final String COLUMN_NOTES_CONTENT = "notes_content";
        public static final String COLUMN_TIMESTAMP = "notes_timestamp";

        public static String CREATE_NOTES_TABLE = "CREATE TABLE "
                + Notes.NotesEntry.TABLE_NAME + " ("
                + Notes.NotesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Notes.NotesEntry.COLUMN_NOTES_DIRECTORY+ " TEXT, "
                + Notes.NotesEntry.COLUMN_NOTES_PROJECT + " TEXT, "
                + Notes.NotesEntry.COLUMN_NOTES_TITLE + " TEXT, "
                + Notes.NotesEntry.COLUMN_NOTES_CONTENT + " TEXT, "
                + Notes.NotesEntry.COLUMN_TIMESTAMP + " DATETIME" + ")";

    }


}