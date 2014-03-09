package com.nttuyen.news.feed;

import java.io.Serializable;

public class Image implements Serializable {
    public final String url;
    public final String title;
    public final String link;

    public final int width;
    public final int height;
    public final String description;

	public Image(String url, String title, String link, int width, int height, String description) {
		this.url = url;
		this.title = title;
		this.link = link;

		this.width = width;
		this.height = height;
		this.description = description;
	}
}
