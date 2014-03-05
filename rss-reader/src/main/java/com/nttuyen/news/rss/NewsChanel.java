package com.nttuyen.news.rss;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author nttuyen266@gmail.com
 */
public class NewsChanel implements Serializable {

	//Require field
	/**
	 * The name of the channel.
	 * It's how people refer to your service.
	 * If you have an HTML website that contains the same information as your RSS file,
	 * the title of your channel should be the same as the title of your website.
	 */
	private String title;
	/**
	 * The URL to the HTML website corresponding to the channel.
	 */
	private String link;
	/**
	 * Phrase or sentence describing the channel.
	 */
	private String description;

	//Optional field

	/**
	 * Copyright notice for content in the channel.
	 */
	private String copyright;
	/**
	 * Email address for person responsible for editorial content.
	 */
	private String managingEditor;
	/**
	 * Email address for person responsible for technical issues relating to channel.
	 */
	private String webMaster;
	/**
	 * The publication date for the content in the channel.
	 * For example, the New York Times publishes on a daily basis, the publication date flips once every 24 hours.
	 * That's when the pubDate of the channel changes.
	 * All date-times in RSS conform to the Date and Time Specification of RFC 822,
	 * with the exception that the year may be expressed with two characters or four characters (four preferred).
	 */
	private Date pubDate;
	/**
	 * The last time the content of the channel changed.
	 */
	private Date lastBuildDate;
	/**
	 * Specifies a GIF, JPEG or PNG image that can be displayed with the channel. More info here.
	 */
	private String image;
	/**
	 * Specify one or more categories that the channel belongs to.
	 * Follows the same rules as the <item>-level category element. More info.
	 */
	private String category;

	/**
	 * ttl stands for time to live.
	 * It's a number of minutes that indicates how long a channel can be cached before refreshing from the source.
	 * More info here.
	 */
	private int ttl;


	/**
	 * The language the channel is written in.
	 * This allows aggregators to group all Italian language sites, for example, on a single page.
	 * A list of allowable values for this element, as provided by Netscape, is here.
	 * You may also use values defined by the W3C.
	 */
	private String language;
	/**
	 * A string indicating the program used to generate the channel.
	 */
	private String generator;
	/**
	 * A URL that points to the documentation for the format used in the RSS file.
	 * It's probably a pointer to this page.
	 * It's for people who might stumble across an RSS file on a Web server 25 years from now and wonder what it is.
	 */
	private String docs;
	/**
	 * Allows processes to register with a cloud to be notified of updates to the channel,
	 * implementing a lightweight publish-subscribe protocol for RSS feeds. More info here.
	 */
	private String cloud;
	/**
	 * The PICS rating for the channel.
	 */
	private float rating;
	/**
	 * Specifies a text input box that can be displayed with the channel. More info here.
	 */
	private String textInput;
	/**
	 * A hint for aggregators telling them which hours they can skip. More info here.
	 */
	private int skipHours;
	/**
	 * A hint for aggregators telling them which days they can skip. More info here.
	 */
	private int skipDays;

	private List<NewsEntry> entries;
}
