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

import com.example.bottomnavigation.ForgotPassword;
import com.example.bottomnavigation.ProgressbarLoader;
import com.example.bottomnavigation.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class NewLogin extends AppCompatActivity {

    EditText logemail, logpass;
    Button loginbtn;
    TextView txtsign, slide_logtxt, txtforgot;
    FirebaseAuth firebaseAuth;
    ProgressbarLoader loader;
    FirebaseUser firebaseUser;
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_login);
        logemail = findViewById(R.id.edittext_email);
        logpass = findViewById(R.id.edittext_password);
        loginbtn = findViewById(R.id.login_button);
        txtsign = findViewById(R.id.logtosign);
        //txtforgot = findViewById(R.id.forgotp);
        slide_logtxt = findViewById(R.id.slide_login_text);

        //set animation
        animation = AnimationUtils.loadAnimation(NewLogin.this, R.anim.fade_anim);
        animation.setDuration(1000);
        slide_logtxt.setAnimation(animation);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        //initilize progressbar
        loader = new ProgressbarLoader(NewLogin.this);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loader.showloader();
                loginlistner();
            }
        });

        txtsign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtsignuplistener();
            }
        });

        //txtforgot.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {
       //         txtforgotlistener();
        //    }
       // });

    }

    //private void txtforgotlistener(){
       // Intent intent = new Intent(NewLogin.this, ForgotPassword.class);
       // startActivity(intent);
   // }

    private void txtsignuplistener() {
        Intent intent = new Intent(NewLogin.this, NewRegister.class);
        startActivity(intent);
    }

    private void loginlistner() {

        String email = logemail.getText().toString().trim();
        String password = logpass.getText().toString().trim();
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(NewLogin.this, "Fill all fields", Toast.LENGTH_SHORT).show();
            loader.dismissloader();
        } else {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                loader.dismissloader();
                                Toast.makeText(NewLogin.this, "Success.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(NewLogin.this, showcirclecode.class);
                                startActivity(intent);
                            } else {
                                loader.dismissloader();
                                Toast.makeText(NewLogin.this, "Wrong Credentials.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    @Override
    protected void onStart() {
        if (firebaseUser != null){
            startActivity(new Intent(NewLogin.this, MainActivity.class));
            finish();
        }
        super.onStart();
    }
}