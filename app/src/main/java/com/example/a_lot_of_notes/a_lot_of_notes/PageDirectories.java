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

public class PageDirectories extends AppCompatActivity {
    private static final String TAG = "PageDirectories";
    Context ctx;
    Database db;
    private ListView mListView;
    FloatingActionButton fab;
    ArrayList<String> listData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_directories);

        Log.d(TAG, "onCreate: starting ");
        ctx = this;
        db = new Database(this);
        mListView = findViewById(R.id.list_view);
        fab = findViewById(R.id.page_directories_fab);

        populateListView();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent create_directory = new Intent(getApplicationContext(), AddDirectory.class);
                //startActivity(create_directory);

                LayoutInflater layoutInflater = LayoutInflater.from(ctx);
                View mView = layoutInflater.inflate(R.layout.input_dialog_box, null);
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(ctx);
                alertDialog.setView(mView);

                final EditText userInput = mView.findViewById(R.id.userInputDialog);

                alertDialog
                        .setCancelable(false)
                        .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String directory_name = userInput.getText().toString();

                                Log.d(TAG, "onClick: before insertDirectory");
                                db.insertDirectory(directory_name);
                                db.close();

                                populateListView();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });

                AlertDialog alertDialogAndroid = alertDialog.create();
                alertDialogAndroid.show();
            }
        });


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(ctx,
                        "This is directory " + listData.get(i) + ". Lead this to projects" +
                                " with the directory tag.",
                        Toast.LENGTH_LONG).show();


            }
        });


        Log.d(TAG, "onCreate: ending");

    }

    @Override
    protected void onResume() {
        super.onResume();
        populateListView();
    }

    // populate list view with just directories
    // Directories should be clickable and show page_projects/notes inside
    private void populateListView(){
        Log.d(TAG, "populateListView: starting");

        Cursor data = db.getAllDirectories();
        listData = new ArrayList<>();

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
