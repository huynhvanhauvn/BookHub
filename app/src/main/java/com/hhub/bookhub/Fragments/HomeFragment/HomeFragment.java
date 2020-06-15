package com.hhub.bookhub.Fragments.HomeFragment;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.hhub.bookhub.Activities.BookDetailActivity.BookDetailActivity;
import com.hhub.bookhub.Activities.BookListActivity.BookListActivity;
import com.hhub.bookhub.Adapters.RecentAdapter;
import com.hhub.bookhub.Adapters.SlideAdapter;
import com.hhub.bookhub.Models.Background;
import com.hhub.bookhub.Models.Book;
import com.hhub.bookhub.Models.Promotion;
import com.hhub.bookhub.R;
import com.hhub.bookhub.Utils.NetworkStateReceiver;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Locale;

public class HomeFragment extends Fragment implements HomeView, NetworkStateReceiver.NetworkStateReceiverListener {

    private ViewPager2 viewPager;
    private Handler slideHandler = new Handler();
    private RecyclerView recyclerView, recyclerBest;
    private ConstraintLayout imgLayout;
    private HomePresenter presenter;
    private ImageView btnMoreRecent, btnMoreBest;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ImageView btnSearch;
    private NestedScrollView scrollView;
    private NetworkStateReceiver networkStateReceiver;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewPager = (ViewPager2) view.findViewById(R.id.home_viewPager);
        recyclerView = (RecyclerView) view.findViewById(R.id.home_recycler_recent);
        recyclerBest = (RecyclerView) view.findViewById(R.id.home_recycler_best);
        imgLayout = (ConstraintLayout) view.findViewById(R.id.home_layout);
        btnMoreRecent = (ImageView) view.findViewById(R.id.home_img_more_recent);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.home_scroll);
        btnSearch = (ImageView) view.findViewById(R.id.home_btn_search);
        btnMoreBest = (ImageView) view.findViewById(R.id.home_img_more_best);
        scrollView = (NestedScrollView) view.findViewById(R.id.home_nested);

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

        presenter = new HomePresenter(this);
        presenter.showBackground(Locale.getDefault().getLanguage());

        viewPager.setClipToPadding(false);
        viewPager.setClipChildren(false);
        viewPager.setOffscreenPageLimit(3);
        viewPager.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer transformer = new CompositePageTransformer();
        transformer.addTransformer(new MarginPageTransformer(40));
        transformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
            }
        });
        viewPager.setPageTransformer(transformer);
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                slideHandler.removeCallbacks(slideRunnable);
                slideHandler.postDelayed(slideRunnable,5000);
            }
        });

        presenter.showHeader();
        presenter.getBestMovie();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);

        presenter.showRecentMovie();
    }

    @Override
    public void onPause() {
        super.onPause();
        slideHandler.removeCallbacks(slideRunnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        slideHandler.postDelayed(slideRunnable,5000);
    }

    private Runnable slideRunnable = new Runnable() {
        @Override
        public void run() {
            viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
        }
    };

    @Override
    public void showHeader(final ArrayList<Promotion> promotions) {
        SlideAdapter adapter = new SlideAdapter(getContext(),promotions,viewPager);
        viewPager.setAdapter(adapter);
        adapter.setOnItemClickListener(new SlideAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                Intent intent = new Intent(getContext(), BookDetailActivity.class);
                intent.putExtra("id",promotions.get(position).getIdBook());
                startActivity(intent);
            }
        });
    }

    @Override
    public void showBackground(Background background) {
        Glide.with(getContext()).load(background.getImage()).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                imgLayout.setBackground(resource);
            }
        });
    }

    @Override
    public void showRecentMovie(final ArrayList<Book> books) {
        RecentAdapter adapter = new RecentAdapter(getContext(),books);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new RecentAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                Intent intent = new Intent(getContext(), BookDetailActivity.class);
                intent.putExtra("id",books.get(position).getId());
                startActivity(intent);
            }
        });
        btnMoreRecent.setVisibility(View.VISIBLE);
        btnMoreRecent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), BookListActivity.class);
                intent.putExtra(BookListActivity.TYPE,BookListActivity.TYPE_RECENT);
                startActivity(intent);
            }
        });
    }

    @Override
    public void showBestMovie(final ArrayList<Book> books) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerBest.setLayoutManager(layoutManager);
        RecentAdapter adapter = new RecentAdapter(getContext(),books);
        recyclerBest.setAdapter(adapter);
        adapter.setOnItemClickListener(new RecentAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                Intent intent = new Intent(getContext(), BookDetailActivity.class);
                intent.putExtra("id",books.get(position).getId());
                startActivity(intent);
            }
        });
        btnMoreBest.setVisibility(View.VISIBLE);
        btnMoreBest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), BookListActivity.class);
                intent.putExtra(BookListActivity.TYPE, BookListActivity.TYPE_BEST);
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

    public void startNetworkBroadcastReceiver(Context currentContext) {
        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener((NetworkStateReceiver.NetworkStateReceiverListener) currentContext);
        registerNetworkBroadcastReceiver(currentContext);
    }

    public void registerNetworkBroadcastReceiver(Context currentContext) {
        currentContext.registerReceiver(networkStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
    }

    public void unregisterNetworkBroadcastReceiver(Context currentContext) {
        currentContext.unregisterReceiver(networkStateReceiver);
    }
}
