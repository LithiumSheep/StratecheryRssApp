package com.lithiumsheep.stratechery.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.lithiumsheep.stratechery.models.Story;
import com.prof.rssparser.Article;
import com.prof.rssparser.Parser;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class ArticleViewModel extends ViewModel {

    private static String URL = "https://stratechery.com/feed/?paged=%d";
    private static int INITIAL_PAGE = 1;

    MutableLiveData<List<Story>> articles;
    MutableLiveData<Boolean> loading;
    MutableLiveData<Boolean> paging;

    private int currentPage = 1;

    public LiveData<List<Story>> getStories() {
        if (articles == null) {
            articles = new MutableLiveData<>();
        }
        return articles;
    }

    public LiveData<Boolean> getLoading() {
        if (loading == null) {
            loading = new MutableLiveData<>();
        }
        return loading;
    }

    public LiveData<Boolean> getPaging() {
        if (paging == null) {
            paging = new MutableLiveData<>();
        }
        return paging;
    }

    private String getUrlForPage(int page) {
        return String.format(URL, page);
    }

    public void firstPage() {
        loading.setValue(true);

        Parser parser = new Parser();
        parser.onFinish(new Parser.OnTaskCompleted() {
            @Override
            public void onTaskCompleted(ArrayList<Article> list) {
                loading.postValue(false);
                if (list != null) {
                    articles.postValue(Story.ofAll(list));
                }
            }

            @Override
            public void onError(Exception e) {
                loading.postValue(false);
                Timber.e(e);
            }
        });
        parser.execute(getUrlForPage(INITIAL_PAGE));
    }

    public void nextPage() {
        currentPage += 1;

        paging.setValue(true);
        Parser parser = new Parser();
        parser.onFinish(new Parser.OnTaskCompleted() {
            @Override
            public void onTaskCompleted(ArrayList<Article> list) {
                paging.postValue(false);
                if (list != null) {
                    articles.postValue(Story.ofAll(list));
                }
            }

            @Override
            public void onError(Exception e) {
                paging.postValue(false);
                Timber.e(e);
            }
        });
        parser.execute(getUrlForPage(currentPage));
    }
}
