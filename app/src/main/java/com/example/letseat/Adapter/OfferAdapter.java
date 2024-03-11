package com.example.letseat.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.denzcoskun.imageslider.ImageSlider;
import com.example.letseat.Activities.User_Burger_Explore;
import com.example.letseat.Activities.User_Combo_Explore;
import com.example.letseat.Activities.User_Drink_Explore;
import com.example.letseat.Activities.User_Pizza_Explore;
import com.example.letseat.Model.Offer;
import com.example.letseat.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class OfferAdapter extends SliderViewAdapter<OfferAdapter.SliderAdapterVH> {

    private Context context;
    private List<Offer> offerItem = new ArrayList<>();
    public OfferAdapter(Context context, List<Offer> offerItem) {
        this.context = context;
        this.offerItem = offerItem;
    }
    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_user_offer_design, null);
        return new SliderAdapterVH(inflate);

    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, int position) {
        final Offer offer = offerItem.get(position);
        Glide.with(context).load(offer.getImageUrl()).into(viewHolder.imageSlider);
        int id = offer.getCategoryId();
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(id==201){
                    view.getContext().startActivity(new Intent(view.getContext(), User_Burger_Explore.class));
                } else if (id==202) {
                    view.getContext().startActivity(new Intent(view.getContext(), User_Pizza_Explore.class));
                }else if(id==203){
                    view.getContext().startActivity(new Intent(view.getContext(), User_Combo_Explore.class));
                } else if (id==204) {
                    view.getContext().startActivity(new Intent(view.getContext(), User_Drink_Explore.class));
                }else {
                    Toast.makeText(context, "Sorry Offer Expire", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getCount() {
        return offerItem.size();
    }
    public class SliderAdapterVH extends ViewHolder {
        ImageView imageSlider;
        public SliderAdapterVH(View itemView) {

            super(itemView);

            imageSlider =itemView.findViewById(R.id.User_Offer_Design_Image);
        }
    }
}
