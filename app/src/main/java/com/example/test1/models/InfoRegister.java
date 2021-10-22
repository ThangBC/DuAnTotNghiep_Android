package com.example.test1.models;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.List;

public class InfoRegister implements Serializable {

    String email, name, birthday, sex, specialized, course, addressStudy, show, description;
    String [] interests;
    List<File> images;

    public InfoRegister() {
    }

    public InfoRegister(String email, String name, String birthday, String sex, String specialized, String course,
                        String addressStudy, String [] interests,List<File> images) {
        this.email = email;
        this.name = name;
        this.birthday = birthday;
        this.sex = sex;
        this.specialized = specialized;
        this.course = course;
        this.addressStudy = addressStudy;
        this.interests = interests;
        this.images = images;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSpecialized() {
        return specialized;
    }

    public void setSpecialized(String specialized) {
        this.specialized = specialized;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getAddressStudy() {
        return addressStudy;
    }

    public void setAddressStudy(String addressStudy) {
        this.addressStudy = addressStudy;
    }

    public String getShow() {
        return show;
    }

    public void setShow(String show) {
        this.show = show;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String [] getInterests() {
        return interests;
    }

    public void setInterests(String [] interests) {
        this.interests = interests;
    }

    public List<File>getImages() {
        return images;
    }

    public void setImages(List<File> images) {
        this.images = images;
    }
}
