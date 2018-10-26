package com.example.a_lot_of_notes.a_lot_of_notes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.a_lot_of_notes.a_lot_of_notes.Database.Database;

// Directed from NavDirectories.java
// NavProjects layout should contain projects
// Directs to an individual project.
public class NavProjects extends AppCompatActivity {
    private static final String TAG = "NavProjects";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.projects);

    }
}
