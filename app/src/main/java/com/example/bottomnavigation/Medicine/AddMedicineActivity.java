package com.example.bottomnavigation.Medicine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bottomnavigation.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddMedicineActivity extends AppCompatActivity {

    EditText medName, tablets, food, picUrl, timesDaily;
    Button btnAdd, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine);

        medName = (EditText) findViewById(R.id.txtmedName);
        tablets = (EditText) findViewById(R.id.txtTablets);
        food = (EditText) findViewById(R.id.txtFood);
        picUrl = (EditText) findViewById(R.id.txtPicUrl);
        timesDaily = (EditText) findViewById(R.id.txtTimesDaily);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnBack = (Button) findViewById(R.id.btnBack);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                insertData();
                clearAll();

            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void insertData(){

        Map<String,Object> map = new HashMap<>();
        map.put("medName", medName.getText().toString());
        map.put("tablets", tablets.getText().toString());
        map.put("food", food.getText().toString());
        map.put("timesDaily", timesDaily.getText().toString());
        map.put("picUrl", picUrl.getText().toString());

        FirebaseDatabase.getInstance().getReference().child("Medicine").push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(AddMedicineActivity.this, "Data Inserted Successfully!", Toast.LENGTH_LONG)
                                .show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddMedicineActivity.this, "Something Wrong!", Toast.LENGTH_LONG)
                                .show();
                    }
                });

    }

    private void clearAll(){
        medName.setText("");
        tablets.setText("");
        food.setText("");
        timesDaily.setText("");
        picUrl.setText("");
    }
}