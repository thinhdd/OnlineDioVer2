package com.qsoft.ondio.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.qsoft.ondio.model.orm.Feed;

import java.util.ArrayList;

/**
 * User: thinhdd
 * Date: 11/13/13
 * Time: 2:04 PM
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Homes {
    @JsonProperty("data")
    ArrayList<Feed> homeList;

    @JsonProperty("")
    String error;

    public ArrayList<Feed> getHomeList() {
        return homeList;
    }

    public void setHomeList(ArrayList<Feed> homeList) {
        this.homeList = homeList;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
