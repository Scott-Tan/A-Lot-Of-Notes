package com.example.a_lot_of_notes.a_lot_of_notes;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.a_lot_of_notes.a_lot_of_notes.Database.Database;

import java.util.ArrayList;

public class NavDirectories extends AppCompatActivity {
    private static final String TAG = "NavDirectories";
    Database db;
    private ListView mListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_directories);

        Log.d(TAG, "onCreate: starting ");
        db = new Database(this);
        mListView = (ListView) findViewById(R.id.NavDirectoriesr_listview);

        populateListView();
        Log.d(TAG, "onCreate: ending");

    }

    @Override
    protected void onResume() {
        super.onResume();
        populateListView();
    }

    // populate list view with just directories
    // Directories should be clickable and show projects/notes inside
    private void populateListView(){
        Log.d(TAG, "populateListView: starting");

        Cursor data = db.getAllDirectories();
        ArrayList<String> listData = new ArrayList<>();

        Log.d(TAG, "populateListView: before loop");
        while(data.moveToNext()){
            // index 1 should be directories column?
            listData.add(data.getString(1));
        }
        Log.d(TAG, "populateListView: end of loop");

        ListAdapter adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, listData);
        mListView.setAdapter(adapter);

        Log.d(TAG, "populateListView: ending");
    }


}
