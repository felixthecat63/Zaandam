package com.example.zaandam;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * RecyclerView adapter to display a list of location cards on top of the map
 */
public class LocationRecyclerViewAdapter extends
        RecyclerView.Adapter<LocationRecyclerViewAdapter.ViewHolder> {

    private List<IndividualLocation> listOfLocations;
    private Context context;
    private static ClickListener clickListener;
    private Drawable emojiForCircle = null;
    private Drawable backgroundCircle = null;
    private int upperCardSectionColor = 0;

    private int locationNameColor = 0;
    private int locationAddressColor = 0;
    private int categoryColor = 0;
    private int categoryHeaderColor = 0;
    private int coordinatesColor = 0;
    private int coordinatesHeaderColor = 0;
    private int locationDistanceNumColor = 0;
    private int milesAbbreviationColor = 0;

    public LocationRecyclerViewAdapter(List<IndividualLocation> styles,
                                       Context context, ClickListener cardClickListener) {
        this.context = context;
        this.listOfLocations = styles;
        this.clickListener = cardClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int singleRvCardToUse = R.layout.single_location_map_view_rv_card;
        View itemView = LayoutInflater.from(parent.getContext()).inflate(singleRvCardToUse, parent, false);
        return new ViewHolder(itemView);
    }

    public interface ClickListener {
        void onItemClick(int position);
    }

    @Override
    public int getItemCount() {
        return listOfLocations.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder card, int position) {

        IndividualLocation locationCard = listOfLocations.get(position);

        card.nameTextView.setText(locationCard.getName());
        card.addressTextView.setText(locationCard.getAddress());
        card.distanceNumberTextView.setText(locationCard.getDistance());
        card.coordinatesTextView.setText(locationCard.getCoordinates());
        card.categoryTextView.setText(locationCard.getCategory());

        emojiForCircle = ResourcesCompat.getDrawable(context.getResources(), R.drawable.green_user_location, null);
        backgroundCircle = ResourcesCompat.getDrawable(context.getResources(), R.drawable.white_circle, null);
        setColors(R.color.colorPrimaryDark_neutral, R.color.black, R.color.black, R.color.black,
                R.color.black, R.color.black,
                R.color.black, R.color.black, R.color.black);


        card.emojiImageView.setImageDrawable(emojiForCircle);
        card.constraintUpperColorSection.setBackgroundColor(upperCardSectionColor);
        card.backgroundCircleImageView.setImageDrawable(backgroundCircle);
        card.nameTextView.setTextColor(locationNameColor);
        card.categoryTextView.setTextColor(categoryColor);
        card.coordinatesTextView.setTextColor(coordinatesColor);
        card.coordinatesHeaderTextView.setTextColor(coordinatesHeaderColor);
        card.distanceNumberTextView.setTextColor(locationDistanceNumColor);
        card.milesAbbreviationTextView.setTextColor(milesAbbreviationColor);
        card.addressTextView.setTextColor(locationAddressColor);
        card.categoryHeaderTextView.setTextColor(categoryHeaderColor);
    }

    private void setColors(int colorForUpperCard, int colorForName, int colorForAddress,
                           int colorForHours, int colorForHoursHeader, int colorForPhoneNum,
                           int colorForPhoneHeader, int colorForDistanceNum, int colorForMilesAbbreviation) {
        upperCardSectionColor = ResourcesCompat.getColor(context.getResources(), colorForUpperCard, null);
        locationNameColor = ResourcesCompat.getColor(context.getResources(), colorForName, null);
        locationAddressColor = ResourcesCompat.getColor(context.getResources(), colorForAddress, null);
        coordinatesColor = ResourcesCompat.getColor(context.getResources(), colorForHours, null);
        coordinatesHeaderColor = ResourcesCompat.getColor(context.getResources(), colorForHoursHeader, null);
        categoryColor = ResourcesCompat.getColor(context.getResources(), colorForPhoneNum, null);
        categoryHeaderColor = ResourcesCompat.getColor(context.getResources(), colorForPhoneHeader, null);
        locationDistanceNumColor = ResourcesCompat.getColor(context.getResources(), colorForDistanceNum, null);
        milesAbbreviationColor = ResourcesCompat.getColor(context.getResources(), colorForMilesAbbreviation, null);
    }


    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView nameTextView;
        TextView addressTextView;
        TextView categoryTextView;
        TextView coordinatesTextView;
        TextView distanceNumberTextView;
        TextView coordinatesHeaderTextView;
        TextView milesAbbreviationTextView;
        TextView categoryHeaderTextView;
        ConstraintLayout constraintUpperColorSection;
        CardView cardView;
        ImageView backgroundCircleImageView;
        ImageView emojiImageView;

        ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.location_name_tv);
            addressTextView = itemView.findViewById(R.id.location_description_tv);
            categoryTextView = itemView.findViewById(R.id.category_tv);
            categoryHeaderTextView = itemView.findViewById(R.id.category_header_tv);
            coordinatesTextView = itemView.findViewById(R.id.coordinates_tv);
            backgroundCircleImageView = itemView.findViewById(R.id.background_circle);
            emojiImageView = itemView.findViewById(R.id.emoji);
            constraintUpperColorSection = itemView.findViewById(R.id.constraint_upper_color);
            distanceNumberTextView = itemView.findViewById(R.id.distance_num_tv);
            coordinatesHeaderTextView = itemView.findViewById(R.id.coordinates_header_tv);
            milesAbbreviationTextView = itemView.findViewById(R.id.miles_mi_tv);
            cardView = itemView.findViewById(R.id.map_view_location_card);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onItemClick(getLayoutPosition());
                }
            });
        }

        @Override
        public void onClick(View view) {
        }
    }
}