package com.example.bottomnavigation.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bottomnavigation.Activity.AboutActivity;
import com.example.bottomnavigation.R;

public class HelpFragment extends Fragment {

    ImageView helpCall, helpMail;
    Button aboutUs;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.fragment_help,container,false);

        aboutUs = root.findViewById(R.id.aboutUs);
        helpCall = root.findViewById(R.id.helpCall);
        helpMail = root.findViewById(R.id.helpMail);

        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AboutActivity.class));
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
