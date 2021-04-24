package com.gfree_application.gfree.RestaurantReviewPackage;

import android.text.Editable;

public class RestaurantReview {
    String reviewId, userEmail, restaurantName, reviewDescription;
    float rating;

    public RestaurantReview() {

    }

    public RestaurantReview(String reviewId, String userEmail, String restaurantName, String reviewDescription, float rating) {
        this.reviewId = reviewId;
        this.userEmail = userEmail;
        this.restaurantName = restaurantName;
        this.reviewDescription = reviewDescription;
        this.rating = rating;
    }

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getReviewDescription() {
        return reviewDescription;
    }

    public void setReviewDescription(String reviewDescription) {
        this.reviewDescription = reviewDescription;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
