package com.example.bottomnavigation.Family;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.bottomnavigation.Login;
import com.example.bottomnavigation.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register2 extends AppCompatActivity {

    TextInputLayout regName, regUsername, regEmail, regPassword, regAge, regGender, regChildrenName;
    Button regBtn, regToLoginBtn;

    FirebaseDatabase rootNode;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        //Hooks
        regName = findViewById(R.id.fullName_reg2);
        regUsername = findViewById(R.id.username_reg2);
        regEmail = findViewById(R.id.email_reg2);
        regPassword = findViewById(R.id.password_reg2);
        regAge = findViewById(R.id.age_reg2);
        regGender = findViewById(R.id.gender_reg2);
        regChildrenName = findViewById(R.id.childrenName_reg2);

        regBtn = findViewById(R.id.registerbtn2);
        regToLoginBtn = findViewById(R.id.backloginbtn2);

        //Back To Login
        regToLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register2.this, Login.class));
            }
        });


        //Save Data
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validateName() | !validateUsername() | !validateEmail() | !validateAge() |
                        !validatePassword() | !validateGender() | !validateChildrenName()){
                    return;
                }

                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("Users2");

                //Get all the values
                String name = regName.getEditText().getText().toString();
                String username = regUsername.getEditText().getText().toString();
                String email = regEmail.getEditText().getText().toString();
                String password = regPassword.getEditText().getText().toString();
                String age = regAge.getEditText().getText().toString();
                String gender = regGender.getEditText().getText().toString();
                String childrenName = regChildrenName.getEditText().getText().toString();

                UserModel2 userModel2 = new UserModel2(name, username, email, password, age, gender, childrenName);

                reference.child(username).setValue(userModel2);

                startActivity(new Intent(Register2.this,Login.class));
                Toast.makeText(Register2.this, "Successfully Registered!", Toast.LENGTH_LONG)
                        .show();

            }
        });


    }

    private Boolean validateName(){
        String val = regName.getEditText().getText().toString();

        if (val.isEmpty()){
            regName.setError("Field cannot be empty");
            return false;
        }
        else{
            regName.setError(null);
            regName.setErrorEnabled(false);
            return true;
        }

    }

    private Boolean validateUsername(){
        String val = regUsername.getEditText().getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";

        if (val.isEmpty()){
            regUsername.setError("Field cannot be empty");
            return false;
        } else if (val.length()>=15){
            regUsername.setError("Username too long");
            return false;
        }
        else if (!val.matches(noWhiteSpace)){
            regUsername.setError("White Space are not allowed");
            return false;
        }
        else{
            regUsername.setError(null);
            regUsername.setErrorEnabled(false);
            return true;
        }

    }

    private Boolean validateEmail(){
        String val = regEmail.getEditText().getText().toString();

        if (val.isEmpty()){
            regEmail.setError("Field cannot be empty");
            return false;
        }
        else{
            regEmail.setError(null);
            return true;
        }

    }

    private Boolean validatePassword(){
        String val = regPassword.getEditText().getText().toString();

        if (val.isEmpty()){
            regPassword.setError("Field cannot be empty");
            return false;
        }
        else{
            regPassword.setError(null);
            regPassword.setErrorEnabled(false);
            return true;
        }

    }

    private Boolean validateAge(){
        String val = regAge.getEditText().getText().toString();

        if (val.isEmpty()){
            regAge.setError("Field cannot be empty");
            return false;
        }
        else{
            regAge.setError(null);
            regAge.setErrorEnabled(false);
            return true;
        }

    }

    private Boolean validateGender(){
        String val = regGender.getEditText().getText().toString();

        if (val.isEmpty()){
            regGender.setError("Field cannot be empty");
            return false;
        }
        else{
            regGender.setError(null);
            regGender.setErrorEnabled(false);
            return true;
        }

    }

    private Boolean validateChildrenName(){
        String val = regChildrenName.getEditText().getText().toString();

        if (val.isEmpty()){
            regChildrenName.setError("Field cannot be empty");
            return false;
        }
        else{
            regChildrenName.setError(null);
            regChildrenName.setErrorEnabled(false);
            return true;
        }

    }
}