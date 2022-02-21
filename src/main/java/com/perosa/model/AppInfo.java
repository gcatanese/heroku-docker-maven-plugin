package com.perosa.model;

import org.json.JSONObject;

public class AppInfo {

    private String name;
    private String web_url;
    private String region;
    private int repo_size;

    public AppInfo(String json) {
        JSONObject myJson = new JSONObject(json);

        this.setName(myJson.getJSONObject("app").getString("name"));
        this.setRegion(myJson.getJSONObject("app").getJSONObject("region").getString("name"));
        this.setWeb_url(myJson.getJSONObject("app").getString("web_url"));
        this.setRepo_size(myJson.getJSONObject("app").getInt("repo_size"));

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeb_url() {
        return web_url;
    }

    public void setWeb_url(String web_url) {
        this.web_url = web_url;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getRepo_size() {
        return repo_size;
    }

    public void setRepo_size(int repo_size) {
        this.repo_size = repo_size;
    }
}
