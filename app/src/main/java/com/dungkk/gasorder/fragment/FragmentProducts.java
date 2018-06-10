package com.dungkk.gasorder.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.dungkk.gasorder.R;
import com.dungkk.gasorder.passingObjects.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class FragmentProducts extends Fragment implements View.OnClickListener {


    RecyclerView recyclerView;
    private Toolbar toolbar;
    LinearLayout gas, devices, maintenanceServices, allProduct, product;
    TextView textGas, textDevices, textMaintance, textAllProduct;
    ImageView iconGas, iconDevices, iconMaintance, iconAllProduct;
    String url = Server.getAddress() + "/products";
    static JSONArray products;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_products, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init();


        startInitUnSelect(gas, textGas, iconGas, R.drawable.gas);
        startInitSelect(allProduct, textAllProduct, iconAllProduct, R.drawable.all_white);
        startInitUnSelect(devices, textDevices, iconDevices, R.drawable.device);
        startInitUnSelect(maintenanceServices, textMaintance, iconMaintance, R.drawable.maintanance);
        getArrayProduct();

    }


    private void startInitSelect(LinearLayout linearLayout, TextView textView, ImageView imageView, int id) {
        linearLayout.setBackgroundColor(Color.parseColor("#FFBC4747"));
        textView.setTextColor(getActivity().getResources().getColor(R.color.white));
        imageView.setImageResource(id);
    }

    private void startInitUnSelect(LinearLayout linearLayout, TextView textView, ImageView imageView, int id) {
        linearLayout.setBackgroundColor(Color.parseColor("#00000000"));
        ;
        textView.setTextColor(getActivity().getResources().getColor(R.color.product_list));
        imageView.setImageResource(id);
    }

    public void init() {

        textGas = getView().findViewById(R.id.text_gas);
        textDevices = getView().findViewById(R.id.text_devices);
        textMaintance = getView().findViewById(R.id.text_maintanance);
        textAllProduct = getView().findViewById(R.id.text_all);

        iconGas = getView().findViewById(R.id.icon_gas);
        iconDevices = getView().findViewById(R.id.icon_devices);
        iconMaintance = getView().findViewById(R.id.icon_maintanance);
        iconAllProduct = getView().findViewById(R.id.icon_all);

        gas = getView().findViewById(R.id.product_gas);
        devices = getView().findViewById(R.id.product_devices);
        maintenanceServices = getView().findViewById(R.id.maintenance_service);
        allProduct = getView().findViewById(R.id.all_product);
        product = getView().findViewById(R.id.product_layout);
        product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        gas.setOnClickListener(this);
        devices.setOnClickListener(this);
        maintenanceServices.setOnClickListener(this);
        allProduct.setOnClickListener(this);

        toolbar = getView().findViewById(R.id.toolbar);
        ((AppCompatActivity) getContext()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getContext()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getContext()).getSupportActionBar().setHomeButtonEnabled(true);
        ((AppCompatActivity) getContext()).setTitle("Products");
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

    private void getArrayProduct() {

        final JSONArray productArr = new JSONArray();

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.POST, url, productArr,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {

                            products = response;

                            RecyclerViewAdapter_Product adapter = new RecyclerViewAdapter_Product(products);
                            recyclerView = getView().findViewById(R.id.rv_product);
                            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setAdapter(adapter);

                            Log.e("History", response.getJSONObject(0).toString());

                        } catch (JSONException e) {

                            // create a alterDialog Builder
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());

                            alertDialogBuilder.setTitle("Empty List").setMessage("No product available ... !").setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        // create a alterDialog Builder
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());

                        alertDialogBuilder.setTitle("Connection failed").setMessage("Please check the internet connection again...").setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
    }

    public void getArrayTypeGas(int type) {
        try {

            if (products != null && products.length() > 0) {
                RecyclerViewAdapter_Product adapter = new RecyclerViewAdapter_Product(products, type);
                recyclerView = (RecyclerView) getView().findViewById(R.id.rv_product);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.product_gas:
                startInitSelect(gas, textGas, iconGas, R.drawable.gas_white);
                startInitUnSelect(allProduct, textAllProduct, iconAllProduct, R.drawable.all);
                startInitUnSelect(devices, textDevices, iconDevices, R.drawable.device);
                startInitUnSelect(maintenanceServices, textMaintance, iconMaintance, R.drawable.maintanance);
                getArrayTypeGas(1);
                break;
            case R.id.product_devices:
                startInitUnSelect(gas, textGas, iconGas, R.drawable.gas);
                startInitUnSelect(allProduct, textAllProduct, iconAllProduct, R.drawable.all);
                startInitSelect(devices, textDevices, iconDevices, R.drawable.device_white);
                startInitUnSelect(maintenanceServices, textMaintance, iconMaintance, R.drawable.maintanance);
                getArrayTypeGas(2);
                break;
            case R.id.maintenance_service:
                startInitUnSelect(gas, textGas, iconGas, R.drawable.gas);
                startInitUnSelect(allProduct, textAllProduct, iconAllProduct, R.drawable.all);
                startInitUnSelect(devices, textDevices, iconDevices, R.drawable.device);
                startInitSelect(maintenanceServices, textMaintance, iconMaintance, R.drawable.maintanance_white);
                getArrayTypeGas(3);
                break;
            case R.id.all_product:
                startInitUnSelect(gas, textGas, iconGas, R.drawable.gas);
                startInitSelect(allProduct, textAllProduct, iconAllProduct, R.drawable.all_white);
                startInitUnSelect(devices, textDevices, iconDevices, R.drawable.device);
                startInitUnSelect(maintenanceServices, textMaintance, iconMaintance, R.drawable.maintanance);
                getArrayTypeGas(4);
                break;
            case R.id.product_layout:
                break;

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

