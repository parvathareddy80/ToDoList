package com.codepath.todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ToDoListAdapter extends ArrayAdapter<ToDoItem> {

    Context context;
    ArrayList<ToDoItem> toDoItems = new ArrayList<ToDoItem>();
    int resource;

    public ToDoListAdapter(Context context, int resource,ArrayList<ToDoItem> Tasks){
        super(context,resource , Tasks);
        this.context = context;
        this.resource = resource;
        this.toDoItems = Tasks;
    }

    public View getView(int position, View convertView, ViewGroup parent){

        ToDoItem toDoItem = toDoItems.get(position);

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_todo,
                    parent, false);

        }

        TextView tvTask = (TextView) convertView.findViewById(R.id.tvTask);
        TextView tvStatus = (TextView) convertView.findViewById(R.id.tvStatus);

        tvTask.setText(toDoItem.getTask());
        tvStatus.setText(toDoItem.getStatus());

        return convertView;
    }
}