package com.kkbc.entity;

public class News {
	private Integer id;
	private String title;
	private String url;
	private String image_url;
	private String create_time;
	private String publish_time;
	private String content;
	
	public News(){}
	public News(Integer id,String title, String image_url, String content) {
		super();
		this.id=id;
		this.title = title;
		this.image_url = image_url;
		this.content = content;
	}
	public News(String title, String image_url, String content) {
		super();
		this.title = title;
		this.image_url = image_url;
		this.content = content;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getImage_url() {
		return image_url;
	}
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getPublish_time() {
		return publish_time;
	}
	public void setPublish_time(String publish_time) {
		this.publish_time = publish_time;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}
