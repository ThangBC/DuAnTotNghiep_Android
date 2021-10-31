package com.example.test1.volleys;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.test1.LoginActivity;
import com.example.test1.NameActivity;
import com.example.test1.fragments.HomeFragment;
import com.example.test1.models.InfoRegister;
import com.example.test1.models.Users;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FunctionUserVolley {

    public void insertUserVolley_POST(Context context, InfoRegister infoRegister) {
        // tạo đối tượng Volley

//        Map<String, File> multiPartFileMap = new HashMap<>();
//        multiPartFileMap.put("image1" , );
//        multiPartFileMap.put("image2" , file2);

//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("email", infoRegister.getEmail());
//            jsonObject.put("name", infoRegister.getName());
//            jsonObject.put("hobbies", "aaaa");
//            jsonObject.put("birthDay", infoRegister.getBirthday());
//            jsonObject.put("gender", infoRegister.getSex());
//            jsonObject.put("facilities", infoRegister.getAddressStudy());
//            jsonObject.put("specialized", infoRegister.getSpecialized());
//            jsonObject.put("course", infoRegister.getCourse());
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        Log.e("xyz", infoRegister.getEmail() + "\n" + infoRegister.getName() + "\n" + infoRegister.getBirthday()
                + "\n" + infoRegister.getSex() + "\n" + infoRegister.getSpecialized() + "\n" + infoRegister.getCourse() + "\n" + infoRegister.getAddressStudy()
                + "\n" + infoRegister.getInterests() + "\n" + infoRegister.getImages() + "\n" + infoRegister.getShow());

        AndroidNetworking.upload("https://poly-dating.herokuapp.com/api/users/insert")
                .addMultipartParameter("email", "duyenrach@gmail.com")
                .addMultipartParameter("name", infoRegister.getName())
                .addMultipartFileList("avatars", infoRegister.getImages())
                .addMultipartParameter("hobbies", Arrays.toString(infoRegister.getInterests()))
                .addMultipartParameter("isShow", infoRegister.getShow())
                .addMultipartParameter("birthDay", infoRegister.getBirthday())
                .addMultipartParameter("gender", infoRegister.getSex())
                .addMultipartParameter("facilities", infoRegister.getAddressStudy())
                .addMultipartParameter("specialized", infoRegister.getSpecialized())
                .addMultipartParameter("course", infoRegister.getCourse())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("aaa", "Trả về" + response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("aa", "Lỗi" + anError.getErrorBody());
                    }
                });

//        RequestQueue queue = Volley.newRequestQueue(context);
//        // Url
//        String url = "https://poly-dating.herokuapp.com/api/users/insert";
//        // Đưa url vào request
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//
//            @Override
//            public void onResponse(String response) {
//                Log.e("aa","Trả về nè "+response.toString());
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e("aa","Lỗi nè "+error.getMessage());
//            }
//        }) {
//
//            @Nullable
//
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map map  =new HashMap();
//                map.put("email", infoRegister.getEmail());
//                return map;
//            }

//            @Override
//            public String getBodyContentType() {
//                return "application/json; charset=utf-8";
//            }

//            @Override
//            public byte[] getBody() {
//                JSONObject jsonObject = new JSONObject();
//                try {
//                    jsonObject.put("email", infoRegister.getEmail());
//                    jsonObject.put("name", infoRegister.getName());
////                    jsonObject.put("avatars", "quanrach.jpg");
//                    jsonObject.put("hobbies", "Quân rách");
//                    jsonObject.put("birthDay", infoRegister.getBirthday());
//                    jsonObject.put("gender", infoRegister.getSex());
//                    jsonObject.put("facilities", infoRegister.getAddressStudy());
//                    jsonObject.put("specialized", infoRegister.getSpecialized());
//                    jsonObject.put("course", infoRegister.getCourse());
//                    Log.e("mm", ""+infoRegister.getEmail());
//                    Log.e("hobbies", ""+infoRegister.getInterests());
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//                return jsonObject.toString().getBytes();
//            }

//        };
        // đưa vào request
//        queue.add(stringRequest);
    }

    public void searchUser_GET(String email) {
        LoginActivity loginActivity = new LoginActivity();
        String url = "https://poly-dating.herokuapp.com/api/users/search/{email}";
        AndroidNetworking.get(url)
                .addPathParameter("email", email)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
//
                        try {
                            if (response.getString("data").equals("null")) {
                                loginActivity.moveActivity = true;
                            } else {
                                List<String> fileimg = new ArrayList<>();
                                JSONObject jo = response.getJSONObject("data").getJSONObject("user");
                                Log.e("data:", response.toString());
                                String email = jo.getString("email");
                                String name = jo.getString("name");
                                JSONArray avatars = jo.getJSONArray("avatars");
                                for (int j = 0; j < avatars.length(); j++) {
                                    fileimg.add(avatars.getString(j));
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

                                if (users.getIsActive().equals("Kích hoạt")) {
                                    loginActivity.moveActivity = false;
                                } else {
                                    Log.e("Not Active", users.getIsActive());

                                }

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
