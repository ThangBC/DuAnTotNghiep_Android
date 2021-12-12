package com.example.test1.networking;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.test1.HomeActivity;
import com.example.test1.LoginActivity;
import com.example.test1.signupactivities.NameActivity;
import com.example.test1.adapters.UserAdapter;
import com.example.test1.fragments.HomeFragment;
import com.example.test1.functions.Loading;
import com.example.test1.models.Users;
import com.example.test1.ultilties.Constants;
import com.example.test1.ultilties.PreferenceManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class FunctionUserFAN {

    SharedPreferences sp;

    public void insertUser(Activity context, Users users,Loading loading) {

        AndroidNetworking.upload("https://poly-dating.herokuapp.com/api/users/sign-up")
                .addMultipartParameter("email", users.getEmail())
                .addMultipartParameter("name", users.getName())
                .addMultipartFileList("images", users.getImages())
                .addMultipartParameter("hobbies", users.getHobbies().toString())
                .addMultipartParameter("birthDay", users.getBirthday())
                .addMultipartParameter("gender", users.getGender())
                .addMultipartParameter("facilities", users.getFacilities())
                .addMultipartParameter("specialized", users.getSpecialized())
                .addMultipartParameter("course", users.getCourse())
                .addMultipartParameter("token", users.getToken())
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.toString().contains("200")) {
                                checkUser(users.getEmail(),users.getName(),users.getImages().get(0), users.getToken(), context, null, 2, null, null, null);
                            } else {
                                Toast.makeText(context, response.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        loading.dismiss();
                    }

                    @Override
                    public void onError(ANError anError) {
                        loading.dismiss();
                        String firstStr = anError.getErrorBody().substring(29);
                        String lastStr = firstStr.substring(0, firstStr.length() - 2);
                        Toast.makeText(context, lastStr, Toast.LENGTH_SHORT).show();
                    }
                });

    }

    public void checkUser(String email,String nameRes,File imageRes, String token, Activity context, GoogleSignInClient googleSignInClient, int check, Loading loading, Dialog dialog, JSONObject response1) {

        AndroidNetworking.post("https://poly-dating.herokuapp.com/api/users/sign-in")
                .addBodyParameter("email", email)
                .addBodyParameter("token", token)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getString("statusCode").equals("200")) {
                                JSONObject jo = response.getJSONObject("data");
                                List<String> imgUrl = new ArrayList<>();
                                List<String> hobbiesList = new ArrayList<>();
                                List<String> isShowList = new ArrayList<>();
                                String _id = jo.getString("_id");
                                String email = jo.getString("email");
                                String name = jo.getString("name");
                                JSONArray avatars = jo.getJSONArray("images");
                                for (int i = 0; i < avatars.length(); i++) {
                                    imgUrl.add(avatars.getString(i));
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
                                boolean statusHobbies = jo.getBoolean("statusHobby");

                                HomeActivity.users = new Users(_id, email, name, imgUrl, hobbiesList, birthDay, gender, description,
                                        facilities, specialized, course, isShowList, isActive, statusHobbies);

                                if (check == 0) {// cập nhật khám phá
                                    loading.dismiss();
                                    dialog.dismiss();
                                    Toast.makeText(context, response1.getString("message"), Toast.LENGTH_SHORT).show();
                                }
                                if (check == 1) {// đăng nhập
                                    context.startActivity(new Intent(context, HomeActivity.class));
                                    Toast.makeText(context, "Chào mừng trở lại " + HomeActivity.users.getName(), Toast.LENGTH_SHORT).show();
                                }
                                if (check == 2) {// đăng ký
                                    PreferenceManager preferenceManager = new PreferenceManager(context);
                                    FirebaseFirestore database = FirebaseFirestore.getInstance();
                                    HashMap<String, Object> user = new HashMap<>();
                                    user.put(Constants.KEY_NAME, nameRes);
                                    user.put(Constants.KEY_EMAIL, email);
                                    user.put(Constants.KEY_IAMGE, String.valueOf(imageRes));
                                    database.collection(Constants.KEY_COLLECTION_USER)
                                            .add(user)
                                            .addOnSuccessListener(documentReference -> {
                                                preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN, true);
                                                preferenceManager.putString(Constants.KEY_USER_ID, documentReference.getId());
                                                preferenceManager.putString(Constants.KEY_NAME, nameRes);
                                                preferenceManager.putString(Constants.KEY_IAMGE, String.valueOf(imageRes));
                                            });
                                    user.put(Constants.KEY_NAME, nameRes);
                                    context.startActivity(new Intent(context, HomeActivity.class));
                                    Toast.makeText(context, "Tạo tài khoản thành công", Toast.LENGTH_SHORT).show();
                                }
                                if (check == 3) {// cập nhật ảnh
                                    context.overridePendingTransition(0, 0);
                                    context.finish();
                                    context.overridePendingTransition(0, 0);
                                    context.startActivity(context.getIntent());
                                    context.overridePendingTransition(0, 0);
                                    Toast.makeText(context, response1.getString("message"), Toast.LENGTH_SHORT).show();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        if (anError.getErrorBody().contains("400")) {
                            if (googleSignInClient != null) {
                                googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                    }
                                });
                                LoginActivity.loading.dismiss();
                            }
                            Toast.makeText(context, "Tài khoản của bạn đã bị khóa", Toast.LENGTH_SHORT).show();
                            context.startActivity(new Intent(context, LoginActivity.class));
                        } else {
                            Intent intent = new Intent(context, NameActivity.class);
                            intent.putExtra("email", email);
                            context.startActivity(intent);
                        }
                    }
                });
    }

    public void checkListUser1(Context context, List<Users> usersList1, TextView tv12, ImageButton imgReload, ProgressBar progressBar,
                               SwipeFlingAdapterView flingAdapterView) {
        List<String> usersListCheck1 = new ArrayList<>();

        AndroidNetworking.get("https://poly-dating.herokuapp.com/api/friends/list-friends/{email}")
                .addPathParameter("email", HomeActivity.users.getEmail())
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

                                String email = jo.getString("email");

                                usersListCheck1.add(email);
                            }
                            checkListUser2(context, usersList1, tv12, imgReload, progressBar,
                                    flingAdapterView, usersListCheck1);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("có chạy vào đây ko ta", "đoán xem");
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        tv12.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        tv12.setText("Chưa có bạn bè");
                    }
                });
    }

    public void checkListUser2(Context context, List<Users> usersList1, TextView tv12, ImageButton imgReload, ProgressBar progressBar,
                               SwipeFlingAdapterView flingAdapterView, List<String> userListCheck1) {
        List<String> usersListCheck2 = new ArrayList<>();

        AndroidNetworking.get("https://poly-dating.herokuapp.com/api/friends/list-friends-requests/{email}")
                .addPathParameter("email", HomeActivity.users.getEmail())
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

                                String email = jo.getString("email");

                                usersListCheck2.add(email);
                            }
                            checkListUser3(context, usersList1, tv12, imgReload, progressBar,
                                    flingAdapterView, userListCheck1, usersListCheck2);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("có chạy vào đây ko ta", "đoán xem");
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        tv12.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        tv12.setText("Chưa có bạn bè");
                    }
                });
    }

    public void checkListUser3(Context context, List<Users> usersList1, TextView tv12, ImageButton imgReload, ProgressBar progressBar,
                               SwipeFlingAdapterView flingAdapterView, List<String> userListCheck1, List<String> userListCheck2) {
        List<String> usersListCheck3 = new ArrayList<>();

        AndroidNetworking.get("https://poly-dating.herokuapp.com/api/friends/list-of-requests-sent/{email}")
                .addPathParameter("email", HomeActivity.users.getEmail())
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
                                String email = jo.getString("email");
                                usersListCheck3.add(email);

                            }
                            getListUser(context, usersList1, tv12, imgReload, progressBar, flingAdapterView, userListCheck1, userListCheck2, usersListCheck3);
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

    public void getListUser(Context context, List<Users> usersList1, TextView tv12, ImageButton imgReload, ProgressBar progressBar,
                            SwipeFlingAdapterView flingAdapterView, List<String> usersListCheck1, List<String> usersListCheck2, List<String> usersListCheck3) {

        List<Users> usersList = new ArrayList<>();

        AndroidNetworking.get("https://poly-dating.herokuapp.com/api/users/list")
                .addQueryParameter("isShow", HomeActivity.users.getIsShow().toString())// hiển thị giới tính, cơ sở, ngành học, khóa học
                .addQueryParameter("hobbies", HomeActivity.users.getHobbies().toString())// hiển thị sở thích
                .addQueryParameter("statusHobby", String.valueOf(HomeActivity.users.isStatusHobbies()))// hiển thị sở thích
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject arr = response.getJSONObject("data");
                            JSONArray usersJSON = arr.getJSONArray("users");

                            for (int i = 0; i < usersJSON.length(); i++) {
                                List<String> fileimg = new ArrayList<>();
                                List<String> hobbiesList = new ArrayList<>();
                                List<String> isShowList = new ArrayList<>();
                                String _id = usersJSON.getJSONObject(i).getString("_id");
                                String email = usersJSON.getJSONObject(i).getString("email");
                                String name = usersJSON.getJSONObject(i).getString("name");
                                JSONArray avatars = usersJSON.getJSONObject(i).getJSONArray("images");
                                for (int j = 0; j < avatars.length(); j++) {
                                    fileimg.add(avatars.getString(j));
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

                                Users users = new Users();
                                users.set_id(_id);
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
                                users.setIsShow(isShowList);
                                users.setActive(isActive);
                                usersList.add(users);
                                Log.e("alo123", users.getName());
                            }
                            for (int i = 0; i < usersList.size(); i++) {
                                if (usersList.get(i).getEmail().equals(HomeActivity.users.getEmail())) {
                                    usersList.remove(i);
                                }
                            }
                            if (usersList.size() == 0) {
                                tv12.setVisibility(View.VISIBLE);
                                imgReload.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);
                                tv12.setText("Không tìm thấy dữ liệu, nhấn nút phía dưới để làm mới");
                                imgReload.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        context.startActivity(new Intent(context, HomeActivity.class));
                                    }
                                });
                            } else {
                                HomeFragment.usersListCheck1 = new ArrayList<>(usersListCheck1);
                                HomeFragment.usersListCheck2 = new ArrayList<>(usersListCheck2);
                                HomeFragment.usersListCheck3 = new ArrayList<>(usersListCheck3);
                                HomeFragment.userAdapter = new UserAdapter(getRandomElement(usersList, usersList1, usersList.size()), context,
                                        usersListCheck1, usersListCheck2, usersListCheck3);
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
                        String firstStr = anError.getErrorBody().substring(29);
                        String lastStr = firstStr.substring(0, firstStr.length() - 2);
                        Toast.makeText(context, lastStr, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void updateUser(String mail, String _id, Users users, String result, String title, Activity context, Dialog dialog, TextView tv, Loading loading) {

        sp = context.getSharedPreferences("MyToken", Context.MODE_PRIVATE);
        String token = sp.getString("token", "");

        AndroidNetworking.post("https://poly-dating.herokuapp.com/api/users/update/information")
                .addBodyParameter("_id", _id)
                .addBodyParameter("description", users.getDescription())
                .addBodyParameter("hobbies", users.getHobbies().toString())
                .addBodyParameter("facilities", users.getFacilities())
                .addBodyParameter("specialized", users.getSpecialized())
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.toString().contains("200")) {
                                checkUser(mail,null,null, token, context, null, 0, loading, dialog, response);
                                tv.setText(title + result);
                            } else {
                                Toast.makeText(context, response.getString("message"), Toast.LENGTH_SHORT).show();
                                loading.dismiss();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        String firstStr = anError.getErrorBody().substring(29);
                        String lastStr = firstStr.substring(0, firstStr.length() - 2);
                        Toast.makeText(context, lastStr, Toast.LENGTH_SHORT).show();
                        loading.dismiss();
                    }
                });
    }

    public void updateImages(String mail, String _id, File images, String image, String checkRemove, Activity context) {
        sp = context.getSharedPreferences("MyToken", Context.MODE_PRIVATE);
        String token = sp.getString("token", "");
        AndroidNetworking.upload("https://poly-dating.herokuapp.com/api/users/update/images")
                .addMultipartParameter("_id", _id)
                .addMultipartParameter("imageUrl", image)
                .addMultipartParameter("checkRemove", checkRemove)
                .addMultipartFile("images", images)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.toString().contains("200")) {
                                checkUser(mail,null,null, token, context, null, 3, null, null, response);
                            } else {
                                Toast.makeText(context, response.getString("message"), Toast.LENGTH_SHORT).show();
                            }
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

    public void updateIsShow(String email, String _id, String[] isShowarr, String showStr, Activity context, Dialog dialog, TextView tv, Loading loading) {
        sp = context.getSharedPreferences("MyToken", Context.MODE_PRIVATE);
        String token = sp.getString("token", "");
        AndroidNetworking.post("https://poly-dating.herokuapp.com/api/users/update/is-show")
                .addBodyParameter("_id", _id)
                .addBodyParameter("isShow", Arrays.toString(isShowarr))
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.toString().contains("200")) {
                                checkUser(email,null,null, token, context, null, 0, loading, dialog, response);
                                tv.setText(showStr);
                            } else {
                                Toast.makeText(context, response.getString("message"), Toast.LENGTH_SHORT).show();
                                loading.dismiss();
                            }
                            Log.e("aaa", "Trả về" + response);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        String firstStr = anError.getErrorBody().substring(29);
                        String lastStr = firstStr.substring(0, firstStr.length() - 2);
                        Toast.makeText(context, lastStr, Toast.LENGTH_SHORT).show();
                        loading.dismiss();
                    }
                });
    }

    public void requestCode(String email, Activity context) {

        AndroidNetworking.post("https://poly-dating.herokuapp.com/api/users/request-code")
                .addBodyParameter("email", email)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Toast.makeText(context, response.getString("message"), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
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

    public void updateStatusHobbies(String email, String _id, String statusHobby, Activity context,
                                    Loading loading, Dialog dialog, TextView tvFilterInterest) {

        sp = context.getSharedPreferences("MyToken", Context.MODE_PRIVATE);
        String token = sp.getString("token", "");

        AndroidNetworking.post("https://poly-dating.herokuapp.com/api/users/update/status-hobby")
                .addBodyParameter("_id", _id)
                .addBodyParameter("statusHobby", statusHobby)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            checkUser(email,null,null, token, context, null, 0, loading, dialog, response);
                            if (statusHobby.equals("true")) {
                                tvFilterInterest.setText("Bật");
                            } else {
                                tvFilterInterest.setText("Tắt");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        String firstStr = anError.getErrorBody().substring(29);
                        String lastStr = firstStr.substring(0, firstStr.length() - 2);
                        Toast.makeText(context, lastStr, Toast.LENGTH_SHORT).show();
                        loading.dismiss();
                        dialog.dismiss();
                    }
                });
    }

    public void deleteUser(String _id, String code, Activity context, Loading loading, GoogleApiClient googleApiClient) {

        AndroidNetworking.post("https://poly-dating.herokuapp.com/api/users/delete")
                .addBodyParameter("_id", _id)
                .addBodyParameter("code", code)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
                                @Override
                                public void onResult(@NonNull Status status) {
                                }
                            });
                            Toast.makeText(context, response.getString("message"), Toast.LENGTH_SHORT).show();
                            context.startActivity(new Intent(context, LoginActivity.class));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        Log.e("aaa", "Trả về" + response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        String firstStr = anError.getErrorBody().substring(29);
                        String lastStr = firstStr.substring(0, firstStr.length() - 2);
                        Toast.makeText(context, lastStr, Toast.LENGTH_SHORT).show();
                        loading.dismiss();
                    }
                });
    }

    public void signOutUser(String email, Activity context, Loading loading, GoogleApiClient googleApiClient) {
        AndroidNetworking.post("https://poly-dating.herokuapp.com/api/users/sign-out")
                .addBodyParameter("email", email)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
                                @Override
                                public void onResult(@NonNull Status status) {
                                }
                            });
                            Toast.makeText(context, response.getString("message"), Toast.LENGTH_SHORT).show();
                            context.startActivity(new Intent(context, LoginActivity.class));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        Log.e("aaa", "Trả về" + response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        String firstStr = anError.getErrorBody().substring(29);
                        String lastStr = firstStr.substring(0, firstStr.length() - 2);
                        Toast.makeText(context, lastStr, Toast.LENGTH_SHORT).show();
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
