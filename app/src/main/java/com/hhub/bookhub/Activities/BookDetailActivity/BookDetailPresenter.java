package com.hhub.bookhub.Activities.BookDetailActivity;

import android.content.Context;
import android.content.res.Resources;

import com.hhub.bookhub.Models.Book;
import com.hhub.bookhub.Services.APIService;
import com.hhub.bookhub.Services.Service;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class BookDetailPresenter implements BookDetailInterface {

    private BookDetailView view;
    private Service service = APIService.getService();

    public BookDetailPresenter(BookDetailView view) {
        this.view = view;
    }

    @Override
    public void showBook(String id, String language) {
        Observable<Book> movieObservable = service.movie(id, language);
        movieObservable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Book>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Book book) {
                        if(book != null) {
                            view.showBook(book);
                        }
                    }
                });
    }

    @Override
    public void getAuthor(String idAuthor) {

    }

    @Override
    public void showRating(String idUser, String idBook, Context context, Resources resources) {

    }

    @Override
    public void actionRating(float rating, String idUser, String idBook) {

    }

    @Override
    public void showReviews(String id, String title, String poster) {

    }
}
