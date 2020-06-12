package com.hhub.bookhub.Activities.BookDetailActivity;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.widget.Toast;

import com.hhub.bookhub.Models.Author;
import com.hhub.bookhub.Models.Book;
import com.hhub.bookhub.Models.Quote;
import com.hhub.bookhub.R;
import com.hhub.bookhub.Services.APIService;
import com.hhub.bookhub.Services.Service;

import java.util.ArrayList;

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
        Observable<ArrayList<Author>> artistObservable = service.author(idAuthor);
        artistObservable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<Author>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("hvhau", e.getLocalizedMessage());
                    }

                    @Override
                    public void onNext(ArrayList<Author> authors) {
                        if (authors != null) {
                            view.getAuthor(authors);
                        }
                    }
                });
    }

    @Override
    public void showRating(String idUser, String idBook, final Context context, final Resources resources) {
        Observable<String> checkVotedObservable = service.checkVoted(idUser,idBook);
        checkVotedObservable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        if(s.equals("true")) {
                            view.showRating();
                        } else {
                            Toast.makeText(context,
                                    resources.getString(R.string.detail_voted),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void actionRating(float rating, String idUser, final String idBook) {
        Observable<String> voteObservable = service.vote(rating,
                idUser,idBook);
        voteObservable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        if(s.equals("success")) {
                            Observable<String> ratingObservable = service.rating(idBook);
                            ratingObservable.subscribeOn(Schedulers.newThread())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Observer<String>() {
                                        @Override
                                        public void onCompleted() {

                                        }

                                        @Override
                                        public void onError(Throwable e) {

                                        }

                                        @Override
                                        public void onNext(String s) {
                                            if(s != null) {
                                                view.updateRating(s);
                                            }
                                        }
                                    });
                        }
                    }
                });
    }

    @Override
    public void showReviews(String id, final String title, final String poster) {
        Observable<ArrayList<Quote>> observable = service.getQuote(id);
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
                        if(quotes != null) {
                            view.showReviews(quotes,title,poster);
                        }
                    }
                });
    }
}
