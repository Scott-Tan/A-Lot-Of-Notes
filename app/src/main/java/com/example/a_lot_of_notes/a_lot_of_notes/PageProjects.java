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

// Directed from PageDirectories.java
// PageProjects layout should contain page_projects
// Directs to an individual project.
public class PageProjects extends AppCompatActivity {
    private static final String TAG = "PageProjects";
    static String projectPath = "";
    Context ctx;
    Database db;
    private ListView mListView;
    FloatingActionButton fab;
    ArrayList<String> listData;
    ArrayList<String> directoryData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page);
        setTitle("Projects in " + PageDirectories.directoryPath + "...");

        Log.d(TAG, "onCreate: starting");
        ctx = this;
        db = new Database(this);
        mListView = findViewById(R.id.list_view);
        fab = findViewById(R.id.page_fab);

        populateListView();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: fab starting");
                LayoutInflater layoutInflater = LayoutInflater.from(ctx);
                View mView = layoutInflater.inflate(R.layout.input_dialog_box, null);
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(ctx);
                alertDialog.setView(mView);
                final EditText userInput = mView.findViewById(R.id.userInputDialog);
                Log.d(TAG, "onClick: fab before alertdialog");
                alertDialog
                        .setCancelable(false)
                        .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String project_name = userInput.getText().toString();
                                Log.d(TAG, "onClick: before insertDirectory");
                                db.insertProject(project_name, PageDirectories.directoryPath);
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

        // Clicking on an item will bring up the projects tagged with the directory
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(ctx,
                        "This is project tag " + listData.get(i) + ". Lead this to notes" +
                                " with the project and directory tag("+ directoryData.get(i) + ")",
                        Toast.LENGTH_LONG).show();
                Log.d(TAG, "onItemClick: after toast");
                projectPath = listData.get(i);
                PageDirectories.directoryPath = directoryData.get(i);

                Intent navToNotes = new Intent(ctx, PageNotes.class);
                startActivity(navToNotes);

            }
        });

        Log.d(TAG, "onCreate: ending");
    }

    // populate list view with projects
    // projects should be clickable and show image/file and the associated notes
    private void populateListView(){
        Log.d(TAG, "populateListView: starting");

        Cursor data = db.getProjectsFromDirectory(PageDirectories.directoryPath);
        listData = new ArrayList<>();
        directoryData = new ArrayList<>();

        Log.d(TAG, "populateListView: before loop");
        while(data.moveToNext()){
            // index 1 should be project name column
            listData.add(data.getString(1));
            // indexx 2 should be the directory tag column
            directoryData.add(data.getString(2));
        }
        Log.d(TAG, "populateListView: end of loop");

        ListAdapter adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, listData);
        mListView.setAdapter(adapter);

        Log.d(TAG, "populateListView: ending");
    }

}
