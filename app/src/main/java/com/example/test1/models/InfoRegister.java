package com.example.test1.models;

import java.io.Serializable;

public class InfoRegister implements Serializable {

    String name,birthday,sex,specialized,course,addressStudy,show;
    String[] arrayInterest;

    public InfoRegister(String name, String birthday, String sex, String specialized, String course, String addressStudy, String show) {
        this.name = name;
        this.birthday = birthday;
        this.sex = sex;
        this.specialized = specialized;
        this.course = course;
        this.addressStudy = addressStudy;
        this.show = show;
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
}
