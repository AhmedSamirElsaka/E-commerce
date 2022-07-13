package com.example.e_commerce.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.e_commerce.R;
import com.example.e_commerce.adapters.RvProductsAdapter;
import com.example.e_commerce.models.ProductModel;
import com.example.e_commerce.models.ShowRvModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ProductsListFragment extends Fragment {
    RecyclerView ProductListFragmentRv;
    RvProductsAdapter ProductListFragmentAdapter;

    onProductListItemClick listener;
    FirebaseFirestore firestore;
    ProgressDialog progressDialog ;

    public ProductsListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof onProductListItemClick) {
            listener = (onProductListItemClick) context;
        } else {
            throw new ClassCastException("Your activity does not implement onSimilarProductsitemClick");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        progressDialog = new ProgressDialog(getActivity()) ;
        progressDialog = new ProgressDialog(getActivity()) ;
        progressDialog.setTitle("Welcome to my E-commerce app");
        progressDialog.setMessage("Please wait......");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_products_list, container, false);
        firestore = FirebaseFirestore.getInstance();

        ProductListFragmentRv = v.findViewById(R.id.product_list_rv);
        ArrayList<ProductModel> ProductListFragmentModels = new ArrayList<>();

        ProductListFragmentAdapter = new RvProductsAdapter(ProductListFragmentModels, new RvProductsAdapter.onProductClick() {
            @Override
            public void onClick(Intent intent) {
                listener.onProductClick(intent);
            }
        }, getActivity());
        GridLayoutManager ProductListFragmentLayoutManager = new GridLayoutManager(getContext(), 2);
        ProductListFragmentRv.setLayoutManager(ProductListFragmentLayoutManager);
        ProductListFragmentRv.setAdapter(ProductListFragmentAdapter);
        firestore.collection("New Products")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (int i = 0; i < 10; i++) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    ProductModel model = new ProductModel(document.getString("img_url1"),
                                            document.getString("img_url2"),
                                            document.getString("img_url3"),
                                            document.getString("img_url4"),
                                            document.getString("name"),
                                            document.getDouble("price"),
                                            false, document.getString("description"),
                                            document.getDouble("rating"));
                                    ProductListFragmentModels.add(model);
                                    ProductListFragmentAdapter.notifyDataSetChanged();
                                    progressDialog.dismiss();
                                }
                            }

                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), task.getException().getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        return v;
    }

    public interface onProductListItemClick {
        void onProductClick(Intent intent);
    }
}