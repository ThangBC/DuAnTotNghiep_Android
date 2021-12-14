package com.example.test1.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test1.HomeActivity;
import com.example.test1.InChatActivity;
import com.example.test1.R;
import com.example.test1.adapters.LikeAdapter;
import com.example.test1.listeners.UserListener;
import com.example.test1.models.User;
import com.example.test1.models.Users;
import com.example.test1.networking.FunctionFriendsFAN;
import com.example.test1.ultilties.Constants;
import com.example.test1.ultilties.PreferenceManager;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

public class ListFriendsFragment extends Fragment implements UserListener {
    TextView tvCountListFriend, tv12;
    ProgressBar progressBar;
    RecyclerView rycListFriend;
    LikeAdapter likeAdapter;
    List<Users> likesList;
    EditText txtFilterListFriend;
    PreferenceManager preferenceManager;
    DocumentReference documentReference;
    FirebaseFirestore database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        tvCountListFriend = view.findViewById(R.id.tvCountFavorite);
        progressBar = view.findViewById(R.id.progressBar);
        tv12 = view.findViewById(R.id.textView12);
        rycListFriend = view.findViewById(R.id.rycListFavorite);
        txtFilterListFriend = view.findViewById(R.id.txtFilterListFriend);

        tvCountListFriend.setText("0 bạn bè");
        txtFilterListFriend.setVisibility(View.VISIBLE);

        getToken();

        likesList = new ArrayList<>();
        likeAdapter = new LikeAdapter(likesList, getContext(), this, 3);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        rycListFriend.setLayoutManager(gridLayoutManager);
        rycListFriend.setAdapter(likeAdapter);

        preferenceManager = new PreferenceManager(getContext());
        database = FirebaseFirestore.getInstance();
        documentReference = database.collection(Constants.KEY_COLLECTION_USER)
                .document(preferenceManager.getString(Constants.KEY_USER_ID));

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
                        FunctionFriendsFAN functionFriendsFAN = new FunctionFriendsFAN();
                        functionFriendsFAN.getListFriends(getContext(),users, likesList, rycListFriend,
                                likeAdapter, progressBar, tvCountListFriend, tv12);
                    } else {
                    }
                });

        txtFilterListFriend.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.e("ngu si đần độn", "đkm android studio");
                filter(editable.toString());
            }
        });
        return view;
    }

    private void getToken() {
        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(this::updateToken);
    }

    private void updateToken(String token) {
        preferenceManager.putString(Constants.KEY_FCM_TOKEN, token);
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        DocumentReference documentReference =
                database.collection(Constants.KEY_COLLECTION_USER).document(
                        preferenceManager.getString(Constants.KEY_USER_ID)
                );
        documentReference.update(Constants.KEY_FCM_TOKEN, token)
                .addOnFailureListener(e -> Toast.makeText(getContext(), "Failed to update Token", Toast.LENGTH_SHORT).show());

    }


    public void filter(String text) {
        if (likesList.size() != 0) {
            List<Users> filterList = new ArrayList<>();
            for (Users item : likesList) {
                if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                    filterList.add(item);
                }
            }
            likeAdapter.filterList(filterList);
        }
    }

    @Override
    public void onUserClicked(User user) {
        Intent intent = new Intent(getActivity(), InChatActivity.class);
        intent.putExtra(Constants.KEY_USER, user);
        startActivity(intent);
    }

}
