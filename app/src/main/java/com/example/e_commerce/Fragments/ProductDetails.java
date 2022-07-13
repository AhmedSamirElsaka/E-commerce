package com.example.e_commerce.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.e_commerce.R;
import com.example.e_commerce.activities.MainActivity;
import com.example.e_commerce.adapters.RvProductsAdapter;
import com.example.e_commerce.models.ProductModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ProductDetails extends Fragment {

    onSimilarProductsitemClick listener;
    TextView tvViewAllSimilarProducts;
    MaterialButton btnBuyNow, btnAddToCart;
    Bundle bundle;
    FirebaseFirestore firestore;
    FirebaseAuth auth;

    TextView tvProductDetailsName, tvProductDetailsPrice, tvProductDetailsDescription;
    RatingBar productDeatilsRatingBar;
    ImageView isLovedImage;
    private RecyclerView similarProductsRv;
    private RvProductsAdapter similarProductsAdapter;
    private String img_url1;
    private String img_url2;
    private String img_url3;
    private String img_url4;
    private String name;
    private double price;
    private boolean isLoved;
    private String description;
    private double rating;


    public ProductDetails() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof onSimilarProductsitemClick) {
            listener = (onSimilarProductsitemClick) context;
        } else {
            throw new ClassCastException("Your activity does not implement onSimilarProductsitemClick");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getArguments();
        if (bundle != null) {
            img_url1 = bundle.getString("img_url1");
            img_url2 = bundle.getString("img_url2");
            img_url3 = bundle.getString("img_url3");
            img_url4 = bundle.getString("img_url4");
            name = bundle.getString("name");
            description = bundle.getString("description");
            price = bundle.getDouble("price");
            rating = bundle.getDouble("rating");
            isLoved = bundle.getBoolean("isloved");
        }

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_product_details, container, false);
        tvProductDetailsName = v.findViewById(R.id.product_details_name);
        tvProductDetailsPrice = v.findViewById(R.id.product_details_price);
        tvProductDetailsDescription = v.findViewById(R.id.product_details_overview);
        productDeatilsRatingBar = v.findViewById(R.id.product_details_rating_bar);
        isLovedImage = v.findViewById(R.id.product_is_loved);
        if (isLoved) {
            isLovedImage.setImageResource(R.drawable.ic_black_heart);
        } else {
            isLovedImage.setImageResource(R.drawable.ic_white_hear);
        }
        isLovedImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isLoved) {
                    isLovedImage.setImageResource(R.drawable.ic_white_hear);
                    isLoved = false;
                } else {
                    isLovedImage.setImageResource(R.drawable.ic_black_heart);
                    isLoved = true;
                }
            }
        });
        tvProductDetailsName.setText(name);
        tvProductDetailsPrice.setText(price + "");
        tvProductDetailsDescription.setText(description);
        productDeatilsRatingBar.setRating((float) rating);

        ImageSlider productDetailsImageSlider = v.findViewById(R.id.product_details_image_slider);
        List<SlideModel> productDetailsImageSliderModels = new ArrayList<>();
        productDetailsImageSliderModels.add(new SlideModel(img_url1, "", ScaleTypes.CENTER_CROP));
        productDetailsImageSliderModels.add(new SlideModel(img_url2, "", ScaleTypes.CENTER_CROP));
        productDetailsImageSliderModels.add(new SlideModel(img_url3, "", ScaleTypes.CENTER_CROP));
        productDetailsImageSliderModels.add(new SlideModel(img_url4, "", ScaleTypes.CENTER_CROP));


        productDetailsImageSlider.setImageList(productDetailsImageSliderModels);


        tvViewAllSimilarProducts = v.findViewById(R.id.tv_similar_products_view_all);
        tvViewAllSimilarProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onTvViewAllClick(MainActivity.TV_VIEW_ALL_SIMILAR_PRODUCTS);
            }
        });
        btnBuyNow = v.findViewById(R.id.product_details_btn_buy_now);
        btnBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "buy now", Toast.LENGTH_SHORT).show();
            }
        });
        btnAddToCart = v.findViewById(R.id.product_details_btn_add_to_cart);
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToCart();
            }
        });

        similarProductsRv = v.findViewById(R.id.rv_product_details_similar_products);
        ArrayList<ProductModel> similarProductsModel = new ArrayList<>();

        similarProductsAdapter = new RvProductsAdapter(similarProductsModel, new RvProductsAdapter.onProductClick() {
            @Override
            public void onClick(Intent intent) {
                listener.onProductClick(intent);
            }
        }, getActivity());
        LinearLayoutManager similarProductsLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        similarProductsRv.setLayoutManager(similarProductsLayoutManager);
        similarProductsRv.setAdapter(similarProductsAdapter);
        firestore.collection("New Products")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (int i = 0; i < 10; i++) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    ProductModel model = new ProductModel(
                                            document.getString("img_url1"),
                                            document.getString("img_url2"),
                                            document.getString("img_url3"),
                                            document.getString("img_url4"),
                                            document.getString("name"),
                                            document.getDouble("price"),
                                            false, document.getString("description"),
                                            document.getDouble("rating"));
                                    similarProductsModel.add(model);
                                    similarProductsAdapter.notifyDataSetChanged();
                                }
                            }

                        } else {

                        }
                    }
                });

        return v;

    }

    private void addToCart() {
        HashMap<String, Object> addToCartMap = new HashMap<>();
        addToCartMap.put("name", name);
        addToCartMap.put("img_url1", img_url1);
        addToCartMap.put("img_url2", img_url2);
        addToCartMap.put("img_url3", img_url3);
        addToCartMap.put("img_url4", img_url4);
        addToCartMap.put("price", price);
        addToCartMap.put("description", description);
        addToCartMap.put("isloved", isLoved);
        addToCartMap.put("rating", rating);
        firestore.collection("AddToCart").document(auth.getCurrentUser().getUid())
                .collection("User")
                .add(addToCartMap)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity(), "Added To cart", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }

    public interface onSimilarProductsitemClick {
        void onProductClick(Intent intent);

        void onTvViewAllClick(String fragmentName);

    }
}