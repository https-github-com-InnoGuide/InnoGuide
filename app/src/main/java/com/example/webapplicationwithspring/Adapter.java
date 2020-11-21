package com.example.webapplicationwithspring;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.webapplicationwithspring.Events.Place;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class Adapter extends PagerAdapter {

    private List<Place> places;
    private LayoutInflater layoutInflater;
    private Context context;
    TextView seeOnMap;

    public Adapter(List<Place> places, Context context) {
        this.places = places;
        this.context = context;

    }
//get amount of places in the list
    @Override
    public int getCount() {
        return places.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.place, container , false);
// initialize each text view in the card with data from places list
        TextView title = view.findViewById(R.id.card_title);
        TextView address = view.findViewById(R.id.card_address);
        TextView workHours = view.findViewById(R.id.card_workHours);
        TextView phoneNumber= view.findViewById(R.id.card_phoneNumber);
        TextView telegram = view.findViewById(R.id.card_telegram);
        TextView text_click = view.findViewById(R.id.card_clickText);
        TextView description = view.findViewById(R.id.card_description);

        title.setText(places.get(position).getName());
        address.setText(places.get(position).getAddress());
        workHours.setText(places.get(position).getWorkHours());
        phoneNumber.setText(places.get(position).getPhoneNumber());
        telegram.setText(places.get(position).getTelegram());
        description.setText(places.get(position).getDescription());
        text_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String location = places.get(position).getLocation();
                String[] coords = location.split(",");
                Double latitude = Double.parseDouble(coords[0]);
                Double longitude = Double.parseDouble(coords[1]);
                LatLng latLng = new LatLng(latitude, longitude);
                context.startActivity(new Intent(context, MapActivity.class));
            }
        });
        container.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
