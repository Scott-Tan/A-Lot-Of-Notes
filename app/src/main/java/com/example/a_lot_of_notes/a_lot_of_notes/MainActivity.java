package com.example.a_lot_of_notes.a_lot_of_notes;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a_lot_of_notes.a_lot_of_notes.Database.Database;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";

    private Context ctx;
    private Database db;
    private ListView toDoList;
    ArrayList<TodoRow> taskData;
    ArrayList<String> taskIdData;
    TodoAdapter todoAdapter;

    private String taskCategory = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new Database(this);
        ctx = this;
        toDoList = findViewById(R.id.list_view);
        setTitle("To-Do List");

        loadActionBarOptions();
        populateToDoList();

        toDoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ctx, "Edit ToDo", Toast.LENGTH_LONG).show();
            }
        });
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

    private void handlefabOnClick(View view){
        Log.d(TAG, "handlefabOnClick: fab starting");
        LayoutInflater layoutInflater = LayoutInflater.from(ctx);
        View mView = layoutInflater.inflate(R.layout.todo_dialog_box, null);
        android.support.v7.app.AlertDialog.Builder alertDialog = new android.support.v7.app.AlertDialog.Builder(ctx);
        alertDialog.setView(mView);

        final EditText userInput = mView.findViewById(R.id.todo_userInputDialog);
        final EditText userInput2 = mView.findViewById(R.id.todo_due_userInputDialog);
        TextView dialogTitle = mView.findViewById(R.id.todo_dialogTitle);

        dialogTitle.setText("Create something new todo...");
        Log.d(TAG, "handlefabOnClick: fab before alertdialog");
        alertDialog
                .setCancelable(false)
                .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String task_name = userInput.getText().toString();
                        String task_due = userInput2.getText().toString();
                        Log.d(TAG, "handlefabOnClick: before insertDirectory");
                        db.insertNewTask(task_name, task_due, taskCategory);
                        populateToDoList();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        android.support.v7.app.AlertDialog alertDialogAndroid = alertDialog.create();
        alertDialogAndroid.show();
    }

    private void loadActionBarOptions() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { handlefabOnClick(view); }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    //populate list in content_main.xml
    private void populateToDoList(){
        Log.d(TAG, "populateToDoList: starting");

        taskData = new ArrayList<>();
        taskIdData = new ArrayList<>();
        Cursor taskCursor = db.getTaskList();

        Log.d(TAG, "populateToDoList: before loop");
        while(taskCursor.moveToNext()){
            String taskId= taskCursor.getString(0);
            String taskName = taskCursor.getString(1);
            String taskDue = taskCursor.getString(3);

            taskData.add(new TodoRow(taskName, taskDue));
            taskIdData.add(taskId);
        }

        Log.d(TAG, "populateToDoList: after loop");
        todoAdapter = new TodoAdapter(this, R.layout.todo_custom, taskData);
        todoAdapter.notifyDataSetChanged();
        toDoList.setAdapter(todoAdapter);

        Log.d(TAG, "populateToDoList: ending");
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Log.d(TAG, ".onNavigationItemSelected");

        if(id == R.id.nav_folders){
            Log.d(TAG, ".onNavigationItemSelected: clicked nav_folders");
            Intent navToDirectories = new Intent(this, PageDirectories.class);
            startActivity(navToDirectories);
        }else if (id == R.id.nav_camera) {
            Log.d(TAG, ".onNavigationItemSelected: clicked nav_camera");
            if(!this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
                //disable camera
                AlertDialog.Builder a_builder = new AlertDialog.Builder(MainActivity.this);
                a_builder.setMessage("No camera detected on this device")
                        .setCancelable(false)
                        .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                AlertDialog alert = a_builder.create();
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
            //Intent intent = new Intent(this, MainActivity.class);
            //startActivity(intent);
            onBackPressed();

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

    public void deleteTask(View view) {
        View parentRow = (View) view.getParent();
        ListView taskList = (ListView) parentRow.getParent();
        final int position = taskList.getPositionForView(parentRow);

        String id = taskIdData.get(position);
        db.deleteSingleTask(id);

        Toast.makeText(ctx, "Done!", Toast.LENGTH_SHORT).show();
        populateToDoList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        populateToDoList();
    }

}
