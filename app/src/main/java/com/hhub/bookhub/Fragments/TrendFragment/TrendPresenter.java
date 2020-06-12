package com.hhub.bookhub.Fragments.TrendFragment;

import android.content.res.Resources;
import android.graphics.Color;
import android.util.Log;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.hhub.bookhub.Models.AuthorTrend;
import com.hhub.bookhub.Models.Background;
import com.hhub.bookhub.Models.BookTrend;
import com.hhub.bookhub.Models.Chart;
import com.hhub.bookhub.Models.DateView;
import com.hhub.bookhub.Models.Quote;
import com.hhub.bookhub.R;
import com.hhub.bookhub.Services.APIService;
import com.hhub.bookhub.Services.Service;

import java.util.ArrayList;
import java.util.Locale;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TrendPresenter implements TrendInterface {

    private TrendView view;
    private Resources resources;
    private Service service = APIService.getService();
    private LineData lineData;
    private LineDataSet lineDataSet;
    private ArrayList lineEntries;
    private ArrayList<ILineDataSet> dataSets = new ArrayList<>();

    public TrendPresenter(TrendView view, Resources resources) {
        this.view = view;
        this.resources = resources;
    }

    @Override
    public void getBackground() {
        Observable<Background> observable = service.background("trend", Locale.getDefault().getLanguage());
        observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Background>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Background background) {
                        if(background != null) {
                            view.showBackground(background);
                        }
                    }
                });
    }

    @Override
    public void getTrend() {
        Observable<ArrayList<BookTrend>> observable = service.bookTrend();
        observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<BookTrend>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("hvhau",e.getLocalizedMessage());
                    }

                    @Override
                    public void onNext(ArrayList<BookTrend> books) {
                        Log.d("hvhau",books.size()+"");
                        if(books != null) {
                            view.showTrend(books);
                        }
                    }
                });
    }

    @Override
    public void getTopData(String id1, String id2, String id3) {
        final ArrayList<Chart> charts = new ArrayList<>();
        charts.add(new Chart(id1,resources.getColor(R.color.colorGold)));
        charts.add(new Chart(id2,resources.getColor(R.color.colorSilver)));
        charts.add(new Chart(id3,resources.getColor(R.color.colorBronze)));
        for (int i=0; i<3; i++) {
            final int finalI = i;
            Observable<ArrayList<DateView>> observable = service.getDateView(charts.get(i).getId());
            observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ArrayList<DateView>>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(ArrayList<DateView> dateViews) {
                            if (dateViews != null) {
                                getEntries(dateViews);
                                lineDataSet = new LineDataSet(lineEntries, "");
                                lineDataSet.setColors(charts.get(finalI).getColor());
                                lineDataSet.setValueTextColor(Color.TRANSPARENT);
                                lineDataSet.setValueTextSize(18f);
                                dataSets.add(lineDataSet);
                                lineData = new LineData(dataSets);
                                view.showChart(lineData);
                            }
                        }
                    });
        }
    }

    @Override
    public void getHotAuthor() {
        Observable<ArrayList<AuthorTrend>> observable = service.hotAuthor();
        observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<AuthorTrend>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ArrayList<AuthorTrend> authors) {
                        if(authors != null) {
                            view.showHotAuthor(authors);
                        }
                    }
                });
    }

    @Override
    public void getHotReview() {
        Observable<ArrayList<Quote>> observable = service.hotReview();
        observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<Quote>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ArrayList<Quote> quotes) {
                        if (quotes != null) {
                            view.showHotReview(quotes);
                        }
                    }
                });
    }

    private void getEntries(ArrayList<DateView> dateViews) {
        lineEntries = new ArrayList<>();
        lineEntries.add(new Entry(0f, dateViews.get(0).getView()));
        lineEntries.add(new Entry(1f, dateViews.get(1).getView()));
        lineEntries.add(new Entry(2f, dateViews.get(2).getView()));
        lineEntries.add(new Entry(3f, dateViews.get(3).getView()));
        lineEntries.add(new Entry(4f, dateViews.get(4).getView()));
        lineEntries.add(new Entry(5f, dateViews.get(5).getView()));
        lineEntries.add(new Entry(6f, dateViews.get(6).getView()));
    }
}
