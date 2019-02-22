package com.html.app;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.html.net.Net_HTMLParse;

public class HTML_TestActivity extends Activity {
	

	private ArrayList<HashMap<String, String>> data;
	private Button bt_open;
	private ListView list;
	private SimpleAdapter sa;
	private Net_HTMLParse hp;
	
	private final Handler handler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			listUpdate();
		}
	};
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        data = new ArrayList<HashMap<String, String>>();
        
        bt_open = (Button)findViewById(R.id.bt_open);
        list = (ListView)findViewById(R.id.listView1);
        
        hp = new Net_HTMLParse(HTML_TestActivity.this, handler, data);
        
        sa = new SimpleAdapter(HTML_TestActivity.this, data, R.layout.list_row,
        		new String[]{"title","writer","subject"}, new int[]{R.id.tv_title, R.id.tv_writer, R.id.tv_subject});
        
        bt_open.setOnClickListener(listener);
        list.setAdapter(sa);
        
    }
    
    //버튼 리스너
    OnClickListener listener = new OnClickListener(){

		@Override
		public void onClick(View arg0)
		{
			hp.open();
		}
    	
    };
    
    //업데이트하기
    private void listUpdate()
    {
    	sa.notifyDataSetChanged();
    }
}