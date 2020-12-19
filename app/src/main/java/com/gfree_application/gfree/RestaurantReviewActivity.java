package com.gfree_application.gfree;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class RestaurantReviewActivity extends AppCompatActivity {

    private Button createReview, readReview, updateReview, deleteReview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_review);

        createReview = findViewById(R.id.restaurant_review_create_button);
        readReview = findViewById(R.id.restaurant_review_read_button);
        updateReview = findViewById(R.id.restaurant_review_update_button);
        deleteReview = findViewById(R.id.restaurant_review_delete_button);

        createReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RestaurantReviewActivity.this, CreateResterauntReviewActivity.class));
            }
        });

        readReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        updateReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        deleteReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}