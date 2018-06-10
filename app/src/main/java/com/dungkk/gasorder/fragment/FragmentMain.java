package com.dungkk.gasorder.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dungkk.gasorder.MainActivity;
import com.dungkk.gasorder.adapter.MainSliderAdapter;
import com.dungkk.gasorder.passingObjects.location;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.dungkk.gasorder.R;
import com.example.bannerslider.Slider;

import java.io.IOException;


public class FragmentMain extends Fragment implements View.OnClickListener{

    private FragmentTransaction transaction;
    private LinearLayout layout_order, layout_product, layout_tips;
    private View view;
    private Slider slider;
    private String[] arrslides;


    @Override
    public void onResume() {
        super.onResume();

        if(getView() == null){
            return;
        }

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
                    System.exit(0);
                    return true;
                }
                return false;
            }
        });
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_main, container, false);


        setSlider(view);

        layout_order = view.findViewById(R.id.layout_order);
        layout_product = view.findViewById(R.id.layout_product);
        layout_tips = view.findViewById(R.id.layout_tips);

        layout_order.setOnClickListener(this);
        layout_product.setOnClickListener(this);
        layout_tips.setOnClickListener(this);

        return view;
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager manager = getFragmentManager();
        transaction = manager.beginTransaction();
        transaction.replace(R.id.content_main, fragment, FragmentMain.class.getSimpleName())
                .addToBackStack(FragmentMain.class.getSimpleName())
                .commit();
    }

    private void setSlider(View view) {
        slider = view.findViewById(R.id.banner_slider1);

        slider.setInterval(2000);
        slider.setIndicatorSize(24);
        slider.setAnimateIndicators(true);
        slider.showIndicators();
        slider.setSelectedSlideIndicator(ContextCompat.getDrawable(getContext(), R.drawable.selected_slide_indicator));
        slider.setUnSelectedSlideIndicator(ContextCompat.getDrawable(getContext(), R.drawable.unselected_slide_indicator));
        arrslides = getDataFromAsset();
        slider.setAdapter(new MainSliderAdapter(arrslides));
        slider.setSelectedSlide(0);
        slider.setLoopSlides(true);

    }


    private String[] getDataFromAsset() {
        String[] data = null;
        try {
            data = getActivity().getAssets().list("slide");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_order:
                replaceFragment(new FragmentOrder());
                break;
            case R.id.layout_product:
                replaceFragment(new FragmentProducts());
                break;
            case R.id.layout_tips:
                replaceFragment(new FragmentTips());
                break;
        }
    }
}
