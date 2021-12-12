package com.example.test1.networking;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.test1.EditProActivity;
import com.example.test1.HomeActivity;
import com.example.test1.adapters.LikeAdapter;
import com.example.test1.adapters.RecentConversionsAdapter;
import com.example.test1.fragments.ChatFragment;
import com.example.test1.fragments.DfrPeopleLikeFragment;
import com.example.test1.fragments.LikeFragment;
import com.example.test1.fragments.ListFriendsFragment;
import com.example.test1.fragments.MeLikeFragment;
import com.example.test1.listeners.ConversationListener;
import com.example.test1.listeners.UserListener;
import com.example.test1.models.User;
import com.example.test1.models.Users;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FunctionFriendsFAN {

    public void insertFriends(Context context, String myEmail, String emailFriends, int check) {

        AndroidNetworking.post("https://poly-dating.herokuapp.com/api/friends/friend-request")
                .addBodyParameter("myEmail", myEmail)
                .addBodyParameter("emailFriend", emailFriends)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Toast.makeText(context, response.getString("message"), Toast.LENGTH_SHORT).show();
                            if (check == 1) {
                                LikeFragment.viewPagerAdapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        String firstStr = anError.getErrorBody().substring(29);
                        String lastStr = firstStr.substring(0, firstStr.length() - 2);
                        Toast.makeText(context, lastStr, Toast.LENGTH_SHORT).show();
                        Log.e("insert", "Lỗi");
                    }
                });
    }

    public void getListFriendsRequetst(Context context, String email, List<Users> likeList, RecyclerView rycLike,
                                       LikeAdapter likeAdapter, ProgressBar progressBar,
                                       TextView tvCountFavorite, TextView tv12) {

        AndroidNetworking.get("https://poly-dating.herokuapp.com/api/friends/list-friends-requests/{email}")
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
                                JSONObject jo = usersJSON.getJSONObject(i).getJSONObject("myUser");
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

                                likeList.add(users);
                                Log.e("abcdef", users.getEmail());
                            }
                            if (likeList.size() == 0) {
                                tvCountFavorite.setText(likeList.size() + " lượt thích");
                                tv12.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);
                                tv12.setText("Chưa ai thích bạn");

                            } else {
                                tvCountFavorite.setText(likeList.size() + " lượt thích");
                                progressBar.setVisibility(View.GONE);
                                tv12.setVisibility(View.GONE);
                            }
                            likeAdapter.notifyDataSetChanged();
                            rycLike.smoothScrollToPosition(0);
                            rycLike.setVisibility(View.VISIBLE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("có chạy vào đây ko ta", "đoán xem");
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        String firstStr = anError.getErrorBody().substring(29);
                        String lastStr = firstStr.substring(0, firstStr.length() - 2);
                        Toast.makeText(context, lastStr, Toast.LENGTH_SHORT).show();
                        tv12.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        tv12.setText("Có vấn đề xảy ra");
                    }
                });
    }

    public void getListOfRequestSend(Context context, String email, List<Users> likeList, RecyclerView rycLike,
                                     LikeAdapter likeAdapter, ProgressBar progressBar,
                                     TextView tvCountFavorite, TextView tv12) {

        AndroidNetworking.get("https://poly-dating.herokuapp.com/api/friends/list-of-requests-sent/{email}")
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
                                JSONObject jo = usersJSON.getJSONObject(i).getJSONObject("friend");
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

                                likeList.add(users);
                            }
                            if (likeList.size() == 0) {
                                tvCountFavorite.setText(likeList.size() + " lượt đã thích");
                                tv12.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);
                                tv12.setText("Bạn chưa thích ai");
                            } else {
                                tvCountFavorite.setText(likeList.size() + " lượt đã thích");
                                progressBar.setVisibility(View.GONE);
                                tv12.setVisibility(View.GONE);
                            }
                            likeAdapter.notifyDataSetChanged();
                            rycLike.smoothScrollToPosition(0);
                            rycLike.setVisibility(View.VISIBLE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("có chạy vào đây ko ta", "đoán xem");
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        String firstStr = anError.getErrorBody().substring(29);
                        String lastStr = firstStr.substring(0, firstStr.length() - 2);
                        Toast.makeText(context, lastStr, Toast.LENGTH_SHORT).show();
                        tv12.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        tv12.setText("Có vấn đề xảy ra");
                    }
                });

    }

    public void getListFriends(Context context, String email, UserListener userListener, List<User> usersList, List<Users> likeList1,
                               RecyclerView rycLike, LikeAdapter likeAdapter, ProgressBar progressBar,
                               TextView tvCountFavorite, TextView tv12) {

        AndroidNetworking.get("https://poly-dating.herokuapp.com/api/friends/list-friends/{email}")
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
                                JSONObject jo = usersJSON.getJSONObject(i).getJSONObject("myUser");
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

                                if (usersList != null) {
                                    for (int j = 0; j < usersList.size(); j++) {
                                        if (usersList.get(j).email.equals(email)) {
                                            String token = usersList.get(i).token;
                                            String _id = usersList.get(i).id;
                                            users.setToken(token);
                                            users.set_id(_id);
                                        }
                                    }

                                }

                                likeList1.add(users);
                            }
                            if (likeList1.size() == 0) {
                                tvCountFavorite.setText(likeList1.size() + " bạn bè");
                                tv12.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);
                                tv12.setText("Chưa có bạn bè");
                            } else {
                                tvCountFavorite.setText(likeList1.size() + " bạn bè");
                                progressBar.setVisibility(View.GONE);
                                tv12.setVisibility(View.GONE);
                            }
                            likeAdapter.notifyDataSetChanged();
                            rycLike.smoothScrollToPosition(0);
                            rycLike.setVisibility(View.VISIBLE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        String firstStr = anError.getErrorBody().substring(29);
                        String lastStr = firstStr.substring(0, firstStr.length() - 2);
                        Toast.makeText(context, lastStr, Toast.LENGTH_SHORT).show();
                        tv12.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        tv12.setText("Có vấn đề xảy ra");
                    }
                });
    }

    public void deleteFriends(Context context, String myEmail, String emailFriends, String message) {

        AndroidNetworking.post("https://poly-dating.herokuapp.com/api/friends/delete")
                .addBodyParameter("myEmail", myEmail)
                .addBodyParameter("emailFriend", emailFriends)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                        LikeFragment.viewPagerAdapter.notifyDataSetChanged();
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
