package com.gfree_application.gfree;

import android.text.Editable;

public class RestaurantReview {
    String reviewId, restaurantName, reviewDescription;
    float rating;

    public RestaurantReview() {

    }

    public RestaurantReview(String reviewId, String restaurantName, String reviewDescription, float rating) {
        this.reviewId = reviewId;
        this.restaurantName = restaurantName;
        this.reviewDescription = reviewDescription;
        this.rating = rating;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public String getReviewDescription() {
        return reviewDescription;
    }

    public float getRating() {
        return rating;
    }

    public String getReviewId() {
        return reviewId;
    }
}
