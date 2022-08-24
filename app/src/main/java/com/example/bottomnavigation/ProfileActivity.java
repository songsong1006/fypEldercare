package com.example.bottomnavigation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    FirebaseUser user;
    DatabaseReference reference;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    String userID;

    TextInputLayout username,email,age,gender,childrenName;

    Button logout,update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        //Hooks
        username = findViewById(R.id.username_profile);
        email = findViewById(R.id.email_profile);
        age = findViewById(R.id.age_profile);
        gender = findViewById(R.id.gender_profile);
        childrenName = findViewById(R.id.childrenName_profile);

        //ShowAllData
        showAllUserData();
        
        

        logout = findViewById(R.id.logoutbtn);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ProfileActivity.this, Login.class));
            }
        });

        update = findViewById(R.id.update);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this,UpdateProfileActivity.class));
            }
        });


    }

    private void showAllUserData() {

        Intent intent = getIntent();
        String user_username = intent.getStringExtra("username");
        String user_email = intent.getStringExtra("email");
        String user_age = intent.getStringExtra("age");
        String user_gender = intent.getStringExtra("gender");
        String user_childrenName = intent.getStringExtra("children");

        username.getEditText().setText(user_username);
        email.getEditText().setText(user_email);
        age.getEditText().setText(user_age);
        gender.getEditText().setText(user_gender);
        childrenName.getEditText().setText(user_childrenName);


    }

}