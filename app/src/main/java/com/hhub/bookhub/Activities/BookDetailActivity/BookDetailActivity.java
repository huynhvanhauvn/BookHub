package com.hhub.bookhub.Activities.BookDetailActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hhub.bookhub.Activities.WelcomeActivity.WelcomeActivity;
import com.hhub.bookhub.Models.Author;
import com.hhub.bookhub.Models.Book;
import com.hhub.bookhub.Models.Quote;
import com.hhub.bookhub.R;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;

public class BookDetailActivity extends AppCompatActivity implements BookDetailView {

    private YouTubePlayerView playerView;
    private BookDetailPresenter presenter;
    private ConstraintLayout layout, ratingLayout, ratingPoint, title;
    private TextView txtTitle, txtRating, txtDescription,
            txtLabelDescription, txtDirector, txtWriter, txtCast,
            txtDateLabel, txtDate, txtNationLabel, txtNation, txtReviewsLabel;
    private RecyclerView recyclerDirector, recyclerWriter, recyclerCast, recyclerQuote;
    private RatingBar ratingBar;
    private CardView cardSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        getSupportActionBar().hide();

        presenter = new BookDetailPresenter(this);
        playerView = (YouTubePlayerView) findViewById(R.id.detail_youtube);
        layout = (ConstraintLayout) findViewById(R.id.detail_layout);
        txtTitle = (TextView) findViewById(R.id.detail_txt_title);
        recyclerDirector = (RecyclerView) findViewById(R.id.detail_recycler_director);
        recyclerWriter = (RecyclerView) findViewById(R.id.detail_recycler_writer);
        recyclerCast = (RecyclerView) findViewById(R.id.detail_recycler_cast);
        txtDescription = (TextView) findViewById(R.id.detail_txt_description);
        txtLabelDescription = (TextView) findViewById(R.id.detail_txt_description_label);
        txtDirector = (TextView) findViewById(R.id.detail_txt_director);
        txtWriter = (TextView) findViewById(R.id.detail_txt_writer);
        txtCast = (TextView) findViewById(R.id.detail_txt_cast);
        txtRating = (TextView) findViewById(R.id.detail_txt_rating);
        ratingLayout = (ConstraintLayout) findViewById(R.id.detail_layout_background);
        ratingPoint = (ConstraintLayout) findViewById(R.id.detail_rating);
        ratingBar = (RatingBar) findViewById(R.id.detail_rating_star);
        cardSend = (CardView) findViewById(R.id.detail_card_send);
        txtDateLabel = (TextView) findViewById(R.id.detail_txt_date_label);
        txtDate = (TextView) findViewById(R.id.detail_txt_date);
        txtNationLabel = (TextView) findViewById(R.id.detail_txt_nation_label);
        txtNation = (TextView) findViewById(R.id.detail_txt_nation);
        title = (ConstraintLayout) findViewById(R.id.detail_title);
        txtReviewsLabel = (TextView) findViewById(R.id.detail_txt_quote_label);
        recyclerQuote = (RecyclerView) findViewById(R.id.detail_recycler_quote);

        ratingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ratingLayout.setVisibility(View.GONE);
            }
        });

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        if(id != null) {
            Locale.getDefault().getLanguage();
            presenter.showBook(id,Locale.getDefault().getLanguage());
        }
    }


    @Override
    public void showBook(final Book book) {
        txtTitle.setText(book.getTitle());
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(MovieDetailActivity.this, ReviewActivity.class);
//                intent.putExtra("id",movie.getId());
//                startActivity(intent);
            }
        });
        final DecimalFormat format = new DecimalFormat("0.0");
        txtRating.setText(format.format(Float.parseFloat(book.getRating())));
        if(book.getDescription() != null && !book.getDescription().equals("")) {
            txtLabelDescription.setVisibility(View.VISIBLE);
            txtDescription.setText(book.getDescription());
        }
//        showArtist(movie.getId(),1);
//        showArtist(movie.getId(),2);
//        showArtist(movie.getId(),3);
        if(book.getDate() != null && !book.getDate().equals("")) {
            txtDateLabel.setVisibility(View.VISIBLE);
            txtDate.setVisibility(View.VISIBLE);
            txtDate.setText(book.getDate());
        }
        if(book.getNation() != null && !book.getNation().equals("")) {
            txtNationLabel.setVisibility(View.VISIBLE);
            txtNation.setVisibility(View.VISIBLE);
            txtNation.setText(book.getNation());
        }
        if(book.getId() != null && !book.getId().equals("")) {
            presenter.showReviews(book.getId(),book.getTitle(),book.getCover());
        }

        SharedPreferences preferences = getApplicationContext().getSharedPreferences(WelcomeActivity.SHARED_DATA, Context.MODE_PRIVATE);
        final String id = preferences.getString(WelcomeActivity.USER_ID,"");

        ratingPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!id.equals("")) {
                    presenter.showRating(id, book.getId(), getApplicationContext(), getResources());
                }
            }
        });

        cardSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ratingBar.getRating()>0) {
                    ratingLayout.setVisibility(View.GONE);
                    presenter.actionRating(ratingBar.getRating(),id,book.getId());
                } else {
                    Toast.makeText(getApplicationContext(),
                            getResources().getString(R.string.detail_not_rating),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void getAuthor(ArrayList<Author> authors) {

    }

    @Override
    public void showRating() {

    }

    @Override
    public void updateRating(String rating) {

    }

    @Override
    public void showReviews(ArrayList<Quote> quotes, String title, String poster) {

    }
}
