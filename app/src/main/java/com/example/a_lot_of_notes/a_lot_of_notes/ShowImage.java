package com.example.a_lot_of_notes.a_lot_of_notes;

import android.app.Dialog;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

public class ShowImage extends AppCompatActivity {
    private static final String TAG = "ShowImage";
    private Context ctx;
    ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_image);
        Log.d(TAG, "onCreate: starting");
        setTitle(PageNotes.imageTitle);

        ctx = this;
        imageView = findViewById(R.id.imageView_show_image);
        loadImageFromPath(PageNotes.imagePath);

    }

    private void loadImageFromPath(String imagePath) {
        imageView.setImageBitmap(BitmapFactory.decodeFile(imagePath));
    }

}
