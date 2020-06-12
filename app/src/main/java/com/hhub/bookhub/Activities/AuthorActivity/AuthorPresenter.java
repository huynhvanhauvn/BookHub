package com.hhub.bookhub.Activities.AuthorActivity;


import com.hhub.bookhub.Models.Author;
import com.hhub.bookhub.Models.Book;
import com.hhub.bookhub.Services.APIService;
import com.hhub.bookhub.Services.Service;

import java.util.ArrayList;
import java.util.Locale;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AuthorPresenter implements AuthorInterface {
    private AuthorView view;
    private Service service = APIService.getService();

    public AuthorPresenter(AuthorView view) {
        this.view = view;
    }

    @Override
    public void getAuthor(String id) {
        Observable<Author> observable = service.authorDetail(id, Locale.getDefault().getLanguage());
        observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Author>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Author author) {
                        if(author != null) {
                            view.showAuthor(author);
                        }
                    }
                });
    }

    @Override
    public void getBookList(String id) {
        Observable<ArrayList<Book>> observable = service.authorbook(id);
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
                        if(books != null && books.size() > 0) {
                            view.showBookList(books);
                        }
                    }
                });
    }
}
