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

import com.example.letseat.Adapter.OfferAdapter;
import com.example.letseat.Model.Offer;
import com.example.letseat.R;
import com.example.letseat.Retrofit.RetrofitServices;
import com.example.letseat.Retrofit.UserApi;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class User_Home extends Fragment {

    SliderView User_Home_ImageSlider;
    ArrayList<Offer> offerArrayList;
    RetrofitServices retrofitServices;
    UserApi userApi;
    OfferAdapter offerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_user__home, container, false);
        Button BackToTop = view.findViewById(R.id.BackToTop);
        ScrollView sv = view.findViewById(R.id.scrollview);
        retrofitServices = new RetrofitServices();
        userApi = retrofitServices.getRetrofit().create(UserApi.class);
        User_Home_ImageSlider = view.findViewById(R.id.User_Home_ImageSlider);
        offerArrayList = new ArrayList<>();
        getOffer();

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
                if(id == R.id.User_Home_Screen_Filter)
                {
                    startActivity(new Intent(getContext().getApplicationContext(),User_Product_Filter.class));
                }
                return false;
            }
        });
        super.onCreateOptionsMenu(menu,inflater);
    }

    public void getOffer()
    {
        userApi.getAllOffer().enqueue(new Callback<List<Offer>>() {
            @Override
            public void onResponse(Call<List<Offer>> call, Response<List<Offer>> response) {
                offerArrayList = (ArrayList<Offer>) response.body();
                offerAdapter = new OfferAdapter(getContext(),offerArrayList);
                User_Home_ImageSlider.setSliderAdapter(offerAdapter);
                User_Home_ImageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);

                // below line is for setting auto cycle duration.
                User_Home_ImageSlider.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);

                // below line is for setting
                // scroll time animation
                User_Home_ImageSlider.setScrollTimeInSec(3);

                // below line is for setting auto
                // cycle animation to our slider
                User_Home_ImageSlider.setAutoCycle(true);

                // below line is use to start
                // the animation of our slider view.
                User_Home_ImageSlider.startAutoCycle();
            }

            @Override
            public void onFailure(Call<List<Offer>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();

    }
}