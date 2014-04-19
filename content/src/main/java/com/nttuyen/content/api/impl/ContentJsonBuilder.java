package com.nttuyen.content.api.impl;

import com.nttuyen.common.Builder;
import com.nttuyen.content.entity.Content;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author nttuyen266@gmail.com
 */
@Deprecated
public class ContentJsonBuilder implements Builder<Content> {
    private final JSONObject json;
    public ContentJsonBuilder(String jsonString) {
        this.json = new JSONObject(jsonString);
    }
    public ContentJsonBuilder(JSONObject json) {
        this.json = json;
    }

    @Override
    public Content build() {
        Content content = new Content();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        content.setId(Integer.parseInt(json.getString("id")));
        content.setAssetId(Integer.parseInt(json.getString("asset_id")));
        content.setTitle(json.getString("title"));
        content.setAlias(json.getString("alias"));
        content.setIntroText(json.getString("introtext"));
        content.setFullText(json.getString("fulltext"));
        content.setState(Integer.parseInt(json.getString("state")));
        content.setCatid(Long.parseLong(json.getString("catid")));
        try {
            content.setCreated(df.parse(json.getString("created")));
        } catch (Exception ex) {}
        content.setCreatedBy(Long.parseLong(json.getString("created_by")));
        try {
            content.setModified(df.parse(json.getString("modified")));
        } catch (Exception ex){}
        content.setCheckout(Integer.parseInt(json.getString("checked_out")));
        try {
            content.setCheckoutTime(df.parse(json.getString("checked_out_time")));
        } catch (Exception ex) {}
        try {
            content.setPublishUp(df.parse(json.getString("publish_up")));
        } catch (Exception ex){}
        try {
            content.setPublishUp(df.parse(json.getString("publish_down")));
        } catch (Exception ex){}
        Map<String, String> images = new HashMap<>();
        JSONObject jsonImages = json.getJSONObject("images");
        Set<String> keys = jsonImages.keySet();
        for(String key : keys) {
            images.put(key, jsonImages.getString(key));
        }
        content.setImages(images);

        Map<String, String> urls = new HashMap<>();
        JSONObject jsonUrl = json.getJSONObject("urls");
        keys = jsonUrl.keySet();
        for(String key : keys) {
            try {
                urls.put(key, jsonUrl.getString(key));
            } catch (JSONException ex) {
                urls.put(key, "");
            }
        }

        Map<String, String> attrs = new HashMap<>();
        JSONObject js = json.getJSONObject("attribs");
        keys = js.keySet();
        for(String key : keys) {
            attrs.put(key, js.getString(key));
        }

        content.setVersion(Integer.parseInt(json.getString("version")));
        content.setMetakey(json.getString("metakey"));
        content.setMetaDescription(json.getString("metadesc"));
        content.setAccess(Integer.parseInt(json.getString("access")));
        content.setHits(Long.parseLong(json.getString("hits")));

        Map<String, String> meta = new HashMap<>();
        js = json.getJSONObject("metadata");
        keys = js.keySet();
        for(String key : keys) {
            meta.put(key, js.getString(key));
        }
        content.setMetadata(meta);

        content.setFeature(Integer.parseInt(json.getString("featured")));
        content.setLanguage(json.getString("language"));
        content.setXreference(json.getString("xreference"));
        content.setArticleText(json.getString("articletext"));
        content.setOriginLink(json.getString("originLink"));

        return content;
    }
}
