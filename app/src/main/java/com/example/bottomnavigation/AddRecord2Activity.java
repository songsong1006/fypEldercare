package com.example.bottomnavigation;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class AddRecord2Activity extends AppCompatActivity {

    EditText name_input, tablets_input, times_input, food_input;
    FloatingActionButton floatingActionButton;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record2);

        name_input = findViewById(R.id.txtmedName3);
        tablets_input = findViewById(R.id.txtTablets3);
        times_input = findViewById(R.id.txtTimesDaily3);
        food_input = findViewById(R.id.txtFood3);
        toolbar = findViewById(R.id.toolbar5);
        setSupportActionBar(toolbar);


        floatingActionButton = findViewById(R.id.saveBtn3);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDatabaseHelper myDatabaseHelper = new MyDatabaseHelper(AddRecord2Activity.this);
                myDatabaseHelper.addmedicine(name_input.getText().toString().trim(),
                        tablets_input.getText().toString().trim(),
                        Integer.valueOf(times_input.getText().toString().trim()),
                        food_input.getText().toString().trim());
                        finish();
            }
        });


    }

}