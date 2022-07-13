package com.example.e_commerce.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.e_commerce.R;
import com.example.e_commerce.activities.MainActivity;
import com.example.e_commerce.adapters.CategoryAdapter;
import com.example.e_commerce.adapters.RvProductsAdapter;
import com.example.e_commerce.adapters.RvShowAdapter;
import com.example.e_commerce.models.CategoryModel;
import com.example.e_commerce.models.ProductModel;
import com.example.e_commerce.models.ShowRvModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    TextView tvViewAllNewProducts, tvViewAllRecommendedProducts, tvViewAllBestSeller;
    FirebaseFirestore firestore;
    TextView tvDeliveryToEgypt;
    SearchView homeSearchView;
    ArrayList<ProductModel> bestSellerProductModels;
    ProgressDialog progressDialog;
    private RecyclerView categoryRv, showRv, newProductRv, recommendedProductRv, bestSellerProductsRv;
    private CategoryAdapter categoryAdapter;
    private RvShowAdapter showAdapter;
    private RvProductsAdapter newProductsAdapter, recommendedProductAdapter, bestSellerProductsAdapter;
    private onHomesreenItemClick listener;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof onHomesreenItemClick) {
            listener = (onHomesreenItemClick) context;
        } else {
            throw new ClassCastException("Your activity does not implement onHomesreenItemClick");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        firestore = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Welcome to my E-commerce app");
        progressDialog.setMessage("Please wait......");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        tvDeliveryToEgypt = v.findViewById(R.id.home_tv_delivery_to_egypt);
        tvDeliveryToEgypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Sorry, this service does not avilable now ", Toast.LENGTH_SHORT).show();
            }
        });

        ArrayList<ProductModel> allProducts = new ArrayList<>();

        homeSearchView = v.findViewById(R.id.home_search_view);
        firestore.collection("New Products")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                ProductModel allProductModel = new ProductModel(doc.getString("img_url1"),
                                        doc.getString("img_url2"),
                                        doc.getString("img_url3"),
                                        doc.getString("img_url4"),
                                        doc.getString("name"),
                                        doc.getDouble("price"),
                                        false, doc.getString("description"),
                                        doc.getDouble("rating"));
                                allProducts.add(allProductModel);
                            }

                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), task.getException().getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }

                });
        homeSearchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
            }
        });
        homeSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Toast.makeText(getActivity(), "Sorry, this service does not available now ", Toast.LENGTH_SHORT).show();
