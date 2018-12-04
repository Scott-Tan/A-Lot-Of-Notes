package com.example.a_lot_of_notes.a_lot_of_notes;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {
    private Context ctx;
    private int layout;
    private ArrayList<ListRow> showList;

    public ListAdapter(Context ctx, int layout, ArrayList<ListRow> showList) {
        this.ctx = ctx;
        this.layout = layout;
        this.showList = showList;
    }

    @Override
    public int getCount() {
        return showList.size();
    }

    @Override
    public Object getItem(int position) {
        return showList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        TextView listTitle, listDate;
        ImageView fileImage;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ListAdapter.ViewHolder holder = new ListAdapter.ViewHolder();

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);
            holder.listTitle = row.findViewById(R.id.list_name);
            holder.listDate = row.findViewById(R.id.item_date);
            holder.fileImage = row.findViewById(R.id.note_img);
            row.setTag(holder);
        }
        else {
            holder = (ListAdapter.ViewHolder) row.getTag();
        }

        ListRow listRow = showList.get(position);

        holder.listTitle.setText(listRow.getTitle());
        holder.listDate.setText(listRow.getDate());

        int paddingDp = 8;
        float density = ctx.getResources().getDisplayMetrics().density;
        int paddingPixel = (int)(paddingDp * density);

        if (listRow.getType() == "img"){
            holder.fileImage.setImageBitmap(BitmapFactory.decodeFile(listRow.getFile()));
            holder.fileImage.getLayoutParams().height = 300;
            holder.fileImage.getLayoutParams().width = 300;
        }
        else if (listRow.getType() == "pdf"){
            holder.fileImage.setImageResource(R.drawable.ic_pdf_file);
            holder.fileImage.getLayoutParams().height = 180;
            holder.fileImage.getLayoutParams().width = 180;
            holder.fileImage.setPadding(paddingPixel,paddingPixel,paddingPixel,0);
        }
        else if(listRow.getType() == "note"){
            holder.fileImage.setImageResource(R.drawable.ic_note_file);
            holder.fileImage.getLayoutParams().height = 180;
            holder.fileImage.getLayoutParams().width = 180;
            holder.fileImage.setPadding(paddingPixel,paddingPixel,paddingPixel,0);
        }
        return row;
    }
}
