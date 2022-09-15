package com.example.bottomnavigation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.bottomnavigation.Family.Login2;

public class SelectUser extends AppCompatActivity {

    Button elderlyBtn, familyBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_user);

        elderlyBtn = findViewById(R.id.elderly);
        familyBtn = findViewById(R.id.family);

        elderlyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectUser.this, NewLogin.class));
                Toast.makeText(SelectUser.this, "Welcome", Toast.LENGTH_SHORT).show();
            }
        });

        familyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectUser.this, NewLogin.class));
                Toast.makeText(SelectUser.this, "Welcome", Toast.LENGTH_SHORT).show();
            }
        });


    }
}