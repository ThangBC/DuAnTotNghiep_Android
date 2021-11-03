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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.test1.AddImageActivity;
import com.example.test1.HomeActivity;
import com.example.test1.LoginActivity;
import com.example.test1.NameActivity;
import com.example.test1.adapters.ReportAdapter;
import com.example.test1.fragments.HomeFragment;
import com.example.test1.models.InfoRegister;
import com.example.test1.models.Reports;
import com.example.test1.models.Users;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FunctionUserVolley {

    public void insertUserVolley_POST(Context context, InfoRegister infoRegister) {
 Log.e("xyz", infoRegister.getEmail() + "\n" + infoRegister.getName() + "\n" + infoRegister.getBirthday()
                + "\n" + infoRegister.getSex() + "\n" + infoRegister.getSpecialized() + "\n" + infoRegister.getCourse() + "\n" + infoRegister.getAddressStudy()
                + "\n" + Arrays.toString(infoRegister.getInterests())  + "\n" + infoRegister.getImages() + "\n" + infoRegister.getShow());

        AndroidNetworking.upload("https://poly-dating.herokuapp.com/api/users/insert")
                .addMultipartParameter("email","abc098@gmail.com")
                .addMultipartParameter("name",infoRegister.getName())
                .addMultipartFileList("images",infoRegister.getImages())
                .addMultipartParameter("hobbies", Arrays.toString(infoRegister.getInterests()))
                .addMultipartParameter("isShow",Arrays.toString(infoRegister.getShow()))
                .addMultipartParameter("birthDay",infoRegister.getBirthday())
                .addMultipartParameter("gender",infoRegister.getSex())
                .addMultipartParameter("facilities",infoRegister.getAddressStudy())
                .addMultipartParameter("specialized",infoRegister.getSpecialized())
                .addMultipartParameter("course",infoRegister.getCourse())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if(response.toString().contains("201")){
                            Toast.makeText(context, "Tạo tài khoản thành công", Toast.LENGTH_SHORT).show();
                             context.startActivity(new Intent(context, HomeActivity.class));
                             AddImageActivity.loading.dismissDialog();
                        }else{
                            Toast.makeText(context, "Tạo tài khoản thất bại", Toast.LENGTH_SHORT).show();
                            AddImageActivity.loading.dismissDialog();
                        }
                        Log.e("aaa", "Trả về" + response);
                    }
                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(context, "Tạo tài khoản thất bại", Toast.LENGTH_SHORT).show();
                        AddImageActivity.loading.dismissDialog();
                        Log.e("aa", "Lỗi" + anError.getErrorBody());
                    }
                });

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
                                boolean isActive = jo.getBoolean("isActive");
                                boolean status = jo.getBoolean("status");

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
                                users.setStatus(status);
                                users.setIsActive(isActive);

                                if (users.getIsActive() == true) {
                                    loginActivity.moveActivity = false;
                                } else {
//                                    Log.e("Not Active", users.getIsActive());
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

    public void insertReport(Context context, Reports reports) {

        Log.e("reportList",reports.getEmailReport()+reports.getEmailReported()+reports.getTitle()+reports.getContent());

        AndroidNetworking.upload("https://poly-dating.herokuapp.com/api/reports/insert")
                .addMultipartParameter("emailReport",reports.getEmailReport())
                .addMultipartParameter("emailReported",reports.getEmailReported())
                .addMultipartParameter("title",reports.getTitle())
                .addMultipartParameter("content",reports.getContent())
                .addMultipartFile("images",reports.getImages())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if(response.toString().contains("201")){
                            ReportAdapter.flagchkReport = true;
                        }else{
                            ReportAdapter.flagchkReport = false;
                        }
                        Log.e("aaa", "Trả về" + response);
                    }
                    @Override
                    public void onError(ANError anError) {
                        ReportAdapter.flagchkReport = false;
                        Log.e("aa", "Lỗi" + anError.getErrorBody());
                    }
                });

    }

}
