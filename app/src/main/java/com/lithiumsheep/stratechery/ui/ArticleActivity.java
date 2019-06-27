package com.lithiumsheep.stratechery.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lithiumsheep.stratechery.R;
import com.lithiumsheep.stratechery.models.Story;
import com.lithiumsheep.stratechery.utils.PicassoImageGetter;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

public final class ArticleActivity extends AppCompatActivity {

    private static final String ARTICLE = "extra_article";

    public static Intent newIntent(Context context, Story story) {
        Intent intent = new Intent(context, ArticleActivity.class);
        intent.putExtra(ARTICLE, story);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);   // one instance of article activity please
        return intent;
    }

    @BindView(R.id.backdrop) ImageView backdrop;
    @BindView(R.id.scroll_view) NestedScrollView scrollView;
    @BindView(R.id.author) TextView author;
    @BindView(R.id.title) TextView title;
    @BindView(R.id.content) TextView content;

    private Story story;
    private boolean flaggedForLowProfile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        ButterKnife.bind(this);

        if (getIntent().hasExtra(ARTICLE)) {
            this.story = getIntent().getParcelableExtra(ARTICLE);
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(story.getTitle());
            getSupportActionBar().setSubtitle("date/time");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if (this.story != null) {
            if (story.getImage() != null && !story.getImage().isEmpty()) {
                Picasso.get()
                        .load(story.getImage())
                        .into(backdrop);
            }

            author.setText(story.getAuthor());
            title.setText(story.getTitle());

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                content.setText(Html.fromHtml(story.getContent(), Html.FROM_HTML_MODE_LEGACY,
                        new PicassoImageGetter(content), null));
            } else {
                content.setText(Html.fromHtml(story.getContent(),
                        new PicassoImageGetter(content), null));
            }
        } else {
            Toast.makeText(this, "Couldn't load null article", Toast.LENGTH_LONG).show();
        }

        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView nestedScrollView, int scrollX, int scrollY, int oldX, int oldY) {
                if (scrollY > oldY) {   // scrolling DOWN
                    if (!flaggedForLowProfile) {
                        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                        flaggedForLowProfile = true;
                    }
                }
                if (scrollY < oldY) {   // scrolling UP
                    if (flaggedForLowProfile) {
                        getWindow().getDecorView().setSystemUiVisibility(0);
                        flaggedForLowProfile = false;
                    }
                }

                if (scrollY == 0) { // scrolled to TOP
                    if (flaggedForLowProfile) {
                        getWindow().getDecorView().setSystemUiVisibility(0);
                        flaggedForLowProfile = false;
                    }
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
