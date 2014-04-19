package com.nttuyen.content.api.impl;

import com.nttuyen.content.api.ContentApi;
import com.nttuyen.content.entity.Content;
import com.nttuyen.http.*;
import com.nttuyen.http.decorator.AddJsonFormatDecorator;
import com.nttuyen.http.decorator.AuthenticationRequiredDecorator;
import com.nttuyen.http.decorator.MultiRequestDecorator;
import org.apache.log4j.Logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * @author nttuyen266@gmail.com
 */
@Deprecated
public class RestContentApi implements ContentApi {
    private static final Logger log = Logger.getLogger(RestContentApi.class);
    private final Request authentication;
    public RestContentApi() {
        authentication = new Request("http://127.0.0.1:86/administrator/index.php", Method.POST);
        authentication.addParam("option", "com_login");
        authentication.addParam("task", "api.login");
        authentication.addParam("username", "nttuyen");
        authentication.addParam("password", "adminpass");
        authentication.addParam("format", "json");
    }
    @Override
    public Content nextCrawledContent() {
        Executor executor = HTTP.createDefault();
        executor = new AddJsonFormatDecorator().setExecutor(executor);
        executor = new MultiRequestDecorator().setExecutor(executor);
        executor = new AuthenticationRequiredDecorator(authentication).setExecutor(executor);

        Request request = new Request("http://127.0.0.1:86/administrator/index.php", Method.POST);
        request.addParam("option", "com_contentapi");
        request.addParam("view", "nextcontent");
        request.addParam("format", "json");

        try {
            Response response = executor.execute(request);
            if(response != null && response.getStatusCode() == 200) {
                //TODO: how to build content from json
                String jsonString = response.getResponse();
                if(jsonString != null && !"".equals(jsonString)) {
                    ContentJsonBuilder builder = new ContentJsonBuilder(jsonString);
                    return builder.build();
                }
            }
        } catch (HttpException ex) {
            log.error(ex);
        }
        return null;
    }

