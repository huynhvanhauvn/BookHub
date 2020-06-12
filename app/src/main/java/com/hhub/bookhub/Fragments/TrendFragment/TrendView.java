package com.hhub.bookhub.Fragments.TrendFragment;

import com.github.mikephil.charting.data.LineData;
import com.hhub.bookhub.Models.AuthorTrend;
import com.hhub.bookhub.Models.Background;
import com.hhub.bookhub.Models.BookTrend;
import com.hhub.bookhub.Models.Quote;

import java.util.ArrayList;

public interface TrendView {
    void showBackground(Background background);
    void showTrend(ArrayList<BookTrend> books);
    void showChart(LineData lineData);
    void showHotAuthor(ArrayList<AuthorTrend> authors);
    void showHotReview(ArrayList<Quote> quotes);
}
