package com.example.user.e49;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {


    Button btn;
    ImageView imageView;
    int togle = 0;
    int rotation = 0;

    int rotationfloat = 0;

    int i = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button) findViewById(R.id.btn);
        imageView = (ImageView)findViewById(R.id.imageView);



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (i >= 0) {
                    imageView.setRotation(rotation + 10);
                    rotation = rotation +10;
                    i++;
                }
                else {

                }
            }
        });


    }
}
