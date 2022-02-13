package com.example.lab4;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.lab4.Todo;

import java.util.List;

class ToDoAdapter extends ArrayAdapter <Todo> {
    private int resourceLayout;
    private Context mContext;

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Todo todo = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_text, parent, false);
        }
        TextView textView = convertView.findViewById(R.id.customTextView);
        if (todo.getIsUrgent()) {
            textView.setBackgroundColor(Color.RED);
            textView.setTextColor(Color.WHITE);
        } else {
            textView.setTextColor(Color.BLACK);
            textView.setBackgroundColor(Color.WHITE);
        }
        textView.setText(todo.getTxt());
        return convertView;
    }

    @Nullable
    @Override
    public Todo getItem(int position) {
        return super.getItem(position);
       // return position;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public ToDoAdapter (Context context, int resource, List<Todo> todos) {
        super(context, resource, todos);
        this.resourceLayout = resource;
        this.mContext = context;
    }
}