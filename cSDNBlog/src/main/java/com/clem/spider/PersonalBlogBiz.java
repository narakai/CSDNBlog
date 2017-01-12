package com.clem.spider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import android.util.Log;

public class PersonalBlogBiz {
	
//	public String getAuthor(String originURL) throws CommonException{
//		String htmlStr = DataUtil.doGet(originURL);
//		Document doc = Jsoup.parse(htmlStr);
//		String author = doc.select("div#blog_title h2").text();
//		return author;
//	}
//	
//	public String getSlogan(String originURL) throws CommonException{
//		String htmlStr = DataUtil.doGet(originURL);
//		Document doc = Jsoup.parse(htmlStr);
//		String slogan= doc.select("div#blog_title h3").text();
//		return slogan;
//	}
	
	public List<PersonalBlog> getBlogLists(String url, int currentPage)
	throws CommonException, IOException{
		
//		String urlStr = BlogURLUtil.generateBlogMainUrl(url, currentPage);
		int index1 = url.indexOf("/?view");
		int index2 = url.indexOf("list/");
		String urlStr = url.substring(0, index2 + 5) + currentPage + url.substring(index1, url.length());
		Log.i("urlStr", urlStr);
		String htmlStr = DataUtil.doGet(urlStr);
		List<PersonalBlog> blogLists = new ArrayList<PersonalBlog>();
		PersonalBlog blogList = null;
		
		Document doc = Jsoup.parse(htmlStr);
		Elements personalblogLists = doc
				.getElementsByClass("link_title");
		

		

		for (int i =0; i<personalblogLists.size(); i++) {
			
			blogList = new PersonalBlog();
			String author = doc.select("div#blog_title h2").text();
			blogList.setBlogAuthor(author);
			
			String slogan= doc.select("div#blog_title h3").text();
			blogList.setBlogSlogan(slogan);
			
			String mainLink = doc.select("div#blog_title h2").select("a[href]").attr("href");
			blogList.setMainLink(mainLink);
			
			String lastPart = doc.select("span.link_title").select("a[href]")
					.get(i).attr("href");
			blogList.setBlogLink("http://blog.csdn.net" + lastPart);
			
			String blogTitle = doc.select("span.link_title").get(i).text();
			blogList.setBlogTitle(blogTitle);
			
			String blogTime = doc.select("span.link_postdate").get(i).text();
			blogList.setBlogTime(blogTime);
					
			blogLists.add(blogList);
		}
		
		Log.i("List get!", blogLists.size() + "");
		return blogLists;
		
	}
}
