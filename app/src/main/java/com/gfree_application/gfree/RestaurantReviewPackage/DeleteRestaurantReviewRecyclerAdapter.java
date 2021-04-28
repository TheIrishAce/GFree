package com.gfree_application.gfree.RestaurantReviewPackage;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gfree_application.gfree.R;

import java.util.ArrayList;

public class DeleteRestaurantReviewRecyclerAdapter extends RecyclerView.Adapter<DeleteRestaurantReviewRecyclerAdapter.MyViewHolder> {

    private ArrayList<RestaurantReview> mList;
    private Context context;
    private OnDeleteReviewListener mOnDeleteReviewListener;

    public DeleteRestaurantReviewRecyclerAdapter(Context context, ArrayList<RestaurantReview> mList, OnDeleteReviewListener onDeleteReviewListener){
        this.mList = mList;
        this.context = context;
        this.mOnDeleteReviewListener = onDeleteReviewListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.update_reviewitem, parent, false);
        return new MyViewHolder(v, mOnDeleteReviewListener);
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

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView restaurantName, reviewDescription;
        RatingBar starRating;
        Button submitEdit;

        OnDeleteReviewListener onDeleteReviewListener;

        public MyViewHolder(@NonNull View itemView, OnDeleteReviewListener onDeleteReviewListener) {
            super(itemView);

            this.onDeleteReviewListener = onDeleteReviewListener;

            restaurantName = itemView.findViewById(R.id.restaurant_review_name_text);
            reviewDescription = itemView.findViewById(R.id.restaurant_review_description_text);
            starRating = itemView.findViewById(R.id.restaurant_review_rating_bar);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onDeleteReviewListener.OnDeleteReviewClick(getAdapterPosition());
        }
    }

    public interface OnDeleteReviewListener {
        void OnDeleteReviewClick(int position);
    }


}
