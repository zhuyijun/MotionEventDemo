package com.zyj.motion.touch;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.zyj.motion.R;

public class TouchStickyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_sticky);

        findViewById(R.id.touchStickyView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(TouchStickyActivity.this, "Click Event", Toast.LENGTH_SHORT).show();
            }
        });
    }
}