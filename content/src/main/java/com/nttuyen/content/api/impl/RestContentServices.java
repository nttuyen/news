package com.nttuyen.content.api.impl;

import com.nttuyen.common.util.JSONUtils;
import com.nttuyen.content.api.ContentServiceException;
import com.nttuyen.content.api.ContentServices;
import com.nttuyen.content.entity.Article;
import com.nttuyen.http.Executor;
import com.nttuyen.http.HTTP;
import com.nttuyen.http.Method;
import com.nttuyen.http.Request;
import com.nttuyen.http.Response;
import com.nttuyen.http.decorator.AddJsonFormatDecorator;
import com.nttuyen.http.decorator.AuthenticationRequiredDecorator;
import com.nttuyen.http.decorator.MultiRequestDecorator;
import com.nttuyen.http.impl.HttpClientExecutor;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @author nttuyen266@gmail.com
 */
public class RestContentServices implements ContentServices {
    private static final Logger log = Logger.getLogger(RestContentServices.class);
    private static final Map<String, String> defaultForm = new HashMap<>();
    static {
        defaultForm.put("option", "com_content");
        defaultForm.put("task", "article.apply");
        defaultForm.put("format", "json");

        defaultForm.put("id", "0");
        defaultForm.put("jform[id]", "0");
        defaultForm.put("jform[asset_id]", "");
        defaultForm.put("jform[title]", "");
        defaultForm.put("jform[alias]", "");

        //defaultForm.put("jform[introtext]", "");
        //defaultForm.put("jform[fulltext]", "");

        defaultForm.put("jform[state]", "0");

        //Uncategory
        //TODO: how to process category
        defaultForm.put("jform[catid]", "2");

        defaultForm.put("jform[created]", "");
        defaultForm.put("jform[created_by]", "0");
        defaultForm.put("jform[created_by_alias]", "");
        defaultForm.put("jform[modified]", "");
        defaultForm.put("jform[modified_by]", "");
        defaultForm.put("jform[checked_out]", "0");
        defaultForm.put("jform[checked_out_time]", "");

        defaultForm.put("jform[publish_up]", "");
        defaultForm.put("jform[publish_down]", "");

        //TODO: how to process IMAGE
        defaultForm.put("jform[images][image_intro]", "");
        defaultForm.put("jform[images][float_intro]", "");
        defaultForm.put("jform[images][image_intro_alt]", "");
        defaultForm.put("jform[images][image_intro_caption]", "");
        defaultForm.put("jform[images][image_fulltext]", "");
        defaultForm.put("jform[images][float_fulltext]", "");
        defaultForm.put("jform[images][image_fulltext_alt]", "");
        defaultForm.put("jform[images][image_fulltext_caption]", "");

        defaultForm.put("jform[urls][urla]", "");
        defaultForm.put("jform[urls][urlatext]", "");
        defaultForm.put("jform[urls][targeta]", "");
        defaultForm.put("jform[urls][urlb]", "");
        defaultForm.put("jform[urls][urlbtext]", "");
        defaultForm.put("jform[urls][targetb]", "");
        defaultForm.put("jform[urls][urlc]", "");
        defaultForm.put("jform[urls][urlctext]", "");
        defaultForm.put("jform[urls][targetc]", "");

        defaultForm.put("jform[attribs][show_title]", "");
        defaultForm.put("jform[attribs][link_titles]", "");
        defaultForm.put("jform[attribs][show_tags]", "");
        defaultForm.put("jform[attribs][show_intro]", "");
        defaultForm.put("jform[attribs][info_block_position]", "");
        defaultForm.put("jform[attribs][show_category]", "");
        defaultForm.put("jform[attribs][link_category]", "");
        defaultForm.put("jform[attribs][show_parent_category]", "");
        defaultForm.put("jform[attribs][link_parent_category]", "");
        defaultForm.put("jform[attribs][show_author]", "");
        defaultForm.put("jform[attribs][link_author]", "");
        defaultForm.put("jform[attribs][show_create_date]", "");
        defaultForm.put("jform[attribs][show_modify_date]", "");
        defaultForm.put("jform[attribs][show_publish_date]", "");
        defaultForm.put("jform[attribs][show_item_navigation]", "");
        defaultForm.put("jform[attribs][show_icons]", "");
        defaultForm.put("jform[attribs][show_print_icon]", "");
        defaultForm.put("jform[attribs][show_email_icon]", "");
        defaultForm.put("jform[attribs][show_vote]", "");
        defaultForm.put("jform[attribs][show_hits]", "");
        defaultForm.put("jform[attribs][show_noauth]", "");
        defaultForm.put("jform[attribs][urls_position]", "");
        defaultForm.put("jform[attribs][alternative_readmore]", "");
        defaultForm.put("jform[attribs][article_layout]", "");
        defaultForm.put("jform[attribs][show_publishing_options]", "");
        defaultForm.put("jform[attribs][show_article_options]", "");
        defaultForm.put("jform[attribs][show_urls_images_backend]", "");
        defaultForm.put("jform[attribs][show_urls_images_frontend]", "");

        defaultForm.put("jform[version]", "");
        defaultForm.put("jform[ordering]", "0");

        //TODO: how to process SEO
        defaultForm.put("jform[metakey]", "");
        defaultForm.put("jform[metadesc]", "");

        defaultForm.put("jform[access]", "1");
        defaultForm.put("jform[hits]", "");

        defaultForm.put("jform[metadata][robots]", "");
        defaultForm.put("jform[metadata][author]", "");
        defaultForm.put("jform[metadata][rights]", "");
        defaultForm.put("jform[metadata][xreference]", "");

        defaultForm.put("jform[featured]", "0");
        defaultForm.put("jform[language]", "*");
        defaultForm.put("jform[xreference]", "");

        defaultForm.put("articletext", "");
        defaultForm.put("originLink", "");

        defaultForm.put("jform[version_note]", "Save from ContentRestService");
    }

