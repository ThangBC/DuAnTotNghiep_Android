package com.example.test1.models;

import java.io.File;
import java.util.List;

public class Users {

    private String email;
    private String name;
    private List<String> avatars;
    private String hobbies;
    private String birthDay;
    private String gender;
    private String description;
    private String  facilities;
    private String specialized;
    private String course;
    private String isShow;
    private boolean isActive;
    private boolean status;

    public Users() {
    }

    public Users(String email, String name, List<String> avatars, String hobbies, String birthDay, String gender, String description, String facilities, String specialized, String course, String isShow, boolean isActive, boolean status) {
        this.email = email;
        this.name = name;
        this.avatars = avatars;
        this.hobbies = hobbies;
        this.birthDay = birthDay;
        this.gender = gender;
        this.description = description;
        this.facilities = facilities;
        this.specialized = specialized;
        this.course = course;
        this.isShow = isShow;
        this.isActive = isActive;
        this.status = status;
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

    public List<String> getAvatars() {
        return avatars;
    }

    public void setAvatars(List<String> avatars) {
        this.avatars = avatars;
    }

    public String getHobbies() {
        return hobbies;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFacilities() {
        return facilities;
    }

    public void setFacilities(String facilities) {
        this.facilities = facilities;
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

    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean active) {
        isActive = active;
    }
}
