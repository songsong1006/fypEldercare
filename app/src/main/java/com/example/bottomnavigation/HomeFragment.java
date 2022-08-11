package com.example.bottomnavigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class HomeFragment extends Fragment implements View.OnClickListener {

    CardView checkCard, locationCard, medicineCard, emergencyCard;

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

