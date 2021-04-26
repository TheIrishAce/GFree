package com.gfree_application.gfree.RestaurantReviewPackage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.gfree_application.gfree.DashboardPackage.UserDashboardActivity;
import com.gfree_application.gfree.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditRestaurantReviewActivity extends AppCompatActivity {

    private Button submitReview;
    private TextInputEditText reviewDescriptionTextInput;
    private EditText restaurantName;
    private RatingBar restaurantRating;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    String email, name, uid, providerId;


    String incReviewId;
    String incRestaurantName;
    String incRestaurantDescription;
    float incRestaurantReviewRating;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_restaurant_review);

        submitReview = findViewById(R.id.submit_edited_review_button);
        reviewDescriptionTextInput = findViewById(R.id.edit_review_optional_description);
        restaurantName = findViewById(R.id.edit_review_resteraunt_name_EditText);
        restaurantRating = findViewById(R.id.edit_review_restaurant_RatingBar);

        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("reviews");

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

        incReviewId = getIntent().getStringExtra("SELECTED REVIEW ID");
        incRestaurantName = getIntent().getStringExtra("SELECTED REVIEW RESTAURANT NAME");
        incRestaurantDescription = getIntent().getStringExtra("SELECTED REVIEW RESTAURANT DESCRIPTION");
        incRestaurantReviewRating = getIntent().getFloatExtra("SELECTED REVIEW RESTAURANT RATING", 0);


        restaurantName.setText(incRestaurantName);
        reviewDescriptionTextInput.setText(incRestaurantDescription);
        restaurantRating.setRating(incRestaurantReviewRating);

    }

    public void editRestaurantReview(View view){
        if (isRestaurantNameChanged() || isRestaurantReviewBodyChanged() || isRestaurantRatingChanged()){
            Toast.makeText(this, "Review has been updated.", Toast.LENGTH_LONG).show();
            startActivity(new Intent(EditRestaurantReviewActivity.this, RestaurantReviewActivity.class));
        } else {
            Toast.makeText(this, "Review is same, cannot update review.", Toast.LENGTH_LONG).show();
        }
    }

    public boolean isRestaurantRatingChanged() {
        if (!String.valueOf(incRestaurantReviewRating).equals(String.valueOf(restaurantRating))){
            reference.child(incReviewId).child("rating").setValue(restaurantRating.getRating());
            return true;
        } else {
            return false;
        }
    }

    public boolean isRestaurantReviewBodyChanged() {
        if (!incRestaurantDescription.equals(reviewDescriptionTextInput.getText().toString())){
            reference.child(incReviewId).child("reviewDescription").setValue(reviewDescriptionTextInput.getText().toString());
            return true;
        } else {
            return false;
        }
    }

    public boolean isRestaurantNameChanged() {
        if (!incRestaurantName.equals(restaurantName.getText().toString())){
            reference.child(incReviewId).child("restaurantName").setValue(restaurantName.getText().toString());
            return true;
        } else {
            return false;
        }
    }
}