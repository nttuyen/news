package com.nttuyen.news.feed.impl;

import com.nttuyen.news.feed.Builder;
import com.nttuyen.news.feed.Feed;
import com.nttuyen.news.feed.FeedEntry;
import com.nttuyen.news.feed.Image;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author nttuyen266@gmail.com
 */
public class FeedBuilder implements Builder<Feed> {
	private String title;
	private String link;
	private String description;
	private Image image;
	private Date publishDate;
	private Date lastUpdateDate;
	private int ttl;
	private String category;
	private List<FeedEntry> entries;

	public FeedBuilder() {}

	public FeedBuilder(Feed feed) {
		this.title = feed.getTitle();
		this.link = feed.getLink();
		this.description = feed.getDescription();
		this.image = feed.getImage();
		this.publishDate = feed.getPublishDate();
		this.lastUpdateDate = feed.getLastUpdateDate();
		this.ttl = feed.getTtl();
		this.category = feed.getCategory();
		this.entries = feed.getEntries();
	}

	@Override
	public Feed build() {
		return new FeedImpl(title, link, description, image, publishDate, lastUpdateDate, ttl, category, entries);
	}

	public FeedBuilder withTitle(String title) {
		this.title = title;
		return this;
	}
	public FeedBuilder withLink(String link) {
		this.link = link;
		return this;
	}
	public FeedBuilder withDescription(String description) {
		this.description = description;
		return this;
	}
	public FeedBuilder withImage(Image image) {
		this.image = image;
		return this;
	}
	public FeedBuilder withPublishDate(Date publishDate) {
		this.publishDate = publishDate;
		return this;
	}
	public FeedBuilder withLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
		return this;
	}
	public FeedBuilder withTtl(int ttl) {
		this.ttl = ttl;
		return this;
	}
	public FeedBuilder withCategory(String category) {
		this.category = category;
		return this;
	}
	public FeedBuilder withEntries(List<FeedEntry> entries) {
		this.entries = entries;
		return this;
	}
	public FeedBuilder addEntry(FeedEntry entry) {
		if(this.entries == null) {
			this.entries = new LinkedList<FeedEntry>();
		}
		this.entries.add(entry);
		return this;
	}
	public FeedBuilder removeEntry(FeedEntry entry) {
		if(this.entries == null) {
			return this;
		}
		this.entries.remove(entry);
		return this;
	}
	public FeedBuilder removeEntry(int index) {
		if(this.entries == null || this.entries.size() <= index) {
			return this;
		}
		this.entries.remove(index);
		return this;
	}
}