    @Override
    public void persist(Content content) {
        if(content == null) {
            return;
        }

        Executor executor = HTTP.createDefault();
        executor = new AddJsonFormatDecorator().setExecutor(executor);
        executor = new MultiRequestDecorator().setExecutor(executor);
        executor = new AuthenticationRequiredDecorator(authentication).setExecutor(executor);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Request request = new Request("http://127.0.0.1:86/administrator/index.php", Method.POST);
        //Option component
        request.addParam("option", "com_content");
        request.addParam("task", "article.apply");
        request.addParam("format", "json");

        request.addParam("jform[id]", content.getId());
        request.addParam("jform[title]", content.getTitle().trim());
        request.addParam("jform[alias]", content.getAlias());
        //TODO: what is articleText
        request.addParam("jform[articletext]", content.getArticleText());
        //Uncategory
        //TODO: how to process category
        request.addParam("jform[catid]", content.getCatid());

        request.addParam("jform[state]", content.getState());
        request.addParam("jform[featured]", content.getFeature());
        request.addParam("jform[access]", content.getAccess());
        request.addParam("jform[language]", content.getLanguage());
        request.addParam("jform[version_note]", "Update by RestContentApi");
        request.addParam("jform[publish_up]", content.getPublishUp() == null ? "" : df.format(content.getPublishUp()));
        request.addParam("jform[publish_down]", "");
        request.addParam("jform[created]", content.getCreated() == null ? "" : df.format(content.getCreated()));
        request.addParam("jform[created_by]", content.getCreatedBy());
        request.addParam("jform[created_by_alias]", "");
        //request.addParam("jform[modified]", content.getModified() == null ? "" : df.format(content.getModified()));
        request.addParam("jform[modified]", "");
        request.addParam("jform[modified_by]", "");
        request.addParam("jform[version]", "");
        //TODO: should we update hits
        request.addParam("jform[hits]", content.getHits());
        //TODO: how to process SEO
        request.addParam("jform[metadesc]", content.getMetaDescription());
        request.addParam("jform[metakey]", content.getMetakey());
        request.addParam("jform[xreference]", content.getOriginLink());
        request.addParam("jform[metadata][robots]", content.getMetadata().containsKey("robots") ? content.getMetadata().get("robots") : "");
        request.addParam("jform[metadata][author]", content.getMetadata().containsKey("author") ? content.getMetadata().get("author") : "");
        request.addParam("jform[metadata][rights]", content.getMetadata().containsKey("rights") ? content.getMetadata().get("rights") : "");
        request.addParam("jform[metadata][xreference]", content.getOriginLink());
        request.addParam("originLink", content.getOriginLink());

        //TODO: how to process IMAGE
        request.addParam("jform[images][image_intro]", content.getImages().containsKey("image_intro") ? content.getImages().get("image_intro") : "");
        request.addParam("jform[images][float_intro]", content.getImages().containsKey("float_intro") ? content.getImages().get("float_intro") : "");
        request.addParam("jform[images][image_intro_alt]", content.getImages().containsKey("image_intro_alt") ? content.getImages().get("image_intro_alt") : "");
        request.addParam("jform[images][image_intro_caption]", content.getImages().containsKey("image_intro_caption") ? content.getImages().get("image_intro_caption") : "");
        request.addParam("jform[images][image_fulltext]", content.getImages().containsKey("image_fulltext") ? content.getImages().get("image_fulltext") : "");
        request.addParam("jform[images][float_fulltext]", content.getImages().containsKey("float_fulltext") ? content.getImages().get("float_fulltext") : "");
        request.addParam("jform[images][image_fulltext_alt]", content.getImages().containsKey("image_fulltext_alt") ? content.getImages().get("image_fulltext_alt") : "");
        request.addParam("jform[images][image_fulltext_caption]", content.getImages().containsKey("image_fulltext_caption") ? content.getImages().get("image_fulltext_caption") : "");

        request.addParam("jform[urls][urla]", content.getUrls().containsKey("urla") ? content.getUrls().get("urla") : "");
        request.addParam("jform[urls][urlatext]", content.getUrls().containsKey("urlatext") ? content.getUrls().get("urlatext") : "");
        request.addParam("jform[urls][targeta]", content.getUrls().containsKey("targeta") ? content.getUrls().get("targeta") : "");
        request.addParam("jform[urls][urlb]", content.getUrls().containsKey("urlb") ? content.getUrls().get("urlb") : "");
        request.addParam("jform[urls][urlbtext]", content.getUrls().containsKey("urlbtext") ? content.getUrls().get("urlbtext") : "");
        request.addParam("jform[urls][targetb]", content.getUrls().containsKey("targetb") ? content.getUrls().get("targetb") : "");
        request.addParam("jform[urls][urlc]", content.getUrls().containsKey("urlc") ? content.getUrls().get("urlc") : "");
        request.addParam("jform[urls][urlctext]", content.getUrls().containsKey("urlctext") ? content.getUrls().get("urlctext") : "");
        request.addParam("jform[urls][targetc]", content.getUrls().containsKey("targetc") ? content.getUrls().get("targetc") : "");

        request.addParam("jform[attribs][show_title]", content.getAttributes().containsKey("show_title") ? content.getAttributes().get("show_title") : "");
        request.addParam("jform[attribs][link_titles]", content.getAttributes().containsKey("link_titles") ? content.getAttributes().get("link_titles") : "");
        request.addParam("jform[attribs][show_tags]", content.getAttributes().containsKey("show_tags") ? content.getAttributes().get("show_tags") : "");
        request.addParam("jform[attribs][show_intro]", content.getAttributes().containsKey("show_intro") ? content.getAttributes().get("show_intro") : "");
        request.addParam("jform[attribs][info_block_position]", content.getAttributes().containsKey("info_block_position") ? content.getAttributes().get("info_block_position") : "");
        request.addParam("jform[attribs][show_category]", content.getAttributes().containsKey("show_category") ? content.getAttributes().get("show_category") : "");
        request.addParam("jform[attribs][link_category]", content.getAttributes().containsKey("link_category") ? content.getAttributes().get("link_category") : "");
        request.addParam("jform[attribs][show_parent_category]", content.getAttributes().containsKey("show_parent_category") ? content.getAttributes().get("show_parent_category") : "");
        request.addParam("jform[attribs][link_parent_category]", content.getAttributes().containsKey("link_parent_category") ? content.getAttributes().get("link_parent_category") : "");
        request.addParam("jform[attribs][show_author]", content.getAttributes().containsKey("show_author") ? content.getAttributes().get("show_author") : "");
        request.addParam("jform[attribs][link_author]", content.getAttributes().containsKey("link_author") ? content.getAttributes().get("link_author") : "");
        request.addParam("jform[attribs][show_create_date]", content.getAttributes().containsKey("show_create_date") ? content.getAttributes().get("show_create_date") : "");
        request.addParam("jform[attribs][show_modify_date]", content.getAttributes().containsKey("show_modify_date") ? content.getAttributes().get("show_modify_date") : "");
        request.addParam("jform[attribs][show_publish_date]", content.getAttributes().containsKey("show_publish_date") ? content.getAttributes().get("show_publish_date") : "");
        request.addParam("jform[attribs][show_item_navigation]", content.getAttributes().containsKey("show_item_navigation") ? content.getAttributes().get("show_item_navigation") : "");
        request.addParam("jform[attribs][show_icons]", content.getAttributes().containsKey("show_icons") ? content.getAttributes().get("show_icons") : "");
        request.addParam("jform[attribs][show_print_icon]", content.getAttributes().containsKey("show_print_icon") ? content.getAttributes().get("show_print_icon") : "");
        request.addParam("jform[attribs][show_email_icon]", content.getAttributes().containsKey("show_email_icon") ? content.getAttributes().get("show_email_icon") : "");
        request.addParam("jform[attribs][show_vote]", content.getAttributes().containsKey("show_vote") ? content.getAttributes().get("show_vote") : "");
        request.addParam("jform[attribs][show_hits]", content.getAttributes().containsKey("show_hits") ? content.getAttributes().get("show_hits") : "");
        request.addParam("jform[attribs][show_noauth]", content.getAttributes().containsKey("show_noauth") ? content.getAttributes().get("show_noauth") : "");
        request.addParam("jform[attribs][urls_position]", content.getAttributes().containsKey("urls_position") ? content.getAttributes().get("urls_position") : "");
        request.addParam("jform[attribs][alternative_readmore]", content.getAttributes().containsKey("alternative_readmore") ? content.getAttributes().get("alternative_readmore") : "");
        request.addParam("jform[attribs][article_layout]", content.getAttributes().containsKey("article_layout") ? content.getAttributes().get("article_layout") : "");
        request.addParam("jform[attribs][show_publishing_options]", content.getAttributes().containsKey("show_publishing_options") ? content.getAttributes().get("show_publishing_options") : "");
        request.addParam("jform[attribs][show_article_options]", content.getAttributes().containsKey("show_article_options") ? content.getAttributes().get("show_article_options") : "");
        request.addParam("jform[attribs][show_urls_images_backend]", content.getAttributes().containsKey("show_urls_images_backend") ? content.getAttributes().get("show_urls_images_backend") : "");
        request.addParam("jform[attribs][show_urls_images_frontend]", content.getAttributes().containsKey("show_urls_images_frontend") ? content.getAttributes().get("show_urls_images_frontend") : "");

        try {
            Response response = executor.execute(request);
        } catch (HttpException ex) {
            log.error(ex);
        }
    }
}
