package com.example.e_commerce.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.e_commerce.Fragments.HomeFragment;
import com.example.e_commerce.Fragments.MyCartFragment;
import com.example.e_commerce.Fragments.ProductDetails;
import com.example.e_commerce.Fragments.ProductsListFragment;
import com.example.e_commerce.Fragments.ProfileFragment;
import com.example.e_commerce.Fragments.WishListFragment;
import com.example.e_commerce.R;
import com.example.e_commerce.models.MyCartproductModel;
import com.example.e_commerce.models.ProductModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity implements HomeFragment.onHomesreenItemClick, ProductDetails.onSimilarProductsitemClick, WishListFragment.onWishListItemClick, ProductsListFragment.onProductListItemClick, MyCartFragment.onCartItemClick {

    public static final String TV_VIEW_ALL_NEW_PRODUCTS = "new products";
    public static final String TV_VIEW_ALL_RECOMMENDED_PRODUCTS = "recommended products";
    public static final String TV_VIEW_ALL_BEST_SELLER_PRODUCTS = "best seller products";
    public static final String TV_VIEW_ALL_SIMILAR_PRODUCTS = "similar products";
    private static final int PER_REQ_CODE = 1;
    Fragment homeFragment;
    BottomNavigationView mainBottomNavigation;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        firestore = FirebaseFirestore.getInstance();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, PER_REQ_CODE);
        }

        mainBottomNavigation = findViewById(R.id.main_bottom_navigation);

        homeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, homeFragment)
                .commit();
        mainBottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragmant = null;
                switch (item.getItemId()) {
                    case R.id.home:
                        selectedFragmant = new HomeFragment();
                        break;
                    case R.id.wishlist:
                        selectedFragmant = new WishListFragment();
                        break;
                    case R.id.cart:
                        selectedFragmant = new MyCartFragment();
                        break;
                    case R.id.profile:
                        selectedFragmant = new ProfileFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, selectedFragmant)
                        .commit();
                return true;
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.main_frame_layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    @Override
    public void onTvViewAllClick(String fragmentName) {
        Fragment selectedFragment = null;
        if (fragmentName.equals(TV_VIEW_ALL_NEW_PRODUCTS)) {
            selectedFragment = new ProductsListFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, selectedFragment)
                    .addToBackStack(null).commit();
        } else if (fragmentName.equals(TV_VIEW_ALL_RECOMMENDED_PRODUCTS)) {
            selectedFragment = new ProductsListFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, selectedFragment)
                    .addToBackStack(null).commit();
        } else if (fragmentName.equals(TV_VIEW_ALL_BEST_SELLER_PRODUCTS)) {
            selectedFragment = new ProductsListFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, selectedFragment)
                    .addToBackStack(null).commit();
        } else if (fragmentName.equals(TV_VIEW_ALL_SIMILAR_PRODUCTS)) {
            selectedFragment = new ProductsListFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, selectedFragment)
                    .addToBackStack(null).commit();
        }
    }

    @Override
    public void onCategoryItemClick(int id) {
        Fragment selectedFragment = new ProductsListFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, selectedFragment)
                .addToBackStack(null).commit();
    }

    @Override
    public void onSaleItemClick(int id) {
        Fragment selectedFragment = new ProductsListFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, selectedFragment)
                .addToBackStack(null).commit();
    }

    @Override
    public void onProductClick(Intent intent) {
//        Toast.makeText(this, "name is " + productName, Toast.LENGTH_SHORT).show();
        Fragment selectedFragment = new ProductDetails();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, selectedFragment)
                .addToBackStack(null).commit();
        if (intent.getSerializableExtra("details") instanceof ProductModel) {
            ProductModel model = (ProductModel) intent.getSerializableExtra("details");
            Bundle bundle = new Bundle();
            bundle.putString("name", model.getName());
            bundle.putString("description", model.getDescription());
            bundle.putString("img_url1", model.getImg_url1());
            bundle.putString("img_url2", model.getImg_url2());
            bundle.putString("img_url3", model.getImg_url3());
            bundle.putString("img_url4", model.getImg_url4());
            bundle.putDouble("price", model.getPrice());
            bundle.putDouble("rating", model.getRating());
            bundle.putBoolean("isloved", model.isLoved());
            selectedFragment.setArguments(bundle);
        }

    }

    @Override
    public void onIsLovedImageClick(int id) {

    }

    @Override
    public void onItemClick(int id) {
        Toast.makeText(this, "id is " + id, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onCartItemClick(Intent intent) {
        Fragment selectedFragment = new ProductDetails();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, selectedFragment)
                .addToBackStack(null).commit();
        if (intent.getSerializableExtra("details") instanceof MyCartproductModel) {
            MyCartproductModel model = (MyCartproductModel) intent.getSerializableExtra("details");
            Bundle bundle = new Bundle();
            bundle.putString("name", model.getName());
            bundle.putString("description", model.getDescription());
            bundle.putString("img_url1", model.getImg_url1());
            bundle.putString("img_url2", model.getImg_url2());
            bundle.putString("img_url3", model.getImg_url3());
            bundle.putString("img_url4", model.getImg_url4());
            bundle.putDouble("price", model.getPrice());
            bundle.putDouble("rating", model.getRating());
            bundle.putBoolean("isloved", model.isLoved());
            selectedFragment.setArguments(bundle);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PER_REQ_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            }
        }
    }


}