package com.example.bottomnavigation.Medicine;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bottomnavigation.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MedicineAdapter extends FirebaseRecyclerAdapter<MedicineModel,MedicineAdapter.myViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MedicineAdapter(@NonNull FirebaseRecyclerOptions<MedicineModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, final int position, @NonNull MedicineModel model) {

        holder.medName.setText(model.getMedName());
        holder.tablets.setText(model.getTablets());
        holder.food.setText(model.getFood());
        holder.timesDaily.setText(model.getTimesDaily());

        Glide.with(holder.img.getContext())
                .load(model.getPicUrl())
                .placeholder(com.google.android.gms.base.R.drawable.common_google_signin_btn_icon_dark)
                .circleCrop()
                .error(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.img);

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.img.getContext())
                        .setContentHolder(new ViewHolder(R.layout.update_popup))
                        .setExpanded(true, 1350)
                        .create();

                //dialogPlus.show();

                View view = dialogPlus.getHolderView();

                EditText medName = view.findViewById(R.id.txtmedName);
                EditText tablets = view.findViewById(R.id.txtTablets);
                EditText timesDaily = view.findViewById(R.id.txtTimesDaily);
                EditText food = view.findViewById(R.id.txtFood);
                EditText picUrl = view.findViewById(R.id.txtPicUrl);

                Button btnUpdate = view.findViewById(R.id.btnUpdate);

                medName.setText(model.getMedName());
                tablets.setText(model.getTablets());
                timesDaily.setText(model.getTimesDaily());
                food.setText(model.getFood());
                picUrl.setText(model.getPicUrl());

                dialogPlus.show();

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String,Object> map = new HashMap<>();
                        map.put("medName", medName.getText().toString());
                        map.put("tablets", tablets.getText().toString());
                        map.put("food", food.getText().toString());
                        map.put("timesDaily", timesDaily.getText().toString());
                        map.put("picUrl", picUrl.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("Medicine")
                                .child(getRef(holder.getAdapterPosition()).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {

                                        Toast.makeText(holder.medName.getContext(), "Data Updated Successfully", Toast.LENGTH_LONG)
                                                .show();
                                        dialogPlus.dismiss();

                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                        Toast.makeText(holder.medName.getContext(), "Error While Updating!", Toast.LENGTH_LONG)
                                                .show();
                                        dialogPlus.dismiss();

                                    }
                                });

                    }
                });

            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.medName.getContext());
                builder.setTitle("Are you sure?");
                builder.setMessage("Deleted data can't be UNDO.");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        FirebaseDatabase.getInstance().getReference().child("Medicine")
                                .child(getRef(holder.getPosition()).getKey()).removeValue();

                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Toast.makeText(holder.medName.getContext(), "Cancelled.", Toast.LENGTH_LONG).show();

                    }
                });

                builder.show();
            }
        });

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.medicine_item,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{

        CircleImageView img;
        TextView medName, tablets, timesDaily, food;

        Button btnEdit, btnDelete;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            img = (CircleImageView) itemView.findViewById(R.id.imgl);
            medName = (TextView) itemView.findViewById(R.id.medName_text);
            tablets = (TextView) itemView.findViewById(R.id.tablets_text);
            timesDaily = (TextView) itemView.findViewById(R.id.timesDaily_text);
            food = (TextView)  itemView.findViewById(R.id.food_text);

            btnEdit = (Button) itemView.findViewById(R.id.btnEdit);
            btnDelete = (Button) itemView.findViewById(R.id.btnDelete);

        }
    }
}
