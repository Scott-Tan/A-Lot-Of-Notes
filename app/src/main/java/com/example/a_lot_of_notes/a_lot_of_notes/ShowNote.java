package com.example.a_lot_of_notes.a_lot_of_notes;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;


// Work to do:
//  Parse the bundle extra by splitting the string by the newline character.
//  This will allow for us to separate parts of a note (title, content, etc) and let us implement
//   an edit feature.
public class ShowNote extends AppCompatActivity {
    TextView textView_title;
    TextView textView_body;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_notes);

        textView_title = findViewById(R.id.input_notes_textview_title);
        textView_body = findViewById(R.id.input_notes_textview_content);

    }
}
