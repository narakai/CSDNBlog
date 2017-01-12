package com.clem.spider;

public class URLUtil {

//	public static final String NEWS_TYPE_URL_SHOUYE = "http://blog.csdn.net";
	public static final String NEWS_TYPE_URL_YIDONG = "http://blog.csdn.net/mobile";
	public static final String NEWS_TYPE_URL_WEB = "http://blog.csdn.net/web";
	public static final String NEWS_TYPE_URL_JIAGOU = "http://blog.csdn.net/enterprise";
	public static final String NEWS_TYPE_URL_BIANCHENG = "http://blog.csdn.net/code";
	public static final String NEWS_TYPE_URL_HULIANWANG = "http://blog.csdn.net/www";
	public static final String NEWS_TYPE_URL_SHUJUKU = "http://blog.csdn.net/database";
	public static final String NEWS_TYPE_URL_YUNWEI = "http://blog.csdn.net/system";
	public static final String NEWS_TYPE_URL_YUNJISUAN = "http://blog.csdn.net/cloud";
	public static final String NEWS_TYPE_URL_YANFA  = "http://blog.csdn.net/software";
	public static final String NEWS_TYPE_URL_ZONGHE = "http://blog.csdn.net/other";
	
	// public static final String NEWS_TYPE_URL_GENGDUO = "";

	// 根据文章类型，和当前页码生成url
	public static String generateUrl(int newsType, int currentPage) {
		currentPage = currentPage > 0 ? currentPage : 1;
		String urlStr = "";

		switch (newsType) {
//		case Constaint.NEWS_TYPE_SHOUYE:
//			urlStr = NEWS_TYPE_URL_SHOUYE;
//			break;
//		case Constaint.NEWS_TYPE_YIDONG:
//			urlStr = NEWS_TYPE_URL_YIDONG;
//			break;
		case Constaint.NEWS_TYPE_WEB:
			urlStr = NEWS_TYPE_URL_WEB;
			break;
		case Constaint.NEWS_TYPE_JIAGOU:
			urlStr = NEWS_TYPE_URL_JIAGOU;
			break;
		case Constaint.NEWS_TYPE_BIANCHENG:
			urlStr = NEWS_TYPE_URL_BIANCHENG;
			break;
		case Constaint.NEWS_TYPE_HULIANWANG:
			urlStr = NEWS_TYPE_URL_HULIANWANG;
			break;
		case Constaint.NEWS_TYPE_SHUJUKU:
			urlStr = NEWS_TYPE_URL_SHUJUKU;
			break;
		case Constaint.NEWS_TYPE_YUNWEI:
			urlStr = NEWS_TYPE_URL_YUNWEI;
			break;
		case Constaint.NEWS_TYPE_YUNJISUAN:
			urlStr = NEWS_TYPE_URL_YUNJISUAN;
			break;
		case Constaint.NEWS_TYPE_YANFA:
			urlStr = NEWS_TYPE_URL_YANFA;
			break;
		case Constaint.NEWS_TYPE_ZONGHE:
			urlStr = NEWS_TYPE_URL_ZONGHE;
			break;
		default:
			urlStr = NEWS_TYPE_URL_YIDONG;
			break;
		}
		
		
		urlStr += "/index.html?&page=" + currentPage; 
		return urlStr;
	}

}
