package com.example.flex;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.flex.ui.home.HomeFragment;

public class MyViewPageAdapter extends FragmentStateAdapter {

    public MyViewPageAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new WorkoutFragment();
            case 1:
                return new ProfileFragment();
            case 2:
                return new FeedFragment();
            default:
                return new WorkoutFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}