package com.example.flex;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.flex.fragments.FeedFragment;
import com.example.flex.fragments.ProfileFragment;
import com.example.flex.fragments.WorkoutFragment;

public class MyViewPagerAdapter extends FragmentStateAdapter {

    public MyViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new ProfileFragment();
            case 1:
                return new FeedFragment();
            case 2:
                return new WorkoutFragment();
            default:
                return  new ProfileFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
