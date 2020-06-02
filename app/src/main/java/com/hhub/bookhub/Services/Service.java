package com.hhub.bookhub.Services;

import com.hhub.bookhub.Models.Background;
import com.hhub.bookhub.Models.Book;
import com.hhub.bookhub.Models.Promotion;
import com.hhub.bookhub.Models.User;

import java.util.ArrayList;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

public interface Service {

    @FormUrlEncoded
    @POST("login.php")
    Observable<User> login(@Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("signup.php")
    Observable<String> signup(@Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("background.php")
    Observable<Background> background(@Field("position") String position, @Field("language") String language);

    @GET("promotion.php")
    Observable<ArrayList<Promotion>> promotion();

    @FormUrlEncoded
    @POST("book.php")
    Observable<Book> movie(@Field("id") String id, @Field("language") String language);
}
