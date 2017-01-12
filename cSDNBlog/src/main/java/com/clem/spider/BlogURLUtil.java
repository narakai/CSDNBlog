package com.clem.spider;

public class BlogURLUtil {

	public static String generateBlogMainUrl(String url, int currentPage) {
		currentPage = currentPage > 0 ? currentPage : 1;
		String mainurl = url + "/article/list/" + currentPage +"/?viewmode=contents";
		return mainurl;
	}

}