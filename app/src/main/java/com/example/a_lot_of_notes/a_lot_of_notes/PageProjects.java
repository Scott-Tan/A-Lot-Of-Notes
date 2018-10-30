package com.example.a_lot_of_notes.a_lot_of_notes;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.a_lot_of_notes.a_lot_of_notes.Database.Database;

import java.util.ArrayList;

// Directed from PageDirectories.java
// PageProjects layout should contain page_projects
// Directs to an individual project.
public class PageProjects extends AppCompatActivity {
    private static final String TAG = "PageProjects";
    Database db;
    private ListView mListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_directories);

        Log.d(TAG, "onCreate: starting");

        db = new Database(this);
        mListView = findViewById(R.id.list_view);

        populateListView();

        Log.d(TAG, "onCreate: ending");
    }

    // populate list view with projects
    // projects should be clickable and show image/file and the associated notes
    private void populateListView(){
        Log.d(TAG, "populateListView: starting");

        Cursor data = db.getAllDirectories();
        ArrayList<String> listData = new ArrayList<>();
        ArrayList<String> directory_data = new ArrayList<>();

        Log.d(TAG, "populateListView: before loop");
        while(data.moveToNext()){
            // index 1 should be project name column
            listData.add(data.getString(1));
            // indexx 2 should be the directory tag column
            directory_data.add(data.getString(2));
        }
        Log.d(TAG, "populateListView: end of loop");

        ListAdapter adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, listData);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

        Log.d(TAG, "populateListView: ending");
    }

}
