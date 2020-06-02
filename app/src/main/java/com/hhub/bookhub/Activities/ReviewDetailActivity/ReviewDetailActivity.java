package com.hhub.bookhub.Activities.ReviewDetailActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.hhub.bookhub.R;

public class ReviewDetailActivity extends AppCompatActivity {

    public static final String ID = "idReview";
    public static final String TITLE = "title";
    public static final String POSTER = "poster";
    private String id, title, poster;
    private TextView txtTitle, txtIntro, txtStory, txtAct, txtPic, txtSound, txtFeel, txtMsg, txtEnd;
    private ImageView imgStory, imgAct, imgPic, imgSound, imgFeel, imgMsg;
    //private ReviewDetailPresenter presenter;
    private ConstraintLayout layout, layoutVote;
    private CardView cardSend;
    private RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_detail);
    }
}
