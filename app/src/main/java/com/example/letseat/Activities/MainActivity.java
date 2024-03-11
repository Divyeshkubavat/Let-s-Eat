package com.example.letseat.Activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.letseat.R;
import com.example.letseat.Utilities.NetworkChangeListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView navigationView;
    ProgressDialog pg;
    NetworkChangeListener listener = new NetworkChangeListener();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pg = new ProgressDialog(this);
        navigationView = findViewById(R.id.bottom_navigation);
        navigationView.setSelectedItemId(R.id.User_Home);
        getSupportFragmentManager().beginTransaction().replace(R.id.body_container,new User_Home()).commit();
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId())
                {
                    case  R.id.User_Home:
                        fragment = new User_Home();
                        break;
                    case  R.id.User_Cart:
                        fragment = new User_Cart();

                        break;
                    case  R.id.User_Profile:
                        fragment = new User_Profile();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.body_container,fragment).commit();
                return true;
            }
        });
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Do you want to Exit 🥺🥺🥺 ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application
                finishAffinity();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user select "No", just cancel this dialog and continue with app
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
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