package com.clem.csdnblog;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ActionMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.clem.dao.MyFavDao;
import com.clem.spider.MyFav;
import com.clem.util.ToastUtil;
import com.viewpagerindicator.TabPageIndicator;

public class MainActivity extends FragmentActivity{
	
	private TabPageIndicator mIndicatoer;
	private ViewPager mViewPager;
	private FragmentPagerAdapter mAdapter;
	private ImageView mImageView;
	private ImageView mAboutImageView;
	
//	private Button mAddButton;
	
	@Override
	protected void onCreate(Bundle savedInstance) {
		super.onCreate(savedInstance);
		setContentView(R.layout.activity_main);
		
		
		mIndicatoer = (TabPageIndicator) findViewById(R.id.id_indicator);
		mViewPager = (ViewPager) findViewById(R.id.id_pager);
		mAdapter = new TabAdapter(getSupportFragmentManager());
		mViewPager.setAdapter(mAdapter);
		mIndicatoer.setViewPager(mViewPager, 0);
		
		mAboutImageView = (ImageView) findViewById(R.id.about);
		mAboutImageView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ToastUtil.toast(getApplicationContext(), "Powered by Leon Lai @ narakai.lai@gmail.com");
			}
		});
		
		mImageView = (ImageView) findViewById(R.id.myFav);
		mImageView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						MyFavActivity.class);
				startActivity(intent);
			}
		});
		
//		mIndicatoer.setOnPageChangeListener(new OnPageChangeListener(){
//
//			@Override
//			public void onPageScrollStateChanged(int arg0) {
//				// TODO Auto-generated method stub
//				ToastUtil.toast(getApplicationContext(), "StateChanged");
//			}
//
//			@Override
//			public void onPageScrolled(int arg0, float arg1, int arg2) {
//				// TODO Auto-generated method stub
//				ToastUtil.toast(getApplicationContext(), "PageScrolled");
//			}
//
//			@Override
//			public void onPageSelected(int arg0) {
//				// TODO Auto-generated method stub
//				ToastUtil.toast(getApplicationContext(), "PageSelected");
//			}
//			
//		});
		
		
//		mAddButton = (Button) findViewById(R.id.collect_button);
		
//		mAddButton.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				Toast.makeText(getApplicationContext(), "clicked", Toast.LENGTH_SHORT).show();
//				
//			}
//		});
		
	}

	
}
