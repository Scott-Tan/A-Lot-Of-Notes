package com.example.a_lot_of_notes.a_lot_of_notes;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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
    This class should show to viewers all of their current projects they're working on.
    Sort projects by lexicographric order, or by chronology of revision/creation.


 */

public class TestPages extends AppCompatActivity {
    private static final String TAG = "TestPages";

    /* Tester to see if data base is working */
    Database db;
    private ListView lv;
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
        lv = findViewById(R.id.testpage_listview);
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

    public void save(View view) {
        String s1 = noteTitle.getText().toString();
        String s2 = noteContent.getText().toString();
        db.insertNote(s1, s2);

        populateListView();
    }

    private void populateListView() {
        Cursor data = db.getAllNotes();
        ArrayList<String> listData = new ArrayList<>();

        while(data.moveToNext()){
            String s1 = data.getString(3);
            String s2 = data.getString(4);
            String s12 = s1 + "\n" + s2 + "\n";

            listData.add(s12);
        }

        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        lv.setAdapter(adapter);

    }

    public void clear_notes(View view) {
        db.deleteAllNotes();
        populateListView();
    }
}
