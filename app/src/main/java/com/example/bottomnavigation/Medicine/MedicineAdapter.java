package com.example.bottomnavigation.Medicine;

import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bottomnavigation.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

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
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull MedicineModel model) {

        holder.medName.setText(model.getMedName());
        holder.tablets.setText(model.getTablets());
        holder.food.setText(model.getFood());
        holder.timesDaily.setText(model.getTimesDaily());

        Glide.with(holder.img.getContext())
                .load(model.getPicUrl())
                .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
                .circleCrop()
                .error(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.img);

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

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            img = (CircleImageView) itemView.findViewById(R.id.imgl);
            medName = (TextView) itemView.findViewById(R.id.medName_text);
            tablets = (TextView) itemView.findViewById(R.id.tablets_text);
            timesDaily = (TextView) itemView.findViewById(R.id.timesDaily_text);
            food = (TextView)  itemView.findViewById(R.id.food_text);

        }
    }
}
