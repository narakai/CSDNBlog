package com.clem.spider;

//blog list
public class NewsItem {

		private int id;

		/**
		 * 标题
		 */
		private String title;
		/**
		 * 链接
		 */
		private String link;
		/**
		 * 发布日期
		 */
		private String date;
		/**
		 * 图片的链接
		 */
		private String imgLink;
		/**
		 * 浏览
		 */
		private String view;

		/**
		 * 评论
		 */
		private String comments;
		/**
		 * 内容
		 */
		private String content;

		/**
		 * 类型  
		 * 
		 */
		private int newsType;
		
		/**
		 * 作者
		 * 
		 */
		private String author;

		public String getAuthor() {
			return author;
		}

		public void setAuthor(String author) {
			this.author = author;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getLink() {
			return link;
		}

		public void setLink(String link) {
			this.link = link;
		}

		public String getDate() {
			return date;
		}

		public void setDate(String date) {
			this.date = date;
		}

		public String getImgLink() {
			return imgLink;
		}

		public void setImgLink(String imgLink) {
			this.imgLink = imgLink;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public int getNewsType() {
			return newsType;
		}

		public void setNewsType(int newsType) {
			this.newsType = newsType;
		}
		
		public String getView() {
			return view;
		}

		public void setView(String view) {
			this.view = view;
		}

		public String getComments() {
			return comments;
		}

		public void setComments(String comments) {
			this.comments = comments;
		}

		@Override
		public String toString() {
			return "NewsItem [id=" + id + ", title=" + title + ", link=" + link + ", date=" + date + ", imgLink=" + imgLink  
	                + ", content=" + content + ", newsType=" + newsType + ", view=" + view +  ", comments=" + comments +" author=" + author +  "]";
		}

		
}
