package com.zyj.motion.nestedScroll;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

/**
 * @Author zhuyijun
 * @Version 1.0.0
 * @Date 2021/7/30 14:33
 */
public class MyPageAdapter extends FragmentStateAdapter {

    private List<BlankFragment> mList;
    private List<String> mTitles;

    public MyPageAdapter(@NonNull FragmentActivity fragmentActivity, List<BlankFragment> fragmentList, List<String> titles) {
        super(fragmentActivity);
        this.mList = fragmentList;
        this.mTitles = titles;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return mList.get(position);
    }

    @Override
    public int getItemCount() {
        return mTitles.size();
    }
}
