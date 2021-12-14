package com.example.test1.models;

public class Notification {

    String _id;
    String title;
    String content;
    String link;
    String date;

    public Notification() {
    }

    public Notification(String _id, String title, String content, String link,String date) {
        this._id = _id;
        this.title = title;
        this.content = content;
        this.link = link;
        this.date = date;
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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
