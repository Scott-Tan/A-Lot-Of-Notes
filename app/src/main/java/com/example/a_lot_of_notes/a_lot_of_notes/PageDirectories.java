package com.example.a_lot_of_notes.a_lot_of_notes;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a_lot_of_notes.a_lot_of_notes.Database.Database;

import java.util.ArrayList;

public class PageDirectories extends AppCompatActivity
        implements EditDirectory.EditDirectoryListener, NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "PageDirectories";
    static String directoryPath = "";
    static String directoryName = "";
    String newDirName;
    Context ctx;
    Database db;
    ListView listDirectory;
    ArrayList<ListRow> directoryData;
    ArrayList<String> directoryIdData;
    ListAdapter dirAdapter;
    FloatingActionButton fab;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_nav);

        Log.d(TAG, "onCreate: starting");
        ctx = this;

        // Change action bar title
        setTitle("List of Directory");

        db = new Database(this);
        listDirectory = findViewById(R.id.list_view);
        fab = findViewById(R.id.page_fab);

        loadActionBarOptions();
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
                TextView dialogTitle = mView.findViewById(R.id.dialogTitle);
                dialogTitle.setText("Create a new directory:");
                Log.d(TAG, "onClick: fab before alertdialog");
                alertDialog
                        .setCancelable(false)
                        .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String directory_name = userInput.getText().toString();
                                Log.d(TAG, "onClick: before insertDirectory");
                                db.insertDirectory(directory_name);
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
                Log.d(TAG, "onItemClick: starting");
                directoryPath = directoryIdData.get(i);
                Cursor directoryCursor = db.getAllDirectories();
                directoryCursor.moveToPosition(i);
                directoryName = directoryCursor.getString(1);

//                Toast.makeText(ctx,
//                        "This is directory " + directoryName + ". Lead this to projects" +
//                                " with the directory tag: " + directoryPath,
//                        Toast.LENGTH_LONG).show();

                Log.d(TAG, "onItemClick: ending");
                Intent navToProjects = new Intent(ctx, PageProjects.class);
                startActivity(navToProjects);

            }
        });

        // Long press on listDirectory item will show context menu to edit/delete selected directory
        registerForContextMenu(listDirectory);

        Log.d(TAG, "onCreate: ending");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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

    // handles loading a new tool bar for this activity since it was defined in manifest file
    //  to have no actionbar
    private void loadActionBarOptions() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    /**
     * List view manipulation and accessing methods below
     * */

    // populate the list view with directories found in sql database
    private void populateDirectoryList(){
        Log.d(TAG, "populateDirectoryList: starting");

        Cursor directoryCursor = db.getAllDirectories();
        directoryData = new ArrayList<>();
        directoryIdData = new ArrayList<>();

        Log.d(TAG, "populateDirectoryList: before loop");
        while(directoryCursor.moveToNext()){
            String directoryId = directoryCursor.getString(0);
            String directoryName = directoryCursor.getString(1);
            String directoryDate = directoryCursor.getString(2);

            directoryData.add(new ListRow(directoryName, directoryDate));
            directoryIdData.add(directoryId);
        }

        Log.d(TAG, "populateDirectoryList: after loop");
        dirAdapter = new ListAdapter(this, R.layout.list_custom, directoryData);
        dirAdapter.notifyDataSetChanged();
        listDirectory.setAdapter(dirAdapter);

        Log.d(TAG, "populateDirectoryList: ending");
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
        directoryPath = directoryIdData.get(index);

        Cursor directoryCursor = db.getAllDirectories();
        directoryCursor.moveToPosition(index);
        String oldName = directoryCursor.getString(1);

        if (item.getTitle() == "Edit") {
            // Open dialog for edit
            openEditDir(oldName, directoryPath);
        }
        else if (item.getTitle() == "Delete") {
            // Confirmation Dialog
            deleteDirAlert(directoryPath);
        }
        return true;
    }

    // Helper: Edit Directory
    public void openEditDir(String oldName, String id){
        EditDirectory editDirectory = new EditDirectory();
        Bundle bundle = new Bundle();
        bundle.putString("page", "Directory");
        bundle.putString("oldName", oldName);
        bundle.putString("id", id);
        editDirectory.setArguments(bundle);
        editDirectory.show(getSupportFragmentManager(), "edit directory");
    }

    // Helper: Delete Directory Alert Box
    public void deleteDirAlert(final String dirID){
        AlertDialog.Builder builder = new AlertDialog.Builder(PageDirectories.this);
        builder.setTitle("Confirmation");
        builder.setMessage("Are you sure you want to delete directory?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        db.deleteSingleDirectory(dirID);
                        Toast.makeText(PageDirectories.this, "Deleted", Toast.LENGTH_LONG).show();
                        populateDirectoryList();
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
    public void updateName(String newName, String id) {
        newDirName = newName;
        db.updateDirectoryName(newDirName, id);
        db.updateDirDate(id);
        Toast.makeText(PageDirectories.this, "Updated" , Toast.LENGTH_LONG).show();
        populateDirectoryList();
    }

    /**
     * Navigation methods below
     * */

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Log.d(TAG, ".onNavigationItemSelected");

        if(id == R.id.nav_folders){
            Log.d(TAG, ".onNavigationItemSelected: clicked nav_folders");
            //Use intent if not already in PageDirectories
            //Intent navToDirectories = new Intent(this, PageDirectories.class);
            //startActivity(navToDirectories);

            onBackPressed();

        }else if (id == R.id.nav_camera) {
            Log.d(TAG, ".onNavigationItemSelected: clicked nav_camera");
            if(!this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
                //disable camera
                android.app.AlertDialog.Builder a_builder = new android.app.AlertDialog.Builder(ctx);
                a_builder.setMessage("No camera detected on this device")
                        .setCancelable(false)
                        .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                android.app.AlertDialog alert = a_builder.create();
                alert.setTitle("Alert");
                alert.show();
            }
            Log.d(TAG, ".onNavigationItemSelected: end if statement");
            Intent i=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivity(i);
            Log.d(TAG, ".onNavigationItemSelected: end nav_camera");
        }
        else if (id == R.id.to_do) {
            //Use intents if not already in MainActivity
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        } else if (id == R.id.dev_note_page) {
            // Navigate to dev page options in navigation menu
            Intent intent = new Intent(this, TestPages.class);
            startActivity(intent);
        } else if (id == R.id.dev_image_page) {
            // Navigate to image testing page in navigation menu
            Intent intent = new Intent(this, TestImage.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
