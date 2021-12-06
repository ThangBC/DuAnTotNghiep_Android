package com.example.test1.models;

import java.io.File;
import java.util.List;

public class Users {

    private String _id;
    private String email;
    private String password;
    private String name;
    private List<File> images;
    private List<String> imageUrl;
    private List<String> hobbies;
    private String birthday;
    private String gender;
    private String description;
    private String facilities;
    private String specialized;
    private String course;
    private List<String> isShow;
    private boolean isActive;
    private boolean status;
    private boolean statusHobbies;
    private boolean roleAdmin;
    private String token;

    public Users() {
    }

    public Users(String _id, String email, String password, String name, List<String> imageUrl, List<String> hobbies
            , String birthday, String gender, String description, String facilities, String specialized
            , String course, List<String> isShow, boolean isActive, boolean status,boolean statusHobbies) {
        this._id = _id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.imageUrl = imageUrl;
        this.hobbies = hobbies;
        this.birthday = birthday;
        this.gender = gender;
        this.description = description;
        this.facilities = facilities;
        this.specialized = specialized;
        this.course = course;
        this.isShow = isShow;
        this.isActive = isActive;
        this.status = status;
        this.statusHobbies = statusHobbies;
    }

    public Users(String email, String name, List<File> images, List<String> hobbies, String birthday,
                 String gender, String facilities, String specialized, String course,String token) {
        this.email = email;
        this.name = name;
        this.images = images;
        this.hobbies = hobbies;
        this.birthday = birthday;
        this.gender = gender;
        this.facilities = facilities;
        this.specialized = specialized;
        this.course = course;
        this.token = token;
    }

    public Users(String description,List<String> hobbies,String facilities,String specialized){
        this.description = description;
        this.hobbies = hobbies;
        this.facilities = facilities;
        this.specialized = specialized;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getHobbies() {
        return hobbies;
    }

    public void setHobbies(List<String> hobbies) {
        this.hobbies = hobbies;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
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

    public List<String> getIsShow() {
        return isShow;
    }

    public void setIsShow(List<String> isShow) {
        this.isShow = isShow;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isRoleAdmin() {
        return roleAdmin;
    }

    public void setRoleAdmin(boolean roleAdmin) {
        this.roleAdmin = roleAdmin;
    }

    public List<File> getImages() {
        return images;
    }

    public void setImages(List<File> images) {
        this.images = images;
    }

    public boolean isStatusHobbies() {
        return statusHobbies;
    }

    public void setStatusHobbies(boolean statusHobbies) {
        this.statusHobbies = statusHobbies;
    }

    public List<String> getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(List<String> imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
