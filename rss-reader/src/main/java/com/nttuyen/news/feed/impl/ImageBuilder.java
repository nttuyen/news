package com.nttuyen.news.feed.impl;

import com.nttuyen.common.Builder;
import com.nttuyen.news.feed.Image;

/**
 * @author nttuyen266@gmail.com
 */
public class ImageBuilder implements Builder<Image> {
	public String url;
	public String title;
	public String link;

	public int width;
	public int height;
	public String description;

	public ImageBuilder() {}
	public ImageBuilder(Image image) {
		this.withURL(image.url)
				.withTitle(image.title)
				.withLink(image.link)
				.withWidth(image.width)
				.withHeight(image.height)
				.withDescription(image.description);
	}

	@Override
	public Image build() {
		return new Image(url, title, link, width, height, description);
	}

	public ImageBuilder withURL(String url) {
		this.url = url;
		return this;
	}
	public ImageBuilder withTitle(String title) {
		this.title = title;
		return this;
	}
	public ImageBuilder withLink(String link) {
		this.link = link;
		return this;
	}
	public ImageBuilder withWidth(int width) {
		this.width = width;
		return this;
	}
	public ImageBuilder withHeight(int height) {
		this.height = height;
		return this;
	}
	public ImageBuilder withDescription(String description) {
		this.description = description;
		return this;
	}
}
