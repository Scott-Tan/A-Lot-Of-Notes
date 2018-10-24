package com.example.a_lot_of_notes.a_lot_of_notes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

// Directed from ListView.java
// Projects layout should contain projects
// Directs to an individual project.
public class Projects extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.projects);
    }
}
