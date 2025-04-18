package com.yourdomain.summarizer.model;

public class Article {
    private String title;
    private String link;
    private String summary;

    public Article() {}

    public Article(String title, String link, String summary) {
        this.title = title;
        this.link = link;
        this.summary = summary;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
