package com.example.bottomnavigation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    TextInputLayout regName, regUsername, regEmail, regPassword, regAge, regGender, regChildrenName;
    Button regBtn, regToLoginBtn;

    FirebaseDatabase rootNode;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Hooks
        regName = findViewById(R.id.fullName_reg);
        regUsername = findViewById(R.id.username_reg);
        regEmail = findViewById(R.id.email_reg);
        regPassword = findViewById(R.id.password_reg);
        regAge = findViewById(R.id.age_reg);
        regGender = findViewById(R.id.gender_reg);
        regChildrenName = findViewById(R.id.childrenName_reg);

        regBtn = findViewById(R.id.registerbtn);
        regToLoginBtn = findViewById(R.id.backloginbtn);

        //Save Data
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }
}