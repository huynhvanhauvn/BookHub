package com.hhub.bookhub.Activities.BookDetailActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.hhub.bookhub.Activities.AuthorActivity.AuthorActivity;
import com.hhub.bookhub.Activities.ReviewActivity.ReviewActivity;
import com.hhub.bookhub.Activities.WelcomeActivity.WelcomeActivity;
import com.hhub.bookhub.Adapters.AuthorAdapter;
import com.hhub.bookhub.Adapters.ReviewAdapter;
import com.hhub.bookhub.Models.Author;
import com.hhub.bookhub.Models.Book;
import com.hhub.bookhub.Models.Quote;
import com.hhub.bookhub.R;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;

public class BookDetailActivity extends AppCompatActivity implements BookDetailView {

    private ImageView imgWall;
    private BookDetailPresenter presenter;
    private ConstraintLayout layout, ratingLayout, ratingPoint, title;
    private TextView txtTitle, txtRating, txtDescription,
            txtLabelDescription, txtAuthor,
            txtDateLabel, txtDate, txtNationLabel, txtNation, txtReviewsLabel;
    private RecyclerView recyclerAuthor, recyclerQuote;
    private RatingBar ratingBar;
    private CardView cardSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        getSupportActionBar().hide();

        presenter = new BookDetailPresenter(this);
        imgWall = (ImageView) findViewById(R.id.detail_wall);
        layout = (ConstraintLayout) findViewById(R.id.detail_layout);
        txtTitle = (TextView) findViewById(R.id.detail_txt_title);
        recyclerAuthor = (RecyclerView) findViewById(R.id.detail_recycler_author);
        txtDescription = (TextView) findViewById(R.id.detail_txt_description);
        txtLabelDescription = (TextView) findViewById(R.id.detail_txt_description_label);
        txtAuthor = (TextView) findViewById(R.id.detail_txt_author);
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
        Glide.with(getApplicationContext()).load(book.getCover()).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                layout.setBackground(resource);
            }
        });
        Glide.with(getApplicationContext()).load(book.getCover()).centerCrop().into(imgWall);
        txtTitle.setText(book.getTitle());
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BookDetailActivity.this, ReviewActivity.class);
                intent.putExtra("id",book.getId());
                startActivity(intent);
            }
        });
        final DecimalFormat format = new DecimalFormat("0.0");
        txtRating.setText(format.format(Float.parseFloat(book.getRating())));
        if(book.getDescription() != null && !book.getDescription().equals("")) {
            txtLabelDescription.setVisibility(View.VISIBLE);
            txtDescription.setText(book.getDescription());
        }
        presenter.getAuthor(book.getId());
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
        txtAuthor.setVisibility(View.VISIBLE);
        recyclerAuthor.setNestedScrollingEnabled(false);
        GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),2);
        recyclerAuthor.setLayoutManager(layoutManager);
        final AuthorAdapter adapter = new AuthorAdapter(getApplicationContext(),authors);
        recyclerAuthor.setAdapter(adapter);
        adapter.setOnItemClickListener(new AuthorAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                Intent intent = new Intent(BookDetailActivity.this, AuthorActivity.class);
                intent.putExtra(AuthorActivity.ID,adapter.getArtists().get(position).getId());
                startActivity(intent);
            }
        });
    }

    @Override
    public void showRating() {
        ratingLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void updateRating(String rating) {
        final DecimalFormat format = new DecimalFormat("0.0");
        txtRating.setText(format.format(Float.parseFloat(rating)));
    }

    @Override
    public void showReviews(ArrayList<Quote> quotes, String title, String poster) {
        if(quotes.size()>0) {
            txtReviewsLabel.setVisibility(View.VISIBLE);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            layoutManager.setOrientation(RecyclerView.VERTICAL);
            ReviewAdapter adapter = new ReviewAdapter(getApplicationContext(),quotes);
            recyclerQuote.setLayoutManager(layoutManager);
            recyclerQuote.setAdapter(adapter);
            adapter.setOnItemClickListener(new ReviewAdapter.OnItemClickListener() {
                @Override
                public void OnItemClick(View view, int position) {
//                    Intent intent = new Intent(MovieDetailActivity.this, ReviewDetailActivity.class);
//                    intent.putExtra(ReviewDetailActivity.ID,quotes.get(position).getId());
//                    intent.putExtra(ReviewDetailActivity.TITLE,title);
//                    intent.putExtra(ReviewDetailActivity.POSTER,poster);
//                    startActivity(intent);
                }
            });
        }
    }
}
