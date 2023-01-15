package com.example.bottomnavigation.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.bottomnavigation.R;

public class SelectUser extends AppCompatActivity {

    Button elderlyBtn, familyBtn;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_user);

        elderlyBtn = findViewById(R.id.elderly);
        familyBtn = findViewById(R.id.family);

        elderlyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder = new AlertDialog.Builder(SelectUser.this);
                builder.setMessage("Remember to switch off the dark mode of your phone before login.").setTitle("Alert");
                builder.setMessage("Remember to switch off the dark mode of your phone before login." +
                                " As the saying goes, age is just a number!")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(SelectUser.this, NewLogin.class));
                                Toast.makeText(SelectUser.this, "Welcome", Toast.LENGTH_SHORT).show();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.setTitle("Alert");
                alert.show();
            }
        });

        familyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder = new AlertDialog.Builder(SelectUser.this);
                builder.setMessage("Remember to switch off the dark mode of your phone before login.").setTitle("Alert");
                builder.setMessage("Remember to switch off the dark mode of your phone before login." +
                                " As the saying goes, age is just a number!")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(SelectUser.this, NewLogin.class));
                                Toast.makeText(SelectUser.this, "Welcome", Toast.LENGTH_SHORT).show();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.setTitle("Alert");
                alert.show();
            }
        });


    }
}