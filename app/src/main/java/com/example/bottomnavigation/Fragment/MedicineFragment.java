package com.example.bottomnavigation.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bottomnavigation.Medicine.MedicineAdapter;
import com.example.bottomnavigation.Medicine.MedicineModel;
import com.example.bottomnavigation.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Modifier;

public class MedicineFragment extends Fragment {

    RecyclerView recyclerView;

    MedicineAdapter medicineAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_medicine,container,false);


        recyclerView = (RecyclerView) root.findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        FirebaseRecyclerOptions<MedicineModel> options =
                new FirebaseRecyclerOptions.Builder<MedicineModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Medicine"), MedicineModel.class)
                        .build();



        medicineAdapter = new MedicineAdapter(options);

        recyclerView.setAdapter(medicineAdapter);

        setHasOptionsMenu(true);



        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        medicineAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        medicineAdapter.stopListening();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        inflater.inflate(R.menu.search, menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                txtSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                txtSearch(query);
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    private void txtSearch(String str){

        FirebaseRecyclerOptions<MedicineModel> options =
                new FirebaseRecyclerOptions.Builder<MedicineModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Medicine")
                                .orderByChild("medName").startAt(str)
                                .endAt(str+"~"), MedicineModel.class)
                        .build();

        medicineAdapter = new MedicineAdapter(options);
        medicineAdapter.startListening();
        recyclerView.setAdapter(medicineAdapter);

    }
}
