package com.gfree_application.gfree.RestaurantReviewPackage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gfree_application.gfree.R;

import java.util.ArrayList;

public class UpdateRestaurantReviewRecyclerAdapter extends RecyclerView.Adapter<UpdateRestaurantReviewRecyclerAdapter.MyViewHolder> {

    ArrayList<RestaurantReview> mList;
    Context context;

    public UpdateRestaurantReviewRecyclerAdapter(Context context, ArrayList<RestaurantReview> mList){
        this.mList = mList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.update_reviewitem, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        RestaurantReview restaurantReview = mList.get(position);
        holder.restaurantName.setText(restaurantReview.getRestaurantName());
        holder.reviewDescription.setText(restaurantReview.getReviewDescription());
        holder.starRating.setRating(restaurantReview.getRating());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView restaurantName, reviewDescription;
        RatingBar starRating;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            restaurantName = itemView.findViewById(R.id.restaurant_review_name_text);
            reviewDescription = itemView.findViewById(R.id.restaurant_review_description_text);
            starRating = itemView.findViewById(R.id.restaurant_review_rating_bar);
        }
    }
}
