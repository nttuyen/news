package com.nttuyen.content.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author nttuyen266@gmail.com
 */
public class Content implements Serializable {
    private long id = 0;
    private long assetId = 0;
    private String title = "";
    private String alias = "";
    private String introText = "";
    private String fullText = "";
    private String articleText;
    private int state = 0;
    //TODO: default category?
    private long catid = 2;
    private Date created = new Date();
    private long createdBy = 0;
    private Date modified = new Date();
    private long modifiedBy = 0;
    private int checkout = 0;
    private Date checkoutTime = new Date();
    private Date publishUp = new Date();
    private Date publishDown = new Date();
    private Map<String, String> images = new HashMap<>();
    private Map<String, String> urls = new HashMap<>();
    private Map<String, String> attributes = new HashMap<>();
    private int version = 0;
    private String metakey = "";
    private String metaDescription = "";
    private int access = 1;
    private long hits = 0;
    private Map<String, String> metadata = new HashMap<>();
    private int feature = 0;
    private String language = "*";
    private String xreference = "";
    //TODO: how to process tags
    private String tags = "";
    private String originLink = "";

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAssetId() {
        return assetId;
    }

    public void setAssetId(long assetId) {
        this.assetId = assetId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getIntroText() {
        return introText;
    }

    public void setIntroText(String introText) {
        this.introText = introText;
    }

    public String getFullText() {
        return fullText;
    }

    public void setFullText(String fullText) {
        this.fullText = fullText;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public long getCatid() {
        return catid;
    }

    public void setCatid(long catid) {
        this.catid = catid;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public long getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(long modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public int getCheckout() {
        return checkout;
    }

    public void setCheckout(int checkout) {
        this.checkout = checkout;
    }

    public Date getCheckoutTime() {
        return checkoutTime;
    }

    public void setCheckoutTime(Date checkoutTime) {
        this.checkoutTime = checkoutTime;
    }

    public Date getPublishUp() {
        return publishUp;
    }

    public void setPublishUp(Date publishUp) {
        this.publishUp = publishUp;
    }

    public Date getPublishDown() {
        return publishDown;
    }

    public void setPublishDown(Date publishDown) {
        this.publishDown = publishDown;
    }

    public Map<String, String> getImages() {
        return images;
    }

    public void setImages(Map<String, String> images) {
        this.images = images;
    }

    public Map<String, String> getUrls() {
        return urls;
    }

    public void setUrls(Map<String, String> urls) {
        this.urls = urls;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getMetakey() {
        return metakey;
    }

    public void setMetakey(String metakey) {
        this.metakey = metakey;
    }

    public String getMetaDescription() {
        return metaDescription;
    }

    public void setMetaDescription(String metaDescription) {
        this.metaDescription = metaDescription;
    }

    public int getAccess() {
        return access;
    }

    public void setAccess(int access) {
        this.access = access;
    }

    public long getHits() {
        return hits;
    }

    public void setHits(long hits) {
        this.hits = hits;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }

    public int getFeature() {
        return feature;
    }

    public void setFeature(int feature) {
        this.feature = feature;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getXreference() {
        return xreference;
    }

    public void setXreference(String xreference) {
        this.xreference = xreference;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getOriginLink() {
        return originLink;
    }

    public void setOriginLink(String originLink) {
        this.originLink = originLink;
    }

    public String getArticleText() {
        return articleText;
    }

    public void setArticleText(String articleText) {
        this.articleText = articleText;
    }
}
