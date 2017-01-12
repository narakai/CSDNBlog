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
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.clem.dao.MyFavDao;
import com.clem.spider.CommonException;
import com.clem.spider.MyFav;
import com.clem.spider.News;
import com.clem.spider.NewsBiz;
import com.clem.util.ToastUtil;


public class NewsContentActivity extends Activity implements IXListViewLoadMore {

	private XListView mListView;
	private ImageView mImageView;
	private ImageView mBackButton;
	private ImageView mAddView;
	/**
	 * 该页面的url
	 */
	private String url;
	private String author;
	private String title;
	private NewsBiz mNewsBiz;
	private List<News> mDatas;
	private ProgressBar mProgressBar;
	private NewContentAdapter mAdapter;
	private MyFavDao myFavDao;
	private MyFav mMyFav;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.news_content);

		mNewsBiz = new NewsBiz();

		Bundle extras = getIntent().getExtras();
		url = extras.getString("url");
		author = extras.getString("author");
		title = extras.getString("title");
		
		mAdapter = new NewContentAdapter(this);

		mListView = (XListView) findViewById(R.id.id_listview);
		mImageView = (ImageView) findViewById(R.id.more);
		mAddView = (ImageView) findViewById(R.id.add);
		mBackButton = (ImageView) findViewById(R.id.backBtn);
		mProgressBar = (ProgressBar) findViewById(R.id.id_newsContentPro);

		mListView.setAdapter(mAdapter);
		mListView.disablePullRefreash();
		mListView.disablePullLoad();
//		mListView.setPullLoadEnable(this);
		
//		//参考下面ActionMode.Callback
//		mListView.setOnItemLongClickListener(new OnItemLongClickListener() {
//
//	        @Override
//	        public boolean onItemLongClick(AdapterView<?> parent, View view,
//	                int postion, long id) {
//	        	
//	        	if (mActionMode != null) {
//	                return false;
//	            }
//	        	
//	        	 // Start the CAB using the ActionMode.Callback defined above
//	            mActionMode = startActionMode(mActionModeCallback);
//	            view.setSelected(true);
//	            return true;
//	        }
//	    });
		
		mAddView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				mMyFav = new MyFav();
				myFavDao = new MyFavDao(getApplicationContext());
				
				String favTitle = title;
				String favUrl = url;
				String favAuthor = author;
				mMyFav.setFavLink(favUrl);
				mMyFav.setFavTitle(favTitle);
				mMyFav.setFavAuthor(favAuthor);
//				List<MyFav> myFavs = new ArrayList<MyFav>();
//				myFavs.add(mMyFav);
				
				myFavDao.add(mMyFav);
				Log.i("author is", favAuthor);
				Log.i("title is", favTitle);
				Log.i("url is", favUrl);
				
				ToastUtil.toast(getApplicationContext(), "Article added to local");
				
			}
		});

		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				News news = mDatas.get(position - 1);
				String imageLink = news.getImageLink();
				// Toast.makeText(NewContentActivity.this, imageLink, 1).show();
				Intent intent = new Intent(NewsContentActivity.this,
						ImageShowActivity.class);
				intent.putExtra("url", imageLink);
				startActivity(intent);
			}
		});
		
		

		mImageView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
//				if (author != "") {
//					Intent intent = new Intent(NewsContentActivity.this,
//							BlogContentActivity.class);
//					intent.putExtra("blogurl", "http://blog.csdn.net/" + author);
//					finish();
//					startActivity(intent);
//				}else{
//					Bundle extras = getIntent().getExtras();
//					url = extras.getString("url");
//					author = extras.getString("author");
//					title = extras.getString("title");
//					Intent intent = new Intent(NewsContentActivity.this,
//							BlogContentActivity.class);
//					intent.putExtra("author", author);
//					intent.putExtra("url", url);
//					intent.putExtra("title", title);
//					intent.putExtra("blogurl", "http://blog.csdn.net/" + author);
//					finish();
//					startActivity(intent);
//				}
				Intent intent = new Intent(NewsContentActivity.this,
						BlogContentActivity.class);
				intent.putExtra("author", author);
				intent.putExtra("url", url);
				intent.putExtra("title", title);
				intent.putExtra("blogurl", "http://blog.csdn.net/" + author);
				finish();
				startActivity(intent);	
			}
		});
		
		mBackButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});

		new LoadDataTask().execute();

	}



	
	@Override
	public void onLoadMore() {
//		ToastUtil.toast(getApplicationContext(), "没有更多数据");
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

	
//	private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {
//
//	    // Called when the action mode is created; startActionMode() was called
//	    @Override
//	    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
//	        // Inflate a menu resource providing context menu items
//	        MenuInflater inflater = mode.getMenuInflater();
//	        inflater.inflate(R.menu.menu_item_context, menu);
//	        return true;
//	    }
//
//	    // Called each time the action mode is shown. Always called after onCreateActionMode, but
//	    // may be called multiple times if the mode is invalidated.
//	    @Override
//	    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
//	        return false; // Return false if nothing is done
//	    }
//
//	    // Called when the user selects a contextual menu item
//	    @Override
//	    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
//	        switch (item.getItemId()) {
//	            case R.id.menu_item_collection:
//	                ToastUtil.toast(getApplicationContext(), "Good");
//	                mode.finish(); // Action picked, so close the CAB
//	                return true;
//	            default:
//	                return false;
//	        }
//	    }
//
//	    // Called when the user exits the action mode
//	    @Override
//	    public void onDestroyActionMode(ActionMode mode) {
//	        mActionMode = null;
//	    }
//	};
	


}
