package com.example.lab4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelStore;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView myListView;
    private Button addBtn;
    private EditText editText;
    private Switch urgentSwitch;
    private TextView customTV;
    private DBHelper dbHelper;
    List <Todo> toDoList;

    @NonNull
    @Override
    public ViewModelStore getViewModelStore() {
        return super.getViewModelStore();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DBHelper(MainActivity.this);
        toDoList = dbHelper.getAllTodos();
        Log.d("todos", String.valueOf(dbHelper.getAllTodos()));
        setContentView(R.layout.activity_main);
        myListView = findViewById(R.id.myListView);
        urgentSwitch = findViewById(R.id.urgentSwitch);
        addBtn = findViewById(R.id.addBtn);
        editText = findViewById(R.id.editText);
        customTV = findViewById(R.id.customTextView);
        ToDoAdapter toDoAdapter = new ToDoAdapter(this, 0, toDoList);
        myListView.setAdapter (toDoAdapter);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int urgentInInt = urgentSwitch.isChecked() ? 1 : 0; //to change from bool to int
                dbHelper.insertTodo(editText.getText().toString(), urgentInInt, MainActivity.this);
                Todo todo = new Todo(editText.getText().toString(), urgentSwitch.isChecked());
               // toDoList.add(todo);
                toDoList.add(dbHelper.getLastToDo());
                Log.d("to do length", String.valueOf(toDoList.size()));
                toDoAdapter.notifyDataSetChanged();
                editText.setText("");

            }
        });
        myListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle("Do you want to delete this");
                alertDialog.setMessage("Selected row is: " + String.valueOf(i));

                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dbHelper.delTodo(toDoList.get(i).getTxt());
                        toDoList.remove(i);
                        toDoAdapter.notifyDataSetChanged();
                    }
                });
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                alertDialog.show();
                return false;
            }
        });


    }
}