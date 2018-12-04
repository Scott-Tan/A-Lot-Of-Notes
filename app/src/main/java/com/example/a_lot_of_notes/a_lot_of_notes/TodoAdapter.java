package com.example.a_lot_of_notes.a_lot_of_notes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class TodoAdapter extends BaseAdapter {

    private Context ctx;
    private int layout;
    private ArrayList<TodoRow> showTodo;

    public TodoAdapter(Context ctx, int layout, ArrayList<TodoRow> showTodo) {
        this.ctx = ctx;
        this.layout = layout;
        this.showTodo = showTodo;
    }

    @Override
    public int getCount() {
        return showTodo.size();
    }

    @Override
    public Object getItem(int position) {
        return showTodo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        TextView todoTitle, todoDate;
        ImageView doneButton;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = new ViewHolder();

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);
            holder.todoTitle = row.findViewById(R.id.todo_title);
            holder.todoDate = row.findViewById(R.id.todo_date);
            holder.doneButton = row.findViewById(R.id.delete_todo);
            row.setTag(holder);
        }
        else {
            holder = (ViewHolder) row.getTag();
        }

        TodoRow todoRow = showTodo.get(position);

        holder.todoTitle.setText(todoRow.getTitle());
        holder.todoDate.setText(todoRow.getDate());

        return row;
    }
}