package com.example.a_lot_of_notes.a_lot_of_notes;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.a_lot_of_notes.a_lot_of_notes.Database.Database;

public class EditDirectory extends AppCompatDialogFragment {

    private EditText editDirName;
    private EditDirectoryListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.edit_directory, null);
        editDirName = view.findViewById(R.id.edit_directory);

        Bundle bundle = getArguments();
        final String oldName = bundle.getString("oldName");
        final String page = bundle.getString("page");

        editDirName.setText(oldName);
        editDirName.setSelection(oldName.length());

        builder.setView(view)
                .setTitle("Edit " + page)
                .setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newName = editDirName.getText().toString();
                        listener.updateName(newName, oldName);
                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (EditDirectoryListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString() +
            "must implement EditDirectoryListener");
        }
    }

    public interface EditDirectoryListener{
        void updateName(String newName, String oldName);
    }

}
