package com.example.justsauce.ui.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.justsauce.R;
import com.example.justsauce.ui.Utils;
import com.example.justsauce.ui.datamodels.Order;
import com.example.justsauce.ui.datamodels.Product;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private LayoutInflater inflater;
    private Context context;
    private Order order;


    public OrderAdapter(Context context, Order order){
        this.context = context;
        this.order = order;
        inflater = LayoutInflater.from(context);
    }

    public interface OnRemovedItemListener{
        void onRemove(Product product);
    }

    private OnRemovedItemListener onRemovedItemListener;

    public OnRemovedItemListener getOnRemovedItemListener() {
        return onRemovedItemListener;
    }

    public void setOnRemovedItemListener(OnRemovedItemListener onRemovedItemListener) {
        this.onRemovedItemListener = onRemovedItemListener;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        int layoutResources = R.layout.item_order;
        View view = inflater.inflate(layoutResources,viewGroup,false);

        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder orderViewHolder, int position) {
        Product item = order.getProducts().get(position);

        orderViewHolder.itemQuantita_textView.setText("1x");
        orderViewHolder.itemNome_textView.setText(item.getNome());
        orderViewHolder.itemSubtotal_textView.setText(String.valueOf(item.getPrezzo()));
    }

    @Override
    public int getItemCount() {
        return order.getProducts().size();
    }


    public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView itemQuantita_textView,itemNome_textView,itemSubtotal_textView;
        public ImageButton remove_btn;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);

            itemQuantita_textView = itemView.findViewById(R.id.itemQuantita_textView);
            itemNome_textView = itemView.findViewById(R.id.itemNome_textView);
            itemSubtotal_textView = itemView.findViewById(R.id.itemSubtotal_textView);
            remove_btn = itemView.findViewById(R.id.remove_btn);

            remove_btn.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            if(v.getId() == R.id.remove_btn){

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("ELIMINA");
                builder.setCancelable(true);
                builder.setMessage("Sei sicuro di voler eliminare il prodotto?");

                builder.setNegativeButton(
                        "NO",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                return;
                            }
                        }
                );
                builder.setPositiveButton(
                        "SI",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if( (order.getTotal() - order.getProducts().get(getAdapterPosition()).getPrezzo()) < (order.getRestaurant().getOrdineMinimo()) ) {
                                    Utils.showToast(context, "Impossibile eliminare il prodotto.\nL'ordine è minore dell'ordine minimo.");
                                    return;
                                }
                                Product product = order.getProducts().get(getAdapterPosition());
                                onRemovedItemListener.onRemove(product);
                                notifyItemRemoved(getAdapterPosition());
                            }
                        }
                );
                builder.setIcon(R.drawable.baseline_warning_black_24);
                builder.create().show();
            }
        }
    }
}

