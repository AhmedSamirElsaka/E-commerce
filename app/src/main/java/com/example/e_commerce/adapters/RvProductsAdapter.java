package com.example.e_commerce.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.e_commerce.Fragments.ProductDetails;
import com.example.e_commerce.models.ProductModel;
import com.example.e_commerce.R;

import java.io.Serializable;
import java.util.ArrayList;

public class RvProductsAdapter extends RecyclerView.Adapter<RvProductsAdapter.RvProductViewHolder> {
    ArrayList<ProductModel> productModels ;
    onProductClick listener ;
    Context context ;

    public RvProductsAdapter(ArrayList<ProductModel> productModels, onProductClick listener, Context context) {
        this.productModels = productModels;
        this.listener = listener;
        this.context = context;
    }


    @NonNull
    @Override
    public RvProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_product_template , parent , false) ;
        RvProductViewHolder viewHolder = new RvProductViewHolder(v) ;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RvProductViewHolder holder, int position) {
        ProductModel productModel = productModels.get(position) ;
        holder.tvProductName.setText(productModel.getName());
        holder.tvProductPrice.setText("$" + productModel.getPrice());
        Glide.with(context).load(productModel.getImg_url1()).into(holder.productImage) ;
        holder.tvProductPrice.setTag(position);
        if(productModel.isLoved()){
            holder.isLovedImage.setImageResource(R.drawable.ic_black_heart);
        }else {
            holder.isLovedImage.setImageResource(R.drawable.ic_white_hear);
        }


    }

    @Override
    public int getItemCount() {
        return productModels.size();
    }

    class RvProductViewHolder extends RecyclerView.ViewHolder{
        ImageView productImage ;
        TextView tvProductName , tvProductPrice ;
        ImageView isLovedImage ;
        public RvProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_image);
            tvProductPrice = itemView.findViewById(R.id.product_price);
            tvProductName = itemView.findViewById(R.id.product_name);
            isLovedImage = itemView.findViewById(R.id.product_is_loved);

            productImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = (int) tvProductPrice.getTag();
                    Intent intent = new Intent() ;
                    intent.putExtra("details" , (Serializable) productModels.get(position)) ;
                    listener.onClick(intent);
                }
            });
            tvProductPrice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = (int) tvProductPrice.getTag();
                    Intent intent = new Intent() ;
                    intent.putExtra("details" , (Serializable) productModels.get(position)) ;
                    listener.onClick(intent);
                }
            });
            tvProductName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = (int) tvProductPrice.getTag();
                    Intent intent = new Intent() ;
                    intent.putExtra("details" , (Serializable) productModels.get(position)) ;
                    listener.onClick(intent);
                }
            });
            isLovedImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int id = (int) tvProductPrice.getTag();
                    if (productModels.get(id).isLoved()){
                        isLovedImage.setImageResource(R.drawable.ic_white_hear);
                        productModels.get(id).setLoved(false);

                    }else{
                        isLovedImage.setImageResource(R.drawable.ic_black_heart);
                        productModels.get(id).setLoved(true);
                    }
                }
            });
        }
    }
    public interface onProductClick{
        void onClick(Intent intent) ;
    }
}
