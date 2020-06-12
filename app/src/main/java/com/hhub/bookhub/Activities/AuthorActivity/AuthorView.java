package com.hhub.bookhub.Activities.AuthorActivity;

import com.hhub.bookhub.Models.Author;
import com.hhub.bookhub.Models.Book;

import java.util.ArrayList;

public interface AuthorView {
    void showAuthor(Author author);
    void showBookList(ArrayList<Book> books);

}
