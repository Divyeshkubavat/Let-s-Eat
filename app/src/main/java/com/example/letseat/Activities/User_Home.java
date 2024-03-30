package com.example.letseat.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.letseat.Adapter.OfferAdapter;
import com.example.letseat.Adapter.productAdapter;
import com.example.letseat.Model.Offer;
import com.example.letseat.Model.Product;
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
    RecyclerView Burger_Recycler,Pizza_Recycler,Drink_Recycler,Combo_Recycler;
    ArrayList<Product> burgerList,pizzaList,comboList,drinkList;
    productAdapter burgerAdapter,pizzaAdapter,drinkAdapter,comboAdapter;
    ProgressDialog pg;
    TextView burgerExplore,pizzaExplore,comboExplore,drinkExplore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_user__home, container, false);
        Button BackToTop = view.findViewById(R.id.BackToTop);
        ScrollView sv = view.findViewById(R.id.scrollview);
        Burger_Recycler=view.findViewById(R.id.User_Home_Burger_Recyclerview);
        Pizza_Recycler=view.findViewById(R.id.User_Home_Pizza_Recyclerview);
        Drink_Recycler=view.findViewById(R.id.User_Home_Drink_Recyclerview);
        Combo_Recycler=view.findViewById(R.id.User_Home_Combo_Recyclerview);
        burgerExplore=view.findViewById(R.id.User_Home_Burger_Explore);
        pizzaExplore=view.findViewById(R.id.User_Home_Pizza_Explore);
        comboExplore=view.findViewById(R.id.User_Home_Combo_Explore);
        drinkExplore=view.findViewById(R.id.User_Home_Drink_Explore);
        setHasOptionsMenu(true);
        burgerExplore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), User_Burger_Explore.class));
            }
        });
        pizzaExplore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), User_Pizza_Explore.class));
            }
        });
        comboExplore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), User_Combo_Explore.class));
            }
        });
        drinkExplore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), User_Drink_Explore.class));
            }
        });
        retrofitServices = new RetrofitServices();
        userApi = retrofitServices.getRetrofit().create(UserApi.class);
        burgerList=new ArrayList<>();
        pizzaList=new ArrayList<>();
        comboList=new ArrayList<>();
        drinkList=new ArrayList<>();
        User_Home_ImageSlider = view.findViewById(R.id.User_Home_ImageSlider);
        offerArrayList = new ArrayList<>();
        pg = new ProgressDialog(getActivity());
        pg.setTitle("Loading..... ");
        pg.setMessage("Please wait we fetch your data... ");
        pg.setIcon(R.drawable.logo);
        pg.setCanceledOnTouchOutside(false);
        pg.show();
        getOffer();
        setProduct();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pg.dismiss();
            }
        },1200);
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
               if(id==R.id.User_Home_Screen_Search)
               {
                   startActivity(new Intent(getContext(),User_Home_Screen_Search.class));
                   return true;
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

    private void setProduct()
    {
        userApi.getSingleProduct(201).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                burgerList = (ArrayList<Product>) response.body();
                burgerAdapter = new productAdapter(burgerList,getContext());
                Burger_Recycler.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
                burgerAdapter.notifyDataSetChanged();
                Burger_Recycler.setAdapter(burgerAdapter);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });
        userApi.getSingleProduct(202).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                pizzaList = (ArrayList<Product>) response.body();
                pizzaAdapter = new productAdapter(pizzaList,getContext());
                Pizza_Recycler.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
                pizzaAdapter.notifyDataSetChanged();
                Pizza_Recycler.setAdapter(pizzaAdapter);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });
        userApi.getSingleProduct(203).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                comboList = (ArrayList<Product>) response.body();
                comboAdapter = new productAdapter(comboList,getContext());
                Combo_Recycler.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
                comboAdapter.notifyDataSetChanged();
                Combo_Recycler.setAdapter(comboAdapter);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });
        userApi.getSingleProduct(204).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                drinkList = (ArrayList<Product>) response.body();
                drinkAdapter = new productAdapter(drinkList,getContext());
                Drink_Recycler.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
                drinkAdapter.notifyDataSetChanged();
                Drink_Recycler.setAdapter(drinkAdapter);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

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