package com.qsoft.ondio.model;

import android.content.ContentValues;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.qsoft.ondio.data.dao.ProfileContract;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Profile implements Serializable
{

    @JsonProperty("id")
    public int id;
    @JsonProperty("facebook_id")
    public int facebook_id;
    @JsonProperty("username")
    public String username;
    @JsonProperty("password")
    public String password;
    @JsonProperty("avatar")
    public String avatar;
    @JsonProperty("cover_image")
    public String cover_image;
    @JsonProperty("display_name")
    public String display_name;
    @JsonProperty("full_name")
    public String full_name;
    @JsonProperty("phone")
    public String phone;
    @JsonProperty("birthday")
    public String birthday;
    @JsonProperty("gender")
    public int gender;
    @JsonProperty("country_id")
    public String country_id;
    @JsonProperty("storage_plan_id")
    public int storage_plan_id;
    @JsonProperty("description")
    public String description;
    @JsonProperty("created_at")
    public String created_at;
    @JsonProperty("updated_at")
    public String updated_at;
    @JsonProperty("sounds")
    public String sounds;
    @JsonProperty("favorites")
    public String favorites;
    @JsonProperty("likes")
    public String likes;
    @JsonProperty("followings")
    public String followings;
    @JsonProperty("audiences")
    public String audiences;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getFacebook_id()
    {
        return facebook_id;
    }

    public void setFacebook_id(int facebook_id)
    {
        this.facebook_id = facebook_id;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getAvatar()
    {
        return avatar;
    }

    public void setAvatar(String avatar)
    {
        this.avatar = avatar;
    }

    public String getCover_image()
    {
        return cover_image;
    }

    public void setCover_image(String cover_image)
    {
        this.cover_image = cover_image;
    }

    public String getDisplay_name()
    {
        return display_name;
    }

    public void setDisplay_name(String display_name)
    {
        this.display_name = display_name;
    }

    public String getFull_name()
    {
        return full_name;
    }

    public void setFull_name(String full_name)
    {
        this.full_name = full_name;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getBirthday()
    {
        return birthday;
    }

    public void setBirthday(String birthday)
    {
        this.birthday = birthday;
    }

    public int getGender()
    {
        return gender;
    }

    public void setGender(int gender)
    {
        this.gender = gender;
    }

    public String getCountry_id()
    {
        return country_id;
    }

    public void setCountry_id(String country_id)
    {
        this.country_id = country_id;
    }

    public int getStorage_plan_id()
    {
        return storage_plan_id;
    }

    public void setStorage_plan_id(int storage_plan_id)
    {
        this.storage_plan_id = storage_plan_id;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getCreated_at()
    {
        return created_at;
    }

    public void setCreated_at(String created_at)
    {
        this.created_at = created_at;
    }

    public String getUpdated_at()
    {
        return updated_at;
    }

    public void setUpdated_at(String updated_at)
    {
        this.updated_at = updated_at;
    }

    public String getSounds()
    {
        return sounds;
    }

    public void setSounds(String sounds)
    {
        this.sounds = sounds;
    }

    public String getFavorites()
    {
        return favorites;
    }

    public void setFavorites(String favorites)
    {
        this.favorites = favorites;
    }

    public String getLikes()
    {
        return likes;
    }

    public void setLikes(String likes)
    {
        this.likes = likes;
    }

    public String getFollowings()
    {
        return followings;
    }

    public void setFollowings(String followings)
    {
        this.followings = followings;
    }

    public String getAudiences()
    {
        return audiences;
    }

    public void setAudiences(String audiences)
    {
        this.audiences = audiences;
    }

    public ContentValues getContentValues()
    {
        ContentValues values = new ContentValues();
        values.put(ProfileContract.ID, id);
        values.put(ProfileContract.DISPLAY_NAME, display_name);
        values.put(ProfileContract.FULL_NAME, full_name);
        values.put(ProfileContract.PHONE, phone);
        values.put(ProfileContract.BIRTHDAY, birthday);
        values.put(ProfileContract.GENDER, gender);
        values.put(ProfileContract.COUNTRY_ID, country_id);
        values.put(ProfileContract.DESCRIPTION, description);
        values.put(ProfileContract.AVATAR, avatar);
        values.put(ProfileContract.COVER_IMAGE, cover_image);
        return values;
    }
}
