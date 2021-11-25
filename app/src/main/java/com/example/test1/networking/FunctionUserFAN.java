package com.example.test1.networking;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.test1.AddImageActivity;
import com.example.test1.EditImgActivity;
import com.example.test1.HomeActivity;
import com.example.test1.LoginActivity;
import com.example.test1.NameActivity;
import com.example.test1.R;
import com.example.test1.SettingActivity;
import com.example.test1.adapters.UserAdapter;
import com.example.test1.fragments.HomeFragment;
import com.example.test1.functions.Loading;
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
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class FunctionUserFAN {

    public void insertUser(Context context, Users users) {

        String[] interestarr = users.getHobbies().toArray(new String[0]);

        AndroidNetworking.upload("https://poly-dating.herokuapp.com/api/users/insert")
                .addMultipartParameter("email", users.getEmail())
                .addMultipartParameter("name", users.getName())
                .addMultipartFileList("images", users.getImages())
                .addMultipartParameter("hobbies", Arrays.toString(interestarr))
                .addMultipartParameter("birthDay", users.getBirthday())
                .addMultipartParameter("gender", users.getGender())
                .addMultipartParameter("facilities", users.getFacilities())
                .addMultipartParameter("specialized", users.getSpecialized())
                .addMultipartParameter("course", users.getCourse())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            if (response.toString().contains("201")) {
                                getOneUser(users.getEmail(), context);
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(context, "Tạo tài khoản thành công", Toast.LENGTH_SHORT).show();
                                        context.startActivity(new Intent(context, HomeActivity.class));
                                    }
                                }, 1500);
                            } else {
                                Toast.makeText(context, response.getString("message"), Toast.LENGTH_SHORT).show();
                                AddImageActivity.loading.dismiss();
                            }
                            Log.e("aaa", "Trả về" + response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        AddImageActivity.loading.dismiss();
                        Log.e("aa", "Lỗi" + anError.getErrorBody());
                    }
                });

    }

    public void checkUser(String email, Context context, GoogleSignInClient googleSignInClient) {
        String url = "https://poly-dating.herokuapp.com/api/users/find/{email}";
        AndroidNetworking.get(url)
                .addPathParameter("email", email)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            if (response.getString("data").equals("null")) {
                                Intent intent = new Intent(context, NameActivity.class);
                                intent.putExtra("email", email);
                                context.startActivity(intent);
                            } else {

                                JSONObject jo = response.getJSONObject("data").getJSONObject("user");
                                boolean isActive = jo.getBoolean("isActive");

                                if (isActive == false) {
                                    googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            LoginActivity.loading.dismiss();
                                            Toast.makeText(context, "Tài khoản đã bị chặn", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                } else {
                                    getOneUser(email, context);
                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(context, "Chào mừng trở lại " + HomeActivity.users.getName(), Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(context, HomeActivity.class);
                                            context.startActivity(intent);
                                        }
                                    }, 1500);
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


    public void getOneUser(String email, Context context) {

        AndroidNetworking.get("https://poly-dating.herokuapp.com/api/users/find/{email}")
                .addPathParameter("email", email)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONObject jo = response.getJSONObject("data").getJSONObject("user");
                            List<File> fileimg = new ArrayList<>();
                            List<String> hobbiesList = new ArrayList<>();
                            List<String> isShowList = new ArrayList<>();
                            String _id = jo.getString("_id");
                            String email = jo.getString("email");
                            String password = jo.getString("password");
                            String name = jo.getString("name");
                            JSONArray avatars = jo.getJSONArray("images");
                            for (int i = 0; i < avatars.length(); i++) {
                                fileimg.add(new File(avatars.getString(i)));
                            }
                            JSONArray hobbies = jo.getJSONArray("hobbies");
                            for (int i = 0; i < hobbies.length(); i++) {
                                hobbiesList.add(hobbies.getString(i));
                            }
                            String birthDay = jo.getString("birthDay");
                            String gender = jo.getString("gender");
                            String description = jo.getString("description");
                            String facilities = jo.getString("facilities");
                            String specialized = jo.getString("specialized");
                            String course = jo.getString("course");
                            JSONArray isShow = jo.getJSONArray("isShow");
                            for (int i = 0; i < isShow.length(); i++) {
                                isShowList.add(isShow.getString(i));
                            }
                            boolean isActive = jo.getBoolean("isActive");
                            boolean status = jo.getBoolean("status");

                            HomeActivity.users = new Users(_id, email, password, name, fileimg, hobbiesList, birthDay, gender, description,
                                    facilities, specialized, course, isShowList, isActive, status);
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

    public void getListUser(Context context, List<Users> usersList1, TextView tv12, ImageButton imgReload, ProgressBar progressBar,
                            SwipeFlingAdapterView flingAdapterView) {
        List<Users> usersList = new ArrayList<>();
        AndroidNetworking.get("https://poly-dating.herokuapp.com/api/users/list")
                .addQueryParameter("isShow[0]", HomeActivity.users.getIsShow().get(0))// hiển thị nam nữ
                .addQueryParameter("isShow[1]", HomeActivity.users.getIsShow().get(1))// hiển thị cơ sở
                .addQueryParameter("isShow[2]", HomeActivity.users.getIsShow().get(2))// hiển thị ngành học
                .addQueryParameter("isShow[3]", HomeActivity.users.getIsShow().get(3))// hiển thị khóa học
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject arr = response.getJSONObject("data");
                            JSONArray usersJSON = arr.getJSONArray("users");

                            for (int i = 0; i < usersJSON.length(); i++) {
                                List<File> fileimg = new ArrayList<>();
                                List<String> hobbiesList = new ArrayList<>();
                                List<String> isShowList = new ArrayList<>();
                                String _id = usersJSON.getJSONObject(i).getString("_id");
                                String email = usersJSON.getJSONObject(i).getString("email");
                                String name = usersJSON.getJSONObject(i).getString("name");
                                JSONArray avatars = usersJSON.getJSONObject(i).getJSONArray("images");
                                for (int j = 0; j < avatars.length(); j++) {
                                    fileimg.add(new File(avatars.getString(j)));
                                }
                                JSONArray hobbies = usersJSON.getJSONObject(i).getJSONArray("hobbies");
                                for (int j = 0; j < hobbies.length(); j++) {
                                    hobbiesList.add(hobbies.getString(j));
                                }
                                String birthDay = usersJSON.getJSONObject(i).getString("birthDay");
                                String gender = usersJSON.getJSONObject(i).getString("gender");
                                String description = usersJSON.getJSONObject(i).getString("description");
                                String facilities = usersJSON.getJSONObject(i).getString("facilities");
                                String specialized = usersJSON.getJSONObject(i).getString("specialized");
                                String course = usersJSON.getJSONObject(i).getString("course");
                                JSONArray isShow = usersJSON.getJSONObject(i).getJSONArray("isShow");
                                for (int j = 0; j < isShow.length(); j++) {
                                    isShowList.add(isShow.getString(j));
                                }
                                boolean isActive = usersJSON.getJSONObject(i).getBoolean("isActive");
                                boolean status = usersJSON.getJSONObject(i).getBoolean("status");

                                Users users = new Users();
                                users.set_id(_id);
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
                                users.setIsShow(isShowList);
                                users.setActive(isActive);
                                users.setStatus(status);
                                usersList.add(users);
                                Log.e("alo123", users.getName());
                            }
                            for (int i = 0; i < usersList.size(); i++) {
                                if (usersList.get(i).getEmail().equals(HomeActivity.users.getEmail())) {
                                    usersList.remove(i);
                                }
                            }
                            if (usersList.size() ==0) {
                                tv12.setVisibility(View.VISIBLE);
                                imgReload.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);
                                tv12.setText("Không tìm thấy dữ liệu, nhấn nút phía dưới để làm mới");
                                imgReload.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        context.startActivity(new Intent(context, HomeActivity.class));
                                        HomeActivity.fragment = new HomeFragment();
                                        HomeActivity.selectedItem = R.id.homeId;
                                    }
                                });
                            } else {
                                HomeFragment.userAdapter = new UserAdapter(getRandomElement(usersList, usersList1, usersList.size()), context);
                                flingAdapterView.setAdapter(HomeFragment.userAdapter);
                                HomeFragment.userAdapter.notifyDataSetChanged();
                                progressBar.setVisibility(View.GONE);
                                tv12.setVisibility(View.GONE);
                                imgReload.setVisibility(View.GONE);
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

    public void updateImages(String mail, String _id, File images, String image, String checkRemove, Activity context) {

        AndroidNetworking.upload("https://poly-dating.herokuapp.com/api/users/update/images")
                .addMultipartParameter("_id", _id)
                .addMultipartParameter("imageUrl", image)
                .addMultipartParameter("checkRemove", checkRemove)
                .addMultipartFile("images", images)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.toString().contains("200")) {
                                getOneUser(mail, context);
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            context.overridePendingTransition(0, 0);
                                            context.finish();
                                            context.overridePendingTransition(0, 0);
                                            context.startActivity(context.getIntent());
                                            context.overridePendingTransition(0, 0);
                                            Toast.makeText(context, response.getString("message"), Toast.LENGTH_SHORT).show();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }, 1500);
                            } else {
                                Toast.makeText(context, response.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                            Log.e("aaa", "Trả về" + response);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        context.overridePendingTransition(0, 0);
                        context.finish();
                        context.overridePendingTransition(0, 0);
                        context.startActivity(context.getIntent());
                        context.overridePendingTransition(0, 0);
                        Toast.makeText(context, "Không thể xóa khi chỉ còn 2 ảnh", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void updateIsShow(String email, String _id, String [] isShowarr, String showStr, Activity context, Dialog dialog, TextView tv, Loading loading) {

        Log.e("array",Arrays.toString(isShowarr));

        AndroidNetworking.post("https://poly-dating.herokuapp.com/api/users/update/is_show")
                .addBodyParameter("_id", _id)
                .addBodyParameter("isShow", Arrays.toString(isShowarr))
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.toString().contains("200")) {
                                getOneUser(email,context);
                                Toast.makeText(context, response.getString("message"), Toast.LENGTH_SHORT).show();
                                tv.setText(showStr);
                                dialog.dismiss();
                            } else {
                                Toast.makeText(context, response.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                            loading.dismiss();
                            Log.e("aaa", "Trả về" + response);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                        loading.dismiss();
                    }
                });
    }

    public List<Users> getRandomElement(List<Users> list, List<Users> list1, int totalItems) {
        Random rand = new Random();
        if (totalItems < 10) {
            for (int i = 0; i < totalItems; i++) {
                int randomIndex = rand.nextInt(list.size());
                while (list1.contains(list.get(randomIndex))) {
                    randomIndex = rand.nextInt(list.size());
                }
                list1.add(list.get(randomIndex));
            }
        } else {
            for (int i = 0; i < 10; i++) {
                int randomIndex = rand.nextInt(list.size());
                while (list1.contains(list.get(randomIndex))) {
                    randomIndex = rand.nextInt(list.size());
                }
                list1.add(list.get(randomIndex));
            }
        }

        return list1;
    }

}
