package com.gfree_application.gfree;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateRestaurantReviewActivity extends AppCompatActivity {

    private Button submitReview;
    private TextInputEditText reviewDescriptionTextInput;
    private EditText restaurantName;
    private RatingBar restaurantRating;

    FirebaseDatabase rootNode;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_restaurant_review);

        submitReview = findViewById(R.id.submit_review_button);
        reviewDescriptionTextInput = findViewById(R.id.create_review_optional_description);
        restaurantName = findViewById(R.id.create_review_resteraunt_name);
        restaurantRating = findViewById(R.id.restaurantRatingBar);

        submitReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(restaurantName.getText().toString().isEmpty()){
                    Toast.makeText(CreateRestaurantReviewActivity.this, "Restaurant name is empty!", Toast.LENGTH_SHORT).show();
                }
                else if(reviewDescriptionTextInput.getText() == null || reviewDescriptionTextInput.length() < 20){
                    Toast.makeText(CreateRestaurantReviewActivity.this, "Review body is empty or isn't larger than 10 characters long.", Toast.LENGTH_SHORT).show();
                }
                else {
                    rootNode = FirebaseDatabase.getInstance();
                    reference = rootNode.getReference("reviews");
                    String id = reference.push().getKey();
                    RestaurantReview review = new RestaurantReview(id, restaurantName.getText().toString(), reviewDescriptionTextInput.getText().toString(), restaurantRating.getRating());
                    reference.child(id).setValue(review);
                    Toast.makeText(CreateRestaurantReviewActivity.this, "REVIEW SUBMITTED!", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(CreateRestaurantReviewActivity.this, UserDashboardActivity.class));
                }
            }
        });
    }
}