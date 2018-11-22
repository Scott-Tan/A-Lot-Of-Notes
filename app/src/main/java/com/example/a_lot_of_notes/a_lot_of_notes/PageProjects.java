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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a_lot_of_notes.a_lot_of_notes.Database.Database;

import java.util.ArrayList;

// Directed from PageDirectories.java
// PageProjects layout should contain page_projects
// Directs to an individual project.
public class PageProjects extends AppCompatActivity
        implements EditDirectory.EditDirectoryListener{
    private static final String TAG = "PageProjects";
    static String projectPath = "";
    String dirPath, newProjName;
    Context ctx;
    Database db;
    private ListView listProject;
    FloatingActionButton fab;
    ArrayList<String> listData;
    ArrayList<String> directoryData;
    ArrayList<String> projectIdData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page);
        dirPath = PageDirectories.directoryPath;
        setTitle("Projects");

        Log.d(TAG, "onCreate: starting");
        ctx = this;
        db = new Database(this);
        listProject = findViewById(R.id.list_view);
        fab = findViewById(R.id.page_fab);

        populateProjectList();


        // create new project within the directory
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: fab starting");
                LayoutInflater layoutInflater = LayoutInflater.from(ctx);
                View mView = layoutInflater.inflate(R.layout.input_dialog_box, null);
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(ctx);
                alertDialog.setView(mView);
                final EditText userInput = mView.findViewById(R.id.userInputDialog);
                TextView dialogTitle = mView.findViewById(R.id.dialogTitle);
                dialogTitle.setText("Create a new project");
                Log.d(TAG, "onClick: fab before alertdialog");
                alertDialog
                        .setCancelable(false)
                        .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String project_name = userInput.getText().toString();
                                Log.d(TAG, "onClick: before insertDirectory");
                                db.insertProject(project_name, dirPath);
                                populateProjectList();
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
        listProject.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                projectPath = projectIdData.get(i);
                dirPath= directoryData.get(i);

                Toast.makeText(ctx,
                        "This is project tag " + projectPath + ". Lead this to notes" +
                                " with the project and directory tag("+ dirPath + ")",
                        Toast.LENGTH_LONG).show();
                Log.d(TAG, "onItemClick: after toast");

                Intent navToPageNotes = new Intent(ctx, PageNotes.class);
                startActivity(navToPageNotes);

            }
        });

        // Long press on listProject item will show context menu to edit/delete selected directory
        registerForContextMenu(listProject);

        Log.d(TAG, "onCreate: ending");
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

    // populate list view with projects
    // projects should be clickable and show image/file and the associated notes
    private void populateProjectList(){
        Log.d(TAG, "populateProjectList: starting");

        Cursor data = db.getProjectsFromDirectory(dirPath);
        listData = new ArrayList<>();
        directoryData = new ArrayList<>();
        projectIdData = new ArrayList<>();

        Log.d(TAG, "populateProjectList: before loop");
        while(data.moveToNext()){
            // index 0 is the project id
            // index 1 should be project name column
            // index 2 should be the directory tag column
            projectIdData.add(data.getString(0));
            listData.add(data.getString(1));
            directoryData.add(data.getString(2));
        }
        Log.d(TAG, "populateProjectList: end of loop");

        ListAdapter adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, listData);
        listProject.setAdapter(adapter);

        Log.d(TAG, "populateProjectList: ending");
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
        final String name = listProject.getItemAtPosition(index).toString();

        if (item.getTitle() == "Edit") {
            // Open dialog for edit
            openEditProj(name);
        }
        else if (item.getTitle() == "Delete") {
            // Confirmation Dialog
            deleteProjAlert(name);
        }
        return true;
    }

    // Helper: Edit Project
    public void openEditProj(String oldName){
        EditDirectory editDirectory = new EditDirectory();
        Bundle bundle = new Bundle();
        bundle.putString("page", "Project");
        bundle.putString("oldName", oldName);
        editDirectory.setArguments(bundle);
        editDirectory.show(getSupportFragmentManager(), "edit directory");
    }

    // Helper: Delete Project Alert Box
    public void deleteProjAlert(final String projName){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation");
        builder.setMessage("Are you sure you want to delete project?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        db.deleteSingleProject(projName,dirPath);
                        Toast.makeText(PageProjects.this, "Deleted", Toast.LENGTH_LONG).show();
                        populateProjectList();
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
        newProjName = newName;
        db.updateProjectName(newProjName, oldName, dirPath);
        Toast.makeText(PageProjects.this, "Updated" , Toast.LENGTH_LONG).show();
        populateProjectList();
    }

}
