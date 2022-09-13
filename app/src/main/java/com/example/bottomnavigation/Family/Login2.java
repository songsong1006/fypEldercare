package com.example.bottomnavigation.Family;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.bottomnavigation.ForgotPassword;
import com.example.bottomnavigation.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login2 extends AppCompatActivity {

    Button register,login;
    TextView logoText,forgotPass;
    TextInputLayout username,password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2); //Hooks
        register = findViewById(R.id.registerbtn2);
        login = findViewById(R.id.loginbtn2);
        logoText = findViewById(R.id.login_name2);
        username = findViewById(R.id.username_login2);
        password = findViewById(R.id.password_login2);

        forgotPass = findViewById(R.id.forgotpass2);

        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login2.this, ForgotPassword.class));
            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login2.this, Register2.class);

                Pair[] pairs = new Pair[5];

                pairs[0] = new Pair<View,String>(logoText,"logo_text");
                pairs[1] = new Pair<View,String>(username,"email_tran");
                pairs[2] = new Pair<View,String>(password,"password_tran");
                pairs[3] = new Pair<View,String>(register,"buttonRegister_tran");
                pairs[4] = new Pair<View,String>(login,"buttonLogin_tran");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Login2.this,pairs);
                startActivity(intent,options.toBundle());

            }
        });

    }

    private Boolean validateUsername(){
        String val = username.getEditText().getText().toString();

        if (val.isEmpty()){
            username.setError("Field cannot be empty");
            return false;
        }
        else{
            username.setError(null);
            username.setErrorEnabled(false);
            return true;
        }

    }

    private Boolean validatePassword(){
        String val = password.getEditText().getText().toString();

        if (val.isEmpty()){
            password.setError("Field cannot be empty");
            return false;
        }
        else{
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }

    }

    public void loginUser(View view){
        if(!validateUsername() | !validatePassword()){
            return;
        }
        else {
            isUser();
        }
    }

    private void isUser() {

        String userEnteredUsername = username.getEditText().getText().toString().trim();
        String userEnteredPassword = password.getEditText().getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users2");

        Query checkUser = reference.orderByChild("username").equalTo(userEnteredUsername);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){

                    username.setError(null);
                    username.setErrorEnabled(false);

                    String passwordFromDB = snapshot.child(userEnteredUsername).child("password")
                            .getValue(String.class);

                    if (passwordFromDB.equals(userEnteredPassword)){

                        String nameFromDB = snapshot.child(userEnteredUsername).child("name")
                                .getValue(String.class);
                        String usernameFromDB = snapshot.child(userEnteredUsername).child("username")
                                .getValue(String.class);
                        String genderFromDB = snapshot.child(userEnteredUsername).child("gender")
                                .getValue(String.class);
                        String ageFromDB = snapshot.child(userEnteredUsername).child("age")
                                .getValue(String.class);
                        String childrenNameFromDB = snapshot.child(userEnteredUsername).child("childrenName")
                                .getValue(String.class);
                        String emailFromDB = snapshot.child(userEnteredUsername).child("email")
                                .getValue(String.class);

                        Intent intent = new Intent(getApplicationContext(), MainActivity3.class);

                        intent.putExtra("name",nameFromDB);
                        intent.putExtra("username",usernameFromDB);
                        intent.putExtra("gender",genderFromDB);
                        intent.putExtra("age",ageFromDB);
                        intent.putExtra("childrenName",childrenNameFromDB);
                        intent.putExtra("email",emailFromDB);
                        intent.putExtra("password",passwordFromDB);

                        startActivity(intent);


                    }
                    else {
                        password.setError("Wrong Password");
                        password.requestFocus();
                    }

                }else{
                    username.setError("No such User exist");
                    username.requestFocus();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}