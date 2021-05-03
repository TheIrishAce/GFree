package com.gfree_application.gfree.RestaurantReviewPackage;

import android.content.Intent;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UpdateRestaurantReviewActivityTest {

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
        review.reviewId = "DEF";
        review.userEmail = "junittestemailupdated@gmail.com";
        review.rating = 2.5F;
        review.restaurantName = "JUNIT Update Review Restaurant Name Updated";
        review.reviewDescription = "JUNIT Update Review Restaurant Description Updated";

        Log.d("Update Restaurant Review Updated Id", review.reviewId);
        Log.d("Update Restaurant Review Updated Email", review.userEmail);
        Log.d("Update Restaurant Review Updated Rating", String.valueOf(review.rating));
        Log.d("Update Restaurant Review Updated Restaurant Name", review.restaurantName);
        Log.d("Update Restaurant Review Updated Restaurant Desc", review.reviewDescription);

        //Assert/Check the updated created instance.
        assertEquals("DEF", review.reviewId); //Assert review id
        assertEquals("junittestemailupdated@gmail.com", review.userEmail); //Assert review email
        assertEquals(2.5F, review.rating);    //Assert review rating
        assertEquals("JUNIT Update Review Restaurant Name Updated", review.restaurantName); //Assert review name
        assertEquals("JUNIT Update Review Restaurant Description Updated", review.reviewDescription);   //Assert review description


    }

    @org.junit.jupiter.api.Test
    void onEditReviewClick() {
    }
}