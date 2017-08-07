package com.example.lsireneva.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    ListView lvItems;
    ArrayList<String> todoItems;
    ArrayAdapter<String> aToDoAdapter;
    EditText etEditText;
    //Intent intent;
    public static final String EXTRA_MESSAGE = "test";
    private final int REQUEST_CODE = 20;
    int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateArrayItems();
        final Intent intent = new Intent(this, EditItemActivity.class);
        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(aToDoAdapter);

        etEditText= (EditText) findViewById(R.id.etEditText);
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                todoItems.remove(i);
                aToDoAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String message=todoItems.get(i).toString();
                intent.putExtra(EXTRA_MESSAGE, message);
                position=i;
                startActivityForResult(intent, REQUEST_CODE);

            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            String item = data.getExtras().getString(EXTRA_MESSAGE);
            todoItems.set(position,item);
            writeItems();
            aToDoAdapter.notifyDataSetChanged();
        }
    }


    public void populateArrayItems(){
        readItems();
        aToDoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, todoItems);

    }
    private void readItems () {
        File fileDir = getFilesDir();
        File file = new  File (fileDir, "todo.txt");
        try {
            todoItems = new ArrayList<String>(FileUtils.readLines(file));
        } catch (IOException e) {
            todoItems = new ArrayList<String>();
        }

    }

    private void writeItems () {
        File fileDir = getFilesDir();
        File file = new  File (fileDir, "todo.txt");
        try {
            FileUtils.writeLines(file, todoItems);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void onAddItem(View view) {
        if (etEditText.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please write something to do",
                    Toast.LENGTH_LONG).show();}
        else{
            aToDoAdapter.add(etEditText.getText().toString());
            etEditText.setText("");
            writeItems();
            }
    }


}
