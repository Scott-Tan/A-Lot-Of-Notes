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
    static String notePath = "";
    Context ctx;
    FloatingActionButton fab;
    Database db;
    ArrayList<String> noteData;
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
                        "This is note " + noteData.get(i) + ". Lead this to a page" +
                                " that shows the note with an edit/save/delete option.",
                        Toast.LENGTH_LONG).show();
                notePath = noteData.get(i);
                Intent openNote = new Intent(ctx, ShowNote.class);
                startActivity(openNote);
            }
        });
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

        noteData = new ArrayList<>();

        Log.d(TAG, "populateNoteList: before loop");
        while(directoryCursor.moveToNext()){
            String directoryName = directoryCursor.getString(3);

            noteData.add(directoryName);
        }
        Log.d(TAG, "populateNoteList: after loop");
        ListAdapter adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, noteData);
        listNote.setAdapter(adapter);

        Log.d(TAG, "populateNoteList: ending");
    }
}
