package com.example.a_lot_of_notes.a_lot_of_notes;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.a_lot_of_notes.a_lot_of_notes.Database.Database;

import java.util.ArrayList;


/*
    This class is the homepage that is viewable at launch.
    This class should show to viewers all of their current page_projects they're working on.
    Sort page_projects by lexicographric order, or by chronology of revision/creation.


 */

public class TestPages extends AppCompatActivity {
    private static final String TAG = "TestPages";

    /* Tester to see if data base is working */
    Database db;
    private ListView listView;
    TextView textView, textView2;
    Button btn_save, btn_clear;
    EditText noteTitle, noteContent;
    /* Tester to see if data base is working */

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_page);

        Log.d(TAG, "onCreate: starting");
        /* Tester to see if data base is working */
        db = new Database(this);
        //listView = findViewById(R.id.testpage_listview);
        listView = findViewById(R.id.list_view);

        textView = findViewById(R.id.testpage_textview);
        btn_save = findViewById(R.id.testpage_save);
        btn_clear = findViewById(R.id.testpage_clear_notes);
        noteTitle = findViewById(R.id.testpage_note_title);
        noteContent = findViewById(R.id.testpage_note_content);

        Log.d(TAG, "onCreate: before populate");
        populateListView();
        /* Tester to see if data base is working */

    }

    @Override
    protected void onResume() {
        super.onResume();
        populateListView();
    }

    private void populateListView() {
        Cursor data = db.getAllNotes();
        final ArrayList<String> listData = new ArrayList<>();

        StringBuilder sb = new StringBuilder("");

        while(data.moveToNext()){
            // clear the sb before adding new items
            sb.setLength(0);
            sb.append("_id: " + data.getString(0) + "\n");
            sb.append("title: " + data.getString(3) + "\n");
            sb.append("content: " + data.getString(4) + "\n");

            listData.add(sb.toString());
        }

        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(TAG, "onItemClick: starting");
                String someNote = listData.get(i);

                Intent intent = new Intent(getApplicationContext(), ShowNote.class);
                intent.putExtra("Note", someNote);

                startActivity(intent);
            }
        });
    }

    public void save(View view) {
        String s1 = noteTitle.getText().toString();
        String s2 = noteContent.getText().toString();
        String s3 = "";
        String s4 = "";

        // Temporary
//        db.insertNote(s1, s2, s3, s4);
        db.insertNote(s1, s2);

        populateListView();
    }

    public void clear_notes(View view) {
        db.deleteAllNotes();
        populateListView();
    }

    //addNote component tested and works
    public void addNote(View view) {
        Intent intent = new Intent(this, AddNotes.class);
        startActivity(intent);
    }


}

