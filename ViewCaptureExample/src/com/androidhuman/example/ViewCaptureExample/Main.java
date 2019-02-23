package com.androidhuman.example.ViewCaptureExample;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Main extends Activity implements OnClickListener{
    private LinearLayout container;
    private Button captureButton;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        container = (LinearLayout)findViewById(R.id.main_container);
        captureButton = (Button)findViewById(R.id.main_capture);
        captureButton.setOnClickListener(this);
    }

	@Override
	public void onClick(View v) {
		container.buildDrawingCache();
		Bitmap captureView = container.getDrawingCache();
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(Environment.getExternalStorageDirectory().toString()+"/capture.jpeg");
			captureView.compress(Bitmap.CompressFormat.JPEG, 100, fos);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Toast.makeText(getApplicationContext(), "Captured!", Toast.LENGTH_LONG).show();
	}
}