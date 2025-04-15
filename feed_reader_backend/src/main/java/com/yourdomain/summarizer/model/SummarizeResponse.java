package com.yourdomain.summarizer.model;

import java.util.List;

public class SummarizeResponse {
    private List<Article> articles;

    public SummarizeResponse() {}

    public SummarizeResponse(List<Article> articles) {
        this.articles = articles;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}