//                ArrayList<ProductModel> searchList = new ArrayList<>();
//                for (int i = 0 ; i < allProducts.size() ; i++){
//                    if(allProducts.get(i).getName().){
//                        searchList.add(allProducts.get(i)) ;
//                    }
//                }
//                Toast.makeText(getActivity(), searchList.get(0).getName(), Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        tvViewAllNewProducts = v.findViewById(R.id.tv_new_products_view_all);
        tvViewAllBestSeller = v.findViewById(R.id.tv_best_seller_products_view_all);
        tvViewAllRecommendedProducts = v.findViewById(R.id.tv_recommended_products_view_all);

        tvViewAllNewProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onTvViewAllClick(MainActivity.TV_VIEW_ALL_NEW_PRODUCTS);
            }
        });
        tvViewAllBestSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onTvViewAllClick(MainActivity.TV_VIEW_ALL_BEST_SELLER_PRODUCTS);
            }
        });
        tvViewAllRecommendedProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onTvViewAllClick(MainActivity.TV_VIEW_ALL_RECOMMENDED_PRODUCTS);
            }
        });
        ImageSlider imageSlider = v.findViewById(R.id.image_slider);
        List<SlideModel> slideModels = new ArrayList<>();
        firestore.collection("Home Image Slider")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                SlideModel slideModel = new SlideModel(document.getString("img_url"), "", ScaleTypes.CENTER_CROP);
                                slideModels.add(slideModel);
                                imageSlider.setImageList(slideModels);
                            }
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), task.getException().getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


        categoryRv = v.findViewById(R.id.rv_category);
        ArrayList<CategoryModel> categoryModels = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(categoryModels, new CategoryAdapter.onCategoryItemClick() {
            @Override
            public void onClick(int id) {
                listener.onCategoryItemClick(id);
            }
        }, getActivity());
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        categoryRv.setLayoutManager(manager);
        categoryRv.setAdapter(categoryAdapter);
        firestore.collection("Category")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                CategoryModel categoryModel = documentSnapshot.toObject(CategoryModel.class);
                                categoryModels.add(categoryModel);
                                categoryAdapter.notifyDataSetChanged();
                            }
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), task.getException().getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


        showRv = v.findViewById(R.id.rv_show);
        ArrayList<ShowRvModel> showRvModels = new ArrayList<>();

        showAdapter = new RvShowAdapter(showRvModels, new RvShowAdapter.onSaleItemClick() {
            @Override
            public void onClick(int id) {
                listener.onSaleItemClick(id);
            }
        }, getActivity());
        LinearLayoutManager rvShowManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        showRv.setLayoutManager(rvShowManager);
        showRv.setAdapter(showAdapter);
        firestore.collection("Show Rv")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ShowRvModel model = new ShowRvModel(document.getString("text"), document.getString("img_url"));
                                showRvModels.add(model);
                                showAdapter.notifyDataSetChanged();
                            }
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), task.getException().getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


        newProductRv = v.findViewById(R.id.rv_new_products);
        ArrayList<ProductModel> newProductModels = new ArrayList<>();

        newProductsAdapter = new RvProductsAdapter(newProductModels, new RvProductsAdapter.onProductClick() {
            @Override
            public void onClick(Intent intent) {
                listener.onProductClick(intent);
            }
        }, getActivity());
        LinearLayoutManager rvNewProductLinearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        newProductRv.setLayoutManager(rvNewProductLinearLayoutManager);
        newProductRv.setAdapter(newProductsAdapter);
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
                                    newProductModels.add(model);
                                    newProductsAdapter.notifyDataSetChanged();
                                    progressDialog.dismiss();
                                }
                            }

                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), task.getException().getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


        recommendedProductRv = v.findViewById(R.id.rv_recommended_products);
        ArrayList<ProductModel> recommendedProductModels = new ArrayList<>();
        recommendedProductAdapter = new RvProductsAdapter(recommendedProductModels, new RvProductsAdapter.onProductClick() {
            @Override
            public void onClick(Intent intent) {
                listener.onProductClick(intent);
            }
        }, getActivity());
        LinearLayoutManager rvRecommendedProductLinearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        recommendedProductRv.setLayoutManager(rvRecommendedProductLinearLayoutManager);
        recommendedProductRv.setAdapter(recommendedProductAdapter);
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
                                    recommendedProductModels.add(model);
                                    recommendedProductAdapter.notifyDataSetChanged();
                                }
                            }

                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), task.getException().getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        bestSellerProductsRv = v.findViewById(R.id.rv_best_seller_products);
        bestSellerProductModels = new ArrayList<>();

        bestSellerProductsAdapter = new RvProductsAdapter(recommendedProductModels, new RvProductsAdapter.onProductClick() {
            @Override
            public void onClick(Intent intent) {
                listener.onProductClick(intent);
            }
        }, getActivity());
        GridLayoutManager rvbestSellerProductLinearLayoutManager = new GridLayoutManager(getContext(), 2);
        bestSellerProductsRv.setLayoutManager(rvbestSellerProductLinearLayoutManager);
        bestSellerProductsRv.setAdapter(bestSellerProductsAdapter);
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
                                    bestSellerProductModels.add(model);
                                    bestSellerProductsAdapter.notifyDataSetChanged();
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

    public interface onHomesreenItemClick {
        void onTvViewAllClick(String fragmentName);

        void onCategoryItemClick(int id);

        void onSaleItemClick(int id);

        void onProductClick(Intent intent);

    }
}