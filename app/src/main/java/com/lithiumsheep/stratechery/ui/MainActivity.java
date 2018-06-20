package com.lithiumsheep.stratechery.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.lithiumsheep.stratechery.R;
import com.lithiumsheep.stratechery.models.Story;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends MaterialDrawerActivity {

    @BindView(R.id.coordinator)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.recycler)
    RecyclerView recycler;

    ArticleViewModel viewModel;
    ArticleAdapter adapter;

    boolean paging;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);
        super.attachDrawer(toolbar);

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
                        paging = false;
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
                if (!paging) {
                    int visibleItemCount = llm.getChildCount();
                    int totalItemCount = llm.getItemCount();
                    int pastVisibleItems = llm.findFirstVisibleItemPosition();

                    if ((visibleItemCount + pastVisibleItems) >= totalItemCount
                            && pastVisibleItems >= 0) {
                        paging = true;
                        viewModel.nextPage();
                    }
                }
            }
        });

        viewModel.firstPage();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                Intent intent = new Intent(this, PostSearchActivity.class);
                startActivityForResult(intent, 0);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected boolean shouldMoveTaskToBackOnBackPressed() {
        return true;
    }
}
