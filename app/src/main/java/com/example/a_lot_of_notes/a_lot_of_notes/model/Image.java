package com.example.a_lot_of_notes.a_lot_of_notes.model;

import android.provider.BaseColumns;

public class Image {
    //override super constructor, no new instances allowed, on access to entries
    private Image(){}

    public static final class ImageEntry implements BaseColumns{
        public static final String TABLE_NAME = "image_table";
        public static final String COLUMN_IMAGE_TITLE = "image_title";
        public static final String COLUMN_IMAGE_PATH = "image_path";
        public static final String COLUMN_IMAGE_DIRECTORY = "image_directory";
        public static final String COLUMN_IMAGE_PROJECT = "image_project";

        public static final String CREATE_IMAGE_TABLE = "CREATE TABLE "
                + Image.ImageEntry.TABLE_NAME + " ("
                + Image.ImageEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Image.ImageEntry.COLUMN_IMAGE_TITLE + " TEXT, "
                + Image.ImageEntry.COLUMN_IMAGE_PATH + " TEXT, "
                + Image.ImageEntry.COLUMN_IMAGE_DIRECTORY + " TEXT, "
                + Image.ImageEntry.COLUMN_IMAGE_PROJECT + " TEXT" + ")";
    }
}
