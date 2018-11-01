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
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.a_lot_of_notes.a_lot_of_notes.Database.Database;

import java.util.ArrayList;

public class PageDirectories extends AppCompatActivity
        implements EditDirectory.EditDirectoryListener {

    private static final String TAG = "PageDirectories";
    static String directoryPath = "";
    Context ctx;
    Database db;
    ListView listDirectory;
    ArrayList<String> directoryData;
    String newDirName;
    FloatingActionButton fab;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page);

        Log.d(TAG, "onCreate: starting");
        ctx = this;

        // Change action bar title
        setTitle("List of Directory");

        db = new Database(this);
        listDirectory = findViewById(R.id.list_view);
        fab = findViewById(R.id.page_fab);

        populateDirectoryList();

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
                                String directory_name = userInput.getText().toString();
                                Log.d(TAG, "onClick: before insertDirectory");
                                db.insertDirectory(directory_name);
                                db.close();
                                populateDirectoryList();
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
        listDirectory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(ctx,
                        "This is directory " + directoryData.get(i) + ". Lead this to projects" +
                                " with the directory tag.",
                        Toast.LENGTH_LONG).show();
                Log.d(TAG, "onItemClick: after toast");
                directoryPath = directoryData.get(i);
                Intent navToProjects = new Intent(ctx, PageProjects.class);
                startActivity(navToProjects);

            }
        });

        // Long press on listDirectory item will show context menu to edit/delete selected directory
        registerForContextMenu(listDirectory);

        Log.d(TAG, "onCreate: ending");
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

    @Override
    public void updateName(String newName, String oldName) {
        newDirName = newName;
        db.updateDirectoryName(newDirName, oldName);
        Toast.makeText(PageDirectories.this, "Updated" , Toast.LENGTH_LONG).show();
        finish();
    }

}
