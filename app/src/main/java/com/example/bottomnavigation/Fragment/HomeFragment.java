package com.example.bottomnavigation.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.bottomnavigation.Activity.MainActivity;
import com.example.bottomnavigation.NewLocation;
import com.example.bottomnavigation.R;
import com.example.bottomnavigation.test;

public class HomeFragment extends Fragment {

    CardView checkCard, locationCard, medicineCard, emergencyCard;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        //Define Cards
        checkCard = (CardView) root.findViewById(R.id.settingsBtn);
        locationCard = (CardView) root.findViewById(R.id.location_card);
        medicineCard = (CardView) root.findViewById(R.id.medicine_card);
        emergencyCard = (CardView) root.findViewById(R.id.emergency_card);

        checkCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment callFrag = new SettingsFragment();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();

                fm.replace(R.id.fragment_container,callFrag).commit();
            }
        });

        emergencyCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment callFrag = new CallFragment();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();

                fm.replace(R.id.fragment_container,callFrag).commit();
            }
        });

        medicineCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment medFrag = new test();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();

                fm.replace(R.id.fragment_container,medFrag).commit();
            }
        });

        locationCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NewLocation.class);
                startActivity(intent);
            }
        });

        //checkCard.setOnClickListener(new View.OnClickListener() {
       //     @Override
       //     public void onClick(View v) {
        //        Fragment checkFrag = new CheckFragment();
        //        FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();

         //       fm.replace(R.id.fragment_container,checkFrag).commit();
        //    }
       // });



        return root;
    }

}

