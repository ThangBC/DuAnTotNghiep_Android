package com.example.test1.volleys;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.test1.HomeActivity;
import com.example.test1.R;
import com.example.test1.adapters.LikeAdapter;
import com.example.test1.adapters.UserAdapter;
import com.example.test1.fragments.ChatFragment;
import com.example.test1.fragments.HomeFragment;
import com.example.test1.fragments.LikeFragment;
import com.example.test1.models.Users;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FunctionFavoriteFAN {

    public void insertFavorite(Context context, String emailBeLiked, String emailLiked) {

        Log.e("email", emailBeLiked + emailLiked);

        AndroidNetworking.post("https://poly-dating.herokuapp.com/api/favorites/insert")
                .addBodyParameter("emailBeLiked", emailBeLiked)
                .addBodyParameter("emailLiked", emailLiked)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Toast.makeText(context, response.getString("message"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("error", anError.getErrorBody());
                        Toast.makeText(context, "Thất bại", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    public void deleteFavorite(Context context, String emailBeLiked, String emailLiked) {

        AndroidNetworking.post("https://poly-dating.herokuapp.com/api/favorites/delete")
                .addBodyParameter("emailBeLiked", emailBeLiked)
                .addBodyParameter("emailLiked", emailLiked)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Toast.makeText(context, response.getString("message"), Toast.LENGTH_SHORT).show();
                            getListFavorite(context, HomeActivity.users.getEmail());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("error", anError.getErrorBody());
                        Toast.makeText(context, "Thất bại", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    public void getListFavorite(Context context, String emailLike) {
        Log.e("email", emailLike);
        LikeFragment.likesList = new ArrayList<>();
        AndroidNetworking.get("https://poly-dating.herokuapp.com/api/favorites/list/{emailBeLiked}")
                .addPathParameter("emailBeLiked", emailLike)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject arr = response.getJSONObject("data");
                            JSONArray usersJSON = arr.getJSONArray("favorites");
                            for (int i = 0; i < usersJSON.length(); i++) {
                                JSONObject jo = usersJSON.getJSONObject(i).getJSONObject("userLiked");
                                List<File> fileimg = new ArrayList<>();
                                List<String> hobbiesList = new ArrayList<>();

                                String email = jo.getString("email");
                                String name = jo.getString("name");
                                JSONArray avatars = jo.getJSONArray("images");
                                for (int j = 0; j < avatars.length(); j++) {
                                    fileimg.add(new File(avatars.getString(j)));
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
                                users.setImages(fileimg);
                                users.setHobbies(hobbiesList);
                                users.setBirthday(birthDay);
                                users.setGender(gender);
                                users.setDescription(description);
                                users.setFacilities(facilities);
                                users.setSpecialized(specialized);
                                users.setCourse(course);

                                LikeFragment.likesList.add(users);
                                Log.e("abcdef", users.getEmail());
                            }
                            if (LikeFragment.likesList.size() == 0) {
                                LikeFragment.tvCountFavorite.setText(LikeFragment.likesList.size() + " lượt thích");
                                LikeFragment.tv12.setVisibility(View.VISIBLE);
                                LikeFragment.progressBar.setVisibility(View.GONE);
                                LikeFragment.tv12.setText("Chưa ai thích bạn");
                                LikeFragment.likeAdapter = new LikeAdapter(LikeFragment.likesList, context);
                                GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false);
                                LikeFragment.rycLike.setLayoutManager(gridLayoutManager);
                                LikeFragment.rycLike.setAdapter(LikeFragment.likeAdapter);
                                LikeFragment.likeAdapter.notifyDataSetChanged();
                            } else {
                                LikeFragment.tvCountFavorite.setText(LikeFragment.likesList.size() + " lượt thích");
                                LikeFragment.likeAdapter = new LikeAdapter(LikeFragment.likesList, context);
                                GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false);
                                LikeFragment.rycLike.setLayoutManager(gridLayoutManager);
                                LikeFragment.rycLike.setAdapter(LikeFragment.likeAdapter);
                                LikeFragment.likeAdapter.notifyDataSetChanged();
                                LikeFragment.progressBar.setVisibility(View.GONE);
                                LikeFragment.tv12.setVisibility(View.GONE);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("có chạy vào đây ko ta", "đoán xem");
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(context, "" + anError.getErrorBody(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void updateFavorite(Context context, String emailBeLiked,String emailLiked) {
        AndroidNetworking.post("https://poly-dating.herokuapp.com/api/favorites/update")
                .addBodyParameter("emailBeLiked", emailBeLiked)
                .addBodyParameter("emailLiked", emailLiked)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Toast.makeText(context, response.getString("message"), Toast.LENGTH_SHORT).show();
                            context.startActivity(new Intent(context,HomeActivity.class));
                            HomeActivity.selectedItem = R.id.chatId;
                            HomeActivity.fragment = new ChatFragment();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("error", anError.getErrorBody());
                        Toast.makeText(context, "Thất bại", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
