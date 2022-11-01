package com.example.musicwebapp.entity;

import java.util.ArrayList;
import java.util.Date;

public class Article{
    public Source source;
    public String author;
    public String title;
    public String description;
    public String url;
    public String urlToImage;
    public Date publishedAt;
    public String content;

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }
}

class Root{
    public String status;
    public int totalResults;
    public ArrayList<Article> articles;
}

class Source{
    public String id;
    public String name;
}