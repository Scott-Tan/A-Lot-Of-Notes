package com.example.a_lot_of_notes.a_lot_of_notes.model;

import android.provider.BaseColumns;

public class PdfFile {
    //override super constructor, no new instances allowed, on access to entries
    private PdfFile(){}

    public static final class PdfFileEntry implements BaseColumns {
        public static final String TABLE_NAME = "pdf_table";
        public static final String COLUMN_PDF_TITLE = "pdf_title";
        public static final String COLUMN_PDF_URI = "pdf_URI";
        public static final String COLUMN_PDF_DIRECTORY = "pdf_directory";
        public static final String COLUMN_PDF_PROJECT = "pdf_project";
        public static final String COLUMN_TIMESTAMP = "pdf_timestamp";

        public static final String CREATE_PDF_TABLE = "CREATE TABLE "
                + PdfFile.PdfFileEntry.TABLE_NAME + " ("
                + PdfFile.PdfFileEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PdfFile.PdfFileEntry.COLUMN_PDF_TITLE + " TEXT, "
                + PdfFile.PdfFileEntry.COLUMN_PDF_URI + " TEXT, "
                + PdfFile.PdfFileEntry.COLUMN_PDF_DIRECTORY + " TEXT, "
                + PdfFileEntry.COLUMN_PDF_PROJECT + " TEXT, "
                + PdfFile.PdfFileEntry.COLUMN_TIMESTAMP + " DATETIME" + ")";
    }
}
