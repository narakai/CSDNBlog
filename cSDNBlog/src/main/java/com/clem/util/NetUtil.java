package com.clem.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


/**
 * �жϵ�ǰ�ֻ�����������
 * 
 * @author Administrator
 * 
 */

public class NetUtil {
	/**
	 * ��鵱ǰ�ֻ�����
	 * 
	 * @param context
	 * @return
	 */
	public static boolean checkNet(Context context){
		// �ж����ӷ�ʽ
		boolean wifiConnected = isWIFIConnected(context);
		boolean mobileConnectd = isMOBILEConnected(context);
		
		if(wifiConnected == false && mobileConnectd == false){
			return false;
		}
		return true;
	}
	
	
	// �ж��ֻ�ʹ����wifi����mobile
	public static boolean isWIFIConnected(Context context){
		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if(networkInfo != null && networkInfo.isConnected()){
			return true;
		}
		return false;
	}
	
	public static boolean isMOBILEConnected(Context context){
		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if(networkInfo != null && networkInfo.isConnected()){
			return true;
		}
		return false;
	}
}
