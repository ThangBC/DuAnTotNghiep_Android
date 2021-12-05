package com.example.test1.models;

public class User {
    int imgURL;
    String name;
    int age;

    public User() {
    }

    public User(int imgURL, String name, int age) {
        this.imgURL = imgURL;
        this.name = name;
        this.age = age;
    }

    public int getImgURL() {
        return imgURL;
    }

    public void setImgURL(int imgURL) {
        this.imgURL = imgURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
