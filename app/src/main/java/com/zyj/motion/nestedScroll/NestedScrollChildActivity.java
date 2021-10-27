package com.zyj.motion.nestedScroll;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.zyj.motion.R;

public class NestedScrollChildActivity extends AppCompatActivity {

    public static final String TAG = NestedScrollChildActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nested_scroll_child);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }
}