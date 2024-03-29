package com.example.bottomnavigation.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bottomnavigation.Model.NewUserModel;
import com.example.bottomnavigation.ProgressbarLoader;
import com.example.bottomnavigation.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class NewRegister extends AppCompatActivity {

    EditText fname, lname, eemail, ppassword;
    Button signupbtn;
    TextView signtolog, signanim;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    ProgressbarLoader loader;
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_register);

        fname = findViewById(R.id.edittext_firstname);
        lname = findViewById(R.id.edittext_lastname);
        eemail = findViewById(R.id.edittext_signemail);
        ppassword = findViewById(R.id.edittext_signpassword);
        signupbtn = findViewById(R.id.signup_button);
        signtolog = findViewById(R.id.signtolog_txt);
        signanim = findViewById(R.id.faded_signup_text);

        //set animation
        animation = AnimationUtils.loadAnimation(NewRegister.this, R.anim.fade_anim);
        animation.setDuration(1000);
        signanim.setAnimation(animation);

        loader = new ProgressbarLoader(NewRegister.this);
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        firebaseAuth = FirebaseAuth.getInstance();

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signuplistner();
            }
        });

        signtolog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signintologinlistener();
            }
        });
    }
    private void signintologinlistener() {

        startActivity(new Intent(NewRegister.this, NewLogin.class));
        finish();
    }

    private void signuplistner() {

        loader.showloader();
        try {
            final String first_name = fname.getText().toString().trim();
            final String last_name = lname.getText().toString().trim();
            final String email = eemail.getText().toString().trim();
            final String password = ppassword.getText().toString().trim();

            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
            final String strdate = dateFormat.format(date);

            if (TextUtils.isEmpty(first_name) || TextUtils.isEmpty(last_name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(NewRegister.this, "Fill the all field", Toast.LENGTH_SHORT).show();
                loader.dismissloader();
            } else {
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    String userid = firebaseAuth.getCurrentUser().getUid();
                                    NewUserModel info = new NewUserModel(userid, first_name, last_name, generatecode(), email, password, strdate, 0, 0);
                                    FirebaseDatabase.getInstance().getReference("users")
                                            .child(userid)
                                            .setValue(info).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    Toast.makeText(NewRegister.this, "Submitted..", Toast.LENGTH_SHORT).show();
                                                    loader.dismissloader();
                                                    Intent in = new Intent(NewRegister.this, NewLogin.class);
                                                    startActivity(in);
                                                }
                                            });

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(NewRegister.this, "Database response error", Toast.LENGTH_SHORT).show();
                                    loader.dismissloader();
                                }
                            }
                        });
            }
        } catch (NullPointerException e) {
            Toast.makeText(NewRegister.this, "Error:" + e, Toast.LENGTH_SHORT).show();
        }
    }

    private String generatecode() {
        Random r = new Random();
        int intcode = 100000 + r.nextInt(900000);
        String code = String.valueOf(intcode);
        return code;
    }
}