package com.hcctech.bookshelf.pojo;

public class GalleryObj {
	private String galleryTitle;
	private String gallerySrc;
	private String galleryHref;
	
	public GalleryObj() {
		super();
	}
	public GalleryObj(String galleryTitle, String gallerySrc, String galleryHref) {
		this.galleryTitle = galleryTitle;
		this.gallerySrc = gallerySrc;
		this.galleryHref = galleryHref;
	}
	public String getGalleryTitle() {
		return galleryTitle;
	}
	public void setGalleryTitle(String galleryTitle) {
		this.galleryTitle = galleryTitle;
	}
	public String getGallerySrc() {
		return gallerySrc;
	}
	public void setGallerySrc(String gallerySrc) {
		this.gallerySrc = gallerySrc;
	}
	public String getGalleryHref() {
		return galleryHref;
	}
	public void setGalleryHref(String galleryHref) {
		this.galleryHref = galleryHref;
	}
	
}
