package com.graziano.justsauce.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.graziano.justsauce.R;
import com.graziano.justsauce.ui.datamodels.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter {
    private LayoutInflater inflater;
    private Context context;
    private List<Product> data;


    public ProductAdapter(Context context){
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.data = new ArrayList<>();
    }

    public void setData(List<Product> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public List<Product> getData(){
        return data;
    }

    public interface OnQuantityChangedListener{
        void onChange(double prezzo);
    }

    private OnQuantityChangedListener onQuantityChangedListener;

    public OnQuantityChangedListener getOnQuantityChangedListener() {
        return onQuantityChangedListener;
    }

    public void setOnQuantityChangedListener(OnQuantityChangedListener onQuantityChangedListener) {
        this.onQuantityChangedListener = onQuantityChangedListener;
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        int layoutResources = R.layout.item_product;
        View view = inflater.inflate(layoutResources,viewGroup,false);

        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        Product item = data.get(position);

        ProductViewHolder vh = (ProductViewHolder)viewHolder;

        vh.itemNome_textView.setText(item.getNome());
        vh.itemPrezzo_textView.setText(String.valueOf(item.getPrezzo()));
        vh.quantita_textView.setText(String.valueOf(item.getQuantita()));

    }

    @Override
    public int getItemCount() {
        return data.size();
    }




    public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView itemNome_textView;
        public TextView itemPrezzo_textView;
        public TextView quantita_textView;
        public Button add_btn,remove_btn;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            itemNome_textView = itemView.findViewById(R.id.itemNome_textView);
            itemPrezzo_textView = itemView.findViewById(R.id.itemPrezzo_textView);
            quantita_textView = itemView.findViewById(R.id.quantita_textView);
            add_btn = itemView.findViewById(R.id.add_btn);
            remove_btn = itemView.findViewById(R.id.remove_btn);

            add_btn.setOnClickListener(this);
            remove_btn.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Product product = data.get(getAdapterPosition());
            if(v.getId() == R.id.add_btn){
                product.increaseQuantity();
                notifyItemChanged(getAdapterPosition());
                onQuantityChangedListener.onChange(product.getPrezzo());
            }
            else if(v.getId() == R.id.remove_btn){
                if(product.getQuantita() == 0) return;
                product.decreaseQuantity();
                notifyItemChanged(getAdapterPosition());
                onQuantityChangedListener.onChange(product.getPrezzo() * -1);
            }
        }
    }
}
