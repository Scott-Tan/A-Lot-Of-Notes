package com.example.a_lot_of_notes.a_lot_of_notes;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.a_lot_of_notes.a_lot_of_notes.Database.Database;

import java.util.ArrayList;

public class PageNotes extends AppCompatActivity {
    private static final String TAG = "PageNotes";
    static String noteIdPath = "";
    Context ctx;
    FloatingActionButton fab;
    Database db;
    ArrayList<String> noteIdData;
    ArrayList<String> note_data;
    ListView listNote;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page);
        setTitle("Notes in " + PageProjects.projectPath + "...");
        ctx = this;
        db = new Database(this);

        fab = findViewById(R.id.page_fab);
        listNote = findViewById(R.id.list_view);

        populateNoteList();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addNotes = new Intent(ctx, AddNotes.class);
                startActivity(addNotes);
            }
        });

        listNote.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(ctx,
                        "This is note id " + noteIdData.get(i) + ". Lead this to a page" +
                                " that shows the note with an edit/save/delete option.",
                        Toast.LENGTH_LONG).show();
                noteIdPath = noteIdData.get(i);
                Intent openNote = new Intent(ctx, ShowNote.class);
                startActivity(openNote);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(this, "No action", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        populateNoteList();
    }

    private void populateNoteList(){
        Log.d(TAG, "populateNoteList: starting");
        Log.d(TAG, "populateNoteList: " + PageDirectories.directoryPath + ", "
                                                + PageProjects.projectPath);
        Cursor directoryCursor =
                db.getNotesByTags(PageDirectories.directoryPath, PageProjects.projectPath);

        noteIdData = new ArrayList<>();
        note_data = new ArrayList<>();

        Log.d(TAG, "populateNoteList: before loop");
        while(directoryCursor.moveToNext()){
            String note_id = directoryCursor.getString(0);
            String note_title = directoryCursor.getString(3);

            note_data.add(note_title + "\n" + note_id);
            noteIdData.add(note_id);
        }
        Log.d(TAG, "populateNoteList: after loop");
        ListAdapter adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, note_data);
        listNote.setAdapter(adapter);

        Log.d(TAG, "populateNoteList: ending");
    }
}
