package com.example.user.offplayer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

// 인트로 화면 java
public class Intro extends Activity{

    // 핸들러 사용
    Handler handler = new Handler();
    Runnable r = new Runnable() {
        @Override
        public void run() {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            // 인트로 종료
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
        // 밀리초간 화면 실행
        handler.postDelayed(r, 1800);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 예약 취소
        handler.removeCallbacks(r);
    }
}
