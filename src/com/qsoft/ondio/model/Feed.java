package com.qsoft.ondio.model;

/**
 * Created with IntelliJ IDEA.
 * User: thinhdd
 * Date: 10/12/13
 * Time: 10:48 AM
 * To change this template use File | Settings | File Templates.
 */
public class Feed
{
    private String feedTitle;
    private String feedUserName;
    private String feedLike;
    private String feedComment;
    private String feedDays;

    public Feed(String feedTitle, String feedUserName, String feedLike, String feedComment, String feedDays)
    {
        this.feedTitle = feedTitle;
        this.feedUserName = feedUserName;
        this.feedLike = feedLike;
        this.feedComment = feedComment;
        this.feedDays = feedDays;
    }

    public String getFeedTitle()
    {
        return feedTitle;
    }

    public void setFeedTitle(String feedTitle)
    {
        this.feedTitle = feedTitle;
    }

    public String getFeedUserName()
    {
        return feedUserName;
    }

    public void setFeedUserName(String feedUserName)
    {
        this.feedUserName = feedUserName;
    }

    public String getFeedLike()
    {
        return feedLike;
    }

    public void setFeedLike(String feedLike)
    {
        this.feedLike = feedLike;
    }

    public String getFeedComment()
    {
        return feedComment;
    }

    public void setFeedComment(String feedComment)
    {
        this.feedComment = feedComment;
    }

    public String getFeedDays()
    {
        return feedDays;
    }

    public void setFeedDays(String feedDays)
    {
        this.feedDays = feedDays;
    }
}
