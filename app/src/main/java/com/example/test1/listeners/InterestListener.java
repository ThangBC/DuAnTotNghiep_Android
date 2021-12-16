package com.example.test1.listeners;

import java.util.List;

public interface InterestListener {
    void changeInterest(List<String> arr, int count);// nhận list giá trị lượt thích từ adapter
    void changeSelectedIsShow(String selected);// nhận giá trị của radiobutton từ adapter
}
