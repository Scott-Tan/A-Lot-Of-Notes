package com.example.a_lot_of_notes.a_lot_of_notes;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;


public class ShowPdf extends AppCompatActivity {
    private static final String TAG = "ShowPdf";

    PDFView pdfView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_pdf);

        Log.d(TAG, "onCreate: starting");
        pdfView = (PDFView)findViewById(R.id.pdfView);

        Uri mUri = Uri.parse(PageNotes.pdfUri);

        Log.d(TAG, "onCreate: PageNotes.pdfUri = " + PageNotes.pdfUri);
        Log.d(TAG, "onCreate: mUri = " + mUri);
        pdfView.fromUri(mUri).load();

        Log.d(TAG, "onCreate: ending");

    }
}
