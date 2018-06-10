package com.dungkk.gasorder.extensions;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import static android.content.Context.LOCATION_SERVICE;

public class GPSTracker implements LocationListener {

    Context context;
    LocationManager lm;
    Location location;

    public GPSTracker(Context context) {
        super();
        this.context = context;
    }

    public Location getLocation(){
        if (ContextCompat.checkSelfPermission( context, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED) {
            Log.e("fist","error");
            Toast.makeText(context, "Permission not granted", Toast.LENGTH_LONG).show();
            return null;
        }
        try {
            lm = (LocationManager) context.getSystemService(LOCATION_SERVICE);
            boolean isGPSEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
            if (isGPSEnabled){
                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,0,this);
                location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                return location;
            }else{
                Toast.makeText(context, "Please enable GPS", Toast.LENGTH_LONG).show();
                Log.e("sec","error");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }
}