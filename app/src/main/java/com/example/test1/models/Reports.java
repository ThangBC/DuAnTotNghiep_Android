package com.example.test1.models;

import java.io.File;
import java.util.List;

public class Reports {
    private String emailReport;
    private String emailReported;
    private String title;
    private String content;
    private File images;

    public Reports() {
    }

    public Reports(String emailReport, String emailReported, String title, String content, File images) {
        this.emailReport = emailReport;
        this.emailReported = emailReported;
        this.title = title;
        this.content = content;
        this.images = images;
    }

    public String getEmailReport() {
        return emailReport;
    }

    public void setEmailReport(String emailReport) {
        this.emailReport = emailReport;
    }

    public String getEmailReported() {
        return emailReported;
    }

    public void setEmailReported(String emailReported) {
        this.emailReported = emailReported;
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

    public File getImages() {
        return images;
    }

    public void setImages(File images) {
        this.images = images;
    }
}
