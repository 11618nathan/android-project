package com.example.user.javaxml;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_main);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT );
        LinearLayout baseLayout = new LinearLayout(this);
        baseLayout.setOrientation(LinearLayout.VERTICAL);



        LinearLayout lay1 = new LinearLayout(this);
        LinearLayout.LayoutParams la = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,1
        );


        LinearLayout lay2 = new LinearLayout(this);
        lay2.setOrientation(LinearLayout.VERTICAL);
        lay2.setBackgroundColor(Color.RED);
        LinearLayout.LayoutParams la2 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,1
        );

        LinearLayout lay3 = new LinearLayout(this);
        lay3.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams la3 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,1
        );


        LinearLayout lay4 = new LinearLayout(this);
        lay4.setOrientation(LinearLayout.VERTICAL);
        lay4.setBackgroundColor(Color.YELLOW);
        LinearLayout.LayoutParams la4 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,1
        );

        LinearLayout lay5 = new LinearLayout(this);
        lay5.setOrientation(LinearLayout.VERTICAL);
        lay5.setBackgroundColor(Color.BLACK);
        LinearLayout.LayoutParams la5 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,1
        );


        LinearLayout lay6 = new LinearLayout(this);
        lay6.setOrientation(LinearLayout.HORIZONTAL );
        lay6.setBackgroundColor(Color.BLUE);
        LinearLayout.LayoutParams la6 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,1
        );


        baseLayout.addView(lay1, la);

        lay3.addView(lay4, la4);
        lay3.addView(lay5, la5);

        lay1.addView(lay2, la2);
        lay1.addView(lay3, la3);



        baseLayout.addView(lay6, la6);

        setContentView(baseLayout, params);

    }
}
