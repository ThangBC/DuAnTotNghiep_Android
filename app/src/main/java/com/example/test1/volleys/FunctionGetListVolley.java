package com.example.test1.volleys;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

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
import com.example.test1.InterestsActivity;
import com.example.test1.SpecializedActivity;
import com.example.test1.UserDetailActivity;
import com.example.test1.adapters.InterestAdapter;
import com.example.test1.adapters.SpinnerAdapter;
import com.example.test1.adapters.UserAdapter;
import com.example.test1.fragments.HomeFragment;
import com.example.test1.interfaces.InterestListener;
import com.example.test1.models.Users;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FunctionGetListVolley {


    public void getListUser_GET(Context context) {
        HomeFragment.userList = new ArrayList<>();
        AndroidNetworking.get("https://poly-dating.herokuapp.com/api/users/list")
                .addQueryParameter("isShow[0]", "[Mọi người]")
                .addQueryParameter("pageSize", "100")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject arr = response.getJSONObject("data");
                            JSONArray usersJSON = arr.getJSONArray("users");

                            for (int i = 0; i < usersJSON.length(); i++) {
                                List<String> fileimg = new ArrayList<>();
                                String email = usersJSON.getJSONObject(i).getString("email");
                                String name = usersJSON.getJSONObject(i).getString("name");
                                JSONArray avatars = usersJSON.getJSONObject(i).getJSONArray("avatars");
                                for (int j = 0;j<avatars.length();j++){
                                    fileimg.add(avatars.getString(j));
                                    Log.e("j = ", String.valueOf(j));
                                }
                                String hobbies = usersJSON.getJSONObject(i).getString("hobbies");
                                String birthDay = usersJSON.getJSONObject(i).getString("birthDay");
                                String gender = usersJSON.getJSONObject(i).getString("gender");
                                String description = usersJSON.getJSONObject(i).getString("description");
                                String facilities = usersJSON.getJSONObject(i).getString("facilities");
                                String specialized = usersJSON.getJSONObject(i).getString("specialized");
                                String course = usersJSON.getJSONObject(i).getString("course");
                                String isShow = usersJSON.getJSONObject(i).getString("isShow");
//                                boolean isActive = usersJSON.getJSONObject(i).getBoolean("isActive");
//                                boolean status = usersJSON.getJSONObject(i).getBoolean("status");

                                Users users = new Users();
                                users.setEmail(email);
                                users.setName(name);
                                users.setAvatars(fileimg);
                                users.setHobbies(hobbies);
                                users.setBirthDay(birthDay);
                                users.setGender(gender);
                                users.setDescription(description);
                                users.setFacilities(facilities);
                                users.setSpecialized(specialized);
                                users.setCourse(course);
                                users.setIsShow(isShow);
//                                users.setIsActive(isActive);
//                                users.setStatus(status);
                                HomeFragment.userList.add(users);
                                Log.e("alo123", users.getName());
                            }
                            HomeFragment.userAdapter = new UserAdapter(HomeFragment.userList, context);
                            HomeFragment.flingAdapterView.setAdapter(HomeFragment.userAdapter);
                            HomeFragment.userAdapter.notifyDataSetChanged();
                            HomeFragment.progressBar.setVisibility(View.GONE);
                            HomeFragment.tv12.setVisibility(View.GONE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("có chạy vào đây ko ta","đoán xem");
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(context, "" + anError.getErrorBody(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void getListAddressAPI(Context context) {
        String url = "https://poly-dating.herokuapp.com/api/facilities";
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, new JSONObject(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject arr = response.getJSONObject("data");
                    JSONArray arr1 = arr.getJSONArray("facilities");
                    for (int i = 0; i < arr1.length(); i++) {
                        AddressStudyActivity.addressStudyList.add(arr1.get(i).toString());
                    }
                    Log.e("listAddress","1");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e("listAddress","2");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    public void getListReportAPI(Context context) {

        String url = "https://poly-dating.herokuapp.com/api/reports";

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, new JSONObject(), new Response.Listener<JSONObject>() {
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
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    public void getListCourseAPI(Context context) {
        String url = "https://poly-dating.herokuapp.com/api/course";
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, new JSONObject(), new Response.Listener<JSONObject>() {
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
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    public void getListInterestAPI(Context context) {
        String url = "https://poly-dating.herokuapp.com/api/hobbies";
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url, new JSONObject(), new Response.Listener<JSONObject>() {
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
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    public void getListSpecializedAPI(Context context) {
        String url = "https://poly-dating.herokuapp.com/api/specialized";
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url, new JSONObject(), new Response.Listener<JSONObject>() {
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
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

}
