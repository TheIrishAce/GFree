package com.gfree_application.gfree.RestaurantReviewPackage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gfree_application.gfree.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class UpdateRestaurantReviewRecyclerAdapter extends RecyclerView.Adapter<UpdateRestaurantReviewRecyclerAdapter.MyViewHolder> {

    private ArrayList<RestaurantReview> mList;
    private Context context;
    private OnEditReviewListner mOnEditReviewListner;

    public UpdateRestaurantReviewRecyclerAdapter(Context context, ArrayList<RestaurantReview> mList, OnEditReviewListner onEditReviewListner){
        this.mList = mList;
        this.context = context;
        this.mOnEditReviewListner = onEditReviewListner;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.update_reviewitem, parent, false);
        return new MyViewHolder(v, mOnEditReviewListner);
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

        OnEditReviewListner onEditReviewListner;

        public MyViewHolder(@NonNull View itemView, OnEditReviewListner onEditReviewListner) {
            super(itemView);

            this.onEditReviewListner = onEditReviewListner;

            restaurantName = itemView.findViewById(R.id.restaurant_review_name_text);
            reviewDescription = itemView.findViewById(R.id.restaurant_review_description_text);
            starRating = itemView.findViewById(R.id.restaurant_review_rating_bar);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onEditReviewListner.OnEditReviewClick(getAdapterPosition());
        }
    }

    public interface OnEditReviewListner{
        void OnEditReviewClick(int position);
    }
}
