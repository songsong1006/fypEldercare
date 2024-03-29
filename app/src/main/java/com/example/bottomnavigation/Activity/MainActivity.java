package com.example.bottomnavigation.Activity;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.bottomnavigation.Fragment.CallFragment;
import com.example.bottomnavigation.Fragment.CheckFragment;
import com.example.bottomnavigation.Fragment.HomeFragment;
import com.example.bottomnavigation.Fragment.LocationFragment;
import com.example.bottomnavigation.Fragment.MedicineFragment;
import com.example.bottomnavigation.Model.NewUserModel;
import com.example.bottomnavigation.NotificationC;
import com.example.bottomnavigation.R;
import com.example.bottomnavigation.Fragment.mycirclefragment;
import com.example.bottomnavigation.reminder.ReminderAlarmService;
import com.example.bottomnavigation.test;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    GoogleMap mMap;
    LocationRequest locationRequest;
    FusedLocationProviderClient fusedLocationProviderClient;
    LatLng latLng;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    TextView header_name, header_email;
    DatabaseReference databaseReference;
    String current_uid;
    AlertDialog.Builder builder;
    CircleImageView profileImg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawerlayout);
        toolbar = findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);
        navigationView = findViewById(R.id.nev_view);
        navigationView.setNavigationItemSelectedListener(this);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.app_name,R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        current_uid = firebaseUser.getUid();

        callpermissionlistener();
        update_location();
        dynamicheaderlistener();


    }

    private void dynamicheaderlistener() {

        View header = navigationView.getHeaderView(0);

        header_name = header.findViewById(R.id.header_name_text);
        header_email = header.findViewById(R.id.header_email_text);
        profileImg = header.findViewById(R.id.imageView23);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String current_name = dataSnapshot.child(current_uid).child("firstname").getValue(String.class);
                String current_email = dataSnapshot.child(current_uid).child("email").getValue(String.class);

                NewUserModel newUserModel = dataSnapshot.child(current_uid).getValue(NewUserModel.class);
                Glide.with(MainActivity.this).load(newUserModel.getImage_url()).into(profileImg);

                String s1 = "Hi, "+current_name;
                header_name.setText(s1);
                header_email.setText(current_email);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void update_location() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PermissionChecker.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)==PermissionChecker.PERMISSION_GRANTED)
        {
            fusedLocationProviderClient = new FusedLocationProviderClient(this);
            locationRequest = new LocationRequest()
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                    .setInterval(3000) //update interval
                    .setFastestInterval(5000); //fastest interval
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, new LocationCallback(){
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    if (locationResult != null)
                    {
                            mMap.clear();
                            final double lat = locationResult.getLastLocation().getLatitude();
                            final double log = locationResult.getLastLocation().getLongitude();
                            latLng = new LatLng(lat, log);
                            mMap.addMarker(new MarkerOptions().position(latLng).title("Your current location"));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15F));


                        //update latitude and longitude
                        Map<String, Object> update = new HashMap<>();
                        update.put("latitude", lat);
                        update.put("longitude", log);
                        databaseReference.child(current_uid).updateChildren(update).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                            }
                        });

                    }else {
                        Toast.makeText(MainActivity.this,"Location not found", Toast.LENGTH_SHORT).show();
                    }
                }
            },getMainLooper());
        }
        else
        {
            callpermissionlistener();
        }

    }

    private void callpermissionlistener() {
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        String rationale = "Please provide location permission so that you can ...";
        Permissions.Options options = new Permissions.Options()
                .setRationaleDialogTitle("Location permission")
                .setSettingsDialogTitle("Warning");

        Permissions.check(this, permissions, rationale, options, new PermissionHandler() {
            @Override
            public void onGranted() {
                // Obtain the SupportMapFragment and get notified when the map is ready to be used.
                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.fragment_container);
                mapFragment.getMapAsync(MainActivity.this);
                update_location();
            }

            @Override
            public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                callpermissionlistener();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            backpressedwarrning();
        }
        //super.onBackPressed();
    }
    public void backpressedwarrning(){
        builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage(R.string.dialog_message).setTitle(R.string.dialog_title);
        builder.setMessage("Do you want to close this application ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.setTitle("Exit");
        alert.show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.getUiSettings().setZoomGesturesEnabled(true);

        /*LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            //case R.id.nev_location:
            //  getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
              //       new LocationFragment()).commit();
             // break;
            //case R.id.nev_profile:
              //  getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                //        new MedicineFragment()).commit();
                /*getSupportActionBar().setTitle("profile");
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/
                //break;
            //case R.id.nev_checkin:
               // getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
               //         new CheckFragment()).commit();
                /*getSupportActionBar().setTitle("profile");
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/
               // break;
            case R.id.nev_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new HomeFragment()).commit();
                break;
            case R.id.nev_medicine:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new test()).commit();
                break;
            case R.id.nev_mycircle:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new mycirclefragment()).commit();
                break;
            case R.id.nev_call:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new CallFragment()).commit();
                break;
            case R.id.nev_logout:

                firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null)
                {
                    builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage(R.string.dialog_message).setTitle(R.string.dialog_title);
                    builder.setMessage("Do you want to logout the application ?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    firebaseAuth.signOut();
                                    finish();
                                    startActivity(new Intent(MainActivity.this, SelectUser.class));
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.setTitle("Exit");
                    alert.show();
                }
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


}