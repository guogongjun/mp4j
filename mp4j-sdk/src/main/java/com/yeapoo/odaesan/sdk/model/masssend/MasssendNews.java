package com.yeapoo.odaesan.sdk.model.masssend;

import java.util.ArrayList;
import java.util.List;

public class MasssendNews {

    private List<MasssendNewsItem> articles = new ArrayList<MasssendNewsItem>();

    public MasssendNews() {}

    public MasssendNews(List<MasssendNewsItem> articles) {
        this.articles = articles;
    }

    public List<MasssendNewsItem> getArticles() {
        return articles;
    }

    public void setArticles(List<MasssendNewsItem> articles) {
        this.articles = articles;
    }

    public void addAtricle(MasssendNewsItem item) {
        articles.add(item);
    }

    private static final String JSONTemplate = 
            "{\n" +
                    "\"articles\": [" + "\n" +
                        "%s" + "\n" +
                    "]" + "\n" +
            "}";

    public String toJSON() {
        StringBuilder articlesData = new StringBuilder();
        for (MasssendNewsItem item : articles) {
            articlesData.append(item.toJSON()).append(",");
        }
        articlesData.deleteCharAt(articlesData.length() - 1);
        return String.format(JSONTemplate, articlesData.toString());
    }
}
