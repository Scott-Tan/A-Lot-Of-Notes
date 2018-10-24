package com.example.a_lot_of_notes.a_lot_of_notes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;


/*
    This class is the homepage that is viewable at launch.
    This class should show to viewers all of their current projects they're working on.
    Sort projects by lexicographric order, or by chronology of revision/creation.


 */

public class ContentMain extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
    }
}
