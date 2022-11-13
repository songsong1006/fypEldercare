package com.example.bottomnavigation;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class AddRecord2Activity extends AppCompatActivity {

    EditText name_input, tablets_input, times_input, food_input;
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record2);

        name_input = findViewById(R.id.txtmedName3);
        tablets_input = findViewById(R.id.txtTablets3);
        times_input = findViewById(R.id.txtTimesDaily3);
        food_input = findViewById(R.id.txtFood3);

        floatingActionButton = findViewById(R.id.saveBtn3);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDatabaseHelper myDatabaseHelper = new MyDatabaseHelper(AddRecord2Activity.this);
                myDatabaseHelper.addmedicine(name_input.getText().toString().trim(),
                        tablets_input.getText().toString().trim(),
                        Integer.valueOf(times_input.getText().toString().trim()),
                        food_input.getText().toString().trim());
            }
        });


    }

}