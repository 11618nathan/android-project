package example.whdghks913.tistory.floatingactionbutton;

import whdghks913.tistory.floatingactionbutton.FloatingActionButton;
import whdghks913.tistory.floatingactionbutton.FloatingScrollView;
import whdghks913.tistory.floatingactionbutton.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Toast;

public class ScrollViewActivity extends Activity {
	private FloatingActionButton mFloatingButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scrollview);

		/**
		 * FloatingActionButton를 설정하는 코드
		 */
		FloatingScrollView mScrollView = (FloatingScrollView) findViewById(R.id.mScrollView);

		mFloatingButton = (FloatingActionButton) findViewById(R.id.mFloatingActionButton);
		mFloatingButton.attachToScrollView(mScrollView);
		mFloatingButton.setAlwaysOnTop(true);
		mFloatingButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(),
						"FloatingButton Clicked", Toast.LENGTH_SHORT).show();
			}
		});
		mFloatingButton.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				Toast.makeText(getApplicationContext(),
						"FloatingButton Long Clicked", Toast.LENGTH_SHORT)
						.show();
				return true;
			}
		});
	}

}
