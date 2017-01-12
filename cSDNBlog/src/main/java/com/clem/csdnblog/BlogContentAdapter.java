package com.clem.csdnblog;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.clem.spider.PersonalBlog;


public class BlogContentAdapter extends BaseAdapter {

	private List<PersonalBlog> mPersonalBlogs;
	private LayoutInflater mInflater;

	
	public BlogContentAdapter(Context context, List<PersonalBlog> blogs) {
		this.mPersonalBlogs = blogs;
		mInflater = LayoutInflater.from(context);
	}

	public void addAll(List<PersonalBlog> mDatas) {
		this.mPersonalBlogs.addAll(mDatas);
	}
	
	public void setDatas(List<PersonalBlog> mDatas)
	{
		this.mPersonalBlogs.clear();
		this.mPersonalBlogs.addAll(mDatas);
	}
	
	
	@Override
	public int getCount() {
		return mPersonalBlogs.size();
	}

	@Override
	public Object getItem(int position) {
		return mPersonalBlogs.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		PersonalBlog blog = mPersonalBlogs.get(position);
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.blogs_item, null);
			holder = new ViewHolder();

			holder.mList = (TextView) convertView
					.findViewById(R.id.id_bloglist);
			holder.mDate = (TextView) convertView.findViewById(R.id.id_blogdate);

			//use tag to reuse view
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag(); 
		}
		
		
		
		holder.mList.setText(blog.getBlogTitle());
		holder.mDate.setText(blog.getBlogTime());
		
		return convertView;
	}
	
	private final class ViewHolder {
		TextView mList;
		TextView mDate;
	}

}
