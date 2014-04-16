package com.nttuyen.news.feed.impl;

import com.nttuyen.common.Builder;
import com.nttuyen.news.feed.FeedEntry;
import com.nttuyen.news.feed.Image;

import java.util.Date;

/**
 * @author nttuyen266@gmail.com
 */
public class FeedEntryBuilder implements Builder<FeedEntry> {
	private String title;
	private String link;
	private String description;
	private Image image;
	private String author;
	private Date publishDate;
	private String category;

	public FeedEntryBuilder() {}
	public FeedEntryBuilder(FeedEntry entry) {
		this.title = entry.getTitle();
		this.link = entry.getLink();
		this.description = entry.getDescription();
		this.image = entry.getImage();
		this.author = entry.getAuthor();
		this.publishDate = entry.getPublishDate();
		this.category = entry.getCategory();
	}

	@Override
	public FeedEntry build() {
		return new FeedEntryImpl(title, link, description, image, author, publishDate, category);
	}

	public FeedEntryBuilder withTitle(String title) {
		this.title = title;
		return this;
	}
	public FeedEntryBuilder withLink(String link) {
		this.link = link;
		return this;
	}
	public FeedEntryBuilder withDescription(String description) {
		this.description = description;
		return this;
	}
	public FeedEntryBuilder withImage(Image image) {
		this.image = image;
		return this;
	}
	public FeedEntryBuilder withAuthor(String author) {
		this.author = author;
		return this;
	}
	public FeedEntryBuilder withPublishDate(Date publishDate) {
		this.publishDate = publishDate;
		return this;
	}
	public FeedEntryBuilder withCategory(String category) {
		this.category = category;
		return this;
	}
}
