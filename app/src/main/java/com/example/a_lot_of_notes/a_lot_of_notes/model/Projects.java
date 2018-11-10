package com.example.a_lot_of_notes.a_lot_of_notes.model;

import android.provider.BaseColumns;

public class Projects {
    //constructor, no instances allowed, only access to entries
    private Projects(){}

    public static final class Projects_Entry implements BaseColumns{
        public static final String TABLE_NAME = "projects_table";
        public static final String COLUMN_PROJECTS_NAME = "projects_name";
        public static final String COLUMN_PROJECT_DIRECTORY = "project_directory";
        public static final String COLUMN_TIMESTAMP = "projects_timestamp";

        public static String CREATE_PROJECTS_TABLE = "CREATE TABLE "
                + Projects.Projects_Entry.TABLE_NAME + " ("
                + Projects.Projects_Entry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Projects.Projects_Entry.COLUMN_PROJECTS_NAME + " TEXT, "
                + Projects.Projects_Entry.COLUMN_PROJECT_DIRECTORY + " TEXT, "
                + Projects.Projects_Entry.COLUMN_TIMESTAMP + " DATETIME" + ")";


    }
}
