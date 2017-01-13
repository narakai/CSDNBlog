package com.clem.spider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class NewsItemBiz {
	// 处理NewItem的业务类
	/**
	 * 业界、移动、云计算
	 * 
	 * @param htmlStr
	 * @return
	 * @throws CommonException
	 */

	public List<NewsItem> getNewsItems(int newsType, int currentPage)
			throws CommonException, IOException {
		String urlStr = URLUtil.generateUrl(newsType, currentPage);
		String htmlStr = DataUtil.doGet(urlStr);

		List<NewsItem> newsItems = new ArrayList<NewsItem>();
		NewsItem newsItem = null;

		Document doc = Jsoup.parse(htmlStr);
		Elements bolgLists = doc.getElementsByClass("blog_list");
		

		for (int i = 0; i < bolgLists.size(); i++) {
			newsItem = new NewsItem();
			newsItem.setNewsType(newsType);

			String title = doc.select("div.blog_list h1").get(i).text();
			newsItem.setTitle(title);

			String content = doc.select("div.blog_list dd").get(i).text();
			newsItem.setContent(content);

			String imgSrc = doc.select("div.blog_list img[src]").get(i)
					.attr("src");
			newsItem.setImgLink(imgSrc);

			String link = doc.select("div.blog_list h1").select("a[href]").get(i)
					.attr("href");
			newsItem.setLink(link);

			String author = doc.select("div.about_info").select("span.fl")
					.get(i).child(0).text();
			newsItem.setAuthor(author);

			String date = doc.select("div.about_info").select("span.fl").get(i)
					.child(1).text();
			newsItem.setDate(date);

			String view = doc.select("div.about_info").select("span.fl").get(i)
					.child(2).text();
			newsItem.setView(view);

			String comments = doc.select("div.about_info").select("span.fl")
					.get(i).child(3).text();
			newsItem.setComments(comments);

			newsItems.add(newsItem);
		}
		
		return newsItems;
	}
}
