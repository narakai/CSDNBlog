package com.clem.spider;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DataUtil {
	// 返回该链接地址的html数据
	public static String doGet(String urlStr) throws CommonException {
		StringBuffer sb = new StringBuffer();

		try {
			//open connection
			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5000);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			
			//read input stream
			if (conn.getResponseCode() == 200) {
				InputStream is = conn.getInputStream();
				InputStreamReader isr = new InputStreamReader(is, "UTF-8");
				int len = 0;
				char[] buf = new char[1024];

				while ((len = isr.read(buf)) != -1) {
					sb.append(new String(buf, 0, len));
				}

				is.close();
				isr.close();

			} else {
				throw new CommonException("访问网络失败!");
			}
		} catch (Exception e) {
			throw new CommonException("访问网络失败!");
		}
		
		return sb.toString();
	}

}
