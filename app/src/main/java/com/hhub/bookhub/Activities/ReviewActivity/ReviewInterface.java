package com.hhub.bookhub.Activities.ReviewActivity;

import okhttp3.MultipartBody;

public interface ReviewInterface {
    void showBookInfo(String id);
    void getReview(String idReview);
    void sendReview(String idUser, String idBook, String intro, String story, String character, String art, String feel,
                    String msg, String end);
    void updateReview(String id, String intro, String story, String character, String art, String feel,
                      String msg, String end);
    void uploadImage(MultipartBody.Part body, String idReview, String type);
    void updateImage(MultipartBody.Part body, String idReview, String type, String oldFile);
}
