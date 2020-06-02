package com.hhub.bookhub.Activities.ReviewActivity;

import com.hhub.bookhub.Models.Book;
import com.hhub.bookhub.Models.Review;

public interface ReviewView {
    void showBookInfo(Book book);
    void showReview(Review review);
    void updateReviewTextSuccess(String idReview);
    void sendReviewSuccess(String idReview);
    void uploadImageSuccess();
}
