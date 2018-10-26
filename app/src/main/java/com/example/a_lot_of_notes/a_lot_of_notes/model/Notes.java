package com.example.a_lot_of_notes.a_lot_of_notes.model;

import android.provider.BaseColumns;

public class Notes {
    //constructor, no instances allowed, only access to entries
    private Notes(){}

    public static final class NotesEntry implements BaseColumns{
        public static final String TABLE_NAME = "notesTable";
        public static final String COLUMN_NOTES_DIRECTORY = "notes_directory";
        public static final String COLUMN_NOTES_PROJECT = "notes_project";
        public static final String COLUMN_NOTES_TITLE = "notes_title";
        public static final String COLUMN_NOTES_CONTENT = "notes_content";
        public static final String COLUMN_TIMESTAMP = "notes_timestamp";

    }


}