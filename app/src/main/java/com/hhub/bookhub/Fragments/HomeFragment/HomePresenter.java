package com.hhub.bookhub.Fragments.HomeFragment;

import com.hhub.bookhub.Models.Background;
import com.hhub.bookhub.Models.Book;
import com.hhub.bookhub.Models.Promotion;
import com.hhub.bookhub.Services.APIService;
import com.hhub.bookhub.Services.Service;

import java.util.ArrayList;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HomePresenter implements HomeInterface {

    private HomeView homeView;
    private Service service = APIService.getService();

    public HomePresenter(HomeView homeView) {
        this.homeView = homeView;
    }

    @Override
    public void showHeader() {
        Service service = APIService.getService();
        Observable<ArrayList<Promotion>> promoObservable = service.promotion();
        promoObservable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<Promotion>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ArrayList<Promotion> promotions) {
                        if (promotions != null) {
                            homeView.showHeader(promotions);
                        }
                    }
                });
    }

    @Override
    public void showBackground(String language) {
        Observable<Background> backgroundObservable = service.background("home",language);
        backgroundObservable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
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
                            homeView.showBackground(background);
                        }
                    }
                });
    }

    @Override
    public void showRecentMovie() {
        Observable<ArrayList<Book>> recentObservable = service.recentBook();
        recentObservable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<Book>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ArrayList<Book> books) {
                        if(books != null) {
                            homeView.showRecentMovie(books);
                        }
                    }
                });
    }

    @Override
    public void getBestMovie() {
        Observable<ArrayList<Book>> observable = service.bestBook();
        observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<Book>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ArrayList<Book> books) {
                        if(books != null) {
                            homeView.showBestMovie(books);
                        }
                    }
                });
    }
}
