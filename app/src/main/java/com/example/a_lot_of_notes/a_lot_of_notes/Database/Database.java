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
import android.provider.ContactsContract;
import android.util.Log;

import com.example.a_lot_of_notes.a_lot_of_notes.model.Directories;
import com.example.a_lot_of_notes.a_lot_of_notes.model.Image;
import com.example.a_lot_of_notes.a_lot_of_notes.model.Notes;
import com.example.a_lot_of_notes.a_lot_of_notes.model.Projects;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
        db.execSQL(Image.ImageEntry.CREATE_IMAGE_TABLE);

        Log.d(TAG, "onCreate: ending database creation");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists " + Directories.Directories_Entry.TABLE_NAME);
        db.execSQL("drop table if exists " + Projects.Projects_Entry.TABLE_NAME);
        db.execSQL("drop table if exists " + Notes.NotesEntry.TABLE_NAME);
        db.execSQL("drop table if exists " + Image.ImageEntry.TABLE_NAME);

        onCreate(db);
    }

    public long insertDirectory(String name){
        Log.d(TAG, "insertDirectory: starting");
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(Directories.Directories_Entry.COLUMN_DIRECTORIES_NAME, name);
        cv.put(Directories.Directories_Entry.COLUMN_TIMESTAMP, getDateTime());

        Log.d(TAG, "insertDirectory: before insertion");
        long directory_id = db.insert(Directories.Directories_Entry.TABLE_NAME, null, cv);

        Log.d(TAG, "insertDirectory: ending");
        return directory_id;
    }

    public long insertProject(String name, String directory_tag){
        Log.d(TAG, "insertProject: starting");
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(Projects.Projects_Entry.COLUMN_PROJECTS_NAME, name);
        cv.put(Projects.Projects_Entry.COLUMN_PROJECT_DIRECTORY, directory_tag);
        cv.put(Projects.Projects_Entry.COLUMN_TIMESTAMP, getDateTime());

        Log.d(TAG, "insertProject: before insertion");
        long project_id = db.insert(Projects.Projects_Entry.TABLE_NAME, null, cv);

        Log.d(TAG, "insertProject: ending");
        return project_id;
    }

    public long insertNote(String title, String content, String directory_tag, String project_tag){
        Log.d(TAG, "insertData: starting");
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(Notes.NotesEntry.COLUMN_NOTES_TITLE, title);
        cv.put(Notes.NotesEntry.COLUMN_NOTES_CONTENT, content);
        cv.put(Notes.NotesEntry.COLUMN_NOTES_DIRECTORY, directory_tag);
        cv.put(Notes.NotesEntry.COLUMN_NOTES_PROJECT, project_tag);
        cv.put(Notes.NotesEntry.COLUMN_TIMESTAMP, getDateTime());

        Log.d(TAG, "insertNote: before insertion");
        long note_id = db.insert(Notes.NotesEntry.TABLE_NAME, null, cv);

        Log.d(TAG, "insertData: ending");
        return note_id;
    }

    public long insertImage(String title, String path, String directory_tag, String project_tag){
        Log.d(TAG, "insertImagePath: starting");

        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(Image.ImageEntry.COLUMN_IMAGE_TITLE, title);
        cv.put(Image.ImageEntry.COLUMN_IMAGE_PATH, path);
        cv.put(Image.ImageEntry.COLUMN_IMAGE_DIRECTORY, directory_tag);
        cv.put(Image.ImageEntry.COLUMN_IMAGE_PROJECT, project_tag);

        Log.d(TAG, "insertImagePath: inserting");
        long image_id = db.insert(Image.ImageEntry.TABLE_NAME, null, cv);
        Log.d(TAG, "insertImagePath: ending");
        return image_id;
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

    public Cursor getAllImages(){
        Log.d(TAG, "getAllImages: starting");

        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + Image.ImageEntry.TABLE_NAME;

        Cursor data = db.rawQuery(query, null);
        Log.d(TAG, "getAllImages: ending");

        return data;
    }

    // define queries to get needs
    public Cursor getProjectsFromDirectory(String directory_tag){
        Log.d(TAG, "getProjectsFromDirectory: starting");
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + Projects.Projects_Entry.TABLE_NAME
                + " WHERE "
                + Projects.Projects_Entry.COLUMN_PROJECT_DIRECTORY + " = '" + directory_tag + "'";
        Cursor data = db.rawQuery(query, null);

        Log.d(TAG, "getProjectsFromDirectory: ending");
        return data;
    }

    // define query to meet needs
    public Cursor getNotesByTags(String directory_tag, String project_tag){
        Log.d(TAG, "getNotesByTags: starting");
        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT * FROM " + Notes.NotesEntry.TABLE_NAME
                + " WHERE "
                + Notes.NotesEntry.COLUMN_NOTES_DIRECTORY + " = '" + directory_tag + "'"
                + " AND "
                + Notes.NotesEntry.COLUMN_NOTES_PROJECT + " = '" + project_tag + "'";
        Cursor data = db.rawQuery(query, null);
        Log.d(TAG, "getNotesByTags: ending");
        return data;
    }

    public Cursor getNote(String id){
        Log.d(TAG, "getNote: starting");
        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT * FROM " + Notes.NotesEntry.TABLE_NAME
                + " WHERE "
                + Notes.NotesEntry._ID + " = '" + id + "'";
        Cursor data = db.rawQuery(query, null);
        Log.d(TAG, "getNote: ending");
        return data;
    }

    // methods to implement:
    // update directories/note content
    // delete items
    // ...and?

    public void updateDirectoryName(String newName, String oldName){
        SQLiteDatabase db =this.getWritableDatabase();
        String query = "UPDATE " + Directories.Directories_Entry.TABLE_NAME
                + " SET " + Directories.Directories_Entry.COLUMN_DIRECTORIES_NAME
                + " = '" + newName + "' WHERE "
                + Directories.Directories_Entry.COLUMN_DIRECTORIES_NAME + " = '"
                + oldName + "'";
        db.execSQL(query);
    }

    public void updateProjectName(String newName, String oldName, String directory_tag){
        SQLiteDatabase db =this.getWritableDatabase();
        String query = "UPDATE " + Projects.Projects_Entry.TABLE_NAME
                + " SET " + Projects.Projects_Entry.COLUMN_PROJECTS_NAME
                + " = '" + newName + "' WHERE "
                + Projects.Projects_Entry.COLUMN_PROJECT_DIRECTORY + " = '"
                + directory_tag + "' AND "
                + Projects.Projects_Entry.COLUMN_PROJECTS_NAME + " = '"
                + oldName + "'";
        db.execSQL(query);
    }

    public void updateNote(String title, String content, String id){
        Log.d(TAG, "updateNote: starting");
        Log.d(TAG, "updateNote: title is: " + title);
        Log.d(TAG, "updateNote: content is: " + content);
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + Notes.NotesEntry.TABLE_NAME
                + " SET " + Notes.NotesEntry.COLUMN_NOTES_TITLE
                + " = '" + title + "', "
                + Notes.NotesEntry.COLUMN_NOTES_CONTENT
                + " = '" + content + "'"
                + " WHERE " + Notes.NotesEntry._ID
                + " = '" + id + "'";
        db.execSQL(query);

        Log.d(TAG, "updateNote: ending");
    }

    public void deleteSingleDirectory(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Directories.Directories_Entry.TABLE_NAME,
                Directories.Directories_Entry.COLUMN_DIRECTORIES_NAME
                        + " = ?", new String[]{name});
    }

    public void deleteAllNotes(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Notes.NotesEntry.TABLE_NAME, null, null);
    }
    public void deleteDirectory(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + Directories.Directories_Entry.TABLE_NAME;
        db.execSQL(query);
    }

    public void deleteSingleNote(String id){
        SQLiteDatabase db = this.getWritableDatabase();
    }

    public void deleteSingleProject(String name, String directory_tag){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Projects.Projects_Entry.TABLE_NAME,
                Projects.Projects_Entry.COLUMN_PROJECTS_NAME
                        + " = ? AND "
                        + Projects.Projects_Entry.COLUMN_PROJECT_DIRECTORY
                        + " = ?", new String[]{name, directory_tag});
    }

    // Be sure to delete file in internal storage and from database
    public int deleteImageById(String id){
        Log.d(TAG, "deleteImageById: starting id is: " + id);

        SQLiteDatabase db = getWritableDatabase();
        int data = db.delete(Image.ImageEntry.TABLE_NAME,
                Image.ImageEntry._ID + "=?",
                new String[]{id});

        Log.d(TAG, "deleteImageById: ending");
        return data;
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}
