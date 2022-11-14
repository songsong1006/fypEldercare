package com.example.bottomnavigation;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class UpdateActivity extends AppCompatActivity {

    EditText name_input, tablets_input, times_input, food_input;
    FloatingActionButton floatingActionButton;

    String id, name, tablets, times, food;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        name_input = findViewById(R.id.txtmedName4);
        tablets_input = findViewById(R.id.txtTablets4);
        times_input = findViewById(R.id.txtTimesDaily4);
        food_input = findViewById(R.id.txtFood4);

        floatingActionButton = findViewById(R.id.saveBtn4);

        //we call this
        getAndSetIntentData();



        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyDatabaseHelper myDatabaseHelper = new MyDatabaseHelper(UpdateActivity.this);
                name = name_input.getText().toString().trim();
                tablets = tablets_input.getText().toString().trim();
                times = times_input.getText().toString().trim();
                food = food_input.getText().toString().trim();
                myDatabaseHelper.updateData(id, name, tablets, times, food);

            }
        });

    }

    void getAndSetIntentData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("name") && getIntent().hasExtra("tablets")
                && getIntent().hasExtra("times") && getIntent().hasExtra("food")){
            //Getting Data from intent
            id = getIntent().getStringExtra("id");
            name = getIntent().getStringExtra("name");
            tablets = getIntent().getStringExtra("tablets");
            times = getIntent().getStringExtra("times");
            food = getIntent().getStringExtra("food");

            //Setting intent data
            name_input.setText(name);
            tablets_input.setText(tablets);
            times_input.setText(times);
            food_input.setText(food);
        }else {
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }
}