package com.hcctech.bookshelf.pojo;

public class BsTopMessage  implements java.io.Serializable {
	private String topMessageId;
	private String topMessageTitle;
	private String topMessageSrc;
	private String topMessageHref;
	
	public BsTopMessage() {
		super();
	}
	
	public BsTopMessage(String topMessageTitle, String topMessageSrc, String topMessageHref) {
		this.topMessageTitle = topMessageTitle;
		this.topMessageSrc = topMessageSrc;
		this.topMessageHref = topMessageHref;
	}
	
	public String getTopMessageTitle() {
		return topMessageTitle;
	}
	public void setTopMessageTitle(String topMessageTitle) {
		this.topMessageTitle = topMessageTitle;
	}
	public String getTopMessageSrc() {
		return topMessageSrc;
	}
	public void setTopMessageSrc(String topMessageSrc) {
		this.topMessageSrc = topMessageSrc;
	}
	public String getTopMessageHref() {
		return topMessageHref;
	}
	public void setTopMessageHref(String topMessageHref) {
		this.topMessageHref = topMessageHref;
	}

	public String getTopMessageId() {
		return topMessageId;
	}

	public void setTopMessageId(String topMessageId) {
		this.topMessageId = topMessageId;
	}
	 
	
}
