package com.example.test1.networking;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.test1.EditProActivity;
import com.example.test1.HomeActivity;
import com.example.test1.LoginActivity;
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
import com.example.test1.signupactivities.NameActivity;
import com.example.test1.ultilties.Constants;
import com.example.test1.ultilties.PreferenceManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FunctionFriendsFAN {


    public void insertFriends(Context context, String emailFriends, int check) {// gửi yêu cầu kết bạn và chấp nhận kết bạn

        AndroidNetworking.post("https://poly-dating.herokuapp.com/api/friends/friend-request")
                .addHeaders("authorization", "Bearer " + HomeActivity.users.getAccessToken())
                .addBodyParameter("emailFriend", emailFriends)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Toast.makeText(context, response.getString("message"), Toast.LENGTH_SHORT).show();
                            if (check == 1) {// nếu là màn lượt thích và ấn chấp nhận thì sẽ load lại màn
                                LikeFragment.viewPagerAdapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        checkLogAccount(anError.getErrorBody(), context, HomeActivity.users.getEmail(), 0);
                    }
                });
    }

    public void getListFriendsRequetst(Context context, List<Users> likeList, RecyclerView rycLike,
                                       ProgressBar progressBar,
                                       TextView tvCountFavorite, TextView tv12) {// lấy list lượt thích

        AndroidNetworking.get("https://poly-dating.herokuapp.com/api/friends/list-friends-requests")
                .addHeaders("authorization", "Bearer " + HomeActivity.users.getAccessToken())
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
                            DfrPeopleLikeFragment.likeAdapter = new LikeAdapter(likeList, context, null, 1);
                            GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false);
                            rycLike.setLayoutManager(gridLayoutManager);
                            rycLike.setAdapter(DfrPeopleLikeFragment.likeAdapter);
                            DfrPeopleLikeFragment.likeAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        checkLogAccount(anError.getErrorBody(), context, HomeActivity.users.getEmail(), 0);
                        tv12.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        tv12.setText("Có vấn đề xảy ra");
                        Log.e("err", anError.getErrorBody());
                    }
                });
    }

    public void getListOfRequestSend(Context context, List<Users> likeList, RecyclerView rycLike,
                                     ProgressBar progressBar,
                                     TextView tvCountFavorite, TextView tv12) {// lấy list lượt đã thích

        AndroidNetworking.get("https://poly-dating.herokuapp.com/api/friends/list-of-requests-sent")
                .addHeaders("authorization", "Bearer " + HomeActivity.users.getAccessToken())
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
                            MeLikeFragment.likeAdapter = new LikeAdapter(likeList, context, null, 2);
                            GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false);
                            rycLike.setLayoutManager(gridLayoutManager);
                            rycLike.setAdapter(MeLikeFragment.likeAdapter);
                            MeLikeFragment.likeAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        checkLogAccount(anError.getErrorBody(), context, HomeActivity.users.getEmail(), 0);
                        tv12.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        tv12.setText("Có vấn đề xảy ra");
                    }
                });

    }

    public void getListFriends(Context context, List<User> usersList, List<Users> likeList,
                               RecyclerView rycLike, ProgressBar progressBar,
                               TextView tvCountFavorite, TextView tv12, UserListener userListener) {
        // danh sách bạn bè

        AndroidNetworking.get("https://poly-dating.herokuapp.com/api/friends/list-friends")
                .addHeaders("authorization", "Bearer " + HomeActivity.users.getAccessToken())
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
                            }
                            List<Users> likeList1 = new ArrayList<>();
                            for (int n = 0; n < usersList.size(); n++) {
                                for (int m = 0; m < likeList.size(); m++) {
                                    if (usersList.get(n).email.equals(likeList.get(m).getEmail())) {
                                        // check mail để thêm id và token ở firebase
                                        Users users1 = new Users();
                                        users1.setEmail(likeList.get(m).getEmail());
                                        users1.setName(likeList.get(m).getName());
                                        users1.setImageUrl(likeList.get(m).getImageUrl());
                                        users1.setHobbies(likeList.get(m).getHobbies());
                                        users1.setBirthday(likeList.get(m).getBirthday());
                                        users1.setGender(likeList.get(m).getGender());
                                        users1.setDescription(likeList.get(m).getDescription());
                                        users1.setFacilities(likeList.get(m).getFacilities());
                                        users1.setSpecialized(likeList.get(m).getSpecialized());
                                        users1.setCourse(likeList.get(m).getCourse());
                                        users1.setToken(usersList.get(n).token);
                                        users1.set_id(usersList.get(n).id);
                                        likeList1.add(users1);
                                    }
                                }
                            }

                            if (likeList1.size() == 0) {
                                tvCountFavorite.setText(likeList1.size() + " bạn bè");
                                tv12.setVisibility(View.VISIBLE);
                                tv12.setText("Chưa có bạn bè");
                                progressBar.setVisibility(View.GONE);
                            } else {
                                tvCountFavorite.setText(likeList1.size() + " bạn bè");
                                tv12.setVisibility(View.GONE);
                                progressBar.setVisibility(View.GONE);
                            }
                            ListFriendsFragment.likeAdapter = new LikeAdapter(likeList1, context, userListener, 3);
                            GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false);
                            rycLike.setLayoutManager(gridLayoutManager);
                            rycLike.setAdapter(ListFriendsFragment.likeAdapter);
                            ListFriendsFragment.likeAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        checkLogAccount(anError.getErrorBody(), context, HomeActivity.users.getEmail(), 0);
                        tv12.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        tv12.setText("Có vấn đề xảy ra");
                    }
                });
    }

    public void deleteFriends(Context context, String emailFriends, String message) {
        // xóa bạn bè, từ chối và hủy yêu cầu

        AndroidNetworking.post("https://poly-dating.herokuapp.com/api/friends/delete")
                .addHeaders("authorization", "Bearer " + HomeActivity.users.getAccessToken())
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
                        checkLogAccount(anError.getErrorBody(), context, HomeActivity.users.getEmail(), 0);
                    }
                });

    }

    private void checkLogAccount(String check, Context context, String email, int check404) {// hàm check lỗi
        if (check.contains("403")) {
            GoogleSignInOptions gso = new GoogleSignInOptions.
                    Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                    build();
            GoogleSignInClient googleSignInClient1 = GoogleSignIn.getClient(context, gso);
            googleSignInClient1.signOut();
            Toast.makeText(context, "Tài khoản của bạn đã bị khóa", Toast.LENGTH_SHORT).show();
            context.startActivity(new Intent(context, LoginActivity.class));
        } else if (check.contains("404")) {
            PreferenceManager preferenceManager = new PreferenceManager(context);
            if (check404 == 1) {
                if (preferenceManager.getString(Constants.KEY_USER_ID) != null) {
                    String id_user = preferenceManager.getString(Constants.KEY_USER_ID);
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    DocumentReference documentReference = db.collection("users").document(id_user);
                    documentReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                GoogleSignInOptions gso = new GoogleSignInOptions.
                                        Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                                        build();
                                GoogleSignInClient googleSignInClient1 = GoogleSignIn.getClient(context, gso);
                                googleSignInClient1.signOut();
                                Toast.makeText(context, "Tài khoản của bạn đã bị xóa", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(context, LoginActivity.class);
                                context.startActivity(intent);
                                preferenceManager.clear();
                            }
                        }
                    });
                } else {
                    Intent intent = new Intent(context, NameActivity.class);
                    intent.putExtra("email", email);
                    context.startActivity(intent);
                }
            } else {
                String id_user = preferenceManager.getString(Constants.KEY_USER_ID);
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                DocumentReference documentReference = db.collection("users").document(id_user);
                documentReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            GoogleSignInOptions gso = new GoogleSignInOptions.
                                    Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                                    build();
                            GoogleSignInClient googleSignInClient1 = GoogleSignIn.getClient(context, gso);
                            googleSignInClient1.signOut();
                            Toast.makeText(context, "Tài khoản của bạn đã bị xóa", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context, LoginActivity.class);
                            context.startActivity(intent);
                            preferenceManager.clear();
                        } else {
                        }
                    }
                });
            }
        } else if (check.contains("500")) {
            Log.e("err", check);
            Toast.makeText(context, "Lỗi không xác định", Toast.LENGTH_SHORT).show();
        } else if (check.contains("400")) {
            String firstStr = check.substring(29);
            String lastStr = firstStr.substring(0, firstStr.length() - 2);
            Toast.makeText(context, lastStr, Toast.LENGTH_SHORT).show();
        }
    }
}
