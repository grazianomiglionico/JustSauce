package com.example.justsauce.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.justsauce.R;
import com.example.justsauce.ui.datamodels.Restaurant;

import java.util.ArrayList;

public class RestaurantAdapter extends RecyclerView.Adapter {

    private LayoutInflater inflater;
    private ArrayList<Restaurant> data;

    public RestaurantAdapter(Context context, ArrayList<Restaurant> data){
        inflater = LayoutInflater.from(context);
        this.data = data;
    }




    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View view = inflater.inflate(R.layout.item_restaurant,viewGroup,false);
        return new RestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        RestaurantViewHolder vh = (RestaurantViewHolder)viewHolder;
        vh.restaurantName_textView.setText(data.get(position).getNome());
        vh.restaurantAddress_textView.setText(data.get(position).getIndirizzo());
        vh.restaurantOrderMinimum_textView.setText("Ordine minimo:" + data.get(position).getOrdineMinimo());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }




    public class RestaurantViewHolder extends RecyclerView.ViewHolder{

        public TextView restaurantName_textView;
        public TextView restaurantAddress_textView;
        public TextView restaurantOrderMinimum_textView;

        public RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);
            restaurantName_textView = itemView.findViewById(R.id.restaurantName_textView);
            restaurantAddress_textView = itemView.findViewById(R.id.restaurantAddress_textView);
            restaurantOrderMinimum_textView = itemView.findViewById(R.id.restaurantOrderMinimum_textView);
        }
    }
}
