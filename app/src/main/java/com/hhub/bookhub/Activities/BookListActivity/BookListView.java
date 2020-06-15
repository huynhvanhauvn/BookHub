package com.hhub.bookhub.Activities.BookListActivity;

import com.hhub.bookhub.Models.Background;
import com.hhub.bookhub.Models.Book;

import java.util.ArrayList;

public interface BookListView {
    void showList(ArrayList<Book> books);
    void showBackground(Background background);
}
