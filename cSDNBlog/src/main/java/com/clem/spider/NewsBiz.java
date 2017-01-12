package com.clem.spider;

import com.clem.spider.News.NewsType;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class NewsBiz {
	/** 
     * �������µ�url����һ��NewsDto���� 
     *  
     * @return 
     * @throws CommonException 
     */  
	public NewsDto getNews(String urlStr) throws CommonException{
		NewsDto newsDto = new NewsDto(); 
		List<News> newsList = new ArrayList<News>();
		
		String htmlStr = DataUtil.doGet(urlStr); 
		Document doc = Jsoup.parse(htmlStr);
		
		// ��������е�title 
		String title= doc.select("div.article_title").text();
		News news = new News(); 
		news.setTitle(title);  
        news.setType(NewsType.TITLE); 
        newsList.add(news);
        
        
		//��������е�img, content(in HTML format)
		Element contentEle = doc.select("div.article_content").get(0);
		Elements childrenEle = contentEle.children();
		
		for (Element child : childrenEle){
			Elements imgEles = child.getElementsByTag("img");
			// ͼƬ  
            if (imgEles.size() > 0)  
            {  
                for (Element imgEle : imgEles)  
                {  
                    if (imgEle.attr("src").equals(""))  
                        continue;  
                    
                    news = new News(); 
                    news.setImageLink(imgEle.attr("src")); 
                    news.setType(NewsType.IMG);
                    newsList.add(news);
                }  
            }  
            
            imgEles.remove();
            
            if (child.text().equals(""))  
                continue;  
            
            news = new News();
            news.setContent(child.outerHtml()); 
            news.setType(NewsType.CONTENT); 
            newsList.add(news);
		}
		
		newsDto.setNewsList(newsList);
		return newsDto;
	}
	
}
