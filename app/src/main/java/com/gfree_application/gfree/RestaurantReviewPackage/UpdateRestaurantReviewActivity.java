package com.gfree_application.gfree.RestaurantReviewPackage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.gfree_application.gfree.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UpdateRestaurantReviewActivity extends AppCompatActivity implements UpdateRestaurantReviewRecyclerAdapter.OnEditReviewListner {

    private RecyclerView recyclerView;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference root = db.getReference().child("reviews");

    private UpdateRestaurantReviewRecyclerAdapter adapter;
    private ArrayList<RestaurantReview> reviewsArrayList = new ArrayList<>();

    private String email, name, uid, providerId;
    private int counter=0;

    private Button submitEditedReview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_restaurant_review);
        Context context = this;
        reviewsArrayList = new ArrayList<>();

        adapter = new UpdateRestaurantReviewRecyclerAdapter(context, reviewsArrayList, this);

        recyclerView = findViewById(R.id.updateReviewsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(adapter);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            for (UserInfo profile : user.getProviderData()) {
                // Id of the provider (ex: google.com)
                providerId = profile.getProviderId();

                // UID specific to the provider
                uid = profile.getUid();

                // Name, email address, and profile photo Url
                name = profile.getDisplayName();
                email = profile.getEmail();
            }
        }



        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                counter=0;
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    RestaurantReview restaurantReview = dataSnapshot.getValue(RestaurantReview.class);
                    if (restaurantReview.getUserEmail().equals(email)) {
                        reviewsArrayList.add(restaurantReview);
                        Log.d("Reviews Array List", String.valueOf(reviewsArrayList.get(counter)));
                        Log.d("Reviews Array List Email", String.valueOf(reviewsArrayList.get(counter).userEmail));
                        Log.d("Reviews Array List Rating", String.valueOf(reviewsArrayList.get(counter).rating));
                        Log.d("Reviews Array List Restaurant Name", String.valueOf(reviewsArrayList.get(counter).restaurantName));
                        Log.d("Reviews Array List Restaurant Description", String.valueOf(reviewsArrayList.get(counter).reviewDescription));
                        counter++;
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void OnEditReviewClick(int position) {
        Intent showReviewEditActivity = new Intent(getBaseContext(), EditRestaurantReviewActivity.class);

        showReviewEditActivity.putExtra("SELECTED REVIEW ID", reviewsArrayList.get(position).reviewId);
        showReviewEditActivity.putExtra("SELECTED REVIEW RESTAURANT NAME", reviewsArrayList.get(position).restaurantName);
        showReviewEditActivity.putExtra("SELECTED REVIEW RESTAURANT DESCRIPTION", reviewsArrayList.get(position).reviewDescription);
        showReviewEditActivity.putExtra("SELECTED REVIEW RESTAURANT RATING", reviewsArrayList.get(position).rating);
        Log.d("SELECTED REVIEW ID", reviewsArrayList.get(position).reviewId);
        Log.d("SELECTED REVIEW RESTAURANT NAME", reviewsArrayList.get(position).restaurantName);
        Log.d("SELECTED REVIEW RESTAURANT DESCRIPTION", reviewsArrayList.get(position).reviewDescription);
        Log.d("SELECTED REVIEW RESTAURANT RATING", String.valueOf(reviewsArrayList.get(position).rating));
        startActivity(showReviewEditActivity);
    }
}