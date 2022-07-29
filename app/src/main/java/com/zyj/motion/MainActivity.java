package com.zyj.motion;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.zyj.motion.gesture.GestureActivity;
import com.zyj.motion.gesture.GestureScaleActivity;
import com.zyj.motion.gesture.LightGestureActivity;
import com.zyj.motion.nestedScroll.NestedScrollActivity;
import com.zyj.motion.nestedScroll.NestedScrollChildActivity;
import com.zyj.motion.sample.EventDispatchActivity;
import com.zyj.motion.scroll.ScrollActivity;
import com.zyj.motion.touch.TouchActivity;
import com.zyj.motion.touch.TouchDrawActivity;
import com.zyj.motion.touch.TouchStickyActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i("", "");

        findViewById(R.id.btn_touch01).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TouchActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.btn_touchSticky).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TouchStickyActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.btn_touchDraw).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TouchDrawActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.btn_scrollSimple).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ScrollActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.btn_gestureSimple).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GestureActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.btn_gestureLight).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LightGestureActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.btn_gestureScale).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GestureScaleActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.btn_eventDispatch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EventDispatchActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.btn_nestedScroll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NestedScrollActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.btn_nestedScrollChild).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NestedScrollChildActivity.class);
                startActivity(intent);
            }
        });

    }

}