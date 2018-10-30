package com.example.a_lot_of_notes.a_lot_of_notes.model;

import android.provider.BaseColumns;

public class Directories {
    //constructor, no instances allowed, only access to entries
    private Directories(){}

    public static final class Directories_Entry implements BaseColumns{
        public static final String TABLE_NAME = "directories_table";
        public static final String COLUMN_DIRECTORIES_NAME = "directories_name";
        public static final String COLUMN_TIMESTAMP = "directories_timestamp";

        public static String CREATE_DIRECTORIES_TABLE = "CREATE TABLE "
                + Directories.Directories_Entry.TABLE_NAME + " ("
                + Directories.Directories_Entry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Directories.Directories_Entry.COLUMN_DIRECTORIES_NAME + " TEXT, "
                + Directories.Directories_Entry.COLUMN_TIMESTAMP + " DATETIME" + ")";
    }
}
