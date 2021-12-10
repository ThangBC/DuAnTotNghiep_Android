package com.example.test1.networking;

import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.test1.AddressStudyActivity;
import com.example.test1.CourseActivity;
import com.example.test1.InterestsActivity;
import com.example.test1.SpecializedActivity;
import com.example.test1.UserDetailActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FunctionGetListFAN {


    public void getListMaster() {

        AddressStudyActivity.addressStudyList = new ArrayList<>();
        SpecializedActivity.specializedList = new ArrayList<>();
        CourseActivity.courseList = new ArrayList<>();
        UserDetailActivity.reportlist = new ArrayList<>();
        InterestsActivity.interestList = new ArrayList<>();
        AndroidNetworking.get("https://poly-dating.herokuapp.com/api/master/list")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject arr = response.getJSONObject("data");
                            JSONArray facilities = arr.getJSONArray("facilities");
                            JSONArray specialized = arr.getJSONArray("specialized");
                            JSONArray course = arr.getJSONArray("course");
                            JSONArray reports = arr.getJSONArray("reports");
                            JSONArray hobbies = arr.getJSONArray("hobbies");

                            for (int i = 0; i < facilities.length(); i++) {
                                AddressStudyActivity.addressStudyList.add(facilities.get(i).toString());
                            }
                            for (int i = 0; i < specialized.length(); i++) {
                                SpecializedActivity.specializedList.add(specialized.get(i).toString());
                            }
                            for (int i = 0; i < course.length(); i++) {
                                CourseActivity.courseList.add(course.get(i).toString());
                            }
                            for (int i = 0; i < reports.length(); i++) {
                                UserDetailActivity.reportlist.add(reports.get(i).toString());
                            }
                            for (int i = 0; i < hobbies.length(); i++) {
                                InterestsActivity.interestList.add(hobbies.get(i).toString());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("err:", anError.getResponse().toString());
                    }
                });
    }

}
