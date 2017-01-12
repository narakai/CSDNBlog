package com.clem.csdnblog;

import java.util.ArrayList;
import java.util.List;

import me.maxwin.view.IXListViewLoadMore;
import me.maxwin.view.XListView;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ProgressBar;

import com.clem.dao.MyFavDao;
import com.clem.spider.MyFav;
import com.clem.util.ToastUtil;

public class MyFavActivity extends Activity implements IXListViewLoadMore {

	private ProgressBar mProgressBar;
	private MyFavAdapter mAdapter;
	private XListView mListView;
	private MyFavDao mMyFavDao;
	private MyFav mMyFav;
	private int currentPage = 1;

	List<MyFav> favList = new ArrayList<MyFav>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fav_item_fragment);
		// set adapter to view
		mAdapter = new MyFavAdapter(this, favList);
		// get data from SQLite
		mMyFavDao = new MyFavDao(this);
		favList = mMyFavDao.listFav(currentPage);
		// set the data to adapter
		mAdapter.addList(favList);
		Log.i("list size is", favList.size() + "");
		// notify changes
		mAdapter.notifyDataSetChanged();

		mListView = (XListView) findViewById(R.id.id_favxlistView);
		mListView.setAdapter(mAdapter);
		mListView.NotRefreshAtBegin();
		
		if(favList.size() == 0){
			ToastUtil.toast(getApplicationContext(), "No article added to local");
		}
		
		// mListView.disablePullRefreash();
		// mListView.disablePullLoad();
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
			registerForContextMenu(mListView);
		} else {
			mListView.setChoiceMode(XListView.CHOICE_MODE_MULTIPLE_MODAL);
			mListView.setMultiChoiceModeListener(new MultiChoiceModeListener() {

				@Override
				public boolean onCreateActionMode(ActionMode mode, Menu menu) {
					MenuInflater inflater = mode.getMenuInflater();
					inflater.inflate(R.menu.menu_item_fav, menu);
					return true;
				}

				@Override
				public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
					// TODO Auto-generated method stub
					return false;
				}

				@Override
				public boolean onActionItemClicked(ActionMode mode,
						MenuItem item) {
					mMyFavDao = new MyFavDao(getApplicationContext());
					int index = 0;
					switch (item.getItemId()) {
					case R.id.menu_item_delete:
						for (int i = mAdapter.getCount() ; i >= 0; i--) {
							if (mListView.isItemChecked(i)) {
								mMyFav = (MyFav) mAdapter.getItem(i - 1);
								mMyFavDao.delete(mMyFav);
								mAdapter.remove(i - 1);
								index++;
							}
						}
						mode.finish();
						mAdapter.notifyDataSetChanged();
						ToastUtil.toast(getApplicationContext(), index
								+ " article(s) deleted");
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

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				MyFav myFav = favList.get(position - 1);
				Intent intent = new Intent(getApplicationContext(),
						FavNewsContentActivity.class);
				intent.putExtra("url", myFav.getFavLink());
				finish();
				startActivity(intent);
			}

		});

	}

	@Override
	public void onLoadMore() {
		// currentPage += 1;
		// mMyFavDao.listFav(currentPage);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		getMenuInflater().inflate(R.menu.menu_item_fav, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		mMyFavDao = new MyFavDao(getApplicationContext());
		mMyFav = new MyFav();
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		int position = info.position;
		mMyFav = (MyFav) mAdapter.getItem(position - 1);

		switch (item.getItemId()) {
		case R.id.menu_item_delete:
			// delete from database
			mMyFavDao.delete(mMyFav);
			// delete from adapter so it will show correctly
			mAdapter.remove(position - 1);
			mAdapter.notifyDataSetChanged();
			ToastUtil.toast(getApplicationContext(), "Article deleted");
			return true;
		}

		return super.onContextItemSelected(item);
	}
}
