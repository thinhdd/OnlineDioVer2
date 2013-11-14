package com.qsoft.ondio.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 * User: thinhdd
 * Date: 11/13/13
 * Time: 2:04 PM
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Homes
{
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
