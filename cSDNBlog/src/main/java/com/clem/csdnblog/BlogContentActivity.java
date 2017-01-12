package com.clem.csdnblog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.maxwin.view.IXListViewLoadMore;
import me.maxwin.view.IXListViewRefreshListener;
import me.maxwin.view.XListView;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.clem.dao.MyFavDao;
import com.clem.spider.BlogURLUtil;
import com.clem.spider.CommonException;
import com.clem.spider.MyFav;
import com.clem.spider.PersonalBlog;
import com.clem.spider.PersonalBlogBiz;
import com.clem.util.ToastUtil;

public class BlogContentActivity extends Activity implements
		IXListViewLoadMore, IXListViewRefreshListener {
	private String url;
	private String author;
	private String title;
	private XListView mBXListView;
	private MyFavDao mMyFavDao;
	private MyFav mMyFav;

	private PersonalBlogBiz mPersonanBlogBiz;
	private ProgressBar mProgressBar;
	private BlogContentAdapter mAdapter;
	private int currentPage = 1;
	private List<PersonalBlog> mPersonalBlogs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.blogs_item_fragment);
		mPersonalBlogs = new ArrayList<PersonalBlog>();
		mAdapter = new BlogContentAdapter(this, mPersonalBlogs);
		mPersonanBlogBiz = new PersonalBlogBiz();

		Bundle extras = getIntent().getExtras();
		url = BlogURLUtil.generateBlogMainUrl(extras.getString("blogurl"),
				currentPage);
		author = extras.getString("author");
		Log.i("URL is", url);

		mProgressBar = (ProgressBar) findViewById(R.id.id_newsContentPro2);
		mBXListView = (XListView) findViewById(R.id.id_blogxlistView);
		mBXListView.setAdapter(mAdapter);

		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
			registerForContextMenu(mBXListView);
		} else {
			mBXListView.setChoiceMode(XListView.CHOICE_MODE_MULTIPLE_MODAL);
			mBXListView
					.setMultiChoiceModeListener(new MultiChoiceModeListener() {

						@Override
						public boolean onCreateActionMode(ActionMode mode,
								Menu menu) {
							MenuInflater inflater = mode.getMenuInflater();
							inflater.inflate(R.menu.menu_item_context, menu);
							return true;
						}

						@Override
						public boolean onPrepareActionMode(ActionMode mode,
								Menu menu) {
							// TODO Auto-generated method stub
							return false;
						}

						@Override
						public boolean onActionItemClicked(ActionMode mode,
								MenuItem item) {
							mMyFavDao = new MyFavDao(getApplicationContext());
							int index = 0;
							switch (item.getItemId()) {
							case R.id.menu_item_collection:
								for (int i = mAdapter.getCount() - 1; i >= 0; i--) {
									if (mBXListView.isItemChecked(i)) {
//										ToastUtil.toast(getApplicationContext(), i + "Checked");
										mMyFav = new MyFav();
										PersonalBlog personalBlog = (PersonalBlog) mAdapter.getItem(i);
										mMyFav.setFavAuthor(author);
//										Log.i("author", author);
										mMyFav.setFavTitle(personalBlog.getBlogTitle());
//										Log.i("Title", personalBlog.getBlogTitle());
										mMyFav.setFavLink(personalBlog.getBlogLink());
//										Log.i("Link", personalBlog.getBlogLink());
										mMyFavDao.add(mMyFav);
										index ++;
									}
								}
								mode.finish();
								mAdapter.notifyDataSetChanged();
								ToastUtil.toast(getApplicationContext(), index + " article(s) added to local");
								return true;
							default:
								return false;
							}
						}

						@Override
						public void onDestroyActionMode(ActionMode mode) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onItemCheckedStateChanged(ActionMode mode,
								int position, long id, boolean checked) {
							// TODO Auto-generated method stub

						}

					});
		}

		mBXListView.disablePullRefreash();
		mBXListView.setPullLoadEnable(this);
		mBXListView.NotRefreshAtBegin();

		mBXListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
//				view.setSelected(true);
				PersonalBlog personalBlog = mPersonalBlogs.get(position - 1);
				Intent intent = new Intent(getApplicationContext(),
						NewsContentActivity.class);
				intent.putExtra("url", personalBlog.getBlogLink());
				intent.putExtra("author", author);
				intent.putExtra("title", personalBlog.getBlogTitle());
				startActivity(intent);
			}
		});

		mProgressBar.setVisibility(View.VISIBLE);
		new LoadDataTask().execute();
	}

	class LoadDataTask extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			try {
				try {
					mPersonalBlogs = mPersonanBlogBiz.getBlogLists(url,
							currentPage);
					Log.i("mPer loading is", mPersonalBlogs.size() + "");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
			if (mPersonalBlogs == null)
				return;
			mAdapter.setDatas(mPersonalBlogs);
			Log.i("mPer is", mPersonalBlogs.size() + "");
			mAdapter.notifyDataSetChanged();
			mBXListView.stopRefresh();
			mBXListView.stopLoadMore();
			mProgressBar.setVisibility(View.GONE);
		}

	}

	@Override
	public void onRefresh() {
		new LoadDataTask().execute();
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		currentPage += 1;
		new LoadDataTask().execute();
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		getMenuInflater().inflate(R.menu.menu_item_context, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		mMyFavDao = new MyFavDao(getApplicationContext());
		mMyFav = new MyFav();
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		int position = info.position;
		PersonalBlog personalBlog = (PersonalBlog) mAdapter.getItem(position);

		mMyFav.setFavAuthor(author);
		mMyFav.setFavTitle(personalBlog.getBlogTitle());
		mMyFav.setFavLink(personalBlog.getBlogLink());

		switch (item.getItemId()) {
		case R.id.menu_item_collection:
			mMyFavDao.add(mMyFav);
			mAdapter.notifyDataSetChanged();
			return true;

		}

		return super.onContextItemSelected(item);
	}

}
