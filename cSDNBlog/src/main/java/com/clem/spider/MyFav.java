package com.clem.spider;

public class MyFav {
	
	private String favTitle;
	private String favLink;
	private String favAuthor;
	

	public String getFavTitle() {
		return favTitle;
	}
	public void setFavTitle(String favTitle) {
		this.favTitle = favTitle;
	}

	public String getFavLink() {
		return favLink;
	}
	public void setFavLink(String favLink) {
		this.favLink = favLink;
	}
	public String getFavAuthor() {
		return favAuthor;
	}
	public void setFavAuthor(String favAuthor) {
		this.favAuthor = favAuthor;
	}
	@Override
	public String toString() {
		return "MyFav [favTitle=" + favTitle + ", favLink=" + favLink
				+ ", favAuthor=" + favAuthor + "]";
	}


	
	
	
}
