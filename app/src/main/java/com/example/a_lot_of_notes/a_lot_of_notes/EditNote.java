package com.example.a_lot_of_notes.a_lot_of_notes;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a_lot_of_notes.a_lot_of_notes.Database.Database;
import com.example.a_lot_of_notes.a_lot_of_notes.PageNotes;
import com.example.a_lot_of_notes.a_lot_of_notes.R;
import com.example.a_lot_of_notes.a_lot_of_notes.model.Notes;

public class EditNote extends AppCompatActivity {

    private static final String TAG = "EditNote";
    EditText editTitle, editContent;
    Button updateNote;
    String title, content, noteID;
    Database db;
    Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_notes);

        db = new Database(this);
        ctx = this;

        editTitle = findViewById(R.id.editText_notes_title);
        editContent = findViewById(R.id.editText_notes_content);
        updateNote = findViewById(R.id.button_notes_save);
        noteID = PageNotes.noteIdPath;

        loadNoteToEdit(noteID);

        updateNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "updateNote: starting");

                title = editTitle.getText().toString();
                content = editContent.getText().toString();
                Log.d(TAG, "updateNote: title is: " + title);
                Log.d(TAG, "updateNote: content is: " + content);

                db.updateNote(title, content, noteID);
                Toast.makeText(EditNote.this, "Updated note with id: " + noteID,
                        Toast.LENGTH_SHORT).show();
                Log.d(TAG, "updateNote: ending");
                finish();
            }
        });

    }

    private void loadNoteToEdit(String note_id) {
        Log.d(TAG, "loadNoteToEdit: starting");
        Cursor noteItem = db.getNote(note_id);

        Log.d(TAG, "loadNoteToEdit: before loop");

        while(noteItem.moveToNext()){
            Log.d(TAG, "loadNoteToEdit: inside loop");
            title = noteItem.getString(noteItem.getColumnIndex(Notes.NotesEntry.COLUMN_NOTES_TITLE));
            content = noteItem.getString(noteItem.getColumnIndex(Notes.NotesEntry.COLUMN_NOTES_CONTENT));
        }

        Log.d(TAG, "loadNoteToEdit: before setting");
        editTitle.setText(title);
        editContent.setText(content);
        Log.d(TAG, "loadNoteToEdit: setted editText");
    }

}

}
