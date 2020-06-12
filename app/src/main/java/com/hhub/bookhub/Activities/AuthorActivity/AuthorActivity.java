package com.hhub.bookhub.Activities.AuthorActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.hhub.bookhub.Activities.BookDetailActivity.BookDetailActivity;
import com.hhub.bookhub.Adapters.RecentAdapter;
import com.hhub.bookhub.Models.Author;
import com.hhub.bookhub.Models.Book;
import com.hhub.bookhub.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

public class AuthorActivity extends AppCompatActivity implements AuthorView {

    public static final String ID = "id";
    private String id="";
    private AuthorPresenter presenter;
    private RoundedImageView imgAvatar;
    private TextView txtName, txtOld, txtBirthday, txtNation, txtLabelBook;
    private RecyclerView recyclerBook;
    private ConstraintLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author);
        getSupportActionBar().hide();

        imgAvatar = (RoundedImageView) findViewById(R.id.artist_img_avatar);
        txtName = (TextView) findViewById(R.id.artist_txt_fullname);
        txtOld = (TextView) findViewById(R.id.artist_txt_yearold);
        txtBirthday = (TextView) findViewById(R.id.artist_txt_birthday);
        txtNation = (TextView) findViewById(R.id.artist_txt_nation);
        txtLabelBook = (TextView) findViewById(R.id.artist_label_book);
        recyclerBook = (RecyclerView) findViewById(R.id.artist_recycler_book);
        layout = (ConstraintLayout) findViewById(R.id.artist_layout);

        presenter = new AuthorPresenter(this);
        Intent intent = getIntent();
        id = intent.getStringExtra(ID);
        if(!id.equals("")) {
            presenter.getAuthor(id);
        }
    }

    @Override
    public void showAuthor(Author author) {
        Glide.with(getApplicationContext()).load(author.getImage()).into(imgAvatar);
        txtName.setText(author.getName());
        txtOld.setText("");
        txtBirthday.setText(author.getBirthday());
        txtNation.setText(author.getNation());
        presenter.getBookList(author.getId());
    }

    @Override
    public void showBookList(final ArrayList<Book> books) {
        Glide.with(getApplicationContext()).load(books.get(0).getCover()).centerCrop().into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                layout.setBackground(resource);
            }
        });
        txtLabelBook.setVisibility(View.VISIBLE);
        recyclerBook.setVisibility(View.VISIBLE);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerBook.setLayoutManager(layoutManager);
        RecentAdapter adapter = new RecentAdapter(getApplicationContext(),books);
        recyclerBook.setAdapter(adapter);
        adapter.setOnItemClickListener(new RecentAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                Intent intent = new Intent(getApplicationContext(), BookDetailActivity.class);
                intent.putExtra("id",books.get(position).getId());
                startActivity(intent);
            }
        });
    }
}