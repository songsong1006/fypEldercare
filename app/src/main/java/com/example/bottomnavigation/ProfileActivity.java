package com.example.bottomnavigation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

    String userID;

    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        logout = findViewById(R.id.logoutbtn);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ProfileActivity.this, Login.class));
            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        final TextView emailTextView = findViewById(R.id.emailAddress);
        final TextView usernameTextView = findViewById(R.id.userName);
        final TextView ageTextView = findViewById(R.id.age);
        final TextView genderTextView = findViewById(R.id.gender);
        final TextView childrenTextView = findViewById(R.id.childrenName);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserModel userProfile = snapshot.getValue(UserModel.class);

                if(userProfile != null){
                    String emailAddress = userProfile.email;
                    String userName = userProfile.username;
                    String age = userProfile.age;
                    String gender = userProfile.gender;
                    String childrenName = userProfile.children;

                    emailTextView.setText(emailAddress);
                    usernameTextView.setText(userName);
                    ageTextView.setText(age);
                    genderTextView.setText(gender);
                    childrenTextView.setText(childrenName);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileActivity.this, "Something wrong happened!", Toast.LENGTH_LONG).show();

            }
        });
    }
}