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
import androidx.fragment.app.FragmentActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.test1.HomeActivity;
import com.example.test1.LoginActivity;
import com.example.test1.R;
import com.example.test1.signupactivities.NameActivity;
import com.example.test1.adapters.UserAdapter;
import com.example.test1.fragments.HomeFragment;
import com.example.test1.functions.Loading;
import com.example.test1.models.Users;
import com.example.test1.ultilties.Constants;
import com.example.test1.ultilties.PreferenceManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
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
import java.util.Map;
import java.util.Random;

public class FunctionUserFAN {

    SharedPreferences sp;

    public void insertUser(Activity context, Users users, Loading loading) {// đăng ký thêm user

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
                                checkUser(users.getEmail(), users.getToken(), context, null, 2, loading, null, null);
                            } else {
                                Toast.makeText(context, response.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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

    public void checkUser(String email, String token, Activity context, GoogleSignInClient googleSignInClient, int check, Loading loading, Dialog dialog, JSONObject response1) {
        // hàm sign và check user
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
                                String isActive = jo.getString("isActive");
                                boolean statusHobbies = jo.getBoolean("statusHobby");
                                String accessToken = jo.getString("accessToken");

                                HomeActivity.users = new Users(_id, email, name, imgUrl, hobbiesList, birthDay, gender, description,
                                        facilities, specialized, course, isShowList, isActive, statusHobbies, accessToken);

                                if (check == 0) {// cập nhật khám phá
                                    loading.dismiss();
                                    dialog.dismiss();
                                    Toast.makeText(context, response1.getString("message"), Toast.LENGTH_SHORT).show();
                                }
                                if (check == 1) {// đăng nhập
                                    PreferenceManager preferenceManager = new PreferenceManager(context);
                                    FirebaseFirestore database = FirebaseFirestore.getInstance();
                                    database.collection(Constants.KEY_COLLECTION_USER)
                                            .whereEqualTo(Constants.KEY_EMAIL, email)
                                            .get()
                                            .addOnCompleteListener(task -> {
                                                if (task.isComplete() && task.getResult() != null
                                                        && task.getResult().getDocumentChanges().size() > 0) {
                                                    DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                                                    preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN, true);
                                                    preferenceManager.putString(Constants.KEY_USER_ID, documentSnapshot.getId());
                                                    preferenceManager.putString(Constants.KEY_NAME, documentSnapshot.getString(Constants.KEY_NAME));
                                                    preferenceManager.putString(Constants.KEY_IAMGE, documentSnapshot.getString(Constants.KEY_IAMGE));
                                                    context.startActivity(new Intent(context, HomeActivity.class));
                                                    Toast.makeText(context, "Chào mừng trở lại " + HomeActivity.users.getName(), Toast.LENGTH_SHORT).show();
                                                } else {
                                                }
                                            });
                                }
                                if (check == 2) {// đăng ký
                                    PreferenceManager preferenceManager = new PreferenceManager(context);
                                    FirebaseFirestore database = FirebaseFirestore.getInstance();
                                    HashMap<String, Object> user = new HashMap<>();
                                    user.put(Constants.KEY_NAME, name);
                                    user.put(Constants.KEY_EMAIL, email);
                                    user.put(Constants.KEY_IAMGE, imgUrl.get(0));
                                    database.collection(Constants.KEY_COLLECTION_USER)
                                            .add(user)
                                            .addOnSuccessListener(documentReference -> {
                                                preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN, true);
                                                preferenceManager.putString(Constants.KEY_USER_ID, documentReference.getId());
                                                preferenceManager.putString(Constants.KEY_NAME, name);
                                                preferenceManager.putString(Constants.KEY_IAMGE, imgUrl.get(0));
                                                context.startActivity(new Intent(context, HomeActivity.class));
                                                Toast.makeText(context, "Tạo tài khoản thành công", Toast.LENGTH_SHORT).show();
                                                loading.dismiss();
                                            });
                                    user.put(Constants.KEY_NAME, name);
                                }
                                if (check == 3) {// cập nhật ảnh
                                    PreferenceManager preferenceManager = new PreferenceManager(context);
                                    DocumentReference docRef = FirebaseFirestore.getInstance()
                                            .collection(Constants.KEY_COLLECTION_USER)
                                            .document(preferenceManager.getString(Constants.KEY_USER_ID));
                                    Map<String, Object> map = new HashMap<>();
                                    map.put("image", HomeActivity.users.getImageUrl().get(0));
                                    docRef.update(map)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    context.overridePendingTransition(0, 0);
                                                    context.finish();
                                                    context.overridePendingTransition(0, 0);
                                                    context.startActivity(context.getIntent());
                                                    context.overridePendingTransition(0, 0);
                                                }
                                            });
                                    Toast.makeText(context, response1.getString("message"), Toast.LENGTH_SHORT).show();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        checkLogAccount(anError.getErrorBody(), googleSignInClient, context, email, 1);
                        loading.dismiss();
                    }
                });
    }

    public void checkListUser1(Context context, List<Users> usersList1, TextView tv12, ImageButton imgReload, ProgressBar progressBar,
                               UserAdapter userAdapter, List<String> usersListCheck1, List<String> usersListCheck2,
                               List<String> usersListCheck3, SwipeFlingAdapterView flingAdapterView) {
        // check list xem nếu là bạn bè thì đổi icon thành nhắn tin
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

                                String email = jo.getString("email");

                                usersListCheck1.add(email);
                            }
                            checkListUser2(context, usersList1, tv12, imgReload, progressBar, userAdapter
                                    , usersListCheck2, usersListCheck3, flingAdapterView);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        checkLogAccount(anError.getErrorBody(), null, context, HomeActivity.users.getEmail(), 0);
                    }
                });
    }

    public void checkListUser2(Context context, List<Users> usersList1, TextView tv12, ImageButton imgReload, ProgressBar progressBar,
                               UserAdapter userAdapter, List<String> usersListCheck2, List<String> usersListCheck3,
                               SwipeFlingAdapterView flingAdapterView) {
// check list xem nếu là người thích mình thì đổi icon thành tích v
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

                                String email = jo.getString("email");

                                usersListCheck2.add(email);
                            }
                            checkListUser3(context, usersList1, tv12, imgReload, progressBar, userAdapter,
                                    usersListCheck3, flingAdapterView);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        checkLogAccount(anError.getErrorBody(), null, context, HomeActivity.users.getEmail(), 0);
                    }
                });
    }

    public void checkListUser3(Context context, List<Users> usersList1, TextView tv12, ImageButton imgReload, ProgressBar progressBar,
                               UserAdapter userAdapter, List<String> userListCheck3, SwipeFlingAdapterView flingAdapterView) {
        // check nếu là người mình đã thích thì đổi icon thành 3 chấm
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
                                String email = jo.getString("email");
                                userListCheck3.add(email);

                            }
                            getListUser(context, usersList1, tv12, imgReload, progressBar, userAdapter, flingAdapterView);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        checkLogAccount(anError.getErrorBody(), null, context, HomeActivity.users.getEmail(), 0);
                    }
                });
    }

    public void getListUser(Context context, List<Users> usersList1, TextView tv12, ImageButton imgReload,
                            ProgressBar progressBar, UserAdapter userAdapter, SwipeFlingAdapterView flingAdapterView) {
        // lấy tất cả danh sách user
        List<Users> usersList = new ArrayList<>();
        AndroidNetworking.get("https://poly-dating.herokuapp.com/api/users/list")
                .addHeaders("authorization", "Bearer " + HomeActivity.users.getAccessToken())
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
                                String isActive = usersJSON.getJSONObject(i).getString("isActive");

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
                            }
                            for (int i = 0; i < usersList.size(); i++) {// loại chính mình trong list danh sách
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
                                        ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,
                                                new HomeFragment()).commit();
                                    }
                                });
                            } else {
                                getRandomElement(usersList, usersList1, usersList.size());
                                userAdapter.notifyDataSetChanged();
                                progressBar.setVisibility(View.GONE);
                                tv12.setVisibility(View.GONE);
                                imgReload.setVisibility(View.GONE);
                                flingAdapterView.setVisibility(View.VISIBLE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        checkLogAccount(anError.getErrorBody(), null, context, HomeActivity.users.getEmail(), 0);
                        tv12.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        tv12.setText("Có lỗi xảy ra");
                    }
                });
    }

    public void updateUser(String mail, Users users, String result, String title, Activity context, Dialog dialog, TextView tv, Loading loading) {
        // cập nhật thông tin người dùng
        sp = context.getSharedPreferences("MyToken", Context.MODE_PRIVATE);
        String token = sp.getString("token", "");

        AndroidNetworking.post("https://poly-dating.herokuapp.com/api/users/update/information")
                .addHeaders("authorization", "Bearer " + HomeActivity.users.getAccessToken())
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
                                checkUser(mail, token, context, null, 0, loading, dialog, response);
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
                        checkLogAccount(anError.getErrorBody(), null, context, HomeActivity.users.getEmail(), 0);
                        loading.dismiss();
                    }
                });
    }

    public void updateImages(String mail, File images, String image, String checkRemove, Activity context) {
        // cập nhật ảnh
        sp = context.getSharedPreferences("MyToken", Context.MODE_PRIVATE);
        String token = sp.getString("token", "");

        AndroidNetworking.upload("https://poly-dating.herokuapp.com/api/users/update/images")
                .addHeaders("authorization", "Bearer " + HomeActivity.users.getAccessToken())
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
                                checkUser(mail, token, context, null, 3, null, null, response);
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
                        String firstStr = anError.getErrorBody().substring(29);
                        String lastStr = firstStr.substring(0, firstStr.length() - 2);
                        Toast.makeText(context, lastStr, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void updateIsShow(String email, String[] isShowarr, String showStr, Activity context, Dialog dialog, TextView tv, Loading loading) {
        // cập nhật khám phá hiển thị
        sp = context.getSharedPreferences("MyToken", Context.MODE_PRIVATE);
        String token = sp.getString("token", "");
        AndroidNetworking.post("https://poly-dating.herokuapp.com/api/users/update/is-show")
                .addHeaders("authorization", "Bearer " + HomeActivity.users.getAccessToken())
                .addBodyParameter("isShow", Arrays.toString(isShowarr))
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.toString().contains("200")) {
                                checkUser(email, token, context, null, 0, loading, dialog, response);
                                tv.setText(showStr);
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
                        checkLogAccount(anError.getErrorBody(), null, context, HomeActivity.users.getEmail(), 0);
                        loading.dismiss();
                    }
                });
    }

    public void requestCode(Activity context) {
        // gửi mã xác nhận vào mail
        AndroidNetworking.post("https://poly-dating.herokuapp.com/api/users/request-code")
                .addHeaders("authorization", "Bearer " + HomeActivity.users.getAccessToken())
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
                        checkLogAccount(anError.getErrorBody(), null, context, HomeActivity.users.getEmail(), 0);
                    }
                });
    }

    public void updateStatusHobbies(String email, String statusHobby, Activity context,
                                    Loading loading, Dialog dialog, TextView tvFilterInterest) {
        // hiển thị và không hiển thị những người có cùng sở thích với mình
        sp = context.getSharedPreferences("MyToken", Context.MODE_PRIVATE);
        String token = sp.getString("token", "");

        AndroidNetworking.post("https://poly-dating.herokuapp.com/api/users/update/status-hobby")
                .addHeaders("authorization", "Bearer " + HomeActivity.users.getAccessToken())
                .addBodyParameter("statusHobby", statusHobby)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            checkUser(email, token, context, null, 0, loading, dialog, response);
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
                        checkLogAccount(anError.getErrorBody(), null, context, HomeActivity.users.getEmail(), 0);
                        loading.dismiss();
                        dialog.dismiss();
                    }
                });
    }

    public void deleteUser(String code, Activity context, Loading loading) {
        // xóa người dùng
        AndroidNetworking.post("https://poly-dating.herokuapp.com/api/users/delete")
                .addHeaders("authorization", "Bearer " + HomeActivity.users.getAccessToken())
                .addBodyParameter("code", code)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            PreferenceManager preferenceManager = new PreferenceManager(context);
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
                                        context.startActivity(new Intent(context, LoginActivity.class));
                                        preferenceManager.clear();
                                    } else {
                                    }
                                }
                            });

                            Toast.makeText(context, response.getString("message"), Toast.LENGTH_SHORT).show();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        checkLogAccount(anError.getErrorBody(), null, context, HomeActivity.users.getEmail(), 0);
                        loading.dismiss();
                    }
                });
    }

    public void signOutUser(Activity context, Loading loading) {
        // đăng xuất
        AndroidNetworking.post("https://poly-dating.herokuapp.com/api/users/sign-out")
                .addHeaders("authorization", "Bearer " + HomeActivity.users.getAccessToken())
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            PreferenceManager preferenceManager = new PreferenceManager(context);
                            FirebaseFirestore database = FirebaseFirestore.getInstance();
                            DocumentReference documentReference =
                                    database.collection(Constants.KEY_COLLECTION_USER).document(
                                            preferenceManager.getString(Constants.KEY_USER_ID)
                                    );
                            HashMap<String, Object> updates = new HashMap<>();
                            updates.put(Constants.KEY_FCM_TOKEN, FieldValue.delete());
                            documentReference.update(updates)
                                    .addOnSuccessListener(unused -> {
                                        preferenceManager.clear();
                                        GoogleSignInOptions gso = new GoogleSignInOptions.
                                                Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                                                build();
                                        GoogleSignInClient googleSignInClient1 = GoogleSignIn.getClient(context, gso);
                                        googleSignInClient1.signOut();
                                        context.startActivity(new Intent(context, LoginActivity.class));
                                    });
                            Toast.makeText(context, response.getString("message"), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        checkLogAccount(anError.getErrorBody(), null, context, HomeActivity.users.getEmail(), 0);
                        loading.dismiss();
                    }
                });
    }

    public void getRandomElement(List<Users> list, List<Users> list1, int totalItems) {
        // hàm random ngẫu nhiên các phần tử trong list
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
    }

    private void checkLogAccount(String check, GoogleSignInClient googleSignInClient, Context context, String email, int check404) {
        // hàm check lỗi
        if (check.contains("403")) {
            if (googleSignInClient != null) {
                googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                    }
                });
                LoginActivity.loading.dismiss();
            } else {
                GoogleSignInOptions gso = new GoogleSignInOptions.
                        Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                        build();
                GoogleSignInClient googleSignInClient1 = GoogleSignIn.getClient(context, gso);
                googleSignInClient1.signOut();
            }
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
                    preferenceManager.putString("emailSignUp", email);
                    context.startActivity(new Intent(context, NameActivity.class));
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
            Toast.makeText(context, check, Toast.LENGTH_SHORT).show();
            Log.e("err", check);
        } else if (check.contains("400")) {
            String firstStr = check.substring(29);
            String lastStr = firstStr.substring(0, firstStr.length() - 2);
            Toast.makeText(context, lastStr, Toast.LENGTH_SHORT).show();
        }
    }

}
