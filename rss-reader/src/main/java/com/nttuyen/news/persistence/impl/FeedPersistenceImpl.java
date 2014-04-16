package com.nttuyen.news.persistence.impl;

import com.nttuyen.common.Callback;
import com.nttuyen.http.Executor;
import com.nttuyen.http.HTTP;
import com.nttuyen.http.HttpException;
import com.nttuyen.http.Method;
import com.nttuyen.http.Request;
import com.nttuyen.http.Response;
import com.nttuyen.news.Consts;
import com.nttuyen.news.feed.Feed;
import com.nttuyen.news.feed.FeedEntry;
import com.nttuyen.news.feed.Image;
import com.nttuyen.news.persistence.FeedPersistence;
import com.nttuyen.news.persistence.FeedPersistenceException;
import com.nttuyen.news.util.StringUtils;
import org.apache.log4j.Logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author nttuyen266@gmail.com
 */
public class FeedPersistenceImpl implements FeedPersistence {
    private static final Logger log = Logger.getLogger(FeedPersistenceImpl.class);

    private Executor executor = HTTP.createDefault();
    private boolean loggedIn = false;

	@Override
	public void persist(Feed feed) throws FeedPersistenceException {
        final String url = Consts.REST_ROOT_URL;
        if(!loggedIn) {
            Request request = new Request(url, Method.POST);
            request.addParam("option", "com_login");
            request.addParam("task", "api.login");
            request.addParam("format", "json");
            request.addParam("username", "crawler");
            request.addParam("password", "123456");
            try {
                Response response = executor.execute(request);
                if(response.getStatusCode() == 200) {
                  loggedIn = true;
                }
            } catch (HttpException e) {
                log.error(e);
                return;
            }
        }
        if(!loggedIn) {
            return;
        }


        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<FeedEntry> entries = feed.getEntries();
        for(FeedEntry entry : entries) {
            //TODO: how to detect this content was collected
            Request request = new Request(Consts.REST_ROOT_URL, Method.POST);
            //Option component
            request.addParam("option", "com_content");
            request.addParam("task", "article.apply");
            request.addParam("format", "json");

            request.addParam("jform[id]", "0");
            request.addParam("jform[title]", entry.getTitle().trim());
            request.addParam("jform[alias]", StringUtils.convertToFriendlyURL(entry.getTitle().trim()));
            request.addParam("jform[articletext]", entry.getDescription());
            //Uncategory
            //TODO: how to process category
            request.addParam("jform[catid]", "2");

            request.addParam("jform[state]", "0");
            request.addParam("jform[featured]", "0");
            request.addParam("jform[access]", "1");
            request.addParam("jform[language]", "*");
            request.addParam("jform[version_note]", "push from feed reader");
            request.addParam("jform[publish_up]", entry.getPublishDate() == null ? "" : df.format(entry.getPublishDate()));
            request.addParam("jform[publish_down]", "");
            request.addParam("jform[created]", "");
            request.addParam("jform[created_by]", "0");
            request.addParam("jform[created_by_alias]", "");
            request.addParam("jform[modified]", "");
            request.addParam("jform[modified_by]", "");
            request.addParam("jform[version]", "");
            request.addParam("jform[hits]", "");
            //TODO: how to process SEO
            request.addParam("jform[metadesc]", "");
            request.addParam("jform[metakey]", "");
            request.addParam("jform[xreference]", entry.getLink());
            request.addParam("jform[metadata][robots]", "");
            request.addParam("jform[metadata][author]", entry.getAuthor() == null ? "" : entry.getAuthor());
            request.addParam("jform[metadata][rights]", "");
            request.addParam("jform[metadata][xreference]", entry.getLink());
            request.addParam("originLink", entry.getLink());

            //TODO: how to process IMAGE
            request.addParam("jform[images][image_intro]", "");
            request.addParam("jform[images][float_intro]", "");
            request.addParam("jform[images][image_intro_alt]", "");
            request.addParam("jform[images][image_intro_caption]", "");
            request.addParam("jform[images][image_fulltext]", "");
            request.addParam("jform[images][float_fulltext]", "");
            request.addParam("jform[images][image_fulltext_alt]", "");
            request.addParam("jform[images][image_fulltext_caption]", "");
            if(entry.getImage() != null) {
                Image img = entry.getImage();
                request.addParam("jform[images][image_intro]", img.url);
                request.addParam("jform[images][image_intro_alt]", img.title);
                if(img.description != null) {
                    request.addParam("jform[images][image_intro_caption]", img.description);
                }
            }

            request.addParam("jform[urls][urla]", "");
            request.addParam("jform[urls][urlatext]", "");
            request.addParam("jform[urls][targeta]", "");
            request.addParam("jform[urls][urlb]", "");
            request.addParam("jform[urls][urlbtext]", "");
            request.addParam("jform[urls][targetb]", "");
            request.addParam("jform[urls][urlc]", "");
            request.addParam("jform[urls][urlctext]", "");
            request.addParam("jform[urls][targetc]", "");

            request.addParam("jform[attribs][show_title]", "");
            request.addParam("jform[attribs][link_titles]", "");
            request.addParam("jform[attribs][show_tags]", "");
            request.addParam("jform[attribs][show_intro]", "");
            request.addParam("jform[attribs][info_block_position]", "");
            request.addParam("jform[attribs][show_category]", "");
            request.addParam("jform[attribs][link_category]", "");
            request.addParam("jform[attribs][show_parent_category]", "");
            request.addParam("jform[attribs][link_parent_category]", "");
            request.addParam("jform[attribs][show_author]", "");
            request.addParam("jform[attribs][link_author]", "");
            request.addParam("jform[attribs][show_create_date]", "");
            request.addParam("jform[attribs][show_modify_date]", "");
            request.addParam("jform[attribs][show_publish_date]", "");
            request.addParam("jform[attribs][show_item_navigation]", "");
            request.addParam("jform[attribs][show_icons]", "");
            request.addParam("jform[attribs][show_print_icon]", "");
            request.addParam("jform[attribs][show_email_icon]", "");
            request.addParam("jform[attribs][show_vote]", "");
            request.addParam("jform[attribs][show_hits]", "");
            request.addParam("jform[attribs][show_noauth]", "");
            request.addParam("jform[attribs][urls_position]", "");
            request.addParam("jform[attribs][alternative_readmore]", "");
            request.addParam("jform[attribs][article_layout]", "");
            request.addParam("jform[attribs][show_publishing_options]", "");
            request.addParam("jform[attribs][show_article_options]", "");
            request.addParam("jform[attribs][show_urls_images_backend]", "");
            request.addParam("jform[attribs][show_urls_images_frontend]", "");

            try {
                executor.execute(request);
            } catch (HttpException ex) {
                log.error(ex);
            }
        }
	}
}
