package com.example.a_lot_of_notes.a_lot_of_notes.model;

import android.provider.BaseColumns;

public class Projects {
    //constructor, no instances allowed, only access to entries
    private Projects(){}

    public static final class Projects_Entry implements BaseColumns{
        public static final String TABLE_NAME = "projects_table";
        public static final String COLUMN_PROJECTS_NAME = "projects_name";
        public static final String COLUMN_TIMESTAMP = "projects_timestamp";
    }
}
