package com.example.utilsuser.immersion;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.utilsgather.list_guide.GuideItemEntity;

import java.util.ArrayList;
import java.util.List;

public class TabAdapter extends FragmentStateAdapter {

    private List<ShowFragment> mFragments = new ArrayList<>();

    public TabAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public TabAdapter(@NonNull FragmentActivity fragmentActivity, List<ShowFragment> fragments) {
        super(fragmentActivity);
        mFragments = fragments;
    }

    public void addFragment(ShowFragment fragment) {
        mFragments.add(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getItemCount() {
        return mFragments == null ? 0 : mFragments.size();
    }
}
