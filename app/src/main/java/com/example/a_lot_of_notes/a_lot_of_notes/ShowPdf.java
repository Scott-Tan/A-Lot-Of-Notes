package com.example.a_lot_of_notes.a_lot_of_notes;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;

public class ShowPdf extends AppCompatActivity {
    private static final String TAG = "ShowPdf";

    PDFView pdfView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_pdf);

        pdfView = (PDFView)findViewById(R.id.pdfView);

        Uri mUri = Uri.parse(PageNotes.pdfUri);
        pdfView.fromUri(mUri).load();

    }
}
