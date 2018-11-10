package com.example.a_lot_of_notes.a_lot_of_notes.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a_lot_of_notes.a_lot_of_notes.R;

public class ImageRecyclerViewHolder extends RecyclerView.ViewHolder {
    private TextView image_title = null;
    private TextView image_path = null;
    private ImageView image_view = null;

    public ImageRecyclerViewHolder(View itemView){
        super(itemView);

        if(itemView!=null){
            image_title = itemView.findViewById(R.id.card_view_image_title);
            image_path = itemView.findViewById(R.id.card_view_image_path);
            image_view = itemView.findViewById(R.id.card_view_image);
        }
    }

    public TextView getImage_title(){
        return image_title;
    }

    public ImageView getImage_view(){
        return image_view;
    }
}
