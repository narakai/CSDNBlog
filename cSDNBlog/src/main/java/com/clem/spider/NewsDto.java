package com.clem.spider;

import java.util.List;

//blogs
public class NewsDto {
	private List<News> newsList;
	private String nextPageUrl;
	public List<News> getNewsList() {
		return newsList;
	}
	public void setNewsList(List<News> newsList) {
		this.newsList = newsList;
	}
	public String getNextPageUrl() {
		return nextPageUrl;
	}
	public void setNextPageUrl(String nextPageUrl) {
		this.nextPageUrl = nextPageUrl;
	}
	

	
	
}
