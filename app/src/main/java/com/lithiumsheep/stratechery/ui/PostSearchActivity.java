package com.lithiumsheep.stratechery.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.SearchView;

import com.jakewharton.rxbinding2.widget.RxSearchView;
import com.lithiumsheep.stratechery.R;
import com.lithiumsheep.stratechery.models.SearchResult;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class PostSearchActivity extends AppCompatActivity {

    @BindView(R.id.search_view)
    SearchView searchView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recycler)
    RecyclerView recycler;

    PostSearchViewModel viewModel;
    Disposable searchDisposable;

    SearchResultAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        recycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SearchResultAdapter();
        recycler.setAdapter(adapter);

        viewModel = ViewModelProviders.of(this)
                .get(PostSearchViewModel.class);

        viewModel.getSearchResults().observe(this, new Observer<SearchResult>() {
            @Override
            public void onChanged(@Nullable SearchResult searchResult) {
                if (searchResult != null) {
                    adapter.clear();
                    adapter.addAll(searchResult.getHits());
                }
            }
        });

        viewModel.getLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean loading) {
                if (loading != null) {
                    swipeRefreshLayout.setRefreshing(loading);
                }
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.clear();
                viewModel.search(searchView.getQuery().toString());
            }
        });

        searchDisposable =
                RxSearchView.queryTextChanges(searchView)
                        .skipInitialValue()
                        .filter(new Predicate<CharSequence>() {
                            @Override
                            public boolean test(CharSequence charSequence) throws Exception {
                                return charSequence.length() >= 2;
                            }
                        })
                        .debounce(350, TimeUnit.MILLISECONDS)
                        .map(new Function<CharSequence, String>() {
                            @Override
                            public String apply(CharSequence charSequence) throws Exception {
                                return charSequence.toString();
                            }
                        })
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<String>() {
                            @Override
                            public void accept(String s) throws Exception {
                                viewModel.search(s);
                            }
                        });


        searchView.requestFocusFromTouch();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (searchDisposable != null) {
            searchDisposable.dispose();
        }
    }
}
