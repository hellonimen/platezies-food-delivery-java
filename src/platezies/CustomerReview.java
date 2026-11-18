/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package platezies;


/**
 *
 */
public class CustomerReview {
    private String reviewId;
    private String orderId;
    private String orderItem;
    private String cuisine;
    private String username;
    private int starRating;
    private String comment;
    private String reviewDate;

    // Constructor
    public CustomerReview(String reviewId, String orderId, String orderItem, String cuisine, String username, int starRating, String comment, String reviewDate) {
        this.reviewId = reviewId;
        this.orderId = orderId;
        this.orderItem = orderItem;
        this.cuisine = cuisine;
        this.username = username;
        this.starRating = starRating;
        this.comment = comment;
        this.reviewDate = reviewDate;
    }

    // Getters and Setters
    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(String orderItem) {
        this.orderItem = orderItem;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getStarRating() {
        return starRating;
    }

    public void setStarRating(int starRating) {
        this.starRating = starRating;
    }

    public String getComment() {
        return comment;
    }
    
    public String getreviewDate() {
        return reviewDate;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return String.format("Review ID: %s\nOrder ID: %s\nOrder Item: %s\nCuisine: %s\nUsername: %s\nRating: %d stars\nComment: %s\nReview Date: %s", 
                reviewId, orderId, orderItem, cuisine, username, starRating, comment, reviewDate);
    }
}
