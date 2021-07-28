package com.zyj.motion.gesture;

import android.os.Bundle;
import android.provider.Settings;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.zyj.motion.R;

public class LightGestureActivity extends AppCompatActivity implements LightGestureView.ScreenLightListener {

    private LightGestureView mGestureView;
    private int screenLight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_gesture);

        mGestureView = findViewById(R.id.gestureView);
        mGestureView.setScreenLightListener(this);

        screenLight = getSystemLight();
        //132
//        Log.i("LightGestureActivity", "++++++++screenLight=" + screenLight);

    }

    private int getSystemLight() {
        try {
            return Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 255);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void onScreenLightChanged(int dy) {
//        Log.i("LightGestureActivity", "++++++++++dy=" + dy);
        if (dy > 0) {
            //向下滑动，亮度减少
            screenLight -= Math.abs(dy);
            if (screenLight <= 10) {
                screenLight = 10;
            }
            setScreenLight(screenLight);
        } else if (dy < 0) {
            //向上滑动，亮度增加
            screenLight += Math.abs(dy);
            if (screenLight >= 255) {
                screenLight = 255;
            }
            setScreenLight(screenLight);
        }
    }

    private void setScreenLight(int light) {
        Window window = getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.screenBrightness = light / 255.0f;
        window.setAttributes(layoutParams);
    }
}