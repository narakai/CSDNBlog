package com.clem.csdnblog;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.clem.spider.MyFav;

public class MyFavAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	List<MyFav> favList = new ArrayList<MyFav>();

	public MyFavAdapter(Context context, List<MyFav> datas) {
		mInflater = LayoutInflater.from(context);
		this.favList = datas;
	}

	public void addList(List<MyFav> datas) {
		favList.addAll(datas);
	}
	
	public void remove(int position){
		favList.remove(position);
	}

	@Override
	public int getCount() {
		return favList.size();
	}

	@Override
	public Object getItem(int position) {
		return favList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		MyFav myFav = favList.get(position);

		ViewHolder holder = null;
		if (null == convertView) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.fav_item, null);
			
			holder.mTextView = (TextView) convertView
					.findViewById(R.id.id_favlist);

			holder.mAuthorView = (TextView) convertView
					.findViewById(R.id.id_favauthor);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.mTextView.setText(myFav.getFavTitle());
		holder.mAuthorView.setText(myFav.getFavAuthor());
		Log.i("authorView", myFav.getFavAuthor());
		return convertView;
	}

	private final class ViewHolder {
		TextView mTextView;
		TextView mAuthorView;
	}

}
