package com.html.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;

public class Net_HTMLParse
{
	private String url;
	private Context context;
	private Handler handler;
	private ProgressDialog progressDialog;
	private Source source;
	private ArrayList<HashMap<String, String>> data;
	
	public Net_HTMLParse(Context context, Handler handler, ArrayList<HashMap<String, String>> data)
	{
		this.context = context;
		this.handler = handler;
		this.data = data;
	}
	
	public void open()
	{
		//동신대학교 권장도서 100선
		url = "http://lib1.dsu.ac.kr/DLSearch/DLLocal/LOC/HTML/dsu/EncourageBook.html?MainMenuNo=275&InformYN=N";
		
		//처리하기
		try
		{
			process();
		}catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private void process() throws IOException
	{
		//상태 Progress 띄우기 위해서 사용함!
		final Handler mHandler = new Handler();
		new Thread()
		{

			@Override
			public void run()
			{
				URL nURL;
				try
				{
					nURL = new URL(url);
					mHandler.post(new Runnable(){

						@Override
						public void run()
						{
							progressDialog = ProgressDialog.show(context, "", "Data loding...");
						}
					});
					
					//모든 데이터 초기화
					data.clear();
					
					InputStream html = nURL.openStream();
					//가져오는 HTML의 인코딩형식
					source = new Source(new InputStreamReader(html, "EUC-KR"));
					
					//테이블가져오기
					Element table = (Element) source.getAllElements(HTMLElementName.TABLE).get(3);
					
					System.out.println("테이블 개수!!!!" + source.getAllElements(HTMLElementName.TABLE).size());
					
					//테이블 안의 TR 개수
					int tr_count = table.getAllElements(HTMLElementName.TR).size();
					
					Element tr = null;
					
					HashMap<String, String> hm = null;
					
					for(int i=1; i<tr_count; i++)
					{
						tr = (Element) table.getAllElements(HTMLElementName.TR).get(i);
						
						hm = new HashMap<String, String>();
						
						hm.put("no", ((Element) tr.getAllElements(HTMLElementName.TD).get(0)).getContent().toString());
						hm.put("title", ((Element) tr.getAllElements(HTMLElementName.TD).get(1)).getContent().toString());
						hm.put("writer", ((Element) tr.getAllElements(HTMLElementName.TD).get(2)).getContent().toString());
						hm.put("subject", ((Element) tr.getAllElements(HTMLElementName.TD).get(3)).getContent().toString());
						
						data.add(hm);
					}
					
					mHandler.post(new Runnable()
					{
						public void run()
						{
							progressDialog.cancel();
							//업데이트 완료를 핸들러로 보내줌
							handler.sendEmptyMessage(0);
						}
					});
				}catch (MalformedURLException e)
				{
					e.printStackTrace();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
				
			}
			
		}.start();
	}
}
