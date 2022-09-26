package com.example.bottomnavigation.Fragment;

import android.content.Intent;
import android.net.Uri;
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

public class HelpFragment extends Fragment {

    ImageView helpCall, helpMail,backBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.fragment_help,container,false);

        helpCall = root.findViewById(R.id.helpCall);
        helpMail = root.findViewById(R.id.helpMail);
        backBtn = root.findViewById(R.id.backBtn4);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment callFrag = new SettingsFragment();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();

                fm.replace(R.id.fragment_container,callFrag).commit();
            }
        });


        helpCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:+6012345678"));
                startActivity(intent);
            }
        });

        helpMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                String UriText = "mailto:" + Uri.encode("eldercare710@gmail.com") + "?subject=" +
                        Uri.encode("Feedback") + Uri.encode("");
                Uri uri = Uri.parse(UriText);
                intent.setData(uri);
                startActivity(Intent.createChooser(intent,"send email"));
            }
        });


        return root;
    }
}
