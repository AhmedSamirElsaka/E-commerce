package com.example.e_commerce.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.e_commerce.R;
import com.example.e_commerce.adapters.LoginPageAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
public class LoginActivity extends AppCompatActivity {
    public static final String PARENT_DB_NAME = "Users";
    TabLayout tabLayout;
    ViewPager viewPager;
    FloatingActionButton fab_google, fab_facebook, fab_twitter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        tabLayout = findViewById(R.id.login_page_tab_layout);
        viewPager = findViewById(R.id.login_page_view_pager);
        fab_google = findViewById(R.id.login_fab_google);
        fab_facebook = findViewById(R.id.login_fab_facebook);
        fab_twitter = findViewById(R.id.login_fab_twitter);

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        LoginPageAdapter adapter = new LoginPageAdapter(getSupportFragmentManager());
        adapter.addTab("Login");
        adapter.addTab("SignUp");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        fab_google.setTranslationY(300);
        fab_facebook.setTranslationY(300);
        fab_twitter.setTranslationY(300);
        tabLayout.setTranslationX(300);

        tabLayout.setAlpha(0);

        fab_facebook.animate().translationY(0).alpha(1).setDuration(2000).setStartDelay(400).start();
        fab_google.animate().translationY(0).alpha(1).setDuration(2000).setStartDelay(600).start();
        fab_twitter.animate().translationY(0).alpha(1).setDuration(2000).setStartDelay(800).start();
        tabLayout.animate().translationX(0).alpha(1).setDuration(2000).setStartDelay(200).start();

        fab_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "Sorry, this service does not avilable now ", Toast.LENGTH_SHORT).show();
            }
        });
        fab_twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "Sorry, this service does not avilable now ", Toast.LENGTH_SHORT).show();
            }
        });
        fab_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "Sorry, this service does not avilable now ", Toast.LENGTH_SHORT).show();
            }
        });
    }
}