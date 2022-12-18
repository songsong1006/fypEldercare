package com.example.bottomnavigation.Fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bottomnavigation.AddRecord2Activity;
import com.example.bottomnavigation.Model.membersadepter;
import com.example.bottomnavigation.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class mycirclefragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    //users cuser;
    ArrayList<String> namelist, idlist;
    AlertDialog.Builder builder;

    DatabaseReference databaseReference, currentreference;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    Button circleDelete;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_circle_fragment, container, false);

        recyclerView = view.findViewById(R.id.view_recyclerview);
        circleDelete = view.findViewById(R.id.circle_delete);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        String useruid = firebaseUser.getUid();

        namelist = new ArrayList<>();
        idlist = new ArrayList<>();

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        currentreference = FirebaseDatabase.getInstance().getReference("users").child(useruid).child("circle_members");

        currentreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                namelist.clear();
                idlist.clear();

                if (dataSnapshot.exists())
                {
                    for (DataSnapshot dss: dataSnapshot.getChildren())
                    {
                        String circleid = dss.child("circlememberid").getValue(String.class);

                        databaseReference.child(circleid).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                //users cuser = dataSnapshot.getValue(users.class);
                                String fatchname = dataSnapshot.child("firstname").getValue(String.class);
                                String fatchid = dataSnapshot.child("id").getValue(String.class);
                                namelist.add(fatchname);
                                idlist.add(fatchid);
                                adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        adapter = new membersadepter(namelist, idlist, getActivity());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        circleDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Are you sure want to delete all?").setTitle("Cautious");
                builder.setMessage("Are you sure want to delete all?")
                        .setCancelable(false)
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                currentreference.removeValue();
                                Toast.makeText(getContext(), "Cleared All.", Toast.LENGTH_SHORT).show();
                                Fragment callFrag = new mycirclefragment();
                                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();

                                fm.replace(R.id.fragment_container,callFrag).commit();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.setTitle("Cautious");
                alert.show();

            }
        });

        return view;
    }
}
