package com.example.bottomnavigation.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.bottomnavigation.R;

public class AboutFragment extends Fragment {

    ImageView backBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_about, container, false);

        backBtn = root.findViewById(R.id.backBtn5);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment callFrag = new SettingsFragment();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();

                fm.replace(R.id.fragment_container,callFrag).commit();
            }
        });

        return root;
    }
}
