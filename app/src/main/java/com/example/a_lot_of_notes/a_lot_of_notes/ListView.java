package com.example.a_lot_of_notes.a_lot_of_notes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


/* This class should show a list of all classes/directories in lexicographic order
 *  clicking on a class/directory should direct the user to another view with all notes and/or
 *  imported pictures/files.
 *
 *
 *  new view should load row_note.xml and maybe some .xml file that shows picture/file?
 */

public class ListView extends AppCompatActivity {
    private static final String TAG = "ListView";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view);

        Log.d(TAG, "onCreate: of ListView");
    }



    // there should be some sort of onClick method that should link to working projects within a
    // directory. Since .xml associated with this is a list view, may not be called onClick
    // word for word. Find it.
}
