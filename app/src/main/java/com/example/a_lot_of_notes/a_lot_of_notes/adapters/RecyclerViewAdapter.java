package com.example.a_lot_of_notes.a_lot_of_notes.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;


import java.util.ArrayList;
import com.example.a_lot_of_notes.a_lot_of_notes.R;

public class RecyclerViewAdapter extends RecyclerView.Adapter<ImageRecyclerViewHolder> {
    public ArrayList<ImageRecyclerViewItem> imageItemList;

    // Constructor
    public RecyclerViewAdapter (ArrayList<ImageRecyclerViewItem> imageItemList){
        this.imageItemList= imageItemList;
    }



    @Override
    public ImageRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View imageItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_image,
                parent, false);

        final TextView carTitleView = (TextView)imageItemView.findViewById(R.id.card_view_image_title);
        final ImageView carImageView = (ImageView)imageItemView.findViewById(R.id.card_view_image);

        ImageRecyclerViewHolder ret = new ImageRecyclerViewHolder(imageItemView);
        return ret;
    }


    // On click
    @Override
    public void onBindViewHolder(ImageRecyclerViewHolder holder, int position) {
        if(imageItemList != null){
            ImageRecyclerViewItem imageItem = imageItemList.get(position);
            if(imageItem != null){
                holder.getImage_title().setText(imageItem.getImage_title());
            }
        }
    }

    @Override
    public int getItemCount() {
        return imageItemList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView myTextView;
        public MyViewHolder(View itemView) {
            super(itemView);
            myTextView = (TextView)itemView.findViewById(R.id.card_view_textView);
        }
    }
}
