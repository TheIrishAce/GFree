package com.gfree_application.gfree.RestaurantReviewPackage;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateRestaurantReviewFirebaseActivityTest {

    @Test
    public void main() {
        FirebaseDatabase rootNode;
        DatabaseReference reference;

        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("reviews");



        //assertEquals(1, 1);
        //Create testing instance
        RestaurantReview review = new RestaurantReview();
        review.reviewId = "ABC";
        review.userEmail = "junittestemail@gmail.com";
        review.rating = 4.5F;
        review.restaurantName = "JUNIT Update Review Restaurant Name";
        review.reviewDescription = "JUNIT Update Review Restaurant Description";

        //RestaurantReview review = new RestaurantReview(id, email, restaurantName.getText().toString(), reviewDescriptionTextInput.getText().toString(), restaurantRating.getRating());
        reference.child(review.reviewId).setValue(review);

        //Assert/Check the created instance.
        assertEquals("ABC", review.reviewId); //Assert review id
        assertEquals("junittestemail@gmail.com", review.userEmail); //Assert review email
        assertEquals(4.5F, review.rating);    //Assert review rating
        assertEquals("JUNIT Update Review Restaurant Name", review.restaurantName); //Assert review name
        assertEquals("JUNIT Update Review Restaurant Description", review.reviewDescription);   //Assert review description
    }
}
