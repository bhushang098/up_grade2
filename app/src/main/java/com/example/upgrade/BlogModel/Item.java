
package com.example.upgrade.BlogModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item {


    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("published")
    @Expose
    private String published;

    @SerializedName("url")
    @Expose
    private String url;

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("author")
    @Expose
    private Author author;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getPublished() {
        return published;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Author getAuthor() {
        return author;
    }

}
