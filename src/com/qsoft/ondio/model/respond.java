package com.qsoft.ondio.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: thinhdd
 * Date: 11/14/13
 * Time: 5:24 PM
 * To change this template use File | Settings | File Templates.
 */
@JsonIgnoreProperties(ignoreUnknown = true)

public class Respond {
    @JsonProperty("data")
    ArrayList<Home> homeList;

    public ArrayList<Home> getHomeList()
    {
        return homeList;
    }

    public void setHomeList(ArrayList<Home> homeList)
    {
        this.homeList = homeList;
    }
}
