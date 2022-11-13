package com.example.bottomnavigation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {


    private Context context;
    private ArrayList medicine_id, medicine_name, medicine_tablets, medicine_times, medicine_food;


    CustomAdapter(Context context, ArrayList medicine_id, ArrayList medicine_name,
                  ArrayList medicine_tablets, ArrayList medicine_times, ArrayList medicine_food){
        this.context = context;
        this.medicine_id = medicine_id;
        this.medicine_name = medicine_name;
        this.medicine_tablets = medicine_tablets;
        this.medicine_times = medicine_times;
        this.medicine_food = medicine_food;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.medicine_id_txt.setText(String.valueOf(medicine_id.get(position)));
        holder.medicine_name_txt.setText(String.valueOf(medicine_name.get(position)));
        holder.medicine_tablets_txt.setText(String.valueOf(medicine_tablets.get(position)));
        holder.medicine_times_txt.setText(String.valueOf(medicine_times.get(position)));
        holder.medicine_food_txt.setText(String.valueOf(medicine_food.get(position)));

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,UpdateActivity.class);
                intent.putExtra("id",String.valueOf(medicine_id.get(position)));
                intent.putExtra("name",String.valueOf(medicine_name.get(position)));
                intent.putExtra("tablets",String.valueOf(medicine_tablets.get(position)));
                intent.putExtra("times",String.valueOf(medicine_times.get(position)));
                intent.putExtra("food",String.valueOf(medicine_food.get(position)));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return medicine_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView medicine_id_txt, medicine_name_txt, medicine_tablets_txt,
                medicine_times_txt, medicine_food_txt;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            medicine_id_txt = itemView.findViewById(R.id.medicine_id_txt);
            medicine_name_txt = itemView.findViewById(R.id.textView);
            medicine_tablets_txt = itemView.findViewById(R.id.textView2);
            medicine_times_txt = itemView.findViewById(R.id.textView3);
            medicine_food_txt = itemView.findViewById(R.id.textView4);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
