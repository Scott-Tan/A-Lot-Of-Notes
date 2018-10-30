package com.example.a_lot_of_notes.a_lot_of_notes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.a_lot_of_notes.a_lot_of_notes.Database.Database;

public class AddDirectory extends AppCompatActivity {
    private static final String TAG = "AddDirectory";

    Database db;
    TextView textView;
    EditText editText;
    Button btn_save;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_directory);

        db = new Database(this);

        textView = findViewById(R.id.add_directory_textView);
        editText = findViewById(R.id.add_directory_editText);
        btn_save = findViewById(R.id.add_directory_save);

    }

    public void save_directory(View view) {
        Log.d(TAG, "save_directory: starting");

        String directory_name = editText.getText().toString();

        Log.d(TAG, "save_directory: before inserting directory");
        db.insertDirectory(directory_name);
        db.close();

        Log.d(TAG, "save_directory: ending");
        finish();
    }
}
