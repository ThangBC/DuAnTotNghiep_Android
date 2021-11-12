package com.example.test1.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.test1.fragments.DfrPeopleLikeFragment;
import com.example.test1.fragments.LikeFragment;
import com.example.test1.fragments.MeLikeFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new DfrPeopleLikeFragment();
            case 1:
                return new MeLikeFragment();
            default:
                return new DfrPeopleLikeFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case 0:
                title = "Lượt thích";
                break;
            case 1:
                title = "Đã thích";
                break;
        }
        return title;
    }
}
