package com.gfree_application.gfree.RestaurantReviewPackage;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeleteRestaurantReviewActivityTest {

    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference root = db.getReference().child("reviews");

    @Test
    public void onCreate() {

        System.out.println("ABC");

        //Create testing instance
        RestaurantReview review = new RestaurantReview();
        review.reviewId = "ABC";
        review.userEmail = "junittestemail@gmail.com";
        review.rating = 4.5F;
        review.restaurantName = "JUNIT Update Review Restaurant Name";
        review.reviewDescription = "JUNIT Update Review Restaurant Description";

        Log.d("Update Restaurant Review Initial Id", review.reviewId);
        Log.d("Update Restaurant Review Initial Email", review.userEmail);
        Log.d("Update Restaurant Review Initial Rating", String.valueOf(review.rating));
        Log.d("Update Restaurant Review Initial Restaurant Name", review.restaurantName);
        Log.d("Update Restaurant Review Initial Restaurant Desc", review.reviewDescription);

        //Assert/Check the created instance.
        assertEquals("ABC", review.reviewId); //Assert review id
        assertEquals("junittestemail@gmail.com", review.userEmail); //Assert review email
        assertEquals(4.5F, review.rating);    //Assert review rating
        assertEquals("JUNIT Update Review Restaurant Name", review.restaurantName); //Assert review name
        assertEquals("JUNIT Update Review Restaurant Description", review.reviewDescription);   //Assert review description

        //Update testing instance - mimicked update
        review.reviewId = null;
        review.userEmail = null;
        review.rating = 0F;
        review.restaurantName = null;
        review.reviewDescription = null;

        //Assert/Check the updated created instance.
        assertEquals(null, review.reviewId); //Assert review id
        assertEquals(null, review.userEmail); //Assert review email
        assertEquals(0, review.rating);    //Assert review rating
        assertEquals(null, review.restaurantName); //Assert review name
        assertEquals(null, review.reviewDescription);   //Assert review description

        review = null;
        assertEquals(null, review);   //Assert review description

    }

    @org.junit.jupiter.api.Test
    void onEditReviewClick() {
    }
}