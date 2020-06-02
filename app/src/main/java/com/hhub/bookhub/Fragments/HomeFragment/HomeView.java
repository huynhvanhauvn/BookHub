package com.hhub.bookhub.Fragments.HomeFragment;

import com.hhub.bookhub.Models.Background;
import com.hhub.bookhub.Models.Book;
import com.hhub.bookhub.Models.Promotion;

import java.util.ArrayList;

public interface HomeView {
    void showHeader(ArrayList<Promotion> promotions);
    void showBackground(Background background);
    void showRecentMovie(ArrayList<Book> books);
    void showBestMovie(ArrayList<Book> books);
}
