package com.example.justsauce.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.justsauce.R;
import com.example.justsauce.ui.activities.ShopActivity;
import com.example.justsauce.ui.datamodels.Restaurant;

import java.util.ArrayList;

public class RestaurantAdapter extends RecyclerView.Adapter implements View.OnClickListener{
    private LayoutInflater inflater;
    private ArrayList<Restaurant> data;
    private Context context;
    private boolean isGridLayout = false;
    private Button menu_button;


    public RestaurantAdapter(Context context, ArrayList<Restaurant> data){
        inflater = LayoutInflater.from(context);
        this.data = data;
        this.context = context;
    }

    public boolean getIsGridLayout() {
        return isGridLayout;
    }

    public void setIsGridLayout(boolean gridLayout) {
        isGridLayout = gridLayout;
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        int layoutResources = getIsGridLayout() ? R.layout.item_restaurant_grid : R.layout.item_restaurant;

        View view = inflater.inflate(layoutResources,viewGroup,false);

        menu_button = view.findViewById(R.id.menu_button);
        menu_button.setOnClickListener(this);

        return new RestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        RestaurantViewHolder vh = (RestaurantViewHolder)viewHolder;
        Restaurant item = data.get(position);

        Glide.with(context).load(item.getImage()).into(vh.image);
        vh.restaurantName_textView.setText(item.getNome());
        vh.restaurantAddress_textView.setText(item.getIndirizzo());
        vh.restaurantCategory_textView.setText(item.getCategoria());
        vh.restaurantOrderMinimum_textView.setText(item.getOrdineMinimo() + "€");
        vh.restaurantTempoConsegna_textView.setText(item.getTempoConsegna());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.menu_button:
                context.startActivity(new Intent(context,ShopActivity.class));
                break;
        }
    }




    public class RestaurantViewHolder extends RecyclerView.ViewHolder{

        public ImageView image;
        public TextView restaurantName_textView;
        public TextView restaurantAddress_textView;
        public TextView restaurantCategory_textView;
        public TextView restaurantOrderMinimum_textView;
        public TextView restaurantTempoConsegna_textView;


        public RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            restaurantName_textView = itemView.findViewById(R.id.restaurantName_textView);
            restaurantAddress_textView = itemView.findViewById(R.id.restaurantAddress_textView);
            restaurantCategory_textView = itemView.findViewById(R.id.restaurantCategory_textView);
            restaurantOrderMinimum_textView = itemView.findViewById(R.id.restaurantOrderMinimum_textView);
            restaurantTempoConsegna_textView = itemView.findViewById(R.id.restaurantTempoConsegna_textView);
        }
    }
}
