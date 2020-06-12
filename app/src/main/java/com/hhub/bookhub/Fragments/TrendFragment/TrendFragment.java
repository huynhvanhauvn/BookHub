package com.hhub.bookhub.Fragments.TrendFragment;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.LineData;
import com.hhub.bookhub.Activities.BookDetailActivity.BookDetailActivity;
import com.hhub.bookhub.Activities.ReviewActivity.ReviewActivity;
import com.hhub.bookhub.Activities.ReviewDetailActivity.ReviewDetailActivity;
import com.hhub.bookhub.Adapters.AuthorTrendAdapter;
import com.hhub.bookhub.Adapters.ReviewItemAdapter;
import com.hhub.bookhub.Adapters.TrendBookAdapter;
import com.hhub.bookhub.Models.AuthorTrend;
import com.hhub.bookhub.Models.Background;
import com.hhub.bookhub.Models.Book;
import com.hhub.bookhub.Models.BookTrend;
import com.hhub.bookhub.Models.Quote;
import com.hhub.bookhub.R;
import com.hhub.bookhub.Utils.NetworkStateReceiver;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class TrendFragment extends Fragment implements TrendView, NetworkStateReceiver.NetworkStateReceiverListener {

    private LineChart lineChart;
    private TrendPresenter presenter;
    private ConstraintLayout layout;
    private SwipeRefreshLayout swipeRefreshLayout;
    private NestedScrollView scrollView;
    private ImageView btnSearch, img1, img2, img3;
    private RecyclerView recyclerTrend, recyclerHotCast, recyclerHotReview;
    private CardView card1, card2, card3;
    private NetworkStateReceiver networkStateReceiver;

    public TrendFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trend, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lineChart = view.findViewById(R.id.trend_chart);
        layout = (ConstraintLayout) view.findViewById(R.id.trend_layout);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.trend_refresh);
        scrollView = (NestedScrollView) view.findViewById(R.id.trend_scroll);
        btnSearch = (ImageView) view.findViewById(R.id.trend_btn_search);
        img1 = (ImageView) view.findViewById(R.id.trend_img1);
        img2 = (ImageView) view.findViewById(R.id.trend_img2);
        img3 = (ImageView) view.findViewById(R.id.trend_img3);
        recyclerTrend = (RecyclerView) view.findViewById(R.id.trend_recycler_trend);
        recyclerHotCast = (RecyclerView) view.findViewById(R.id.trend_recycler_hot_cast);
        recyclerHotReview = (RecyclerView) view.findViewById(R.id.trend_recycler_hot_review);
        card1 = (CardView) view.findViewById(R.id.trend_card1);
        card2 = (CardView) view.findViewById(R.id.trend_card2);
        card3 = (CardView) view.findViewById(R.id.trend_card3);

        scrollView.scrollTo(0,0);
        try {
            Field f = swipeRefreshLayout.getClass().getDeclaredField("mCircleView");
            f.setAccessible(true);
            ImageView img = (ImageView)f.get(swipeRefreshLayout);
            img.setAlpha(0.0f);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                btnSearch.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                    if(i1-i3>0) {
                        btnSearch.setVisibility(View.GONE);
                    }
                }
            });
        }
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(getContext(), SearchActivity.class);
//                startActivity(intent);
            }
        });

        presenter = new TrendPresenter(this, getResources());
        presenter.getBackground();
        presenter.getTrend();
        presenter.getHotAuthor();
        presenter.getHotReview();

        lineChart.setDescription(null);
        lineChart.setNoDataText("Updating...");
        lineChart.getAxisLeft().setEnabled(false);
        lineChart.getAxisRight().setEnabled(false);
        lineChart.getXAxis().setEnabled(false);
        lineChart.animateXY(5000,7000);
        lineChart.setTouchEnabled(false);
    }

    @Override
    public void showBackground(Background background) {
        Glide.with(getContext()).load(background.getImage()).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                layout.setBackground(resource);
            }
        });
    }

    @Override
    public void showTrend(final ArrayList<BookTrend> books) {
        if(books.size()>=3) {
            presenter.getTopData(books.get(0).getId(),
                    books.get(1).getId(),
                    books.get(2).getId());
            Glide.with(getContext()).load(books.get(0).getCover()).into(img1);
            Glide.with(getContext()).load(books.get(1).getCover()).into(img2);
            Glide.with(getContext()).load(books.get(2).getCover()).into(img3);

            card1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), BookDetailActivity.class);
                    intent.putExtra("id",books.get(0).getId());
                    startActivity(intent);
                }
            });
            card2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), BookDetailActivity.class);
                    intent.putExtra("id",books.get(1).getId());
                    startActivity(intent);
                }
            });
            card3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), BookDetailActivity.class);
                    intent.putExtra("id",books.get(2).getId());
                    startActivity(intent);
                }
            });

            final ArrayList<BookTrend> subMovies = new ArrayList<>(books.subList(3,books.size()));
            if(subMovies.size()>0) {
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                layoutManager.setOrientation(RecyclerView.VERTICAL);
                recyclerTrend.setLayoutManager(layoutManager);
                TrendBookAdapter adapter = new TrendBookAdapter(getContext(),subMovies);
                recyclerTrend.setAdapter(adapter);
                adapter.setOnItemClickListener(new TrendBookAdapter.OnItemClickListener() {
                    @Override
                    public void OnItemClick(View view, int position) {
                        Intent intent = new Intent(getContext(), BookDetailActivity.class);
                        intent.putExtra("id",subMovies.get(position).getId());
                        startActivity(intent);
                    }
                });
            }
        }
    }

    @Override
    public void showChart(LineData lineData) {
        lineChart.setData(lineData);
        lineChart.invalidate();
    }

    @Override
    public void showHotAuthor(ArrayList<AuthorTrend> authors) {
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),2);
        recyclerHotCast.setLayoutManager(layoutManager);
        final AuthorTrendAdapter adapter = new AuthorTrendAdapter(getContext(),authors);
        recyclerHotCast.setAdapter(adapter);
        adapter.setOnItemClickListener(new AuthorTrendAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
//                Intent intent = new Intent(getContext(), ArtistActivity.class);
//                intent.putExtra(ArtistActivity.ID,adapter.getArtists().get(position).getId());
//                startActivity(intent);
            }
        });
    }

    @Override
    public void showHotReview(final ArrayList<Quote> quotes) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerHotReview.setLayoutManager(layoutManager);
        ReviewItemAdapter adapter = new ReviewItemAdapter(getContext(),quotes);
        recyclerHotReview.setAdapter(adapter);
        adapter.setOnItemClickListener(new ReviewItemAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                Intent intent = new Intent(getContext(), ReviewActivity.class);
                intent.putExtra(ReviewDetailActivity.ID,quotes.get(position).getId());
                intent.putExtra(ReviewDetailActivity.TITLE,quotes.get(position).getTitle());
                intent.putExtra(ReviewDetailActivity.POSTER,quotes.get(position).getCover());
                startActivity(intent);
            }
        });
    }

    @Override
    public void networkAvailable() {

    }

    @Override
    public void networkUnavailable() {
        Toast.makeText(getContext(),"No Connection",Toast.LENGTH_SHORT).show();
    }
}
