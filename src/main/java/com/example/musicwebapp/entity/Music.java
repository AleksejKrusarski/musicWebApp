package com.example.musicwebapp.entity;

import java.util.ArrayList;
import java.util.Date;

public class Music {
    private String status;
    private float totalResults;
    ArrayList<Article> articles = new ArrayList<Article>();

    public Music(String status, float totalResults) {
        this.status = status;
        this.totalResults = totalResults;
    }

// Getter Methods

    public String getStatus() {
        return status;
    }

    public float getTotalResults() {
        return totalResults;
    }

    public ArrayList<Article> getArticles() {
        return articles;
    }

    public void setArticles(ArrayList<Article> articles) {
        this.articles = articles;
    }

    // Setter Methods

    public void setStatus( String status ) {
        this.status = status;
    }

    public void setTotalResults( float totalResults ) {
        this.totalResults = totalResults;
    }
}
