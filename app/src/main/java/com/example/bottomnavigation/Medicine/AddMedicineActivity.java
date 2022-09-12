package com.example.bottomnavigation.Medicine;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.load.model.Model;
import com.example.bottomnavigation.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class AddMedicineActivity extends AppCompatActivity {

    EditText medName, tablets, food, picUrl, timesDaily;
    Button btnAdd, btnBack;
    ImageView imageView;

    FirebaseDatabase mDatabase;
    DatabaseReference mRef;
    FirebaseStorage mStorage;

    private static final int Gallery_Code = 2;

    Uri imageUrl = null;

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

        imageView = (ImageView) findViewById(R.id.add_photo);

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

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, Gallery_Code);
            }
        });

        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference().child("Medicine");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==Gallery_Code && resultCode==RESULT_OK){
            imageUrl=data.getData();
            imageView.setImageURI(imageUrl);
        }

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

        if (!(imageUrl!=null)){
            StorageReference filepath = mStorage.getReference().child("imagePost").child(imageUrl.getLastPathSegment());
            filepath.putFile(imageUrl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Task<Uri> downloadUrl = taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            String t = task.getResult().toString();

                            DatabaseReference newPost = mRef.push();

                            newPost.child("image").setValue(task.getResult().toString());
                        }
                    });

                }
            });
        }

    }

    private void clearAll(){
        medName.setText("");
        tablets.setText("");
        food.setText("");
        timesDaily.setText("");
        picUrl.setText("");
    }


}