package com.example.bottomnavigation.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.bottomnavigation.Model.CircleJoin;
import com.example.bottomnavigation.ProgressbarLoader;
import com.example.bottomnavigation.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class joincirclefragment extends Fragment {

    EditText joinedit;
    Button joinbtn;
    ImageView backBtn;
    DatabaseReference reference, circlerefernce;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    ProgressbarLoader loader;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.join_circle_fragment, container, false);

        joinedit = view.findViewById(R.id.join_edittext);
        joinbtn = view.findViewById(R.id.join_button);
        backBtn = view.findViewById(R.id.backBtn2);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment callFrag = new SettingsFragment();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();

                fm.replace(R.id.fragment_container,callFrag).commit();
            }
        });

        loader = new ProgressbarLoader(getActivity());
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        try {
            //String currentuid = firebaseUser.getUid();
            reference = FirebaseDatabase.getInstance().getReference("users");
            //currentuser = FirebaseDatabase.getInstance().getReference("users").child(currentuid);
        } catch (NullPointerException e) {
            Toast.makeText(getActivity(), "error:" + e, Toast.LENGTH_SHORT).show();
        }

        joinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                joinbtnlistener();
            }
        });

        return view;
    }

    private void joinbtnlistener() {
        loader.showloader();
        String no = joinedit.getText().toString().trim();
        Query query = reference.orderByChild("circle_id").equalTo(no);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    //users userc;
                    for (DataSnapshot dss : dataSnapshot.getChildren()) {
                            /*userc = dss.getValue(users.class);
                            joinuid = userc.getid();*/

                        String joinid = dss.child("id").getValue(String.class);

                        circlerefernce = FirebaseDatabase.getInstance().getReference().child("users").child(joinid).child("circle_members");
                        //Toast.makeText(getActivity(), "response:"+joinid, Toast.LENGTH_SHORT).show();

                        CircleJoin join = new CircleJoin(firebaseUser.getUid());
                        //circlejoin join1 = new circlejoin(joinuid);

                        circlerefernce.child(firebaseUser.getUid()).setValue(join)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(getActivity(), "Joined Successfully", Toast.LENGTH_SHORT).show();
                                            loader.dismissloader();
                                        }
                                    }
                                });
                    }
                } else {
                    Toast.makeText(getActivity(), "This code is not available", Toast.LENGTH_SHORT).show();
                    loader.dismissloader();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
