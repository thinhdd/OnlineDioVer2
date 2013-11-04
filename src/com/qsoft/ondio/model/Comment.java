package com.qsoft.ondio.model;

/**
 * Created with IntelliJ IDEA.
 * User: thinhdd
 * Date: 10/18/13
 * Time: 10:04 AM
 * To change this template use File | Settings | File Templates.
 */
public class Comment
{
    private String account;
    private String comments;
    private String timeComment;

    public String getAccount()
    {
        return account;
    }

    public void setAccount(String account)
    {
        this.account = account;
    }

    public String getComments()
    {
        return comments;
    }

    public void setComments(String comments)
    {
        this.comments = comments;
    }

    public String getTimeComment()
    {
        return timeComment;
    }

    public void setTimeComment(String timeComment)
    {
        this.timeComment = timeComment;
    }

    public Comment(String account, String comments, String timeComment)
    {
        this.account = account;
        this.comments = comments;
        this.timeComment = timeComment;
    }
}
