package com.example.test1.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;

import com.example.test1.R;
import com.example.test1.UserDetailActivity;
import com.makeramen.roundedimageview.RoundedImageView;

public class HomeFragment extends Fragment {

    RoundedImageView imgUserFrgHome;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home,container,false);
        imgUserFrgHome = view.findViewById(R.id.imgUserFrgHome);

        imgUserFrgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),UserDetailActivity.class));
            }
        });
        return view;
    }
}
