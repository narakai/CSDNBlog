package com.clem.csdnblog;

import java.util.List;

import me.maxwin.view.IXListViewLoadMore;
import me.maxwin.view.XListView;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.clem.dao.MyFavDao;
import com.clem.spider.CommonException;
import com.clem.spider.MyFav;
import com.clem.spider.News;
import com.clem.spider.NewsBiz;
import com.clem.spider.PersonalBlog;
import com.clem.util.ToastUtil;

public class FavNewsContentActivity extends Activity implements IXListViewLoadMore {
	
	private XListView mListView;

	/**
	 * ??????url
	 */
	private String url;
	private ProgressBar mProgressBar;
	private NewContentAdapter mAdapter;
	private NewsBiz mNewsBiz;
	private List<News> mDatas;
	private ImageView mBackButton;

	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.favnews_content);

		mNewsBiz = new NewsBiz();
		
		Bundle extras = getIntent().getExtras();
		url = extras.getString("url");
		Log.i("fav url", url);
		
		mAdapter = new NewContentAdapter(this);
		mBackButton = (ImageView) findViewById(R.id.favbackBtn);
		mListView = (XListView) findViewById(R.id.id_favlistview);
		mProgressBar = (ProgressBar) findViewById(R.id.id_favnewsContentPro);

		mListView.setAdapter(mAdapter);
		mListView.disablePullRefreash();
		mListView.disablePullLoad();
		
		
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				News news = mDatas.get(position - 1);
				String imageLink = news.getImageLink();
				// Toast.makeText(NewContentActivity.this, imageLink, 1).show();
				Intent intent = new Intent(FavNewsContentActivity.this,
						ImageShowActivity.class);
				intent.putExtra("url", imageLink);
				startActivity(intent);
			}
		});
		
		mBackButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(FavNewsContentActivity.this, MyFavActivity.class);
				finish();
				startActivity(intent); 
			}
		});
		
		new LoadDataTask().execute();
	}



	@Override
	public void onLoadMore() {
//		ToastUtil.toast(getApplicationContext(), "??и???????");
	}

	class LoadDataTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			try {
				mDatas = mNewsBiz.getNews(url).getNewsList();
			} catch (CommonException e) {
				Looper.prepare();
				Toast.makeText(getApplicationContext(), e.getMessage(), 1)
						.show();
				Looper.loop();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			if (mDatas == null)
				return;
			mAdapter.addList(mDatas);
			mAdapter.notifyDataSetChanged();
			mProgressBar.setVisibility(View.GONE);
		}

	}
	
}
