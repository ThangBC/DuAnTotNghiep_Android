package com.example.test1.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.test1.HomeActivity;
import com.example.test1.InChatActivity;
import com.example.test1.LoginActivity;
import com.example.test1.R;
import com.example.test1.adapters.LikeAdapter;
import com.example.test1.adapters.RecentConversionsAdapter;
import com.example.test1.listeners.ConversationListener;
import com.example.test1.models.ChatMessage;
import com.example.test1.models.User;
import com.example.test1.models.Users;
import com.example.test1.networking.FunctionFriendsFAN;
import com.example.test1.signupactivities.NameActivity;
import com.example.test1.ultilties.Constants;
import com.example.test1.ultilties.PreferenceManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChatFragment extends Fragment implements ConversationListener {

    TextView tv12;
    ProgressBar progressBar;
    ImageView imgLogoHeader;
    RecyclerView conversationsRecycleView;
    private List<ChatMessage> conversations;
    private FirebaseFirestore database;
    private PreferenceManager preferenceManager;
    private RecentConversionsAdapter conversationsAdapter;
    private DocumentReference documentReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        imgLogoHeader = view.findViewById(R.id.imgLogoHeader);
        conversationsRecycleView = view.findViewById(R.id.conversationsRecycleView);
        tv12 = view.findViewById(R.id.textView12);
        progressBar = view.findViewById(R.id.progressBar);

        preferenceManager = new PreferenceManager(getActivity());

        if(preferenceManager.getString(Constants.KEY_USER_ID)!=null){
            database = FirebaseFirestore.getInstance();
            documentReference = database.collection(Constants.KEY_COLLECTION_USER)
                    .document(preferenceManager.getString(Constants.KEY_USER_ID));
        }

        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(this::updateToken);

        conversations = new ArrayList<>();
        database = FirebaseFirestore.getInstance();

        listenConversations();

        imgLogoHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), HomeActivity.class));
            }
        });

        return view;
    }

    private void listenConversations() {
        database.collection(Constants.KEY_COLLECTION_CONVERSATIONS)
                .whereEqualTo(Constants.KEY_SENDER_ID, preferenceManager.getString(Constants.KEY_USER_ID))
                .addSnapshotListener(eventListener);
        database.collection(Constants.KEY_COLLECTION_CONVERSATIONS)
                .whereEqualTo(Constants.KEY_RECEIVER_ID, preferenceManager.getString(Constants.KEY_USER_ID))
                .addSnapshotListener(eventListener);
    }

    private final EventListener<QuerySnapshot> eventListener = (value, error) -> {
        if (error != null) {
            return;
        }
        if (value != null) {
            for (DocumentChange documentChange : value.getDocumentChanges()) {
                if (documentChange.getType() == DocumentChange.Type.ADDED) { //Khi tin nhắn mới được thêm
                    String senderId = documentChange.getDocument().getString(Constants.KEY_SENDER_ID);
                    String receiverId = documentChange.getDocument().getString(Constants.KEY_RECEIVER_ID);
                    ChatMessage chatMessage = new ChatMessage();
                    chatMessage.senderId = senderId;
                    chatMessage.receiverId = receiverId;
                    if (preferenceManager.getString(Constants.KEY_USER_ID).equals(senderId)) {
                        chatMessage.conversionImage = documentChange.getDocument().getString(Constants.KEY_RECEIVER_IMAGE);
                        chatMessage.conversionName = documentChange.getDocument().getString(Constants.KEY_RECEIVER_NAME);
                        chatMessage.conversionId = documentChange.getDocument().getString(Constants.KEY_RECEIVER_ID);

                    } else {
                        chatMessage.conversionImage = documentChange.getDocument().getString(Constants.KEY_SENDER_IMAGE);
                        chatMessage.conversionName = documentChange.getDocument().getString(Constants.KEY_SENDER_NAME);
                        chatMessage.conversionId = documentChange.getDocument().getString(Constants.KEY_SENDER_ID);
                    }
                    chatMessage.message = documentChange.getDocument().getString(Constants.KEY_LAST_MASSAGE);
                    chatMessage.dateObject = documentChange.getDocument().getDate(Constants.KEY_TIMESTAMP);
                    conversations.add(chatMessage);
                } else if (documentChange.getType() == DocumentChange.Type.MODIFIED) { // Khi tin nhắn đã sửa đổi
                    for (int i = 0; i < conversations.size(); i++) {
                        String senderId = documentChange.getDocument().getString(Constants.KEY_SENDER_ID);
                        String receiverId = documentChange.getDocument().getString(Constants.KEY_RECEIVER_ID);
                        if (conversations.get(i).senderId.equals(senderId) && conversations.get(i).receiverId.equals(receiverId)) {
                            conversations.get(i).message = documentChange.getDocument().getString(Constants.KEY_LAST_MASSAGE);
                            conversations.get(i).dateObject = documentChange.getDocument().getDate(Constants.KEY_TIMESTAMP);
                            break;
                        }
                    }
                }
            }
            FirebaseFirestore database = FirebaseFirestore.getInstance();
            database.collection(Constants.KEY_COLLECTION_USER)
                    .get()
                    .addOnCompleteListener(task -> {
                        String currentUserID = preferenceManager.getString(Constants.KEY_USER_ID);
                        if (task.isSuccessful() && task.getResult() != null) {
                            List<User> users = new ArrayList<>();
                            for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                                if (currentUserID.equals(queryDocumentSnapshot.getId())) {
                                    continue;
                                }
                                User user = new User();
                                user.token = queryDocumentSnapshot.getString(Constants.KEY_FCM_TOKEN);
                                user.id = queryDocumentSnapshot.getId();
                                user.email = queryDocumentSnapshot.getString(Constants.KEY_EMAIL);
                                users.add(user);
                            }
                            List<String> stringList = new ArrayList<>();
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
                                                    stringList.add(email);
                                                }
                                                Log.e("size1", String.valueOf(users.size()));
                                                Log.e("size2", String.valueOf(stringList.size()));
                                                List<String> receiverIds = new ArrayList<>();
                                                for (int n = 0; n < users.size(); n++) {
                                                    for (int m = 0; m < stringList.size(); m++) {
                                                        if (users.get(n).email.equals(stringList.get(m))) {
                                                            receiverIds.add(users.get(n).id);
                                                            Log.e("run1", users.get(n).id);
                                                        }
                                                    }
                                                }
                                                List<ChatMessage> receiverIds1 = new ArrayList<>();
                                                for (int k = 0; k < conversations.size(); k++) {
                                                    for (int j = 0; j < receiverIds.size(); j++) {
                                                        if (conversations.get(k).receiverId.equals(receiverIds.get(j))
                                                                || conversations.get(k).senderId.equals(receiverIds.get(j))) {
                                                            receiverIds1.add(conversations.get(k));
                                                            Log.e("run2", conversations.get(k).receiverId);
                                                        }
                                                    }
                                                }
                                                if (receiverIds1.size() == 0) {
                                                    tv12.setVisibility(View.VISIBLE);
                                                    tv12.setText("Không có cuộc trò chuyện");
                                                } else {
                                                    tv12.setVisibility(View.GONE);
                                                }
                                                progressBar.setVisibility(View.GONE);
                                                Collections.sort(receiverIds1, (obj1, obj2) -> -(obj1.dateObject.compareTo(obj2.dateObject)));
                                                conversationsAdapter = new RecentConversionsAdapter(getActivity(), receiverIds1, ChatFragment.this);
                                                conversationsRecycleView.setAdapter(conversationsAdapter);
                                                conversationsAdapter.notifyDataSetChanged();
                                                conversationsRecycleView.smoothScrollToPosition(0);
                                                conversationsRecycleView.setVisibility(View.VISIBLE);
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }

                                        @Override
                                        public void onError(ANError anError) {
                                            checkLogAccount(anError.getErrorBody(), getContext(), HomeActivity.users.getEmail(), 0);
                                            tv12.setVisibility(View.VISIBLE);
                                            progressBar.setVisibility(View.GONE);
                                            tv12.setText("Có vấn đề xảy ra");
                                        }
                                    });
                        }
                    });
        }
    };

    private void updateToken(String token) {
        preferenceManager.putString(Constants.KEY_FCM_TOKEN, token);
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        DocumentReference documentReference =
                database.collection(Constants.KEY_COLLECTION_USER).document(
                        preferenceManager.getString(Constants.KEY_USER_ID)
                );
        documentReference.update(Constants.KEY_FCM_TOKEN, token)
                .addOnFailureListener(e -> showToast("Unable to update token"));

    }

    private void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConversionClicked(User user) {
        Intent intent = new Intent(getActivity(), InChatActivity.class);
        intent.putExtra(Constants.KEY_USER, user);
        startActivity(intent);
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
            if (check404 == 1) {
                Intent intent = new Intent(context, NameActivity.class);
                intent.putExtra("email", email);
                context.startActivity(intent);
            } else {
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
            Log.e("err", check);
            Toast.makeText(context, "Lỗi không xác định", Toast.LENGTH_SHORT).show();
        } else if (check.contains("400")) {
            String firstStr = check.substring(29);
            String lastStr = firstStr.substring(0, firstStr.length() - 2);
            Toast.makeText(context, lastStr, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        documentReference.update(Constants.KEY_AVAILABILITY, 0);
    }

    @Override
    public void onResume() {
        super.onResume();
        documentReference.update(Constants.KEY_AVAILABILITY, 1);
    }
}
