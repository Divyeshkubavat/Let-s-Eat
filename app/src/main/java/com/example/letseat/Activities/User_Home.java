package com.example.letseat.Activities;

import android.content.Intent;
import android.os.Bundle;;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.letseat.R;


public class User_Home extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_user__home, container, false);
        Button BackToTop = view.findViewById(R.id.BackToTop);
        ScrollView sv = view.findViewById(R.id.scrollview);
        BackToTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sv.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sv.smoothScrollTo(100,sv.getTop());
                    }
                },0);
            }
        });
        return view;
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_bar,menu);
        MenuItem item = menu.findItem(R.id.User_Home_Screen_Search);
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if(id == R.id.User_Home_Screen_Search)
                {
                    startActivity(new Intent(getContext().getApplicationContext(),User_Home_Screen_Search.class));
                }
                return false;
            }
        });
        super.onCreateOptionsMenu(menu,inflater);
    }
}