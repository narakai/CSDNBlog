package com.clem.csdnblog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.maxwin.view.IXListViewLoadMore;
import me.maxwin.view.IXListViewRefreshListener;
import me.maxwin.view.XListView;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.clem.dao.NewsItemDao;
import com.clem.spider.CommonException;
import com.clem.spider.Constaint;
import com.clem.spider.NewsItem;
import com.clem.spider.NewsItemBiz;
import com.clem.util.AppUtil;
import com.clem.util.NetUtil;
import com.clem.util.ToastUtil;

public class MainFragment extends LazyFragment implements
		IXListViewRefreshListener, IXListViewLoadMore {
	private static final int LOAD_MORE = 0x110;
	private static final int LOAD_REFRESH = 0x111;

	private static final int TIP_ERROR_NO_NETWORK = 0X112;
	private static final int TIP_ERROR_SERVER = 0X113;

	private Time mTime;
	/**
	 * 是否是第一次进入
	 */
	private boolean isFirstIn = true;
	/**
	 * 是否连接网络
	 */
	private boolean isConnNet = false;

	/**
	 * 当前数据是否是从网络中获取的
	 */
	private boolean isLoadingDataFromNetWork;

	/**
	 * 默认的newType
	 */
	private int mNewsType = Constaint.NEWS_TYPE_YIDONG;
	/**
	 * 当前页面
	 */
	private int currentPage = 1;
	/**
	 * 处理新闻的业务类
	 */
	private NewsItemBiz mNewsItemBiz;

	/**
	 * 与数据库交互
	 */
	private NewsItemDao mNewsItemDao;

	/**
	 * 扩展的ListView
	 */
	private XListView mXListView;
	/**
	 * 数据适配器
	 */
	private NewsItemAdapter mAdapter;
	/**
	 * 数据
	 */
	private List<NewsItem> mDatas = new ArrayList<NewsItem>();
	private boolean isPrepared;

	/**
	 * 获得newType
	 * 
	 * @param newsType
	 */
	public MainFragment(int newsType) {
		mNewsType = newsType;
		mNewsItemBiz = new NewsItemBiz();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mNewsItemDao = new NewsItemDao(getActivity());
		mAdapter = new NewsItemAdapter(getActivity(), mDatas);

		mXListView = (XListView) getView().findViewById(R.id.id_xlistView);
		mXListView.setAdapter(mAdapter);
		mXListView.setPullRefreshEnable(this);
		mXListView.setPullLoadEnable(this);
		mXListView.setRefreshTime(AppUtil.getRefreshTime(getActivity(),
				mNewsType));

		isPrepared = true;
		
		if (isFirstIn == false) {
			mXListView.NotRefreshAtBegin();
		}

		
		lazyLoad();
	
		mXListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				NewsItem newsItem = mDatas.get(position - 1);
				Intent intent = new Intent(getActivity(),
						NewsContentActivity.class);
				intent.putExtra("url", newsItem.getLink());
				intent.putExtra("title", newsItem.getTitle());
				intent.putExtra("author", newsItem.getAuthor());
				// String ttt = newsItem.getLink();
				startActivity(intent);
			}

		});

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.tab_item_fragment_main,
				container, false);

		return view;

	}

	@Override
	public void onRefresh() {
		new LoadDataTask().execute(LOAD_REFRESH);
	}

	@Override
	public void onLoadMore() {
		new LoadDataTask().execute(LOAD_MORE);
	}

	/**
	 * 记载数据的异步任务
	 * 
	 */

	class LoadDataTask extends AsyncTask<Integer, Void, Integer> {
		// //后面尖括号内分别是参数（线程休息时间），进度(publishProgress用到)，返回值 类型
		@Override
		protected Integer doInBackground(Integer... params) {
			switch (params[0]) {
			case LOAD_MORE:
				try {
					loadMoreData();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case LOAD_REFRESH:
				try {
					return refreshData();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return -1;
		}

		@Override
		protected void onPostExecute(Integer result) {
			switch (result) {
			case TIP_ERROR_NO_NETWORK:
				ToastUtil.toast(getActivity(), "No network connection!");
				mAdapter.setDatas(mDatas);
				mAdapter.notifyDataSetChanged();
				break;
			case TIP_ERROR_SERVER:
				ToastUtil.toast(getActivity(), "Server error!");
				break;
			default:
				// ToastUtil.toast(getActivity(), "Loaded!");
				break;
			}

			mXListView.stopRefresh();
			mXListView.stopLoadMore();

			mXListView.setRefreshTime(AppUtil.getRefreshTime(getActivity(),
					mNewsType));
		}

	}

	/**
	 * 会根据当前网络情况，判断是从数据库加载还是从网络继续获取
	 * 
	 * @throws IOException
	 */

	public void loadMoreData() throws IOException {
		// 当前数据是从网络获取的
		if (isLoadingDataFromNetWork) {
			currentPage += 1;
			try {
				List<NewsItem> newsItems = mNewsItemBiz.getNewsItems(mNewsType,
						currentPage);
				mNewsItemDao.add(newsItems);
				mAdapter.addAll(newsItems);
			} catch (CommonException e) {
				e.printStackTrace();
			}
		} else {
			// 从数据库加载
			currentPage += 1;
			List<NewsItem> newsItems = mNewsItemDao
					.list(mNewsType, currentPage);
			mAdapter.addAll(newsItems);
		}
	}

	/**
	 * 下拉刷新数据
	 * 
	 * @throws IOException
	 */
	public Integer refreshData() throws IOException {
		if (NetUtil.checkNet(getActivity())) {
			isConnNet = true;
			// 获取最新数据
			try {
				List<NewsItem> newsItems = mNewsItemBiz.getNewsItems(mNewsType,
						currentPage);
				mAdapter.setDatas(newsItems);
				isLoadingDataFromNetWork = true;
				// 设置刷新时间
				AppUtil.setRefreashTime(getActivity(), mNewsType);
				// 清除数据库数据
				// mNewsItemDao.deleteAll(mNewsType);
				// 存入数据库
				mNewsItemDao.add(newsItems);
			} catch (CommonException e) {
				e.printStackTrace();
				isLoadingDataFromNetWork = false;
				return TIP_ERROR_SERVER;
			}

		} else {
			isConnNet = false;
			isLoadingDataFromNetWork = false;
			// 从数据库中加载
			List<NewsItem> newsItems = mNewsItemDao
					.list(mNewsType, currentPage);
			mDatas = newsItems;
			return TIP_ERROR_NO_NETWORK;
		}
		return -1;
	}

	@Override
	protected void lazyLoad() {

		if (!isPrepared || !isVisible) {
			return;
		}

		if (isFirstIn) {
			/**
			 * 进来时直接刷新
			 */
			mXListView.startRefresh();
			isFirstIn = false;
		} else {
			mXListView.NotRefreshAtBegin();

			Log.i("not first in", "not first in");
		}
	}

}
