package com.tweesky.cloudtools.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AppInfo {

    private String name;
    private String web_url;
    private String region;
    private int repo_size;
    private String command;

    public AppInfo(String json) {

        if(!isValidJson(json)) {
            throw new RuntimeException("Invalid json");
        }

        JSONObject myJson = new JSONObject(json);

        this.setName(myJson.getJSONObject("app").getString("name"));
        this.setRegion(myJson.getJSONObject("app").getJSONObject("region").getString("name"));
        if(!myJson.getJSONObject("app").isNull("web_url")) {
            this.setWeb_url(myJson.getJSONObject("app").getString("web_url"));
        }
        if(!myJson.getJSONObject("app").isNull("repo_size")) {
            this.setRepo_size(myJson.getJSONObject("app").getInt("repo_size"));
        }
        if(myJson.getJSONArray("dynos") != null && !myJson.getJSONArray("dynos").isEmpty()) {
            this.setCommand(myJson.getJSONArray("dynos").getJSONObject(0).getString("command"));
        }

    }

    boolean isValidJson(String json) {
        try {
            new JSONObject(json);
        } catch (JSONException ex) {
            try {
                new JSONArray(json);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
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

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}
