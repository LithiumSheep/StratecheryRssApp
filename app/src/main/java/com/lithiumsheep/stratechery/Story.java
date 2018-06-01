package com.lithiumsheep.stratechery;

import com.prof.rssparser.Article;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Story {

    String title;
    String author;
    String link;
    String pubDate;
    String description;
    String content;
    String image;
    List<String> categories;

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
}
