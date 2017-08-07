package com.example.lsireneva.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EditItemActivity extends AppCompatActivity {
    EditText edited_item;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        edited_item = (EditText) findViewById(R.id.edited_item);
        edited_item.setText(message);
        edited_item.setSelection(edited_item.getText().length());

    }

    public void onSaveChanges(View view) {
        if (edited_item.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please write something to do",
                    Toast.LENGTH_LONG).show();}
        else {
            edited_item.getText();
            intent.putExtra(MainActivity.EXTRA_MESSAGE, edited_item.getText().toString());
            setResult(RESULT_OK, intent);
            finish();
        }

    }
}
