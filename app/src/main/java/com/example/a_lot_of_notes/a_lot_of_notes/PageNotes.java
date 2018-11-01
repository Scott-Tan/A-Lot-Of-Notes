package com.example.a_lot_of_notes.a_lot_of_notes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class PageNotes extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page);
        setTitle("Notes in " + PageProjects.projectPath + "...");
    }
}
