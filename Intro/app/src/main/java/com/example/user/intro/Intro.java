package com.example.user.intro;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Intro extends Activity{
    Handler handler = new Handler();
    Runnable r = new Runnable() {
        @Override
        public void run() {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro);
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(r, 3000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(r); // 예약 취소
    }
}
