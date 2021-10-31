package com.example.test1.volleys;

import android.content.Context;
import android.util.Log;
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

                            for (int i = 0; i <= usersJSON.length(); i++) {
                                List<String> fileimg = new ArrayList<>();
                                JSONObject jo = usersJSON.getJSONObject(i);
                                String email = jo.getString("email");
                                String name = jo.getString("name");
                                JSONArray avatars = jo.getJSONArray("avatars");
                                for (int j = 0;j<avatars.length();j++){
                                    fileimg.add(avatars.getString(j));
                                    Log.e("j = ", String.valueOf(j));
                                }
                                String hobbies = jo.getString("hobbies");
                                String birthDay = jo.getString("birthDay");
                                String gender = jo.getString("gender");
                                String description = jo.getString("description");
                                String facilities = jo.getString("facilities");
                                String specialized = jo.getString("specialized");
                                String course = jo.getString("course");
                                String isShow = jo.getString("isShow");
                                String isActive = jo.getString("isActive");
                                String status = jo.getString("status");
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
                                users.setIsActive(isActive);
                                users.setStatus(status);
                                HomeFragment.userList.add(users);
                                Log.e("alo123", users.getName());
//                                Log.e("alo12anh",users.getAvatars());
                            }
//                            UserAdapter userAdapter = new UserAdapter(userList,context);
//                            sflw.setAdapter(userAdapter);
//                            Log.e("list",
//                            Arrays.toString(usersList.toArray()));
                            Log.e("msg", String.valueOf(HomeFragment.userList.size()));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

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
