package com.zyj.motion.scroll;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.zyj.motion.R;

public class ScrollActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll);

        findViewById(R.id.scrollItem01).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ScrollActivity.this, "Scroll Item 01", Toast.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.scrollItem02).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ScrollActivity.this, "Scroll Item 02", Toast.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.scrollItem03).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ScrollActivity.this, "Scroll Item 03", Toast.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.scrollItem04).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ScrollActivity.this, "Scroll Item 04", Toast.LENGTH_SHORT).show();
            }
        });

    }
}