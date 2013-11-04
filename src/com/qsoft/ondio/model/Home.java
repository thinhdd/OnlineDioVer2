package com.qsoft.ondio.model;

import android.content.ContentValues;
import android.database.Cursor;
import com.qsoft.ondio.data.dao.HomeContract;

public class Home
{

    public final String id;

    public final String user_id;

    public final String title;

    public final String thumbnail;

    public final String sound_path;

    public final String description;

    public final String duration;

    public final String played;

    public final String created_at;

    public final String updated_at;

    public final String likes;

    public final String viewed;

    public final String comments;

    public final String username;

    public final String display_name;

    public final String avatar;


    public Home(String id, String user_id, String title, String thumbnail, String sound_path, String description
            , String duration, String played, String created_at, String updated_at, String likes, String viewed
            , String comments, String username, String display_name, String avatar)
    {
        this.id = id;
        this.user_id = user_id;
        this.title = title;
        this.thumbnail = thumbnail;
        this.sound_path = sound_path;
        this.description = description;
        this.duration = duration;
        this.played = played;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.likes = likes;
        this.viewed = viewed;
        this.comments = comments;
        this.username = username;
        this.display_name = display_name;
        this.avatar = avatar;
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

    public static Home fromCursor(Cursor cursor)
    {
        String id = cursor.getString(cursor.getColumnIndex(HomeContract.ID));
        String user_id = cursor.getString(cursor.getColumnIndex(HomeContract.USER_ID));
        String title = cursor.getString(cursor.getColumnIndex(HomeContract.TITLE));
        String thumbnail = cursor.getString(cursor.getColumnIndex(HomeContract.THUMBNAIL));
        String sound_path = cursor.getString(cursor.getColumnIndex(HomeContract.SOUND_PATH));
        String description = cursor.getString(cursor.getColumnIndex(HomeContract.DESCRIPTION));
        String duration = cursor.getString(cursor.getColumnIndex(HomeContract.DURATION));
        String played = cursor.getString(cursor.getColumnIndex(HomeContract.PLAYED));
        String created_at = cursor.getString(cursor.getColumnIndex(HomeContract.CREATED_AT));
        String updated_at = cursor.getString(cursor.getColumnIndex(HomeContract.UPDATED_AT));
        String likes = cursor.getString(cursor.getColumnIndex(HomeContract.LIKES));
        String viewed = cursor.getString(cursor.getColumnIndex(HomeContract.VIEWED));
        String comments = cursor.getString(cursor.getColumnIndex(HomeContract.COMMENTS));
        String username = cursor.getString(cursor.getColumnIndex(HomeContract.USERNAME));
        String display_name = cursor.getString(cursor.getColumnIndex(HomeContract.DISPLAY_NAME));
        String avatar = cursor.getString(cursor.getColumnIndex(HomeContract.AVATAR));

        return new Home(id, user_id, title, thumbnail, sound_path, description
                , duration, played, created_at, updated_at, likes, viewed
                , comments, username, display_name, avatar);
    }

}