    private final Executor rest;
    private final Request nextCrawledRequest;
    private final DateFormat df;
    private final Config config;

    public RestContentServices() {
        config = ConfigFactory.load();
        df = new SimpleDateFormat(config.getString("news.content.api.rest.date-time-format"));
        nextCrawledRequest = HTTP.createRequest(config.getConfig("news.content.api.rest.requests.next-crawled-content"));
        this.rest = HTTP.createRestExecutor();
    }

    @Override
    public Article nextCrawledArticle() {
        try {
            Response response = rest.execute(nextCrawledRequest);
            if(response.getStatusCode() != 200) {
                return null;
            }
            String jsonString = response.getResponse();
            if("".equals(jsonString)) {
                return null;
            }
            JSONObject json = new JSONObject(jsonString);
            return new Article(json);
        } catch (Exception ex) {

        }
        return null;
    }

    @Override
    public void save(Article article) throws ContentServiceException {
        JSONObject json = article.getJson();
        Map<String, String> form = this.buildForm(json);

        form.put("id", String.valueOf(article.getId()));
        form.put("jform[id]", String.valueOf(article.getId()));
        form.put("jform[title]", article.getTitle());

        String intro = article.getIntro();
        String fulltext = article.getFulltext();

        String articleText = fulltext == null || "".equals(fulltext) ? intro : new StringBuilder(intro).append("<hr id=\"system-readmore\" />").append(fulltext).toString();
        form.put("jform[articletext]", articleText);

        form.put("jform[state]", String.valueOf(article.getState().getValue()));
        form.put("jform[featured]", article.isFeatured() ? "1" : "0");
        if(article.getPublishDate() != null) {
            form.put("jform[publish_up]", df.format(article.getPublishDate()));
        }
        form.put("jform[metadata][author]", article.getAuthor());
        form.put("jform[metadata][xreference]", article.getOriginLink());
        form.put("originLink", article.getOriginLink());
        form.put("jform[images][image_intro]", article.getIntroImage());
        form.put("jform[images][image_fulltext]", article.getFullImage());

        Request save = new Request(config.getString("news.content.api.rest.requests.save.url"),
                                    Method.valueOf(config.getString("news.content.api.rest.requests.save.method")));
        form.forEach(save::addParam);

        try {
            Response response = rest.execute(save);
            if(response.getStatusCode() != 200 && response.getStatusCode() != 201) {
                log.error("Save content failure with response code" + response.getStatusCode());
                throw new ContentServiceException("Failure with response code: " + response.getStatusCode());
            }
        } catch (Exception ex) {
            log.error(ex);
            throw new ContentServiceException(ex);
        }
    }

    private Map<String, String> buildForm(JSONObject json) {
        Map<String, String> form = new HashMap<>();
        for(Map.Entry<String, String> entry : defaultForm.entrySet()) {
            String key = entry.getKey();
            String def = entry.getValue();
            form.put(key, def);

            //Process key
            String jsonKey = key.replace("jform[", "");
            jsonKey = jsonKey.replaceAll("\\]\\[", ".");
            jsonKey = jsonKey.replace("]", "");

            String jsonVal = JSONUtils.get(json, jsonKey);
            if(jsonVal != null) {
                form.put(key, jsonVal);
            }
        }
        return form;
    }
}
