package com.clem.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.text.TextUtils;

public class AppUtil {
	/**
	 * �����������ͻ�ȡ�ϴθ��µ�ʱ��
	 * 
	 * @param newType
	 * @return
	 */
	public static String getRefreshTime(Context context, int newType){
		String timeStr = PreferenceUtil.readString(context, "NEWS_" + newType);
		if(TextUtils.isEmpty(timeStr)){
			return "���ϴμ�¼...";
		}
		return timeStr;
	}
	
	/**
	 * �����������������ϴθ��µ�ʱ��
	 * 
	 * @param newType
	 * @return
	 */
	public static void setRefreashTime(Context context, int newType){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		PreferenceUtil.write(context, "NEWS_" + newType, df.format(new Date()));
	}

}
