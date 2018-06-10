package com.dungkk.gasorder.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dungkk.gasorder.R;

/**
 * Created by Admin on 9/6/2018.
 */

@SuppressLint("ValidFragment")
public class FragmentTip_Paper extends Fragment{

    private Toolbar toolbar;
    int news_img_id;
    int news_text_id;
    ImageView news_img;
    TextView news_text;


    @SuppressLint("ValidFragment")
    public FragmentTip_Paper(int news_img_id, int news_text_id) {
        this.news_img_id = news_img_id;
        this.news_text_id = news_text_id;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tips_paper, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init();

    }


    public void init() {

        news_img = getView().findViewById(R.id.news_img);
        news_text = getView().findViewById(R.id.news_text);
        news_img.setImageResource(news_img_id);
        news_text.setText(news_text_id);

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

