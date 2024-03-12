package com.example.letseat.Activities;

import static com.example.letseat.Activities.MainActivity.listener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.letseat.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class User_Current_Location extends AppCompatActivity {

    FusedLocationProviderClient fusedLocationProviderClient;
    TextView Current_Address,Current_City;
    String Address,City,Country;

    private final static int REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_current_location);
        Current_Address =findViewById(R.id.User_Current_Location_Textview);
        Current_City=findViewById(R.id.User_Current_Location_Textview_2);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();

    }
    private void getLastLocation(){

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){


            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {

                            if (location != null){



                                try {
                                    Geocoder geocoder = new Geocoder(User_Current_Location.this, Locale.getDefault());
                                    List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                                    Address= addresses.get(0).getAddressLine(0);
                                    City = addresses.get(0).getLocality();
                                    Country = addresses.get(0).getCountryName();
                                    Current_Address.setText(Address);
                                    Current_City.setText(City);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                        }
                    });
        }else {

            askPermission();
        }
    }

    private void askPermission() {
        ActivityCompat.requestPermissions(User_Current_Location.this,new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @org.jetbrains.annotations.NotNull String[] permissions, @NonNull @org.jetbrains.annotations.NotNull int[] grantResults) {
        if (requestCode == REQUEST_CODE){

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){


                getLastLocation();

            }else {


                Toast.makeText(User_Current_Location.this,"Please provide the required permission",Toast.LENGTH_SHORT).show();

            }



        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(listener,filter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(listener);
        super.onStop();
    }
}