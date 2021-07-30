package com.zyj.motion.sample;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.zyj.motion.R;

import java.util.ArrayList;

public class EventDispatchActivity extends AppCompatActivity {

    private static final String[] mTitleArray = {"Item01", "Item02", "Item03"};
    private ArrayList<ItemFragment> fragments = new ArrayList<>();

    private TabLayout mTabLayout;
    private ViewPager2 mViewPager;
    private MyPagerAdapter mPagerAdapter;
    private EventDispatchParentLayout mDispatchRootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_dispatch);

        mDispatchRootView = findViewById(R.id.dispatchRootView);
        mTabLayout = findViewById(R.id.id_event_dispatch_tab);
        mViewPager = findViewById(R.id.id_event_dispatch_viewpager);

        fragments.add(ItemFragment.newInstance(20));
        fragments.add(ItemFragment.newInstance(20));
        fragments.add(ItemFragment.newInstance(20));

        mDispatchRootView.updateFragmentList(fragments);
        mPagerAdapter = new MyPagerAdapter(this);
        mViewPager.setAdapter(mPagerAdapter);

        new TabLayoutMediator(mTabLayout, mViewPager, false, true, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(mTitleArray[position]);
            }
        }).attach();

        mViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });

    }

    class MyPagerAdapter extends FragmentStateAdapter {

        public MyPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return fragments.get(position);
        }

        @Override
        public int getItemCount() {
            return mTitleArray.length;
        }

    }
}