package com.example.e_commerce.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.example.e_commerce.R;

public class SliderAdapter extends PagerAdapter {


    Context context;

    public SliderAdapter(Context context) {
        this.context = context;
    }

    int imagesArray[] = {
            R.drawable.onboard1,
            R.drawable.onboard2,
            R.drawable.onboard3
    };


    int headingArray[] = {
            R.string.onboard1_first_tv,
            R.string.onboard2_first_tv,
            R.string.onboard3_first_tv
    };
    int descriptionArray[] = {
            R.string.onboard1_second_tv,
            R.string.onboard2_second_tv,
            R.string.onboard3_second_tv
    };

    @Override
    public int getCount() {
        return imagesArray.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (ConstraintLayout) object;
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.sliding_layout , (ViewGroup) container, false) ;

        ImageView imageView = v.findViewById(R.id.slider_img) ;
        TextView tv_heading = v.findViewById(R.id.heading) ;
        TextView tv_descripton = v.findViewById(R.id.description) ;


        imageView.setImageResource(imagesArray[position]);
        tv_heading.setText(headingArray[position]);
        tv_descripton.setText(descriptionArray[position]);

        container.addView(v);
        return v;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout)object);
    }
}



