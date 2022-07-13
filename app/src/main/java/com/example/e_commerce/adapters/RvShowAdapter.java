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
import com.example.e_commerce.R;
import com.example.e_commerce.models.ShowRvModel;

import java.util.List;

public class RvShowAdapter extends RecyclerView.Adapter<RvShowAdapter.RvShowViewHolder> {
    List<ShowRvModel> showModels;
    onSaleItemClick listener ;
    Context context ;

    public RvShowAdapter(List<ShowRvModel> showModels, onSaleItemClick listener, Context context) {
        this.showModels = showModels;
        this.listener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public RvShowAdapter.RvShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_sale_template, parent, false);
        RvShowAdapter.RvShowViewHolder holder = new RvShowViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RvShowAdapter.RvShowViewHolder holder, int position) {
        ShowRvModel showModel = showModels.get(position);
        holder.saleTitle.setText(showModel.getText());
        Glide.with(context).load(showModel.getImage()).into(holder.saleImage);
        holder.saleTitle.setTag(position);
    }

    @Override
    public int getItemCount() {
        return showModels.size();
    }

    class RvShowViewHolder extends RecyclerView.ViewHolder {
        ImageView saleImage;
        TextView saleTitle;

        public RvShowViewHolder(@NonNull View itemView) {
            super(itemView);
            saleImage = itemView.findViewById(R.id.image_sale);
            saleTitle = itemView.findViewById(R.id.tv_sale);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int id = (int) saleTitle.getTag();
                    listener.onClick(id);
                }
            });
        }
    }

    public interface onSaleItemClick{
        void onClick(int id) ;
    }
}
