package com.lithiumsheep.stratechery.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lithiumsheep.stratechery.R;
import com.lithiumsheep.stratechery.models.SearchHit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public final class SearchResultAdapter extends BaseRecyclerAdapter<SearchHit> {

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_search_hit, parent, false);
        return new SearchHitVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        SearchHitVH item = (SearchHitVH) holder;

        item.bind(getItem(position));
    }

    static class SearchHitVH extends RecyclerView.ViewHolder {
        @BindView(R.id.hit_type_chip)
        TextView hitType;
        @BindView(R.id.hit_title)
        TextView hitTitle;
        @BindView(R.id.hit_subtitle)
        TextView hitSubtitle;

        Context context;
        SearchHit hit;

        SearchHitVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.context = itemView.getContext();
        }

        void bind(SearchHit hit) {
            this.hit = hit;

            hitType.setText(hit.getLabel());
            hitTitle.setText(hit.getTitle());
            hitSubtitle.setText(Html.fromHtml(hit.getExcerpt()).toString().replaceAll("\n", "").trim());
        }

        @OnClick(R.id.card_view)
        void onSearchHitClicked() {
            Timber.d("Clicked post id %s", hit.getId());
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(hit.getPermalink())));
        }
    }
}
