package com.example.svasuassociate.entity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

public class Banner implements Serializable {
    String id,
            image,
            banner_type,
            bannertitle,
            status,
            created_at,
            updated_at;

    public String getId() {
        return id;
    }

    public void setId(String id1) {
        this.id = id1;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBanner_type() {
        return banner_type;
    }

    public void setBanner_type(String banner_type) {
        this.banner_type = banner_type;
    }

    public String getBannertitle() {
        return bannertitle;
    }

    public void setBannertitle(String bannertitle) {
        this.bannertitle = bannertitle;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public static List<Banner> createListFromJson(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Banner>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    public static String createJsonFromList(List<Banner> object) {
        Gson gson = new Gson();
        Type type = new TypeToken<Banner>() {
        }.getType();
        return gson.toJson(object, type);
    }

    public static com.example.svasuassociate.entity.Banner createobjectFromJson(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<Banner>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    public static String createjsonFromObject(com.example.svasuassociate.entity.Banner banner) {
        Gson gson = new Gson();
        Type type = new TypeToken<Banner>() {
        }.getType();
        return gson.toJson(banner, type);
    }
}
