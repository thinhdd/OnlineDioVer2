package com.qsoft.ondio.model;


import android.content.ContentValues;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.qsoft.ondio.data.dao.HomeContract;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Home
{

    public String account_id;
    @JsonProperty("id")
    public String id;
    @JsonProperty("user_id")
    public String user_id;
    @JsonProperty("title")
    public String title;
    @JsonProperty("thumbnail")
    public String thumbnail;
    @JsonProperty("description")
    public String description;
    @JsonProperty("sound_path")
    public String sound_path;
    @JsonProperty("duration")
    public String duration;
    @JsonProperty("played")
    public String played;
    @JsonProperty("created_at")
    public String created_at;
    @JsonProperty("updated_at")
    public String updated_at;
    @JsonProperty("likes")
    public String likes;
    @JsonProperty("viewed")
    public String viewed;
    @JsonProperty("comments")
    public String comments;
    @JsonProperty("username")
    public String username;
    @JsonProperty("display_name")
    public String display_name;
    @JsonProperty("avatar")
    public String avatar;

    public String getAccount_id()
    {
        return account_id;
    }

    public void setAccount_id(String account_id)
    {
        this.account_id = account_id;
    }

    public String getId()
    {
        return id;
    }

    public String getUser_id()
    {
        return user_id;
    }

    public String getTitle()
    {
        return title;
    }

    public String getThumbnail()
    {
        return thumbnail;
    }

    public String getSound_path()
    {
        return sound_path;
    }

    public String getDescription()
    {
        return description;
    }

    public String getDuration()
    {
        return duration;
    }

    public String getPlayed()
    {
        return played;
    }

    public String getCreated_at()
    {
        return created_at;
    }

    public String getUpdated_at()
    {
        return updated_at;
    }

    public String getLikes()
    {
        return likes;
    }

    public String getViewed()
    {
        return viewed;
    }

    public String getComments()
    {
        return comments;
    }

    public String getUsername()
    {
        return username;
    }

    public String getDisplay_name()
    {
        return display_name;
    }

    public String getAvatar()
    {
        return avatar;
    }

    public ContentValues getContentValues()
    {
        ContentValues values = new ContentValues();
        values.put(HomeContract.ACCOUNT_ID, account_id);
        values.put(HomeContract.ID, id);
        values.put(HomeContract.USER_ID, user_id);
        values.put(HomeContract.TITLE, title);
        values.put(HomeContract.THUMBNAIL, thumbnail);
        values.put(HomeContract.SOUND_PATH, sound_path);
        values.put(HomeContract.DESCRIPTION, description);
        values.put(HomeContract.DURATION, duration);
        values.put(HomeContract.PLAYED, played);
        values.put(HomeContract.CREATED_AT, created_at);
        values.put(HomeContract.UPDATED_AT, updated_at);
        values.put(HomeContract.LIKES, likes);
        values.put(HomeContract.VIEWED, viewed);
        values.put(HomeContract.COMMENTS, comments);
        values.put(HomeContract.USERNAME, username);
        values.put(HomeContract.DISPLAY_NAME, display_name);
        values.put(HomeContract.AVATAR, avatar);
        return values;
    }

}
