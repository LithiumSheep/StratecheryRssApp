package com.lithiumsheep.stratechery.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.lithiumsheep.stratechery.api.HttpClient;
import com.lithiumsheep.stratechery.api.NetworkCallback;
import com.lithiumsheep.stratechery.models.SearchQuery;
import com.lithiumsheep.stratechery.models.SearchResult;

import timber.log.Timber;

public class PostSearchViewModel extends ViewModel {

    MutableLiveData<SearchResult> searchResults;
    MutableLiveData<Boolean> loading;

    public LiveData<SearchResult> getSearchResults() {
        if (searchResults == null) {
            searchResults = new MutableLiveData<>();
        }
        return searchResults;
    }

    public LiveData<Boolean> getLoading() {
        if (loading == null) {
            loading = new MutableLiveData<>();
        }
        return loading;
    }

    public void search(final String query) {

        final SearchQuery searchBody = new SearchQuery(query);

        loading.setValue(true);

        HttpClient.get().searchPosts(searchBody)
                .enqueue(new NetworkCallback<SearchResult>() {
                    @Override
                    protected void onSuccess(SearchResult response) {
                        loading.setValue(false);
                        if (response != null) {
                            if (searchBody.getQuery().equals(query)) {
                                searchResults.setValue(response);
                            }
                        }
                    }

                    @Override
                    protected void onError(Error error) {
                        loading.setValue(false);
                        Timber.e(error.getMessage());
                    }
                });
    }
}
