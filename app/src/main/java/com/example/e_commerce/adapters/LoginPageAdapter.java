package com.example.e_commerce.adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.e_commerce.Fragments.LoginFragment;
import com.example.e_commerce.Fragments.SignUpFragment;

import java.util.ArrayList;

public class LoginPageAdapter extends FragmentPagerAdapter {

    ArrayList<String> tabs = new ArrayList<>();
    public LoginPageAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }


    public void addTab(String tab) {
        tabs.add(tab);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabs.get(position);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                LoginFragment loginFragment = new LoginFragment();
                return loginFragment;
            case 1:
                SignUpFragment signUpFragment = new SignUpFragment();
                return signUpFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabs.size();
    }
}
