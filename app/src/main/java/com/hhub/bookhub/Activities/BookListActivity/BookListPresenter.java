package com.hhub.bookhub.Activities.BookListActivity;

import com.hhub.bookhub.Models.Background;
import com.hhub.bookhub.Models.Book;
import com.hhub.bookhub.Services.APIService;
import com.hhub.bookhub.Services.Service;

import java.util.ArrayList;
import java.util.Locale;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.hhub.bookhub.Activities.BookListActivity.BookListActivity.TYPE_BEST;
import static com.hhub.bookhub.Activities.BookListActivity.BookListActivity.TYPE_RECENT;

public class BookListPresenter implements BookListInterface {
    private BookListView view;
    private Service service = APIService.getService();

    public BookListPresenter(BookListView movieListView) {
        this.view = movieListView;
    }

    @Override
    public void showList(int type) {
        switch (type) {
            case TYPE_RECENT:
                getRecentList();
                break;
            case TYPE_BEST:
                getBestList();
                break;
            default:
                break;
        }
    }

    private void getRecentList() {
        Observable<ArrayList<Book>> observable = service.recentBookList();
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
                            view.showList(books);
                        }
                    }
                });
    }

    private void getBestList() {
        Observable<ArrayList<Book>> observable = service.bestBookList();
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
                            view.showList(books);
                        }
                    }
                });
    }

    @Override
    public void getBackground() {
        Observable<Background> observable = service.background("movielist", Locale.getDefault().getLanguage());
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
}
