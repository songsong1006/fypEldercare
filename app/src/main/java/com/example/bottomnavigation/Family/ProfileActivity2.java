package com.example.bottomnavigation.Family;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bottomnavigation.MainActivity2;
import com.example.bottomnavigation.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity2 extends AppCompatActivity {

    TextInputLayout fullName, email, name, age, gender, childrenName;
    TextView fullNameLabel, usernameLabel;

    Button back;

    //Global variable
    String _username, _name, _email, _age, _gender, _childrenName;

    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile2);
        reference = FirebaseDatabase.getInstance().getReference("Users2");

        //Hooks
        fullName = findViewById(R.id.fullname_profile2);
        email = findViewById(R.id.email_profile2);
        name = findViewById(R.id.username_profile2);
        age = findViewById(R.id.age_profile2);
        gender = findViewById(R.id.gender_profile2);
        childrenName = findViewById(R.id.childrenName_profile2);

        fullNameLabel = findViewById(R.id.full_nameLabel2);
        usernameLabel = findViewById(R.id.username_label2);

        back = findViewById(R.id.back_home2);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity2.this, MainActivity2.class));
            }
        });

        //ShowAllData
        showAllUserData();




    }

    private void showAllUserData() {

        Intent intent = getIntent();
        _username = intent.getStringExtra("username");
        _name = intent.getStringExtra("name");
        _email = intent.getStringExtra("email");
        _age = intent.getStringExtra("age");
        _gender = intent.getStringExtra("gender");
        _childrenName = intent.getStringExtra("childrenName");

        fullNameLabel.setText(_name);
        usernameLabel.setText(_username);

        fullName.getEditText().setText(_name);
        name.getEditText().setText(_username);
        email.getEditText().setText(_email);
        age.getEditText().setText(_age);
        gender.getEditText().setText(_gender);
        childrenName.getEditText().setText(_childrenName);

    }

    public void update2(View view){

        if(isNameChanged() | isAgeChanged()){
            Toast.makeText(this, "Data has been updated", Toast.LENGTH_LONG).show();

        }
        else{
            Toast.makeText(this, "Nothing updated", Toast.LENGTH_LONG).show();
        }



    }

    private boolean isAgeChanged(){

        if (!_age.equals(age.getEditText().getText().toString())){

            reference.child(_username).child("age").setValue(age.getEditText().getText()
                    .toString());
            _age = age.getEditText().getText().toString();
            return true;
        }
        else{
            return false;
        }


    }

    private boolean isNameChanged() {

        if (!_name.equals(fullName.getEditText().getText().toString())){

            reference.child(_username).child("name").setValue(fullName.getEditText().getText()
                    .toString());
            _name = fullName.getEditText().getText().toString();
            return true;
        }
        else{
            return false;
        }

    }
}