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
import com.example.e_commerce.models.CategoryModel;
import com.example.e_commerce.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.RvCategoryViewHolder> {

    List<CategoryModel> categoryModels ;
    onCategoryItemClick listener ;
    Context context ;

    public CategoryAdapter(List<CategoryModel> categoryModels, onCategoryItemClick listener, Context context) {
        this.categoryModels = categoryModels;
        this.listener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public RvCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_adapter_template , parent , false) ;
        RvCategoryViewHolder holder = new RvCategoryViewHolder(v) ;
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RvCategoryViewHolder holder, int position) {
        CategoryModel model = categoryModels.get(position) ;
        Glide.with(context).load(model.getImg_url()).into(holder.categoryImage) ;
        holder.categoryTextView.setText(model.getName());
        holder.categoryTextView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return categoryModels.size();
    }

    class RvCategoryViewHolder extends RecyclerView.ViewHolder{
        ImageView categoryImage ;
        TextView categoryTextView ;
        public RvCategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryImage = itemView.findViewById(R.id.category_image) ;
            categoryTextView = itemView.findViewById(R.id.category_tv) ;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int id = (int) categoryTextView.getTag();
                    listener.onClick(id);
                }
            });
        }
    }

    public interface onCategoryItemClick {
         void onClick(int id) ;
    }
}
