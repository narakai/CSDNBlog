package com.clem.spider;

import java.util.List;

public class PersonalBlog {
	 /** 
     * ���� 
     */  
    private String blogTitle;  
    /** 
     * ʱ��
     */  
    private String blogTime;  
  
    /** 
     * �������� 
     */  
    private String blogLink;
    /** 
     * �������� 
     */  
    private String mainLink;
    public String getMainLink() {
		return mainLink;
	}

	public void setMainLink(String mainLink) {
		this.mainLink = mainLink;
	}

	/** 
     * �������� 
     */ 
    private String blogAuthor;
    public String getBlogAuthor() {
		return blogAuthor;
	}

	public void setBlogAuthor(String blogAuthor) {
		this.blogAuthor = blogAuthor;
	}

	public String getBlogSlogan() {
		return blogSlogan;
	}

	public void setBlogSlogan(String blogSlogan) {
		this.blogSlogan = blogSlogan;
	}

	/** 
     * �������� 
     */ 
    private String blogSlogan;

	public String getBlogTitle() {
		return blogTitle;
	}

	public void setBlogTitle(String blogTitle) {
		this.blogTitle = blogTitle;
	}

	public String getBlogTime() {
		return blogTime;
	}

	public void setBlogTime(String blogTime) {
		this.blogTime = blogTime;
	}

	public String getBlogLink() {
		return blogLink;
	}

	public void setBlogLink(String blogLink) {
		this.blogLink = blogLink;
	}

	@Override
	public String toString() {
		return "PersonanBlog [blogTitle=" + blogTitle + ", blogTime="
				+ blogTime + ", blogLink=" + blogLink + ", mainLink="
				+ mainLink + ", blogAuthor=" + blogAuthor + ", blogSlogan="
				+ blogSlogan + "]";
	}




    
    
}
