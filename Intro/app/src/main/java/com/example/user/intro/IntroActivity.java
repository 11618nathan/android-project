package com.example.user.intro;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import java.util.Timer;
import java.util.TimerTask;

public class IntroActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_intro);

        TimerTask myTask = new TimerTask() {
            public void run() {
                Intent mainIntent = new Intent(IntroActivity.this, MainActivity.class);

                startActivity(mainIntent);
                finish();   //현재 액티비티는 종료
            }
        };

        //타이머 설정
        Timer timer = new Timer();
        timer.schedule(myTask, 3000); //3초후 실행
    }
}
