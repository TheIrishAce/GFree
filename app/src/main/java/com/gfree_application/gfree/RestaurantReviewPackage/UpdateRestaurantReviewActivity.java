package com.gfree_application.gfree.RestaurantReviewPackage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

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

public class UpdateRestaurantReviewActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference root = db.getReference().child("reviews");
    private UpdateRestaurantReviewRecyclerAdapter adapter;
    private ArrayList<RestaurantReview> reviewsArrayList = new ArrayList<>();

    String email, name, uid, providerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_restaurant_review);
        Context context = this;
        reviewsArrayList = new ArrayList<>();

        adapter = new UpdateRestaurantReviewRecyclerAdapter(context, reviewsArrayList);

        recyclerView = findViewById(R.id.readReviewsRecyclerView);
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
                int counter=0;
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

    public void updateRestaurantReviewInformation(View view){

    }
}