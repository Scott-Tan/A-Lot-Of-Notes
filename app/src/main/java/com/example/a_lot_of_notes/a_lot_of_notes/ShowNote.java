package com.example.a_lot_of_notes.a_lot_of_notes;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

    private TextView textView_title, textView_content;
    private EditText editText_title, editText_content;
    private Button btn_save;

    private String note_id_path_copy;
    private String title;
    private String content;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_notes);
        Log.d(TAG, "onCreate: starting");
        db = new Database(this);

        textView_title = findViewById(R.id.textView_notes_title);
        textView_content = findViewById(R.id.textView_notes_content);
        editText_title = findViewById(R.id.editText_notes_title);
        editText_content = findViewById(R.id.editText_notes_content);
        btn_save = findViewById(R.id.button_notes_save);

        note_id_path_copy = PageNotes.noteIdPath;

        Log.d(TAG, "onCreate: before db call, note id: " + note_id_path_copy);
        loadNoteFromDatabase(note_id_path_copy);

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
        editText_title.setText(title);
        editText_content.setText(content);
        Log.d(TAG, "loadNoteFromDatabase: setted editText");
    }

    // Update note based on the id of the current note in view
    public void save_notes(View view) {
        Log.d(TAG, "save_notes: starting");

        title = editText_title.getText().toString();
        content = editText_content.getText().toString();
        Log.d(TAG, "save_notes: title is: " + title);
        Log.d(TAG, "save_notes: content is: " + content);

        db.updateNote(title, content, note_id_path_copy);
        Toast.makeText(this, "Updated note with id: " + note_id_path_copy,
                Toast.LENGTH_SHORT).show();
        Log.d(TAG, "save_notes: ending");
        finish();
    }
}
