package com.example.appmobilestore.Adaptors;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appmobilestore.EditProductActivity;
import com.example.appmobilestore.Items.ItemProduct;
import com.example.appmobilestore.ProductDetailsActivity;
import com.example.appmobilestore.R;
import com.example.appmobilestore.Utilities.Data;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    ArrayList<ItemProduct> listData;
    Context context;

    public ProductAdapter(Context context, ArrayList<ItemProduct> listData) {
        this.listData = listData;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, final int position) {
        holder.setData(listData.get(position),position);
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Data.TYPE_USER.equals("vendedor")){
                    Intent intent = new Intent(context, EditProductActivity.class);
                    intent.putExtra("id",listData.get(position).getId());
                    context.startActivity(intent);
                }else{
                    Intent intent = new Intent(context, ProductDetailsActivity.class);
                    intent.putExtra("id",listData.get(position).getId());
                    context.startActivity(intent);
                }

            }
        });
    }

    @Override
    public int getItemCount() {

        return  listData == null ? 0:listData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageFoto;
        TextView textDescripcion,textStock,textPrecio;
        ConstraintLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageFoto = itemView.findViewById(R.id.imageFoto);
            textDescripcion = itemView.findViewById(R.id.textDescripcion);
            textStock = itemView.findViewById(R.id.editStock);
            textPrecio = itemView.findViewById(R.id.editPrecio);
            parentLayout = itemView.findViewById(R.id.parentLayout);
        }


        public void setData(ItemProduct itemProduct, int position) {
            textDescripcion.setText(itemProduct.getDescripcion());
            textStock.setText(itemProduct.getStock().toString());
            textPrecio.setText(itemProduct.getPrecio().toString());
            Glide.with(context).load(Data.HOST + itemProduct.getFoto()).into(imageFoto);
        }
    }
}
