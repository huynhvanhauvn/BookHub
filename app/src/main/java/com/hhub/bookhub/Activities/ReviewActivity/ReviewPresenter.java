package com.hhub.bookhub.Activities.ReviewActivity;

import com.hhub.bookhub.Models.Book;
import com.hhub.bookhub.Models.Review;
import com.hhub.bookhub.Services.APIService;
import com.hhub.bookhub.Services.Service;

import okhttp3.MultipartBody;

public class ReviewPresenter implements ReviewInterface {
    private ReviewView view;
    private Service service = APIService.getService();

    public ReviewPresenter(ReviewView view) {
        this.view = view;
    }

    @Override
    public void showBookInfo(String id) {

    }

    @Override
    public void getReview(String idReview) {

    }

    @Override
    public void sendReview(String idUser, String idBook, String intro, String story, String character, String art, String feel, String msg, String end) {

    }

    @Override
    public void updateReview(String id, String intro, String story, String character, String art, String feel, String msg, String end) {

    }

    @Override
    public void uploadImage(MultipartBody.Part body, String idReview, String type) {

    }

    @Override
    public void updateImage(MultipartBody.Part body, String idReview, String type, String oldFile) {

    }
}
