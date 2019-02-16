package example.whdghks913.tistory.floatingactionbutton;

import whdghks913.tistory.floatingactionbutton.FloatingActionButton;
import whdghks913.tistory.floatingactionbutton.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ListViewActivity extends Activity {
	private FloatingActionButton mFloatingButton;
	private String[] items;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listview);

		/**
		 * 리스트뷰에 표시할 item을 생성하는 코드
		 */
		items = new String[31];
		for (int index = 0; index <= 30; index++) {
			items[index] = "리스트 " + index;
		}

		/**
		 * 리스트뷰를 설정하는 코드
		 */
		ListView mListView = (ListView) findViewById(R.id.mListView);
		mListView.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, items));

		/**
		 * FloatingActionButton를 설정하는 코드
		 */
		mFloatingButton = (FloatingActionButton) findViewById(R.id.mFloatingActionButton);
		mFloatingButton.attachToListView(mListView);
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

		/**
		 * mFloatingButton.setImageResource(resId)
		 * mFloatingButton.setType(type)
		 * mFloatingButton.hide()
		 * mFloatingButton.hide(animate)
		 * mFloatingButton.show()
		 * mFloatingButton.show(animate)
		 * 
		 * mFloatingButton.attachToListView(listView)
		 * mFloatingButton.attachToScrollView(mScrollView)
		 * mFloatingButton.setShadow(shadow)
		 * mFloatingButton.setColorNormal(color)
		 * mFloatingButton.setColorNormalResId(colorResId)
		 * mFloatingButton.setColorPressed(color)
		 * mFloatingButton.setColorPressedResId(colorResId)
		 * mFloatingButton.setAnimationEnable(setAnimation)
		 * mFloatingButton.setAlwaysOnTop(alwaysOnTop)
		 * 
		 * mFloatingButton.getColorNormal()
		 * mFloatingButton.getColorPressed()
		 * mFloatingButton.getIsAnimation()
		 * mFloatingButton.hasShadow()
		 */
	}

}
