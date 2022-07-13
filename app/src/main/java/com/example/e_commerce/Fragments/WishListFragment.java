package com.example.e_commerce.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
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
import com.example.e_commerce.adapters.RvWishListAdapter;
import com.example.e_commerce.models.MyCartproductModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class WishListFragment extends Fragment {

    RecyclerView wishListRv;
    RvWishListAdapter wishListAdapter;
    onWishListItemClick listener;
    TextView tvDeliveryToEgypt;
    SearchView wishListSearchView;
    FirebaseFirestore firestore;
    ProgressDialog progressDialog;

    public WishListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof onWishListItemClick) {
            listener = (onWishListItemClick) context;
        } else {
            throw new ClassCastException("Your activity does not implement onWishListItemClick");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        firestore = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(getActivity());
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Welcome to my E-commerce app");
        progressDialog.setMessage("Please wait......");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_wish_list, container, false);
        tvDeliveryToEgypt = v.findViewById(R.id.wish_list_tv_delivery_to_egypt);
        tvDeliveryToEgypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Sorry, this service does not avilable now ", Toast.LENGTH_SHORT).show();
            }
        });
        wishListSearchView = v.findViewById(R.id.wish_list_search_view);
        wishListSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Toast.makeText(getActivity(), "Sorry, this service does not avilable now ", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        wishListRv = v.findViewById(R.id.wish_list_rv);
        ArrayList<MyCartproductModel> WishListProductModels = new ArrayList<>();
        wishListAdapter = new RvWishListAdapter(WishListProductModels, new RvWishListAdapter.onWishListAdapterItemClick() {
            @Override
            public void onIsLovedImageClick(int id) {
//                Toast.makeText(getActivity(), "item " + id + " not loved", Toast.LENGTH_SHORT).show();
                listener.onIsLovedImageClick(id);
            }

            @Override
            public void onItemClick(int id) {
                listener.onItemClick(id);
            }
        }, getActivity());
        GridLayoutManager wishListLayoutManager = new GridLayoutManager(getContext(), 1);
        wishListRv.setLayoutManager(wishListLayoutManager);
        wishListRv.setAdapter(wishListAdapter);

        firestore.collection("New Products")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                MyCartproductModel model = new MyCartproductModel(doc.getString("name"),
                                        doc.getString("img_url1"),
                                        doc.getString("img_url2"),
                                        doc.getString("img_url3"),
                                        doc.getString("img_url4"),
                                        doc.getDouble("price"),
                                        doc.getDouble("rating"),
                                        true,
                                        doc.getString("description"));
                                WishListProductModels.add(model);
                                wishListAdapter.notifyDataSetChanged();
                                progressDialog.dismiss();
                            }

                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), task.getException().getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        return v;
    }

    public interface onWishListItemClick {
        void onIsLovedImageClick(int id);

        void onItemClick(int id);
    }
}