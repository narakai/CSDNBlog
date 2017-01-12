package com.clem.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


/**
 * 判断当前手机联网的渠道
 * 
 * @author Administrator
 * 
 */

public class NetUtil {
	/**
	 * 检查当前手机网络
	 * 
	 * @param context
	 * @return
	 */
	public static boolean checkNet(Context context){
		// 判断连接方式
		boolean wifiConnected = isWIFIConnected(context);
		boolean mobileConnectd = isMOBILEConnected(context);
		
		if(wifiConnected == false && mobileConnectd == false){
			return false;
		}
		return true;
	}
	
	
	// 判断手机使用是wifi还是mobile
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
