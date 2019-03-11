package com.lithiumsheep.stratechery.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.widget.TextView;

import com.lithiumsheep.stratechery.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * Much help from https://medium.com/@rajeefmk/android-textview-and-image-loading-from-url-part-1-a7457846abb6
 *
 * PicassoImageGetter places placeholder image drawables in Html <img> tags while loading images
 * from network using Picasso.
 */
public final class PicassoImageGetter implements Html.ImageGetter {

    private TextView textView;
    private Context context;

    public PicassoImageGetter(TextView target) {
        this.context = target.getContext();
        this.textView = target;
    }

    @Override
    public Drawable getDrawable(String source) {
        BitmapDrawablePlaceholder drawable = new BitmapDrawablePlaceholder();
        Picasso.get()
                .load(source)
                .placeholder(R.drawable.ic_image)
                .into(drawable);
        return drawable;
    }

    private class BitmapDrawablePlaceholder extends BitmapDrawable implements Target {

        Drawable drawable;

        @Override
        public void draw(Canvas canvas) {
            if (drawable != null) {
                drawable.draw(canvas);
            }
        }

        void setDrawable(Drawable drawable) {
            this.drawable = drawable;
            int width = drawable.getIntrinsicWidth();
            int height = drawable.getIntrinsicHeight();
            drawable.setBounds(0, 0, width, height);
            setBounds(0, 0, width, height);
            if (textView != null) {
                textView.setText(textView.getText());
            }
        }

        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            setDrawable(new BitmapDrawable(context.getResources(), bitmap));
        }

        @Override
        public void onBitmapFailed(Exception e, Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    }
}
