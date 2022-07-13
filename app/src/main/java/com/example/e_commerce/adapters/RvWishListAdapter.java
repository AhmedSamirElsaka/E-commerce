package com.example.e_commerce.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.e_commerce.models.MyCartproductModel;
import com.example.e_commerce.R;

import java.util.ArrayList;

public class RvWishListAdapter extends RecyclerView.Adapter<RvWishListAdapter.RvWishListViewHolder> {
    ArrayList<MyCartproductModel> cartproductModels ;
    onWishListAdapterItemClick listener ;
    Context context ;

    public RvWishListAdapter(ArrayList<MyCartproductModel> cartproductModels, onWishListAdapterItemClick listener, Context context) {
        this.cartproductModels = cartproductModels;
        this.listener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public RvWishListAdapter.RvWishListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.wish_list_rv_template , parent , false);
        RvWishListAdapter.RvWishListViewHolder viewHolder = new RvWishListAdapter.RvWishListViewHolder(v) ;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RvWishListAdapter.RvWishListViewHolder holder, int position) {
        MyCartproductModel model = cartproductModels.get(position) ;
        Glide.with(context).load(model.getImg_url1()).into(holder.wishListProductImage) ;
        holder.wishListTvName.setText(model.getName());
//        holder.wishListTvColor.setText(model.getColor());
        holder.wishListTvSize.setText("$"+model.getPrice());
//        holder.wishListTvPrice.setText(model.getSize());
        holder.wishListTvPrice.setTag(position);
    }

    @Override
    public int getItemCount() {
        return cartproductModels.size();
    }

    class RvWishListViewHolder extends RecyclerView.ViewHolder{
        ImageView wishListProductImage , isloved  ;
        TextView wishListTvName , wishListTvColor , wishListTvSize , wishListTvPrice  ;
        public RvWishListViewHolder(@NonNull View itemView) {
            super(itemView);
            wishListProductImage = itemView.findViewById(R.id.wish_list_product_image);
            wishListTvName = itemView.findViewById(R.id.wish_list_product_name_tv);
            wishListTvColor = itemView.findViewById(R.id.wish_list_product_color_tv);
            wishListTvSize = itemView.findViewById(R.id.wish_list_product_size_tv);
            wishListTvPrice = itemView.findViewById(R.id.wish_list_product_price_tv);
            isloved = itemView.findViewById(R.id.wish_list_is_loved_image) ;
            isloved.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int id = (int) wishListTvPrice.getTag();
                    listener.onIsLovedImageClick(id);
                }
            });
            wishListProductImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int id = (int) wishListTvPrice.getTag();
                    listener.onItemClick(id);
                }
            });
            wishListTvName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int id = (int) wishListTvPrice.getTag();
                    listener.onItemClick(id);
                }
            });
            wishListTvColor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int id = (int) wishListTvPrice.getTag();
                    listener.onItemClick(id);
                }
            });
            wishListTvSize.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int id = (int) wishListTvPrice.getTag();
                    listener.onItemClick(id);
                }
            });
            wishListTvPrice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int id = (int) wishListTvPrice.getTag();
                    listener.onItemClick(id);
                }
            });
        }
    }
    public interface onWishListAdapterItemClick{
        void onIsLovedImageClick(int id ) ;
        void onItemClick(int id) ;
    }
}
