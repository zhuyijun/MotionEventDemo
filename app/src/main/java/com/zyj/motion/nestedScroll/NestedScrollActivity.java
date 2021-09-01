package com.zyj.motion.nestedScroll;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.zyj.motion.R;

import java.util.ArrayList;
import java.util.List;

public class NestedScrollActivity extends AppCompatActivity {

    private NestedScrollingParentView mRootView;
    private FrameLayout mTopView;
    private TabLayout mTabLayoutView;
    private ViewPager2 mViewPager;
    private MyPageAdapter mPageAdapter;

    private List<BlankFragment> fragmentList = new ArrayList<>();
    private List<String> titleList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nested_scroll);

        mRootView = findViewById(R.id.nestedScrollContainer);
        mTopView = findViewById(R.id.id_nested_scrolling_top);
        mTabLayoutView = findViewById(R.id.id_nested_scrolling_tab);
        mViewPager = findViewById(R.id.id_nested_scrolling_viewpager);

        fragmentList.add(BlankFragment.newInstance());
        fragmentList.add(BlankFragment.newInstance());
        fragmentList.add(BlankFragment.newInstance());

        titleList.add("主页");
        titleList.add("历史");
        titleList.add("详情");

        mPageAdapter = new MyPageAdapter(this, fragmentList, titleList);
        mViewPager.setAdapter(mPageAdapter);

        new TabLayoutMediator(mTabLayoutView, mViewPager, false, true, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(titleList.get(position));
            }
        }).attach();

    }

}