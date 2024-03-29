package com.example.bottomnavigation.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.bottomnavigation.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class invitecodefragment extends Fragment {

    TextView codetxt;
    Button sharebtn;
    ImageView backBtn;

    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    String code;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.invite_code_fragment, container, false);

        codetxt = view.findViewById(R.id.code_textview);
        sharebtn = view.findViewById(R.id.sharebutton);


        sharebtn = view.findViewById(R.id.sharebutton);
        sharebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharebuttonlistener();
            }
        });

        backBtn = view.findViewById(R.id.backBtn3);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment callFrag = new SettingsFragment();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();

                fm.replace(R.id.fragment_container,callFrag).commit();
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        String currentuid = firebaseUser.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("users").child(currentuid);

        displaycodelistener();

        return view;
    }

    private void sharebuttonlistener() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String sharebody = "Your ElderCare Circle Code: "+code;
        String sharesub = "gps helper invitation code";
        intent.putExtra(Intent.EXTRA_SUBJECT,sharesub);
        intent.putExtra(Intent.EXTRA_TEXT,sharebody);
        startActivity(Intent.createChooser(intent, "Sharing Code"));
    }

    private void displaycodelistener() {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                code = dataSnapshot.child("circle_id").getValue(String.class);

                codetxt.setText(code);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
