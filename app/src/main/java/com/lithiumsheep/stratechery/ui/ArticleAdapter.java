package com.lithiumsheep.stratechery.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lithiumsheep.stratechery.R;
import com.lithiumsheep.stratechery.models.Story;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ArticleAdapter extends BaseRecyclerAdapter<Story> {

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_article, parent, false);
        return new storyVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        storyVH item = (storyVH) holder;

        item.bind(getItem(position));
    }

    static class storyVH extends RecyclerView.ViewHolder {

        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.author)
        TextView author;
        @BindView(R.id.publish_date)
        TextView publishDate;
        @BindView(R.id.description)
        TextView description;
        @BindView(R.id.image)
        ImageView image;

        Story story;
        Context context;

        storyVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.context = itemView.getContext();
        }

        void bind(Story story) {
            this.story = story;

            title.setText(story.getTitle());
            author.setText(story.getAuthor());

            SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd", Locale.getDefault());
            //publishDate.setText(format.format(story.getPubDate()));
            description.setText(story.getDescription());

            Picasso.get()
                    .load(story.getImage())
                    .into(image);
        }

        @OnClick(R.id.card_view)
        void onstoryCardClicked() {
            context.startActivity(ArticleActivity.newIntent(context, story));
        }
    }
}
