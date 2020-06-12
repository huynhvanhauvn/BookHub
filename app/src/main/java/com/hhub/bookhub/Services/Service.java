package com.hhub.bookhub.Services;

import com.hhub.bookhub.Models.Author;
import com.hhub.bookhub.Models.AuthorTrend;
import com.hhub.bookhub.Models.Background;
import com.hhub.bookhub.Models.Book;
import com.hhub.bookhub.Models.BookTrend;
import com.hhub.bookhub.Models.DateView;
import com.hhub.bookhub.Models.Promotion;
import com.hhub.bookhub.Models.Quote;
import com.hhub.bookhub.Models.Review;
import com.hhub.bookhub.Models.User;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
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

    @FormUrlEncoded
    @POST("bookinfo.php")
    Observable<Book> bookInfo(@Field("id") String id);

    @FormUrlEncoded
    @POST("reviewinfo.php")
    Observable<Review> getReviewInfo(@Field("id") String id);

    @FormUrlEncoded
    @POST("review.php")
    Observable<String> review(@Field("idUser") String idUser, @Field("idBook") String idMovie,
                              @Field("introduction") String intro, @Field("story") String story,
                              @Field("storyImage") String storyImg, @Field("character") String character,
                              @Field("characterImage") String characterImg, @Field("art") String art,
                              @Field("artImage") String artImage, @Field("feel") String feel,
                              @Field("feelImage") String feelImg, @Field("message") String msg,
                              @Field("messageImage") String msgImg, @Field("end") String end);
    @FormUrlEncoded
    @POST("reviewupdate.php")
    Observable<String> updateReview(@Field("id") String id,
                                    @Field("introduction") String intro, @Field("story") String story,
                                    @Field("character") String character, @Field("art") String art,
                                    @Field("feel") String feel,
                                    @Field("message") String msg, @Field("end") String end);

    @Multipart
    @POST("uploadimage.php")
    Observable<String> uploadImage(@Part MultipartBody.Part file, @Query("id") String idBook, @Query("type") String type);

    @Multipart
    @POST("updateimage.php")
    Observable<String> updateImage(@Part MultipartBody.Part file, @Query("id") String idBook, @Query("type") String type,
                                   @Query("oldFile") String oldFile);
    @FormUrlEncoded
    @POST("author.php")
    Observable<ArrayList<Author>> author(@Field("id") String id);

    @FormUrlEncoded
    @POST("checkvoted.php")
    Observable<String> checkVoted(@Field("idUser") String idUser, @Field("idBook") String idBook);

    @FormUrlEncoded
    @POST("vote.php")
    Observable<String> vote(@Field("rating") float rating, @Field("idUser") String idUser, @Field("idBook") String idBook);

    @FormUrlEncoded
    @POST("rating.php")
    Observable<String> rating(@Field("id") String id);

    @FormUrlEncoded
    @POST("quote.php")
    Observable<ArrayList<Quote>> getQuote(@Field("idBook") String idBook);

    @FormUrlEncoded
    @POST("authorname.php")
    Observable<ArrayList<String>> authorName(@Field("id") String id);

    @GET("booktrend.php")
    Observable<ArrayList<BookTrend>> bookTrend();

    @FormUrlEncoded
    @POST("dateviewbook.php")
    Observable<ArrayList<DateView>> getDateView(@Field("idBook") String idBook);

    @GET("authortrend.php")
    Observable<ArrayList<AuthorTrend>> hotAuthor();

    @GET("reviewtrend.php")
    Observable<ArrayList<Quote>> hotReview();

    @FormUrlEncoded
    @POST("authordetail.php")
    Observable<Author> authorDetail(@Field("id") String id, @Field("language") String language);

    @FormUrlEncoded
    @POST("authorlistbook.php")
    Observable<ArrayList<Book>> authorbook(@Field("id") String id);
}
