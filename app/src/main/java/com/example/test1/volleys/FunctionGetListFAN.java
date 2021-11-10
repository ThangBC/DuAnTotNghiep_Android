package com.example.test1.volleys;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.test1.AddressStudyActivity;
import com.example.test1.CourseActivity;
import com.example.test1.HomeActivity;
import com.example.test1.InterestsActivity;
import com.example.test1.NameActivity;
import com.example.test1.SpecializedActivity;
import com.example.test1.UserDetailActivity;
import com.example.test1.adapters.InterestAdapter;
import com.example.test1.adapters.ReportAdapter;
import com.example.test1.adapters.SpinnerAdapter;
import com.example.test1.adapters.UserAdapter;
import com.example.test1.fragments.HomeFragment;
import com.example.test1.fragments.LikeFragment;
import com.example.test1.interfaces.InterestListener;
import com.example.test1.models.Reports;
import com.example.test1.models.Users;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FunctionGetListFAN {

    public void getListAddressAPI() {
        AddressStudyActivity.addressStudyList = new ArrayList<>();
        AndroidNetworking.get("https://poly-dating.herokuapp.com/api/facilities")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject arr = response.getJSONObject("data");
                            JSONArray arr1 = arr.getJSONArray("facilities");
                            for (int i = 0; i < arr1.length(); i++) {
                                AddressStudyActivity.addressStudyList.add(arr1.get(i).toString());
                            }
                            Log.e("listAddress", "1");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.e("listAddress", "2");
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("err:", anError.getResponse().toString());
                    }
                });
    }

    public void getListReportAPI() {

        UserDetailActivity.reportlist = new ArrayList<>();
        AndroidNetworking.get("https://poly-dating.herokuapp.com/api/reports")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject arr = response.getJSONObject("data");
                            JSONArray arr1 = arr.getJSONArray("reports");
                            for (int i = 0; i < arr1.length(); i++) {
                                UserDetailActivity.reportlist.add(arr1.get(i).toString());
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

    public void getListCourseAPI() {
        CourseActivity.courseList = new ArrayList<>();
        AndroidNetworking.get("https://poly-dating.herokuapp.com/api/course")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject arr = response.getJSONObject("data");
                            JSONArray arr1 = arr.getJSONArray("course");
                            for (int i = 0; i < arr1.length(); i++) {

                                CourseActivity.courseList.add(arr1.get(i).toString());
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

    public void getListInterestAPI() {
        InterestsActivity.interestList = new ArrayList<>();
        AndroidNetworking.get("https://poly-dating.herokuapp.com/api/hobbies")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject arr = response.getJSONObject("data");
                            JSONArray arr1 = arr.getJSONArray("hobbies");
                            for (int i = 0; i < arr1.length(); i++) {
                                InterestsActivity.interestList.add(arr1.get(i).toString());
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

    public void getListSpecializedAPI() {
        SpecializedActivity.specializedList = new ArrayList<>();
        AndroidNetworking.get("https://poly-dating.herokuapp.com/api/specialized")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject arr = response.getJSONObject("data");
                            JSONArray arr1 = arr.getJSONArray("specialized");
                            for (int i = 0; i < arr1.length(); i++) {
                                SpecializedActivity.specializedList.add(arr1.get(i).toString());
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
