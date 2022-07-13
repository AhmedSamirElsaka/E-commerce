package com.example.e_commerce.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.e_commerce.R;
import com.example.e_commerce.adapters.SliderAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class OnBoardingActivity extends AppCompatActivity {

    Button btn_get_start;
    ViewPager viewPager;
    ImageView firstDot , seconddot , thirdDot ;

    FloatingActionButton btn_next ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.on_board_layout);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        viewPager = findViewById(R.id.slider);
        btn_get_start = findViewById(R.id.get_started_btn);
        firstDot = findViewById(R.id.image_first_dot);
        seconddot = findViewById(R.id.image_second_dot);
        thirdDot = findViewById(R.id.image_third_dot);
        btn_next = findViewById(R.id.next_btn) ;

        SliderAdapter adapter = new SliderAdapter(getBaseContext());
        viewPager.setAdapter(adapter);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = viewPager.getCurrentItem();
                if (position < 2 ){
                    viewPager.setCurrentItem(position + 1 );
                }
            }
        });
        btn_get_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext() , LoginActivity.class) ;
                startActivity(intent);
                finish();
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                addDots(position);

                if (position == 0) {
                    btn_get_start.setVisibility(View.INVISIBLE);
                    btn_next.setVisibility(View.VISIBLE);
                } else if (position == 1) {
                    btn_get_start.setVisibility(View.INVISIBLE);
                    btn_next.setVisibility(View.VISIBLE);
                } else {
                    btn_get_start.setVisibility(View.VISIBLE);
                    btn_get_start.setTranslationX(600) ;
                    btn_get_start.animate().translationX(0).alpha(1).setDuration(800).start();

                    btn_next.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void addDots(int position) {

        if (position == 0) {
            firstDot.setImageDrawable(ContextCompat.getDrawable(
                    getApplicationContext()
                    ,R.drawable.red_dot));
            seconddot.setImageDrawable(ContextCompat.getDrawable(
                    getApplicationContext()
                    ,R.drawable.grey_dot));
            thirdDot.setImageDrawable(ContextCompat.getDrawable(
                    getApplicationContext()
                    ,R.drawable.grey_dot));
        }
        if (position == 1) {
            firstDot.setImageDrawable(ContextCompat.getDrawable(
                    getApplicationContext()
                    ,R.drawable.red_dot));
            seconddot.setImageDrawable(ContextCompat.getDrawable(
                    getApplicationContext()
                    ,R.drawable.red_dot));
            thirdDot.setImageDrawable(ContextCompat.getDrawable(
                    getApplicationContext()
                    ,R.drawable.grey_dot));
        }
        if (position == 2) {
            firstDot.setImageDrawable(ContextCompat.getDrawable(
                    getApplicationContext()
                    ,R.drawable.red_dot));
            seconddot.setImageDrawable(ContextCompat.getDrawable(
                    getApplicationContext()
                    ,R.drawable.red_dot));
            thirdDot.setImageDrawable(ContextCompat.getDrawable(
                    getApplicationContext()
                    ,R.drawable.red_dot));
        }


    }
}
