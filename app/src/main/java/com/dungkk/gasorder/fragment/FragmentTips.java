package com.dungkk.gasorder.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dungkk.gasorder.R;


public class FragmentTips extends Fragment {

    private Toolbar toolbar;
    private FragmentTransaction transaction;
    LinearLayout news_1, news_2, news_3, news_4, news_5;
    int news_img_id;
    int news_text_id;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tips, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init();

    }


    public void init() {

        news_1 = getView().findViewById(R.id.news_1);
        news_2 = getView().findViewById(R.id.news_2);
        news_3 = getView().findViewById(R.id.news_3);
        news_4 = getView().findViewById(R.id.news_4);
        news_5 = getView().findViewById(R.id.news_5);

        news_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new FragmentTip_Paper(R.drawable.tips_img_1, R.string.tips_text_1));
            }
        });

        news_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new FragmentTip_Paper(R.drawable.tips_img_2, R.string.tips_text_2));
            }
        });

        news_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new FragmentTip_Paper(R.drawable.tips_img_3, R.string.tips_text_3));
            }
        });

        news_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new FragmentTip_Paper(R.drawable.tips_img_4, R.string.tips_text_4));
            }
        });

        news_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new FragmentTip_Paper(R.drawable.tips_img_5, R.string.tips_text_5));
            }
        });



        toolbar = getView().findViewById(R.id.toolbar);
        ((AppCompatActivity)getContext()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getContext()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getContext()).getSupportActionBar().setHomeButtonEnabled(true);
        ((AppCompatActivity)getContext()).setTitle("Tips");
        setHasOptionsMenu(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void replaceFragment(Fragment fragment){
        FragmentManager manager = getFragmentManager();
        transaction = manager.beginTransaction();
        transaction.replace(R.id.content_main, fragment, FragmentTips.class.getSimpleName())
                .addToBackStack(FragmentTips.class.getSimpleName())
                .commit();
    }


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
                    return true;
                }
                return false;
            }
        });
    }
}
