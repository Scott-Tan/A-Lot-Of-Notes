package com.example.a_lot_of_notes.a_lot_of_notes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import com.example.a_lot_of_notes.a_lot_of_notes.Database.Database;
import com.example.a_lot_of_notes.a_lot_of_notes.adapters.RecyclerViewAdapter;

public class TestImage extends AppCompatActivity {
    private static final String TAG = "TestImage";
    private static final int GALLERY_RESULT = 1;

    private Database db;
    private Context ctx;
    ImageView imageView;
    Button btn_save, btn_delete;

    //RecyclerView recyclerView;
    ArrayList<String> allData;
    ArrayList<String> imageIdData;
    ArrayList<String> imagePathData;
    ArrayList<String> imageData;
    ArrayList<String> noteIdData;
    ArrayList<String> note_data;
    ListView listNote;

    private int image_item_size;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_image);

        Log.d(TAG, "onCreate: starting");
        db = new Database(this);
        ctx = this;

        imageView = findViewById(R.id.imageView_show_image);
        btn_save = findViewById(R.id.button_test_image_save);
        btn_delete = findViewById(R.id.button_test_image_delete);
        listNote = findViewById(R.id.list_view);

        //recyclerView = findViewById(R.id.recycler_view);
        populateList();
    }

    // Get the string representation of all the titles and ids of each image.
    // Make each set of strings their own item

    private void populateList(){
        Log.d(TAG, "populateNoteList: starting");
        Log.d(TAG, "populateNoteList: " + PageDirectories.directoryPath + ", "
                + PageProjects.projectPath);

        Cursor noteCursor =
                db.getAllNotes();
        Cursor imageCursor =
                db.getAllImages();

        allData = new ArrayList<>();
        imageIdData = new ArrayList<>();
        imagePathData = new ArrayList<>();
        imageData = new ArrayList<>();
        noteIdData = new ArrayList<>();
        note_data = new ArrayList<>();

        Log.d(TAG, "populateNoteList: before loop");
        while(imageCursor.moveToNext()){
            String image_id = imageCursor.getString(0);
            String image_path = imageCursor.getString(2);

            imageIdData.add(image_id);
            imagePathData.add(image_path);
            imageData.add(image_id + "\n" + image_path);
        }

        image_item_size = imageData.size();
        Log.d(TAG, "populateList: amount of image: " + image_item_size);

        while(noteCursor.moveToNext()){
            String note_id = noteCursor.getString(0);
            String note_title = noteCursor.getString(3);

            note_data.add(note_title + "\n" + note_id);
            noteIdData.add(note_id);
        }
        Log.d(TAG, "populateNoteList: after loop");


        allData.addAll(imageData);
        allData.addAll(note_data);

        ListAdapter adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, allData);
        listNote.setAdapter(adapter);

        Log.d(TAG, "populateNoteList: ending");
    }

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
        if (id == R.id.action_import_image) {
            Toast.makeText(this, "Open gallery", Toast.LENGTH_SHORT).show();
            getImageFromGallery();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getImageFromGallery() {
        Log.d(TAG, "getImageFromGallery: starting");
        try{
            Intent openGallery = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(openGallery, GALLERY_RESULT);
        }catch(Exception e){
            Log.d(TAG, "getImageFromGallery: Exception e");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: starting");
        if(resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case GALLERY_RESULT:
                    Uri selectedImage = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                        imageView.setImageBitmap(bitmap);
                        saveImageToInternalStorage(bitmap, "testTitle");
                    } catch (IOException e) {
                        Log.d(TAG, "onActivityResult: IOException e");
                    }
                    break;
            }
        }
    }

    public void saveImageToInternalStorage(Bitmap bitmap, String title) {
        Log.d(TAG, "saveImageToInternalStorage: starting");

        File internalStorage = ctx.getDir("Images", MODE_PRIVATE);
        File filePath = new File(internalStorage, title+".png");
        String image_path = filePath.getAbsolutePath();
        FileOutputStream fos = null;
        try{
            fos = new FileOutputStream(filePath);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            Log.d(TAG, "saveImageToInternalStorage: Could not save to internal storage");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        saveImageToDatabase(title, image_path);
    }

    private void saveImageToDatabase(String title, String image_path) {
        Log.d(TAG, "saveImageToDatabase: starting");
        Log.d(TAG, "saveImageToDatabase: title is: " + title);
        Log.d(TAG, "saveImageToDatabase: image_path is: " + image_path);
        db.insertImage(title, image_path, "dummy_directory_tag", "dummy_project_tag");
        Log.d(TAG, "saveImageToDatabase: ending");

    }

    public void delete(View view) {

    }
}
