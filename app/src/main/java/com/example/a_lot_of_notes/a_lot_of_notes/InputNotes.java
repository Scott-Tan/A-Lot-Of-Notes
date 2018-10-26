package com.example.a_lot_of_notes.a_lot_of_notes;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.a_lot_of_notes.a_lot_of_notes.Database.Database;


// This class allows for users to input notes.
// This class may be done? Need to figure out how to tag to specific directory/project
public class InputNotes extends AppCompatActivity {
    private static final String TAG = "InputNotes";

    Database db;
    TextView textView_title, textView_content;
    EditText editText_title, editText_content;
    Button btn_save;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_notes);
        Log.d(TAG, "onCreate: starting");

        textView_title = findViewById(R.id.input_notes_textview_title);
        textView_content = findViewById(R.id.input_notes_textview_content);
        editText_title = findViewById(R.id.input_notes_title);
        editText_content = findViewById(R.id.input_notes_content);
        btn_save = findViewById(R.id.input_notes_save);

        Log.d(TAG, "onCreate: ending");
    }


    public void save_notes(View view) {
        Log.d(TAG, "save_notes: starting");

        String title = editText_title.getText().toString();
        String content = editText_content.getText().toString();

        Log.d(TAG, "save_notes: inserting notes to database");
        db.insertNote(title, content);
        Log.d(TAG, "save_notes: inserting done");

        Log.d(TAG, "save_notes: ending");
        finish();

    }
}
