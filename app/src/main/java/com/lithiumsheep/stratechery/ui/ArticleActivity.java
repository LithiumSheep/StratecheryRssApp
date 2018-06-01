package com.lithiumsheep.stratechery.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.lithiumsheep.stratechery.R;
import com.lithiumsheep.stratechery.models.Story;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticleActivity extends AppCompatActivity {

    private static final String ARTICLE = "extra_article";

    public static Intent newIntent(Context context, Story story) {
        Intent intent = new Intent(context, ArticleActivity.class);
        intent.putExtra(ARTICLE, Parcels.wrap(story));
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);   // one instance of article activity please
        return intent;
    }

    @BindView(R.id.backdrop)
    ImageView backdrop;
    @BindView(R.id.author)
    TextView author;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.content)
    TextView content;

    Story story;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        ButterKnife.bind(this);

        if (getIntent().hasExtra(ARTICLE)) {
            this.story = Parcels.unwrap(getIntent().getParcelableExtra(ARTICLE));
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(story.getTitle());
            getSupportActionBar().setSubtitle("date/time");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if (this.story != null) {
            Picasso.get()
                    .load(story.getImage())
                    .into(backdrop);

            author.setText(story.getAuthor());
            title.setText(story.getTitle());

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                content.setText(Html.fromHtml(story.getContent(), Html.FROM_HTML_MODE_COMPACT));
            } else {
                content.setText(Html.fromHtml(story.getContent()));
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            // or
            //finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
