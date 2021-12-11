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

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test1.HomeActivity;
import com.example.test1.InChatActivity;
import com.example.test1.R;
import com.example.test1.adapters.LikeAdapter;
import com.example.test1.adapters.UsersAdapter;
import com.example.test1.listeners.UserListener;
import com.example.test1.models.User;
import com.example.test1.models.Users;
import com.example.test1.networking.FunctionFriendsFAN;
import com.example.test1.ultilties.Constants;
import com.example.test1.ultilties.PreferenceManager;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ListFriendsFragment extends Fragment implements UserListener {
    public static TextView tvCountListFriend,tv12;
    public static ProgressBar progressBar;
    public static RecyclerView rycListFriend;
    public static LikeAdapter likeAdapter;
    public static List<Users> likesList;
    EditText txtFilterListFriend;
    private PreferenceManager preferenceManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_friend, container, false);
        tvCountListFriend = view.findViewById(R.id.tvCountListFriend);
        progressBar = view.findViewById(R.id.progressBar);
        tv12 = view.findViewById(R.id.textView12);
        rycListFriend = view.findViewById(R.id.rycListFriend);
        txtFilterListFriend = view.findViewById(R.id.txtFilterListFriend);

        preferenceManager = new PreferenceManager(getActivity());

        txtFilterListFriend.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });

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
                        functionFriendsFAN.getListFriends(getActivity(), HomeActivity.users.getEmail(),this,users);
                    } else {
                    }
                });
        return view;
    }

    public void filter(String text){
        List<Users> filterList = new ArrayList<>();
        for (Users item: likesList) {
            if(item.getName().toLowerCase().contains(text.toLowerCase())){
                filterList.add(item);
            }
        }
        likeAdapter.filterList(filterList);
    }

    @Override
    public void onUserClicked(User user) {
        Intent intent = new Intent(getActivity(), InChatActivity.class);
        intent.putExtra(Constants.KEY_USER, user);
        startActivity(intent);
    }
}
