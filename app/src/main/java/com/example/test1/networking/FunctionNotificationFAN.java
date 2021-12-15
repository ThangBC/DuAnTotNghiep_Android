package com.example.test1.networking;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.test1.HomeActivity;
import com.example.test1.LoginActivity;
import com.example.test1.R;
import com.example.test1.UserDetailActivity;
import com.example.test1.adapters.LikeAdapter;
import com.example.test1.adapters.NotificationAdapter;
import com.example.test1.fragments.NotificationFragment;
import com.example.test1.models.Notification;
import com.example.test1.models.Users;
import com.example.test1.signupactivities.AddressStudyActivity;
import com.example.test1.signupactivities.CourseActivity;
import com.example.test1.signupactivities.InterestsActivity;
import com.example.test1.signupactivities.NameActivity;
import com.example.test1.signupactivities.SpecializedActivity;
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

import java.util.List;

public class FunctionNotificationFAN {

    public void getListNotification(Context context, List<Notification> notificationList, RecyclerView notificationRyc,
                                    NotificationAdapter notificationAdapter, ProgressBar progressBar, TextView tv12) {

        AndroidNetworking.get("https://poly-dating.herokuapp.com/api/notifications/list")
                .addHeaders("authorization", "Bearer " + HomeActivity.users.getAccessToken())
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject arr = response.getJSONObject("data");
                            JSONArray nofitications = arr.getJSONArray("nofitications");
                            for (int i = 0; i < nofitications.length(); i++) {
                                String _id = nofitications.getJSONObject(i).getString("_id");
                                String title = nofitications.getJSONObject(i).getString("title");
                                String content = nofitications.getJSONObject(i).getString("content");
                                String link = nofitications.getJSONObject(i).getString("link");
                                String createdAt = nofitications.getJSONObject(i).getString("createdAt");

                                Notification notification = new Notification();
                                notification.set_id(_id);
                                notification.setTitle(title);
                                notification.setContent(content);
                                notification.setLink(link);
                                notification.setDate(createdAt);

                                notificationList.add(notification);
                            }
                            if (notificationList.size() == 0) {
                                tv12.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);
                                tv12.setText("Không có thông báo");
                            } else {
                                progressBar.setVisibility(View.GONE);
                                tv12.setVisibility(View.GONE);
                            }
                            notificationAdapter.notifyDataSetChanged();
                            notificationRyc.smoothScrollToPosition(0);
                            notificationRyc.setVisibility(View.VISIBLE);
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

    public void delNotification(Context context, String _id) {

        AndroidNetworking.post("https://poly-dating.herokuapp.com/api/notifications/delete")
                .addHeaders("authorization", "Bearer " + HomeActivity.users.getAccessToken())
                .addBodyParameter("_id", _id)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Toast.makeText(context, response.getString("message"), Toast.LENGTH_SHORT).show();
                            ((FragmentActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,
                                    new NotificationFragment()).commit();
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

    private void checkLogAccount(String check, Context context, String email, int check404) {
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
