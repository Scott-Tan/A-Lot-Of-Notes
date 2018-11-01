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
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.a_lot_of_notes.a_lot_of_notes.Database.Database;
import com.example.a_lot_of_notes.a_lot_of_notes.model.Directories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PageDirectories extends AppCompatActivity
        implements EditDirectory.EditDirectoryListener {

    private static final String TAG = "PageDirectories";

    Database db;
    ListView listDirectory;
    ArrayList<String> directoryData;
    String newDirName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_directories);

        Log.d(TAG, "onCreate: starting");

        // Change action bar title
        setTitle("List of Directory");

        db = new Database(this);
        listDirectory = findViewById(R.id.list_directory);

        populateDirectoryList();

        // Long press on listDirectory item will show context menu to edit/delete selected directory
        registerForContextMenu(listDirectory);

        Log.d(TAG, "onCreate: ending");
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Options");
        menu.add(0, v.getId(), 0, "Edit");
        menu.add(0, v.getId(), 0, "Delete");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        final int index = info.position;
        final String name = listDirectory.getItemAtPosition(index).toString();

        if (item.getTitle() == "Edit") {

            // Open dialog for edit
            openEditDir(name);

        }
        else if (item.getTitle() == "Delete") {

            // Confirmation Dialog
            deleteDirAlert(name);

        }
        return true;
    }

    private void populateDirectoryList(){
        Log.d(TAG, "populateListView: starting");

        Cursor directoryCursor = db.getAllDirectories();
        directoryData = new ArrayList<>();

        Log.d(TAG, "populateListView: before loop");
        while(directoryCursor.moveToNext()){
            String directoryName = directoryCursor.getString(1);

            directoryData.add(directoryName);
        }
        Log.d(TAG, "populateListView: end of loop");
        ListAdapter adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, directoryData);
        listDirectory.setAdapter(adapter);

        Log.d(TAG, "populateListView: ending");
    }

    // Helper: Edit Directory
    public void openEditDir(String oldName){
        EditDirectory editDirectory = new EditDirectory();
        Bundle bundle = new Bundle();
        bundle.putString("oldName", oldName);
        editDirectory.setArguments(bundle);
        editDirectory.show(getSupportFragmentManager(), "edit directory");
    }

    // Helper: Delete Directory Alert Box
    public void deleteDirAlert(final String dirName){
        AlertDialog.Builder builder = new AlertDialog.Builder(PageDirectories.this);
        builder.setTitle("Confirmation");
        builder.setMessage("Are you sure you want to delete directory?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        db.deleteSingleDirectory(dirName);
                        Toast.makeText(PageDirectories.this, "Delete", Toast.LENGTH_LONG).show();
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    // Switch to AddDirectory class
    public void addDirectoryPage(View view){
        Intent addDirectoryIntent = new Intent(this, AddDirectory.class);
        startActivity(addDirectoryIntent);
        finish();
    }

    @Override
    public void updateName(String newName, String oldName) {
        newDirName = newName;
        db.updateDirectoryName(newDirName, oldName);
        Toast.makeText(PageDirectories.this, "Updated" , Toast.LENGTH_LONG).show();
        finish();
    }

}
