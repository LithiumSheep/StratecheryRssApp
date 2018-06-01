package com.lithiumsheep.stratechery;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.prof.rssparser.Article;
import com.prof.rssparser.Parser;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.coordinator)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.recycler)
    RecyclerView recycler;

    ArticleViewModel viewModel;
    ArticleAdapter adapter;

    boolean loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        final LinearLayoutManager llm = new LinearLayoutManager(this);
        recycler.setLayoutManager(llm);
        adapter = new ArticleAdapter();
        recycler.setAdapter(adapter);

        viewModel = ViewModelProviders.of(this)
                .get(ArticleViewModel.class);

        viewModel.getLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean != null) {
                    swipeRefresh.setRefreshing(aBoolean);
                }
            }
        });

        final Snackbar snackbar = Snackbar.make(coordinatorLayout, "Loading more...", Snackbar.LENGTH_SHORT);
        viewModel.getPaging().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean != null) {
                    if (aBoolean) {
                        snackbar.show();
                    } else {
                        loading = false;
                    }
                }
            }
        });

        viewModel.getStories().observe(this, new Observer<List<Story>>() {
            @Override
            public void onChanged(@Nullable List<Story> stories) {
                if (stories != null) {
                    adapter.addAll(stories);
                }
            }
        });

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.clear();
                viewModel.firstPage();
            }
        });

        recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (!loading) {
                    int visibleItemCount = llm.getChildCount();
                    int totalItemCount = llm.getItemCount();
                    int pastVisibleItems = llm.findFirstVisibleItemPosition();

                    if ((visibleItemCount + pastVisibleItems) >= totalItemCount
                            && pastVisibleItems >= 0) {
                        loading = true;
                        viewModel.nextPage();
                    }
                }
            }
        });

        viewModel.firstPage();
    }
}
