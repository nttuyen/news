package com.nttuyen.content.entity;

import com.nttuyen.common.util.JSONUtils;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author nttuyen266@gmail.com
 */
public class Article {
    //. TODO: temporary
    private final JSONObject json;

    public Article(JSONObject json) {
        this.json = json;
    }
    public Article() {
        this(new JSONObject());
    }

    private long id = 0;
    private String title = null;
    private String intro = null;
    private String fulltext = null;
    //TODO: When we need category
    //private int category;
    private ArticleState state = null;
    private Boolean featured = null;
    private Date publishDate = null;
    private String author = null;

    private String introImage = null;
    private String fullImage = null;

    private String originLink = null;

    public JSONObject getJson() {
        return json;
    }

    //public void setJson(JSONObject json) {
    //    this.json = json;
    //}

    public long getId() {
        long def = 0;
        try {
            String jsonID = JSONUtils.get(json, "id");
            if(jsonID != null) def = Long.parseLong(jsonID);
        } catch (Exception ex){}
        return id > 0 ? id : def;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        String def = null;
        try {
            def = JSONUtils.get(json, "title");
        } catch (Exception ex){}
        if(def == null) {
            def = "";
        }

        return title != null && !"".equals(title) ? title : def;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntro() {
        String def = null;
        try {
            def = JSONUtils.get(json, "introtext");
        } catch (Exception ex){}
        if(def == null) {
            def = "";
        }

        return intro != null && !"".equals(intro) ? intro : def;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getFulltext() {
        String def = null;
        try {
            def = JSONUtils.get(json, "fulltext");
        } catch (Exception ex){}
        if(def == null) {
            def = "";
        }

        return fulltext != null && !"".equals(fulltext) ? fulltext : def;
    }

    public void setFulltext(String fulltext) {
        this.fulltext = fulltext;
    }

    public ArticleState getState() {
        int def = 0;
        try {
            String state = JSONUtils.get(json, "state");
            def = Integer.parseInt(state);
        } catch (Exception ex) {}
        return state != null ? state : ArticleState.get(def);
    }

    public void setState(ArticleState state) {
        this.state = state;
    }

    public boolean isFeatured() {
        int def = 0;
        try {
            String state = JSONUtils.get(json, "state");
            def = Integer.parseInt(state);
        } catch (Exception ex) {}
        return featured != null ? featured : def == 1;
    }

    public void setFeatured(boolean featured) {
        this.featured = featured;
    }

    public Date getPublishDate() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date def = null;
        try {
            String date = JSONUtils.get(json, "publish_up");
            if(date != null) {
                def = df.parse(date);
            }
        } catch (Exception ex) {}
        return publishDate != null ? publishDate : def;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public String getAuthor() {
        String def = null;
        try {
            def = JSONUtils.get(json, "metadata/author");
        } catch (Exception ex){}
        if(def == null) {
            def = "";
        }

        return author != null && !"".equals(author) ? author : def;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIntroImage() {
        String def = null;
        try {
            def = JSONUtils.get(json, "images/image_intro");
        } catch (Exception ex){}
        if(def == null) {
            def = "";
        }

        return introImage != null && !"".equals(introImage) ? introImage : def;
    }

    public void setIntroImage(String introImage) {
        this.introImage = introImage;
    }

    public String getFullImage() {
        String def = null;
        try {
            def = JSONUtils.get(json, "images/image_fulltext");
        } catch (Exception ex){}
        if(def == null) {
            def = "";
        }

        return fullImage != null && !"".equals(fullImage) ? fullImage : def;
    }

    public void setFullImage(String fullImage) {
        this.fullImage = fullImage;
    }

    public String getOriginLink() {
        String def = null;
        try {
            def = JSONUtils.get(json, "originLink");
        } catch (Exception ex){}
        if(def == null) {
            def = "";
        }

        return originLink != null && !"".equals(originLink) ? originLink : def;
    }

    public void setOriginLink(String originLink) {
        this.originLink = originLink;
    }
}
