package com.example.a_lot_of_notes.a_lot_of_notes;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.a_lot_of_notes.a_lot_of_notes.Database.Database;
import com.example.a_lot_of_notes.a_lot_of_notes.R;

import java.util.ArrayList;

public class ToDoList extends AppCompatActivity {
    private static final String TAG = "ToDo";
    Context ctx = this;
    Database db;
    ListView toDoList;
    String newTaskName;
    ArrayList<String> taskData;
    static String taskCategory = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        db = new Database(this);
        toDoList = findViewById(R.id.list_view);
        setTitle("To-Do List");

        populateToDoList();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.page_fab);
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

                dialogTitle.setText("Create a new task:");
                Log.d(TAG, "onClick: fab before alertdialog");
                alertDialog
                        .setCancelable(false)
                        .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String task_name = userInput.getText().toString();
                                Log.d(TAG, "onClick: before insertDirectory");
                                db.insertNewTask(task_name, taskCategory);
                                populateToDoList();
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
    }

    private void populateToDoList(){
        Log.d(TAG, "populateToDoList: starting");

        Cursor taskCursor = db.getTaskList();
        taskData = new ArrayList<>();

        Log.d(TAG, "populateToDoList: before loop");
        while(taskCursor.moveToNext()){
            String taskName = taskCursor.getString(1);

            taskData.add(taskName);
        }
        Log.d(TAG, "populateToDoList: after loop");
        ListAdapter adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, taskData);
        toDoList.setAdapter(adapter);

        Log.d(TAG, "populateToDoList: ending");
    }

}
