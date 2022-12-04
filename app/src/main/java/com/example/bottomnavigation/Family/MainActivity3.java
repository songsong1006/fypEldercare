package com.example.bottomnavigation.Family;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.bottomnavigation.Fragment.CallFragment2;
import com.example.bottomnavigation.Fragment.LocationFragment;
import com.example.bottomnavigation.Fragment.MedicineFragment;
import com.example.bottomnavigation.Fragment.HomeFragment2;
import com.example.bottomnavigation.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation2);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container2,
                new HomeFragment2()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener(){
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()){
                        case R.id.nav_home2:
                            selectedFragment = new HomeFragment2();
                            break;
                        //case R.id.nav_location2:
                           // selectedFragment = new LocationFragment();
                          //  break;
                        case R.id.nav_medicine2:
                            selectedFragment = new MedicineFragment();
                            break;
                        case R.id.nav_call2:
                            selectedFragment = new CallFragment2();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container2,
                            selectedFragment).commit();

                    return true;
                }
            };
}