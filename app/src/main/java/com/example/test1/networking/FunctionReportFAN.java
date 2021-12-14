package com.example.test1.networking;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.test1.HomeActivity;
import com.example.test1.LoginActivity;
import com.example.test1.adapters.ReportAdapter;
import com.example.test1.models.Reports;
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

import org.json.JSONException;
import org.json.JSONObject;

public class FunctionReportFAN {


    public void insertReport(Context context, Reports reports, Dialog dialog) {

        AndroidNetworking.upload("https://poly-dating.herokuapp.com/api/reports/insert")
                .addHeaders("authorization","Bearer "+HomeActivity.users.getAccessToken())
                .addMultipartParameter("emailReceiver",reports.getEmailReported())
                .addMultipartParameter("title",reports.getTitle())
                .addMultipartParameter("content",reports.getContent())
                .addMultipartFile("images",reports.getImages())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Toast.makeText(context, response.getString("message"), Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.e("aaa", "Trả về" + response);
                    }
                    @Override
                    public void onError(ANError anError) {
                        checkLogAccount(anError.getErrorBody(),context,HomeActivity.users.getEmail(),0);
                    }
                });
    }

    private void checkLogAccount(String check, Context context, String email, int check404) {
        if (check.contains("403")) {
            GoogleSignInOptions gso = new GoogleSignInOptions.
                    Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                    build();
            GoogleSignInClient googleSignInClient1 = GoogleSignIn.getClient(context,gso);
            googleSignInClient1.signOut();
            Toast.makeText(context, "Tài khoản của bạn đã bị khóa", Toast.LENGTH_SHORT).show();
            context.startActivity(new Intent(context, LoginActivity.class));
        } else if (check.contains("404")) {
            if(check404==1){
                Intent intent = new Intent(context, NameActivity.class);
                intent.putExtra("email", email);
                context.startActivity(intent);
            }else {
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
            Log.e("err",check);
            Toast.makeText(context, "Lỗi không xác định", Toast.LENGTH_SHORT).show();
        }else if(check.contains("400")){
            String firstStr = check.substring(29);
            String lastStr = firstStr.substring(0, firstStr.length() - 2);
            Toast.makeText(context, lastStr, Toast.LENGTH_SHORT).show();
        }
    }

}
