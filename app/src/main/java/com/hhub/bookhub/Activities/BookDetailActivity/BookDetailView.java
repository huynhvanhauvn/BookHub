package com.hhub.bookhub.Activities.BookDetailActivity;

import com.hhub.bookhub.Models.Author;
import com.hhub.bookhub.Models.Book;
import com.hhub.bookhub.Models.Quote;

import java.util.ArrayList;

public interface BookDetailView {
    void showBook(Book book);
    void getAuthor(ArrayList<Author> authors);
    void showRating();
    void updateRating(String rating);
    void showReviews(ArrayList<Quote> quotes, String title, String poster);
}
