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
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_commerce.R;
import com.example.e_commerce.adapters.MyCartRvAdapter;
import com.example.e_commerce.models.MyCartproductModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class MyCartFragment extends Fragment {

    RecyclerView myCartRv;
    MyCartRvAdapter myCartRvAdapter;
    onCartItemClick listener;
    TextView tvItemsCount, tvTotalProductsPrice, tvShippingFeePrice, tvTotalPrice;
    MaterialButton btnCheckout;
    TextView tvDeliveryToEgypt;
    FirebaseAuth auth;
    FirebaseFirestore firestore;
    SearchView myCartSearchView;
    ProgressDialog progressDialog;

    public MyCartFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof onCartItemClick) {
            listener = (onCartItemClick) context;
        } else {
            throw new ClassCastException("Your activity does not implement onWishListItemClick");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Welcome to my E-commerce app");
        progressDialog.setMessage("Please wait......");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_my_cart, container, false);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        myCartSearchView = v.findViewById(R.id.my_cart_search_view);
        myCartSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        tvDeliveryToEgypt = v.findViewById(R.id.my_cart_tv_delivery_to_egypt);
        tvDeliveryToEgypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Sorry this service does not available now ", Toast.LENGTH_SHORT).show();
            }
        });
        myCartRv = v.findViewById(R.id.my_cart_rv);
        tvItemsCount = v.findViewById(R.id.my_cart_products_count);
        tvTotalProductsPrice = v.findViewById(R.id.my_cart_total_products_price);
        tvShippingFeePrice = v.findViewById(R.id.my_cart_total_shipping_fee_price);
        tvTotalPrice = v.findViewById(R.id.my_cart_total_price);
        btnCheckout = v.findViewById(R.id.my_cart_btn_checkout);
        ArrayList<MyCartproductModel> cartproductModels = new ArrayList<>();

        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firestore.collection("AddToCart")
                        .document(auth.getCurrentUser().getUid())
                        .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getActivity(), "deleted success", Toast.LENGTH_SHORT).show();
                    }
                });
                cartproductModels.clear();
                myCartRvAdapter.notifyDataSetChanged();
            }
        });
        myCartRvAdapter = new MyCartRvAdapter(cartproductModels, new MyCartRvAdapter.onCartAdapterItemClick() {
            @Override
            public void onCartItemClick(Intent intent) {
                listener.onCartItemClick(intent);
            }
        }, getActivity());
        GridLayoutManager myCartLayoutManager = new GridLayoutManager(getContext(), 1);
        myCartRv.setLayoutManager(myCartLayoutManager);
        myCartRv.setAdapter(myCartRvAdapter);
        firestore.collection("AddToCart")
                .document(auth.getCurrentUser().getUid())
                .collection("User")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int numberOfProducts = 0;
                            double totalProductsPrice = 0;
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                numberOfProducts += 1;
                                totalProductsPrice += doc.getDouble("price");
                                MyCartproductModel model = new MyCartproductModel(doc.getString("name"),
                                        doc.getString("img_url1"),
                                        doc.getString("img_url2"),
                                        doc.getString("img_url3"),
                                        doc.getString("img_url4"),
                                        doc.getDouble("price"),
                                        doc.getDouble("rating"),
                                        doc.getBoolean("isloved"),
                                        doc.getString("description"));
                                cartproductModels.add(model);
                                myCartRvAdapter.notifyDataSetChanged();
                                progressDialog.dismiss();
                            }
                            tvItemsCount.setText(numberOfProducts + " Products");
                            tvTotalProductsPrice.setText("$ " + totalProductsPrice);
                            double totalPrice = Double.parseDouble(tvTotalProductsPrice.getText().toString().trim().replace("$", "")) + Double.parseDouble(tvShippingFeePrice.getText().toString().trim().replace("$", ""));
                            tvTotalPrice.setText(totalPrice + "");

                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), task.getException().getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        return v;
    }

    public interface onCartItemClick {
        void onCartItemClick(Intent intent);
    }

}