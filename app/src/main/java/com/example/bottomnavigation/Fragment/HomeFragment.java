package com.example.bottomnavigation.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.bottomnavigation.Family.Login2;
import com.example.bottomnavigation.Login;
import com.example.bottomnavigation.MainActivity;
import com.example.bottomnavigation.MainActivity2;
import com.example.bottomnavigation.ProfileActivity;
import com.example.bottomnavigation.R;
import com.example.bottomnavigation.SettingsActivity;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class HomeFragment extends Fragment implements View.OnClickListener {

    CardView checkCard, locationCard, medicineCard, emergencyCard;
    ImageView profile,settings;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        //Define Cards
        checkCard = (CardView) root.findViewById(R.id.check_card);
        locationCard = (CardView) root.findViewById(R.id.location_card);
        medicineCard = (CardView) root.findViewById(R.id.medicine_card);
        emergencyCard = (CardView) root.findViewById(R.id.emergency_card);

        //Add click listener to the cards
        checkCard.setOnClickListener(this);
        locationCard.setOnClickListener(this);
        medicineCard.setOnClickListener(this);
        emergencyCard.setOnClickListener(this);


        profile = root.findViewById(R.id.userProfile);
        settings = root.findViewById(R.id.settings);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ProfileActivity.class));
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SettingsActivity.class));
            }
        });


        return root;
    }



    @Override
    public void onClick(View v) {
        Fragment selectedFragment = null;

        switch (v.getId()) {
            case R.id.check_card:
                selectedFragment = new CheckFragment();
                break;
            case R.id.location_card:
                selectedFragment = new LocationFragment();
                break;
            case R.id.medicine_card:
                _card:
                selectedFragment = new MedicineFragment();
                break;
            case R.id.emergency_card:
                _card:
                selectedFragment = new CallFragment();
            default:
                break;
        }
    }
}

