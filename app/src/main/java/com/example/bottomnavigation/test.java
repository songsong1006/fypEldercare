package com.example.bottomnavigation;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowId;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bottomnavigation.Fragment.AboutFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class test extends Fragment {
    Button reminder;

    ImageView refresh;

    FloatingActionButton addRecordBtn;

    RecyclerView recyclerView;

    MyDatabaseHelper myDatabaseHelper;
    ArrayList<String> medicine_id, medicine_name, medicine_tablets, medicine_times, medicine_food;

    CustomAdapter customAdapter;

    ImageView empty_imageview;
    TextView no_data;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_test, container, false);

        reminder = root.findViewById(R.id.reminder);
        addRecordBtn = root.findViewById(R.id.add_record);
        recyclerView = root.findViewById(R.id.medicine_recycle);
        empty_imageview = root.findViewById(R.id.imageView2);
        no_data = root.findViewById(R.id.textView5);
        refresh = root.findViewById(R.id.reminder2);

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment callFrag = new test();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();

                fm.replace(R.id.fragment_container,callFrag).commit();
                Toast.makeText(getContext(), "Refreshed", Toast.LENGTH_SHORT).show();
            }
        });


        reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AlarmActivity.class);
                startActivity(intent);
            }
        });

        addRecordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddRecord2Activity.class);
                startActivity(intent);

            }
        });

        myDatabaseHelper = new MyDatabaseHelper(getActivity());
        medicine_id = new ArrayList<>();
        medicine_name = new ArrayList<>();
        medicine_tablets = new ArrayList<>();
        medicine_times = new ArrayList<>();
        medicine_food = new ArrayList<>();


        storeDataInArrays();

        customAdapter = new CustomAdapter(getActivity(),getContext(), medicine_id, medicine_name,
                        medicine_tablets, medicine_times, medicine_food);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            getParentFragmentManager()
                    .beginTransaction()
                    .detach(this)
                    .attach(this)
                    .addToBackStack(null)
                    .commit();
        }
    }

    void storeDataInArrays(){
        Cursor cursor = myDatabaseHelper.readAllData();
        if(cursor.getCount() == 0){
            empty_imageview.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.VISIBLE);
            Toast.makeText(getActivity(), "No data.", Toast.LENGTH_SHORT).show();
        }else{
            while(cursor.moveToNext()){
                medicine_id.add(cursor.getString(0));
                medicine_name.add(cursor.getString(1));
                medicine_tablets.add(cursor.getString(2));
                medicine_times.add(cursor.getString(3));
                medicine_food.add(cursor.getString(4));
                empty_imageview.setVisibility(View.GONE);
                no_data.setVisibility(View.GONE);
            }
        }
    }


}
