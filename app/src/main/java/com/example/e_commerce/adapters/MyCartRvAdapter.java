package com.example.e_commerce.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;

public class MyCartRvAdapter extends RecyclerView.Adapter<MyCartRvAdapter.MyCartRvViewHolder> {
    ArrayList<MyCartproductModel> cartproductModels;
    onCartAdapterItemClick listener;
    Context context;

    public MyCartRvAdapter(ArrayList<MyCartproductModel> cartproductModels, onCartAdapterItemClick listener, Context context) {
        this.cartproductModels = cartproductModels;
        this.listener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public MyCartRvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_cart_template, parent, false);
        MyCartRvViewHolder viewHolder = new MyCartRvViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyCartRvViewHolder holder, int position) {
        MyCartproductModel model = cartproductModels.get(position);
        Glide.with(context).load(model.getImg_url1()).into(holder.myCartProductImage);
        holder.myCartTvName.setText(model.getName());
//        holder.myCartTvColor.setText(model.getColor());
        holder.myCartTvPrice.setText("$" + model.getPrice());
//        holder.myCartTvSize.setText(model.getSize());
//        holder.myCartTvCount.setText(model.getCount()+"");
        holder.myCartTvName.setTag(position);
    }

    @Override
    public int getItemCount() {
        return cartproductModels.size();
    }

    public interface onCartAdapterItemClick {
        void onCartItemClick(Intent intent);
    }

    class MyCartRvViewHolder extends RecyclerView.ViewHolder {
        ImageView myCartProductImage;
        TextView myCartTvName, myCartTvColor, myCartTvSize, myCartTvPrice, myCartTvCount;
        FloatingActionButton myCartbtnPlus, myCartbtnMinus;

        public MyCartRvViewHolder(@NonNull View itemView) {
            super(itemView);
            myCartProductImage = itemView.findViewById(R.id.my_cart_product_image);
            myCartTvName = itemView.findViewById(R.id.my_cart_product_name_tv);
            myCartTvColor = itemView.findViewById(R.id.my_cart_product_color_tv);
            myCartTvSize = itemView.findViewById(R.id.my_cart_product_size_tv);
            myCartTvPrice = itemView.findViewById(R.id.my_cart_product_price_tv);
            myCartTvCount = itemView.findViewById(R.id.my_cart_tv_count);
            myCartbtnPlus = itemView.findViewById(R.id.my_cart_btn_plus);
            myCartbtnMinus = itemView.findViewById(R.id.my_cart_btn_minus);
            myCartbtnPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int count = Integer.parseInt(myCartTvCount.getText().toString());
                    count += 1;
                    myCartTvCount.setText(count + "");
                }
            });
            myCartbtnMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int count = Integer.parseInt(myCartTvCount.getText().toString());
                    if (count > 0) {
                        count -= 1;
                        myCartTvCount.setText(count + "");
                    }
                }
            });
            myCartProductImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = (int) myCartTvName.getTag();
                    Log.i("ahmed", position + "");
                    Intent intent = new Intent();
                    intent.putExtra("details", (Serializable) cartproductModels.get(position));
                    listener.onCartItemClick(intent);
                }
            });
            myCartTvName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = (int) myCartTvName.getTag();
                    Intent intent = new Intent();
                    intent.putExtra("details", (Serializable) cartproductModels.get(position));
                    listener.onCartItemClick(intent);
                }
            });
            myCartTvColor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = (int) myCartTvName.getTag();
                    Intent intent = new Intent();
                    intent.putExtra("details", (Serializable) cartproductModels.get(position));
                    listener.onCartItemClick(intent);
                }
            });
            myCartTvSize.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = (int) myCartTvName.getTag();
                    Intent intent = new Intent();
                    intent.putExtra("details", (Serializable) cartproductModels.get(position));
                    listener.onCartItemClick(intent);
                }
            });
            myCartTvPrice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = (int) myCartTvName.getTag();
                    Intent intent = new Intent();
                    intent.putExtra("details", (Serializable) cartproductModels.get(position));
                    listener.onCartItemClick(intent);
                }
            });
        }
    }
}
