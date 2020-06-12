package com.hhub.bookhub.Activities.ReviewActivity;

import android.util.Log;

import com.hhub.bookhub.Models.Book;
import com.hhub.bookhub.Models.Review;
import com.hhub.bookhub.Services.APIService;
import com.hhub.bookhub.Services.Service;

import java.util.Locale;

import okhttp3.MultipartBody;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ReviewPresenter implements ReviewInterface {
    private ReviewView view;
    private Service service = APIService.getService();

    public ReviewPresenter(ReviewView view) {
        this.view = view;
    }

    @Override
    public void showBookInfo(String id) {
        Observable<Book> movieObservable = service.bookInfo(id);
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
                            view.showBookInfo(book);
                        }
                    }
                });
    }

    @Override
    public void getReview(String idReview) {
        Observable<Review> observable = service.getReviewInfo(idReview);
        observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Review>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Review review) {
                        if(review != null) {
                            view.showReview(review);
                        }
                    }
                });
    }

    @Override
    public void sendReview(String idUser, String idBook, String intro, String story, String character, String art, String feel, String msg, String end) {
        Observable<String> observable = service.review(idUser,idBook,intro,story,"",
                character,"",art,"",feel,"",msg,"",end);
        observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        if(s != null && !s.equals("")) {
                            view.sendReviewSuccess(s);
                        }
                    }
                });
    }

    @Override
    public void updateReview(final String id, String intro, String story, String character, String art, String feel, String msg, String end) {
        Observable<String> observable = service.updateReview(id, intro, story, character, art, feel, msg, end);
        observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
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
                            view.updateReviewTextSuccess(id);
                        }
                    }
                });
    }

    @Override
    public void uploadImage(MultipartBody.Part body, String idReview, String type) {
        Observable<String> observable = service.uploadImage(body, idReview, type);
        observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("hvhau",e.getLocalizedMessage());
                    }

                    @Override
                    public void onNext(String s) {
                        if(s.equals("success")) {
                            view.uploadImageSuccess();
                        }
                    }
                });
    }

    @Override
    public void updateImage(MultipartBody.Part body, String idReview, String type, String oldFile) {
        Observable<String> observable = service.updateImage(body, idReview, type, oldFile);
        observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
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
                            view.uploadImageSuccess();
                        }
                    }
                });
    }
}
