package com.example.a_lot_of_notes.a_lot_of_notes;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a_lot_of_notes.a_lot_of_notes.Database.Database;
import com.example.a_lot_of_notes.a_lot_of_notes.model.Notes;

// Work to do:
//  Parse the bundle extra by splitting the string by the newline character.
//  This will allow for us to separate parts of a note (title, content, etc) and let us implement
//   an edit feature.
public class ShowNote extends AppCompatActivity {
    private static final String TAG = "ShowNote";

    private Database db;
    private String dirPath, projPath;

    TextView titleNote, contentNote;
    String noteID, title, content;
    Context ctx;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_note);
        Log.d(TAG, "onCreate: starting");
        setTitle("");

        db = new Database(this);
        ctx = this;

        titleNote = findViewById(R.id.title_view);
        contentNote = findViewById(R.id.content_view);
        dirPath = PageDirectories.directoryPath;
        projPath = PageProjects.projectPath;

        contentNote.setMovementMethod(new ScrollingMovementMethod());

        noteID = PageNotes.noteIdPath;
        Log.d(TAG, "onCreate: before db call, note id: " + noteID);
        loadNoteFromDatabase(noteID);

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadNoteFromDatabase(noteID);
    }

    private void loadNoteFromDatabase(String note_id) {
        Log.d(TAG, "loadNoteFromDatabase: starting");
        Cursor noteItem = db.getNote(note_id);

        Log.d(TAG, "loadNoteFromDatabase: before loop");

        while(noteItem.moveToNext()){
            Log.d(TAG, "loadNoteFromDatabase: inside loop");
            title = noteItem.getString(noteItem.getColumnIndex(Notes.NotesEntry.COLUMN_NOTES_TITLE));
            content = noteItem.getString(noteItem.getColumnIndex(Notes.NotesEntry.COLUMN_NOTES_CONTENT));
        }


        Log.d(TAG, "loadNoteFromDatabase: before setting");
        titleNote.setText(title);
        contentNote.setText(content);
        Log.d(TAG, "loadNoteFromDatabase: setted editText");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu: starting");
        getMenuInflater().inflate(R.menu.note_option, menu);
        return true;
    }

    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected: starting");
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.edit_note) {
            Toast.makeText(this, "Edit note", Toast.LENGTH_SHORT).show();
            openEditNote();
            return true;
        }
        else if (id == R.id.delete_note) {
            deleteNoteAlert(title);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Helper: Edit Project
    public void openEditNote(){
        Intent openNote = new Intent(ctx, EditNote.class);
        startActivity(openNote);
    }

    // Helper: Delete Project Alert Box
    public void deleteNoteAlert(final String noteName){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation");
        builder.setMessage("Are you sure you want to delete note?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        db.deleteSingleNote(noteID,dirPath,projPath);
                        Toast.makeText(ShowNote.this, "Deleted", Toast.LENGTH_LONG).show();
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}

