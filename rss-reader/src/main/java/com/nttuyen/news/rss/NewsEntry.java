package com.nttuyen.news.rss;

import java.io.Serializable;
import java.util.Date;

/**
 * @author nttuyen266@gmail.com
 */
public class NewsEntry implements Serializable {
	/**
	 * The title of the item.
	 */
	private String title;
	/**
	 * The URL of the item.
	 */
	private String link;
	/**
	 * The item synopsis.
	 */
	private String description;
	/**
	 * Email address of the author of the item. More.
	 */
	private String author;
	/**
	 * Includes the item in one or more categories. More.
	 */
	private String category;
	/**
	 * URL of a page for comments relating to the item. More.
	 */
	private String comments;
	/**
	 * Describes a media object that is attached to the item. More.
	 */
	private String enclosure;
	/**
	 * A string that uniquely identifies the item. More.
	 */
	private String guid;
	/**
	 * Indicates when the item was published. More.
	 */
	private Date pubDate;
	/**
	 * The RSS channel that the item came from. More.
	 */
	private String source;
}
