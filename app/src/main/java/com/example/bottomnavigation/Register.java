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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    private FirebaseAuth mAuth;
    TextView banner;
    EditText editTextEmail, editTextUsername, editTextPassword, editTextAge, editTextGender
            , editTextChildren;
    Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        banner = findViewById(R.id.banner);
        editTextEmail = findViewById(R.id.email_reg);
        editTextUsername = findViewById(R.id.username_reg);
        editTextPassword = findViewById(R.id.password_reg);
        editTextAge = findViewById(R.id.age_reg);
        editTextGender = findViewById(R.id.gender_reg);
        editTextChildren = findViewById(R.id.children_reg);


        banner = findViewById(R.id.banner);

        banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this, Login.class));
            }
        });

        registerBtn = findViewById(R.id.registerconfirmbtn);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.banner:
                        startActivity(new Intent(Register.this, Login.class));
                        break;
                    case R.id.registerconfirmbtn:
                        registerUser();
                        startActivity(new Intent(Register.this, Login.class));
                        break;
                }
            }


            private void registerUser() {

                String email= editTextEmail.getText().toString().trim();
                String username= editTextUsername.getText().toString().trim();
                String password= editTextPassword.getText().toString().trim();
                String age= editTextAge.getText().toString().trim();
                String gender= editTextGender.getText().toString().trim();
                String children= editTextChildren.getText().toString().trim();

                if(email.isEmpty()){
                    editTextEmail.setError("Email Address is required!");
                    editTextEmail.requestFocus();
                    return;
                }

                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    editTextEmail.setError("Please provide valid email!");
                    editTextEmail.requestFocus();
                    return;
                }

                if(username.isEmpty()){
                    editTextUsername.setError("Username is required!");
                    editTextUsername.requestFocus();
                    return;
                }

                if(password.isEmpty()){
                    editTextPassword.setError("Password is required!");
                    editTextPassword.requestFocus();
                    return;
                }

                if(password.length() < 6){
                    editTextPassword.setError("Min password length should be 6 characters!");
                    editTextPassword.requestFocus();
                    return;
                }

                if(age.isEmpty()){
                    editTextAge.setError("Age is required!");
                    editTextAge.requestFocus();
                    return;
                }

                if(gender.isEmpty()){
                    editTextGender.setError("Gender is required!");
                    editTextGender.requestFocus();
                    return;
                }

                if(children.isEmpty()){
                    editTextChildren.setError("Children name is required!");
                    editTextChildren.requestFocus();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    UserModel userModel = new UserModel(email,username,age, gender, children);

                                    FirebaseDatabase.getInstance().getReference("Users")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(userModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()){
                                                        Toast.makeText(Register.this, "Register Successfully!", Toast.LENGTH_LONG).show();
                                                    }else {
                                                        Toast.makeText(Register.this, "Failed! Try Again!", Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                            });


                                }else{
                                    Toast.makeText(Register.this, "Failed! Try Again!", Toast.LENGTH_LONG).show();
                                }
                            }
                        });


            }




        });
    }
}