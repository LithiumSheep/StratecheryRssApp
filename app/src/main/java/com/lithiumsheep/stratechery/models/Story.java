package com.lithiumsheep.stratechery.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.prof.rssparser.Article;

import java.util.ArrayList;
import java.util.List;

public final class Story implements Parcelable {

    String title;
    String author;
    String link;
    String pubDate;
    String description;
    String content;
    String image;
    List<String> categories;

    Story() {

    }

    protected Story(Parcel in) {
        title = in.readString();
        author = in.readString();
        link = in.readString();
        pubDate = in.readString();
        description = in.readString();
        content = in.readString();
        image = in.readString();
        categories = in.createStringArrayList();
    }

    public static Story of(Article other) {
        Story article = new Story();
        article.title = other.getTitle();
        article.author = other.getAuthor();
        article.link = other.getLink();
        article.pubDate = other.getPubDate().toString();
        article.description = other.getDescription();
        article.content = other.getContent();
        article.image = other.getImage();
        article.categories = other.getCategories();
        return article;
    }
    
    public static List<Story> ofAll(List<Article> other) {
        ArrayList<Story> list = new ArrayList<>();
        for (Article item : other) {
            list.add(of(item));
        }
        return list;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getLink() {
        return link;
    }

    public String getPubDate() {
        return pubDate;
    }

    public String getDescription() {
        return description;
    }

    public String getContent() {
        return content;
    }

    public String getImage() {
        return image;
    }

    public List<String> getCategories() {
        return categories;
    }

    public static final Creator<Story> CREATOR = new Creator<Story>() {
        @Override
        public Story createFromParcel(Parcel in) {
            return new Story(in);
        }

        @Override
        public Story[] newArray(int size) {
            return new Story[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(author);
        dest.writeString(link);
        dest.writeString(pubDate);
        dest.writeString(description);
        dest.writeString(content);
        dest.writeString(image);
        dest.writeStringList(categories);
    }
}
