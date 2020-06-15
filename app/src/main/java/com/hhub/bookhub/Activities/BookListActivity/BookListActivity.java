package com.hhub.bookhub.Activities.BookListActivity;

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
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.hhub.bookhub.Activities.BookDetailActivity.BookDetailActivity;
import com.hhub.bookhub.Adapters.RecentAdapter;
import com.hhub.bookhub.Models.Background;
import com.hhub.bookhub.Models.Book;
import com.hhub.bookhub.R;

import java.util.ArrayList;

public class BookListActivity extends AppCompatActivity implements BookListView {

    public static final String TYPE = "type";
    public static final int TYPE_RECENT = 432;
    public static final int TYPE_BEST = 433;
    private RecyclerView recycler;
    private BookListPresenter presenter;
    private ConstraintLayout layout;
    private TextView txtTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        getSupportActionBar().hide();
        presenter = new BookListPresenter(this);

        recycler = (RecyclerView) findViewById(R.id.movList_recycler);
        layout = (ConstraintLayout) findViewById(R.id.movList_layout);
        txtTitle = (TextView) findViewById(R.id.movlist_txt_title);

        presenter.getBackground();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recycler.setLayoutManager(layoutManager);

        Intent intent = getIntent();
        int type = intent.getIntExtra(TYPE, TYPE_RECENT);
        switch (type) {
            case TYPE_RECENT:
                txtTitle.setText(getResources().getString(R.string.home_recent));
                break;
            case TYPE_BEST:
                txtTitle.setText(getResources().getString(R.string.home_best));
                break;
        }
        presenter.showList(type);
    }

    @Override
    public void showList(final ArrayList<Book> books) {
        RecentAdapter adapter = new RecentAdapter(getApplicationContext(),books);
        recycler.setAdapter(adapter);
        adapter.setOnItemClickListener(new RecentAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                Intent intent = new Intent(getApplicationContext(), BookDetailActivity.class);
                intent.putExtra("id",books.get(position).getId());
                startActivity(intent);
            }
        });
    }

    @Override
    public void showBackground(Background background) {
        Glide.with(getApplicationContext()).load(background.getImage()).centerCrop().into(new CustomTarget<Drawable>() {

            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                layout.setBackground(resource);
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {

            }
        });
    }
}
