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

        ctx = this;
        imageView = findViewById(R.id.imageView_show_image);
        loadImageFromPath(PageNotes.imagePath);

    }

    private void loadImageFromPath(String imagePath) {
        imageView.setImageBitmap(BitmapFactory.decodeFile(imagePath));
    }

    /*
     * Menu operations below
     *  For ShowImage.class, we could add options to delete, rename, and move the image
     *   around the database
     * */

    // Inflate the menu; this adds items to the action bar if it is present.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu: starting");
        getMenuInflater().inflate(R.menu.page_note, menu);
        return true;
    }

    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected: starting");
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.show_image_some_action) {
            Toast.makeText(this, "Open gallery", Toast.LENGTH_SHORT).show();
            doAction();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // some dummy action, replace with an actual one
    public void doAction(){
        Toast.makeText(ctx, "No action, please implement if need", Toast.LENGTH_SHORT).show();
    }
}
