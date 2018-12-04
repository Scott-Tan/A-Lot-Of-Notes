package com.example.a_lot_of_notes.a_lot_of_notes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.a_lot_of_notes.a_lot_of_notes.Database.Database;


// This class allows for users to input notes.
// This class may be done? Need to figure out how to tag to specific directory/project
public class AddNotes extends AppCompatActivity {
    private static final String TAG = "AddNotes";

    Database db;
    TextView textView_title, textView_content;
    EditText editText_title, editText_content;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_notes);
        Log.d(TAG, "onCreate: starting");
        setTitle("Add Note");

        db = new Database(this);

        textView_title = findViewById(R.id.textView_notes_title);
        textView_content = findViewById(R.id.textView_notes_content);
        editText_title = findViewById(R.id.editText_notes_title);
        editText_content = findViewById(R.id.editText_notes_content);

        Log.d(TAG, "onCreate: ending");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_note, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_note:
                Log.d(TAG, "save_notes: starting");

                String title = editText_title.getText().toString();
                String content = editText_content.getText().toString();
                String directory_tag = PageDirectories.directoryPath;
                String project_tag = PageProjects.projectPath;

                Log.d(TAG, "save_notes: inserting notes to database");
                db.insertNote(title, content, directory_tag, project_tag);
                db.close();
                Log.d(TAG, "save_notes: inserting done");

                Log.d(TAG, "save_notes: ending");
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
