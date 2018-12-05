package com.example.a_lot_of_notes.a_lot_of_notes;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a_lot_of_notes.a_lot_of_notes.Database.Database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class PageNotes extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "PageNotes";
    private static final int GALLERY_RESULT= 1;
    private static final int STORAGE_RESULT= 2;
    private int NUMBER_OF_IMAGES = 0;
    private int NUMBER_OF_PDFS = 0;
    private int NUMBER_OF_IMGPDF = 0;

    private String dirPath, projPath;
    static String noteIdPath = "";
    static String imagePath = "";
    static String pdfUri = "";
    static String imageTitle = "";
    static String pdfTitle = "";
    Context ctx;
    FloatingActionButton fab;
    Database db;
    com.example.a_lot_of_notes.a_lot_of_notes.ListAdapter noteAdapter;
    ListView listNote;

    // Someone please change this into a multi dimensional array. This is awful :[
    ArrayList<ListRow> allData;
    ArrayList<String> imageIdData;
    ArrayList<String> imageTitleData;
    ArrayList<String> imagePathData;
    ArrayList<ListRow> imageData;
    ArrayList<String> pdfIdData;
    ArrayList<String> pdfTitleData;
    ArrayList<ListRow> pdfData;
    ArrayList<String> pdfUriData;
    ArrayList<String> noteIdData;
    ArrayList<ListRow> note_data;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_nav);
        Log.d(TAG, "onCreate: starting");
        setTitle("Notes and materials");

        dirPath = PageDirectories.directoryPath;
        projPath = PageProjects.projectPath;

        ctx = this;
        db = new Database(this);

        fab = findViewById(R.id.page_fab);
        listNote = findViewById(R.id.list_view);

        loadActionBarOptions();
        populateNoteList();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addNotes = new Intent(ctx, AddNotes.class);
                startActivity(addNotes);
            }
        });

        listNote.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(ctx,
//                        "This is item " + allData.get(i) + ". Lead this to a page" +
//                                " that shows the note with an edit/save/delete option.",
//                        Toast.LENGTH_LONG).show();

                if(i >= NUMBER_OF_IMGPDF){              //note is selected
                    Log.d(TAG, "onItemClick: num images = " + NUMBER_OF_IMAGES);
                    Log.d(TAG, "onItemClick: i = " + i);
                    Log.d(TAG, "onItemClick: size of noteIdData " + noteIdData.size());

                    noteIdPath = noteIdData.get(i - NUMBER_OF_IMGPDF);
                    Intent openNote = new Intent(ctx, ShowNote.class);
                    startActivity(openNote);
                } else if(i >= NUMBER_OF_IMAGES){      //pdf is selected
                    Toast.makeText(ctx, "pdf was selected", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onItemClick: i = " + i);
                    Log.d(TAG, "onItemClick: NUMBER_OF_IMAGES = " + NUMBER_OF_IMAGES);
                    Log.d(TAG, "onItemClick: pdfUriData.size() = " + pdfUriData.size());
                    pdfUri = pdfUriData.get(i - NUMBER_OF_IMAGES);
                    Log.d(TAG, "onItemClick: pdfUri = " + pdfUri);
                    Intent openPdf = new Intent(ctx, ShowPdf.class);
                    pdfTitle = pdfTitleData.get(i - NUMBER_OF_IMAGES);
                    startActivity(openPdf);
                } else {                                //image is selected
                    Log.d(TAG, "onItemClick: i = " + i);
                    imagePath = imagePathData.get(i);
                    Intent openImage = new Intent(ctx, ShowImage.class);
                    imageTitle = imageTitleData.get(i);
                    startActivity(openImage);
                }
            }
        });

        // Long press on listDirectory item will show context menu to edit/delete selected directory
        registerForContextMenu(listNote);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        populateNoteList();
    }

    // handles loading a new tool bar for this activity since it was defined in manifest file
    //  to have no actionbar
    private void loadActionBarOptions() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void populateNoteList(){
        Log.d(TAG, "populateNoteList: starting");
        Log.d(TAG, "populateNoteList: " + dirPath + ", "
                + projPath);

        Cursor noteCursor =
                db.getNotesByTags(dirPath, projPath);
        Cursor imageCursor =
                db.getImageByTags(dirPath, projPath);
        Cursor pdfCursor =
                db.getPdfByTags(dirPath,projPath);

        allData = new ArrayList<>();
        imageIdData = new ArrayList<>();
        imageTitleData = new ArrayList<>();
        imagePathData = new ArrayList<>();
        imageData = new ArrayList<>();
        pdfIdData = new ArrayList<>();
        pdfTitleData = new ArrayList<>();
        pdfData = new ArrayList<>();
        pdfUriData = new ArrayList<>();
        noteIdData = new ArrayList<>();
        note_data = new ArrayList<>();

        Log.d(TAG, "populateNoteList: before loop");
        while(imageCursor.moveToNext()){
            String image_id = imageCursor.getString(0);
            String image_title = imageCursor.getString(1);
            String image_path = imageCursor.getString(2);
            String image_date = imageCursor.getString(5);

            imageData.add(new ListRow(image_title, image_date, image_path, "img"));
            imageIdData.add(image_id);
            imageTitleData.add(image_title);
            imagePathData.add(image_path);
        }

        NUMBER_OF_IMAGES = imageData.size();
        Log.d(TAG, "populateNoteList: amount of image: " + NUMBER_OF_IMAGES);

        while(pdfCursor.moveToNext()){
            String pdf_id = pdfCursor.getString(0);
            String pdf_title = pdfCursor.getString(1);
            String pdf_uri = pdfCursor.getString(2);
            String pdf_date = pdfCursor.getString(5);


            pdfData.add(new ListRow(pdf_title, pdf_date, "", "pdf"));
            pdfIdData.add(pdf_id);
            pdfTitleData.add(pdf_title);
            pdfUriData.add(pdf_uri);
        }

        NUMBER_OF_PDFS = pdfData.size();
        Log.d(TAG, "populateNoteList: amount of pdfs: " + NUMBER_OF_PDFS);

        while(noteCursor.moveToNext()){
            String note_id = noteCursor.getString(0);
            String note_title = noteCursor.getString(3);
            String note_date = noteCursor.getString(5);

            note_data.add(new ListRow(note_title, note_date, "", "note"));
            noteIdData.add(note_id);
        }
        Log.d(TAG, "populateNoteList: after loop");


        allData.addAll(imageData);
        allData.addAll(pdfData);
        allData.addAll(note_data);

        noteAdapter = new com.example.a_lot_of_notes.a_lot_of_notes.ListAdapter(this, R.layout.list_custom, allData);
        noteAdapter.notifyDataSetChanged();
        listNote.setAdapter(noteAdapter);

        Log.d(TAG, "populateNoteList: ending");

        NUMBER_OF_IMGPDF = NUMBER_OF_IMAGES + NUMBER_OF_PDFS;
    }

    /*
     * Menu operations below
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
        Boolean switchBool = false;

        //noinspection SimplifiableIfStatement
        switch(id) {
            case R.id.action_import_image:
                Toast.makeText(this, "Open gallery", Toast.LENGTH_SHORT).show();
                getImageFromGallery();
                switchBool = true;
                break;
            case R.id.action_import_pdf:
                getPdfFromStorage();
                switchBool = true;
                break;
            case R.id.action_add_note:
                fab.callOnClick();
                switchBool = true;
                break;
            default:
                //item selected failed
                return super.onOptionsItemSelected(item);
        }

        // item selected was success
        return switchBool;
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

    private void getPdfFromStorage(){
        Log.d(TAG, "getPdfFromStorage: starting");
        try{
            Intent openStorage = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            openStorage.setType("application/pdf");
            openStorage.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivityForResult(openStorage, STORAGE_RESULT);
        }catch(Exception e){
            Log.d(TAG, "getPdfFromStorage: Exception e");
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
                        setImageTitle(bitmap);

                    } catch (IOException e) {
                        Log.d(TAG, "onActivityResult: IOException e");
                    }
                    break;
                case STORAGE_RESULT:
                    Uri selectedPdf = data.getData();
                    setPdfTitle(selectedPdf);
                    break;

                default:
                    Log.d(TAG, "onActivityResult: error: default case?");
                    break;
            }
        }
    }

    private void setImageTitle(final Bitmap bitmap) {
        final LayoutInflater[] layoutInflater = {LayoutInflater.from(ctx)};
        View mView = layoutInflater[0].inflate(R.layout.input_dialog_box, null);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ctx);
        alertDialog.setView(mView);
        final EditText userInput = mView.findViewById(R.id.userInputDialog);
        TextView dialogTitle = mView.findViewById(R.id.dialogTitle);
        dialogTitle.setText("Set a title for the image");
        Log.d(TAG, "onClick: before alertdialog");
        alertDialog
                .setCancelable(false)
                .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String title = userInput.getText().toString();
                        Toast.makeText(ctx, "Image was saved to storage and database!" +
                                        " title: " + title + "",
                                Toast.LENGTH_SHORT).show();
                        saveImageToInternalStorage(bitmap, title);
                        populateNoteList();

                        Log.d(TAG, "onClick: before insertDirectory");

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(ctx, "No image saved",
                                Toast.LENGTH_SHORT).show();
                        dialogInterface.cancel();
                    }
                });
        AlertDialog alertDialogAndroid = alertDialog.create();
        alertDialogAndroid.show();
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
            populateNoteList();
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
        db.insertImage(title, image_path, dirPath, projPath);
        Log.d(TAG, "saveImageToDatabase: ending");

    }

    private void setPdfTitle(final Uri selectedPdf) {
        final LayoutInflater[] layoutInflater = {LayoutInflater.from(ctx)};
        View mView = layoutInflater[0].inflate(R.layout.input_dialog_box, null);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ctx);
        alertDialog.setView(mView);
        final EditText userInput = mView.findViewById(R.id.userInputDialog);
        TextView dialogTitle = mView.findViewById(R.id.dialogTitle);
        dialogTitle.setText("Set a title for the pdf");
        Log.d(TAG, "onClick: before alertdialog");
        alertDialog
                .setCancelable(false)
                .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String title = userInput.getText().toString();
                        Toast.makeText(ctx, "Pdf was saved to storage and database!" +
                                        " title: " + title + "",
                                Toast.LENGTH_SHORT).show();
                        savePdfToInternalStorage(selectedPdf, title);
                        populateNoteList();

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(ctx, "No image saved",
                                Toast.LENGTH_SHORT).show();
                        dialogInterface.cancel();
                    }
                });
        AlertDialog alertDialogAndroid = alertDialog.create();
        alertDialogAndroid.show();
    }

    private void savePdfToInternalStorage(Uri selectedPdf, String title) {
        Log.d(TAG, "savePdfToInternalStorage: starting");
        savePdfToDatabase(title, selectedPdf);
        Log.d(TAG, "savePdfToInternalStorage: ending");
    }

    private void savePdfToDatabase(String title, Uri selectedPdf) {
        Log.d(TAG, "savePdfToDatabase: starting");
        db.insertPdf(title, selectedPdf, dirPath, projPath);
        Log.d(TAG, "savePdfToDatabase: ending");
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Options");
        menu.add(0, v.getId(), 0, "Edit");
        menu.add(0, v.getId(), 0, "Delete");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        final int index = info.position;
        //final String name = listNote.getItemAtPosition(index).toString();
        String id;

        if (item.getTitle() == "Edit") {
            if(index >= NUMBER_OF_IMGPDF){
                Log.d(TAG, "onContextItemSelected: item was selected");
                // Open AddNotes class to edit notes
                noteIdPath = noteIdData.get(index - NUMBER_OF_IMGPDF);
                openEditNote();
            }else if(index >= NUMBER_OF_IMAGES){        // selected pdf
                Log.d(TAG, "onContextItemSelected: pdf was selected");
                Toast.makeText(PageNotes.this, "Unable to edit PDF", Toast.LENGTH_LONG).show();
            }else{                                      // selected image
                Log.d(TAG, "onContextItemSelected: image was selected");
                Toast.makeText(PageNotes.this, "Unable to edit image", Toast.LENGTH_LONG).show();
            }

        } else if (item.getTitle() == "Delete") {
            // Confirmation Dialog
            if(index >= NUMBER_OF_IMGPDF){
                Log.d(TAG, "onContextItemSelected: item was selected");
                id = noteIdData.get(index - NUMBER_OF_IMGPDF);
                deleteNoteAlert(id);
            }else if(index >= NUMBER_OF_IMAGES){        // selected pdf
                Log.d(TAG, "onContextItemSelected: pdf was selected");
                id = pdfIdData.get(index - NUMBER_OF_IMAGES);
                deletePdfAlert(id);
            }else{                                      // selected image
                Log.d(TAG, "onContextItemSelected: image was selected");
                id = imageIdData.get(index);
                deleteImageAlert(id, index);
            }
        }
        return true;
    }

    // Helper: Edit Project
    public void openEditNote(){
//        noteIdPath = noteIdData.get(i - NUMBER_OF_IMAGES);
        Intent openNote = new Intent(ctx, EditNote.class);
        startActivity(openNote);
    }

    // Helper: Delete Project Alert Box
    public void deleteNoteAlert(final String noteName){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation");
        builder.setMessage("Are you sure you want to delete this?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Log.d(TAG, "onClick: deleting note item");
                        db.deleteSingleNote(noteName,dirPath,projPath);
                        Toast.makeText(PageNotes.this, "Deleted", Toast.LENGTH_LONG).show();
                        populateNoteList();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void deletePdfAlert(final String pdfid){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation");
        builder.setMessage("Are you sure you want to delete this?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Log.d(TAG, "onClick: pdfid: " + pdfid
                                + "\n num img" + NUMBER_OF_IMAGES);

                        db.deleteSinglePdf(pdfid, dirPath, projPath);
                        Toast.makeText(PageNotes.this, "Deleted", Toast.LENGTH_LONG).show();
                        populateNoteList();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void deleteImageAlert(final String imageid, final int index){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation");
        builder.setMessage("Are you sure you want to delete this?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Log.d(TAG, "onClick: pdfid: " + imageid
                                + "\n num img" + NUMBER_OF_IMAGES);
                        deleteImageInternalStorage(index);
                        db.deleteSingleImage(imageid, dirPath, projPath);
                        Toast.makeText(PageNotes.this, "Deleted", Toast.LENGTH_LONG).show();
                        populateNoteList();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void deleteImageInternalStorage(int index){
        Log.d(TAG, "deleteImageInternalStorage: deleting image in internal storage");
        File dir = getFilesDir();
        String title = imageTitleData.get(index);
        File file = new File(dir, title+".png");
        file.delete();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Log.d(TAG, ".onNavigationItemSelected");

        if(id == R.id.nav_folders){
            Log.d(TAG, ".onNavigationItemSelected: clicked nav_folders");
            //Use intent if not already in PageDirectories
            Intent navToDirectories = new Intent(this, PageDirectories.class);
            startActivity(navToDirectories);

        }else if (id == R.id.nav_camera) {
            Log.d(TAG, ".onNavigationItemSelected: clicked nav_camera");
            if(!this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
                //disable camera
                android.app.AlertDialog.Builder a_builder = new android.app.AlertDialog.Builder(ctx);
                a_builder.setMessage("No camera detected on this device")
                        .setCancelable(false)
                        .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                android.app.AlertDialog alert = a_builder.create();
                alert.setTitle("Alert");
                alert.show();
            }
            Log.d(TAG, ".onNavigationItemSelected: end if statement");
            Intent i=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivity(i);
            Log.d(TAG, ".onNavigationItemSelected: end nav_camera");
        }
        else if (id == R.id.to_do) {
            //Use intents if not already in MainActivity
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);


        }
//        else if (id == R.id.dev_note_page) {
//            // Navigate to dev page options in navigation menu
//            Intent intent = new Intent(this, TestPages.class);
//            startActivity(intent);
//        } else if (id == R.id.dev_image_page) {
//            // Navigate to image testing page in navigation menu
//            Intent intent = new Intent(this, TestImage.class);
//            startActivity(intent);
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
