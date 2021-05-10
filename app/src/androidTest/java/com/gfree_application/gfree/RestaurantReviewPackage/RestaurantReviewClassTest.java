package com.gfree_application.gfree.RestaurantReviewPackage;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*
 * Review ID is a random string of characters. This is generated in the real thing.
 * Restaurant Name is a String.
 * Restaurant Description is a String.
 * Rating is the star rating as a float, eg 1.5
 * Email is the users email address, this is simply a String.
 */

public class RestaurantReviewClassTest {
    @Test
    public void main(){
        RestaurantReview review = new RestaurantReview("-asdKmsajErueerar","Class Test User Email | testuser@gmail.com", "Class Test Restaurant Name","Class Test Restaurant Description", 5.0F); //declare and instantiate new RestauranReview class.
        assertEquals("-asdKmsajErueerar", review.reviewId); //Assert review id
        assertEquals("Class Test User Email | testuser@gmail.com", review.userEmail); //Assert review email
        assertEquals("Class Test Restaurant Name", review.restaurantName); //Assert review name
        assertEquals("Class Test Restaurant Description", review.reviewDescription);   //Assert review description
        assertEquals(5.0F, review.rating);    //Assert review rating
    }
}
