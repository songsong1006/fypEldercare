package com.example.bottomnavigation.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.bottomnavigation.R;

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

