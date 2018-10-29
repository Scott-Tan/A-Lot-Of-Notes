package com.example.a_lot_of_notes.a_lot_of_notes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

// Directed from PageDirectories.java
// PageProjects layout should contain page_projects
// Directs to an individual project.
public class PageProjects extends AppCompatActivity {
    private static final String TAG = "PageProjects";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_projects);

    }
}
