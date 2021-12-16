package com.example.test1.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.test1.InChatActivity;
import com.example.test1.adapters.LikeAdapter;
import com.example.test1.adapters.UserAdapter;
import com.example.test1.HomeActivity;
import com.example.test1.R;
import com.example.test1.models.User;
import com.example.test1.models.Users;
import com.example.test1.networking.FunctionFriendsFAN;
import com.example.test1.networking.FunctionUserFAN;
import com.example.test1.ultilties.Constants;
import com.example.test1.ultilties.PreferenceManager;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.messaging.FirebaseMessaging;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    ProgressBar progressBar;
    TextView tv12;
    public static SwipeFlingAdapterView flingAdapterView;
    UserAdapter userAdapter;
    ImageView imgLogoHeader;
    ImageButton imgReload;
    List<Users> userList = new ArrayList<>();
    List<String> usersListCheck1 = new ArrayList<>();
    List<String> usersListCheck2 = new ArrayList<>();
    List<String> usersListCheck3 = new ArrayList<>();
    PreferenceManager preferenceManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        flingAdapterView = view.findViewById(R.id.swipe);
        imgLogoHeader = view.findViewById(R.id.imgLogoHeader);
        progressBar = view.findViewById(R.id.progressBar);
        tv12 = view.findViewById(R.id.textView12);
        imgReload = view.findViewById(R.id.imgbtnreload);

        preferenceManager = new PreferenceManager(getContext());
        getToken();

        userAdapter = new UserAdapter(userList, getContext(), usersListCheck1, usersListCheck2, usersListCheck3);
        flingAdapterView.setAdapter(userAdapter);

        FunctionUserFAN functionUserFAN = new FunctionUserFAN();
        functionUserFAN.checkListUser1(getActivity(), userList, tv12, imgReload, progressBar, userAdapter,
                usersListCheck1, usersListCheck2, usersListCheck3, flingAdapterView);

        imgLogoHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), HomeActivity.class));
            }
        });

        flingAdapterView.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {

            }

            @Override// đây là không thích
            public void onLeftCardExit(Object o) {
                userList.remove(0);
                userAdapter.notifyDataSetChanged();
            }

            @Override// đây là thích
            public void onRightCardExit(Object o) {
                for (int i = 0; i < usersListCheck1.size(); i++) {
                    if (userList.get(0).getEmail().equals(usersListCheck1.get(i))) {
                        FirebaseFirestore database = FirebaseFirestore.getInstance();
                        database.collection(Constants.KEY_COLLECTION_USER)
                                .get()
                                .addOnCompleteListener(task -> {
                                    String currentUserID = preferenceManager.getString(Constants.KEY_USER_ID);
                                    if (task.isSuccessful() && task.getResult() != null) {
                                        for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                                            if (currentUserID.equals(queryDocumentSnapshot.getId())) {
                                                continue;
                                            }
                                            if (userList.get(0).getEmail().equals(queryDocumentSnapshot.getString(Constants.KEY_EMAIL))) {
                                                User user = new User();
                                                user.token = queryDocumentSnapshot.getString(Constants.KEY_FCM_TOKEN);
                                                user.id = queryDocumentSnapshot.getId();
                                                user.email = queryDocumentSnapshot.getString(Constants.KEY_EMAIL);
                                                user.name = userList.get(0).getName();
                                                user.image = userList.get(0).getImageUrl().get(0);
                                                Intent intent = new Intent(getActivity(), InChatActivity.class);
                                                intent.putExtra(Constants.KEY_USER, user);
                                                startActivity(intent);
                                            }
                                        }
                                        userList.remove(0);
                                        userAdapter.notifyDataSetChanged();
                                    }
                                });
                        return;
                    }
                }
                FunctionFriendsFAN functionFriendsFAN = new FunctionFriendsFAN();
                functionFriendsFAN.insertFriends(getActivity(), userList.get(0).getEmail(), 0);
                userList.remove(0);
                userAdapter.notifyDataSetChanged();
            }

            @Override
            public void onAdapterAboutToEmpty(int i) {
                if (userList.size() == 0) {
                    tv12.setVisibility(View.VISIBLE);
                    imgReload.setVisibility(View.VISIBLE);
                    tv12.setText("Đã tìm hết, nhấn nút phía dưới để làm mới");
                    imgReload.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,
                                    new HomeFragment()).commit();
                        }
                    });
                }
            }

            @Override
            public void onScroll(float v) {

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
}
