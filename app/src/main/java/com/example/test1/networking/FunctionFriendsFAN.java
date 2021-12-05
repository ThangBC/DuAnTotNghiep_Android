package com.example.test1.networking;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.test1.HomeActivity;
import com.example.test1.adapters.LikeAdapter;
import com.example.test1.fragments.LikeFragment;
import com.example.test1.fragments.ListFriendsFragment;
import com.example.test1.fragments.MeLikeFragment;
import com.example.test1.models.Users;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FunctionFriendsFAN {

    public void insertFriends(Context context, String myEmail, String emailFriends) {
        AndroidNetworking.post("https://poly-dating.herokuapp.com/api/friends/insert")
                .addBodyParameter("myEmail", myEmail)
                .addBodyParameter("emailFriends", emailFriends)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Toast.makeText(context, response.getString("message"), Toast.LENGTH_SHORT).show();
                            LikeFragment.viewPager.setCurrentItem(2,true);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        String firstStr = anError.getErrorBody().substring(29);
                        String lastStr = firstStr.substring(0, firstStr.length() - 2);
                        Toast.makeText(context, lastStr, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void getListFriends(Context context, String email) {

        ListFriendsFragment.likesList = new ArrayList<>();

        AndroidNetworking.get("https://poly-dating.herokuapp.com/api/friends/list/{email}")
                .addPathParameter("email", email)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject arr = response.getJSONObject("data");
                            JSONArray usersJSON = arr.getJSONArray("friends");
                            for (int i = 0; i < usersJSON.length(); i++) {
                                JSONObject jo = usersJSON.getJSONObject(i).getJSONObject("friends");
                                List<String> fileimg = new ArrayList<>();
                                List<String> hobbiesList = new ArrayList<>();

                                String email = jo.getString("email");
                                String name = jo.getString("name");
                                JSONArray avatars = jo.getJSONArray("images");
                                for (int j = 0; j < avatars.length(); j++) {
                                    fileimg.add(avatars.getString(j));
                                }
                                JSONArray hobbies = jo.getJSONArray("hobbies");
                                for (int j = 0; j < hobbies.length(); j++) {
                                    hobbiesList.add(hobbies.getString(j));
                                }
                                String birthDay = jo.getString("birthDay");
                                String gender = jo.getString("gender");
                                String description = jo.getString("description");
                                String facilities = jo.getString("facilities");
                                String specialized = jo.getString("specialized");
                                String course = jo.getString("course");

                                Users users = new Users();
                                users.setEmail(email);
                                users.setName(name);
                                users.setImageUrl(fileimg);
                                users.setHobbies(hobbiesList);
                                users.setBirthday(birthDay);
                                users.setGender(gender);
                                users.setDescription(description);
                                users.setFacilities(facilities);
                                users.setSpecialized(specialized);
                                users.setCourse(course);

                                ListFriendsFragment.likesList.add(users);
                            }
                            if (ListFriendsFragment.likesList.size() == 0) {
                                ListFriendsFragment.tvCountListFriend.setText(ListFriendsFragment.likesList.size() + " bạn bè");
                                ListFriendsFragment.tv12.setVisibility(View.VISIBLE);
                                ListFriendsFragment.progressBar.setVisibility(View.GONE);
                                ListFriendsFragment.tv12.setText("Chưa có bạn bè");
                            } else {
                                ListFriendsFragment.tvCountListFriend.setText(ListFriendsFragment.likesList.size() + " bạn bè");
                                ListFriendsFragment.progressBar.setVisibility(View.GONE);
                                ListFriendsFragment.tv12.setVisibility(View.GONE);
                            }
                            ListFriendsFragment.likeAdapter = new LikeAdapter(ListFriendsFragment.likesList, context, 3);
                            GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false);
                            ListFriendsFragment.rycListFriend.setLayoutManager(gridLayoutManager);
                            ListFriendsFragment.rycListFriend.setAdapter(ListFriendsFragment.likeAdapter);
                            ListFriendsFragment.likeAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("có chạy vào đây ko ta", "đoán xem");
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        ListFriendsFragment.tv12.setVisibility(View.VISIBLE);
                        ListFriendsFragment.progressBar.setVisibility(View.GONE);
                        ListFriendsFragment.tv12.setText("Chưa có bạn bè");
                    }
                });
    }

    public void deleteFriends(Context context, String myEmail, String emailFriends) {

        AndroidNetworking.post("https://poly-dating.herokuapp.com/api/friends/delete")
                .addBodyParameter("myEmail", myEmail)
                .addBodyParameter("emailFriends", emailFriends)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Toast.makeText(context, response.getString("message"), Toast.LENGTH_SHORT).show();
                            getListFriends(context,HomeActivity.users.getEmail());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        String firstStr = anError.getErrorBody().substring(29);
                        String lastStr = firstStr.substring(0, firstStr.length() - 2);
                        Toast.makeText(context, lastStr, Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
