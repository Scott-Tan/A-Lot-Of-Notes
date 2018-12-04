package com.example.a_lot_of_notes.a_lot_of_notes.model;

import android.provider.BaseColumns;

public class Task {
    //override super constructor, no new instances allowed, on access to entries
    private Task(){}

    public static final class TaskEntry implements BaseColumns {
        public static final String TABLE_NAME = "tasks_table";
        public static final String COLUMN_TASK_TITLE = "task_title";
        public static final String COLUMN_TASK_CATEGORY = "task_category";
        public static final String COLUMN_TASK_DUE = "task_due";

        public static String CREATE_TASKS_TABLE = "CREATE TABLE "
                + Task.TaskEntry.TABLE_NAME + " ("
                + Task.TaskEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Task.TaskEntry.COLUMN_TASK_TITLE + " TEXT, "
                + Task.TaskEntry.COLUMN_TASK_CATEGORY + " TEXT, "
                + Task.TaskEntry.COLUMN_TASK_DUE + " TEXT" + ")";
    }
}
