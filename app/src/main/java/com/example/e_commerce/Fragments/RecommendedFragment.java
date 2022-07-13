package com.example.e_commerce.Fragments;

import android.os.Bundle;

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

import java.util.ArrayList;


public class RecommendedFragment extends Fragment {
    RecyclerView recommendedFragmentRv;
    RvProductsAdapter recommendedFragmentAdapter;

    public RecommendedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_recommended, container, false);

        return v;
    }
}