package com.hhub.bookhub.Activities.BookDetailActivity;

import android.content.Context;
import android.content.res.Resources;

public interface BookDetailInterface {
    void showBook(String id, String language);
    void getAuthor(String idAuthor);
    void showRating(String idUser, String idBook, Context context, Resources resources);
    void actionRating(float rating, String idUser, String idBook);
    void showReviews(String id, String title, String poster);
}
