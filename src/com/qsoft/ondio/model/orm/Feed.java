package com.qsoft.ondio.model.orm;


import android.content.ContentValues;
import android.provider.BaseColumns;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.tojc.ormlite.android.annotation.AdditionalAnnotation;

@AdditionalAnnotation.Contract
@DatabaseTable(tableName = "feeds")
@AdditionalAnnotation.DefaultContentUri(authority = "com.qsoft.onlinedio.orm", path = "feeds")
@AdditionalAnnotation.DefaultContentMimeTypeVnd(name = "com.qsoft.onlinedio.orm.provider", type = "feeds")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Feed
{
    @DatabaseField(columnName = BaseColumns._ID, generatedId = true)
    @AdditionalAnnotation.DefaultSortOrder
    private Long _id;
    @DatabaseField
    public String account_id;
    @DatabaseField
    @JsonProperty("id")
    public String feed_id;
    @DatabaseField
    @JsonProperty("user_id")
    public String user_id;
    @DatabaseField
    @JsonProperty("title")
    public String title;
    @DatabaseField
    @JsonProperty("thumbnail")
    public String thumbnail;
    @DatabaseField
    @JsonProperty("description")
    public String description;
    @DatabaseField
    @JsonProperty("sound_path")
    public String sound_path;
    @DatabaseField
    @JsonProperty("duration")
    public String duration;
    @DatabaseField
    @JsonProperty("played")
    public String played;
    @DatabaseField
    @JsonProperty("created_at")
    public String created_at;
    @DatabaseField
    @JsonProperty("updated_at")
    public String updated_at;
    @DatabaseField
    @JsonProperty("likes")
    public String likes;
    @DatabaseField
    @JsonProperty("viewed")
    public String viewed;
    @DatabaseField
    @JsonProperty("comments")
    public String comments;
    @DatabaseField
    @JsonProperty("username")
    public String username;
    @DatabaseField
    @JsonProperty("display_name")
    public String display_name;
    @DatabaseField
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

    public String getFeed_id() {
        return feed_id;
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

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public ContentValues getContentValues()
    {
        ContentValues values = new ContentValues();
        values.put(FeedContract._ID, _id);
        values.put(FeedContract.ACCOUNT_ID, account_id);
        values.put(FeedContract.FEED_ID, feed_id);
        values.put(FeedContract.USER_ID, user_id);
        values.put(FeedContract.TITLE, title);
        values.put(FeedContract.THUMBNAIL, thumbnail);
        values.put(FeedContract.SOUND_PATH, sound_path);
        values.put(FeedContract.DESCRIPTION, description);
        values.put(FeedContract.DURATION, duration);
        values.put(FeedContract.PLAYED, played);
        values.put(FeedContract.CREATED_AT, created_at);
        values.put(FeedContract.UPDATED_AT, updated_at);
        values.put(FeedContract.LIKES, likes);
        values.put(FeedContract.VIEWED, viewed);
        values.put(FeedContract.COMMENTS, comments);
        values.put(FeedContract.USERNAME, username);
        values.put(FeedContract.DISPLAY_NAME, display_name);
        values.put(FeedContract.AVATAR, avatar);
        return values;
    }

}
