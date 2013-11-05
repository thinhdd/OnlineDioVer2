package com.qsoft.ondio.data.dao;

import android.net.Uri;

/**
 * Created with IntelliJ IDEA.
 * User: thinhdd
 * Date: 11/5/13
 * Time: 9:16 AM
 * To change this template use File | Settings | File Templates.
 */
public class ProfileContract
{
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/com.qsoft.profile";
    public static final String CONTENT_TYPE_DIR = "vnd.android.cursor.dir/com.qsoft.profile";

    public static final String AUTHORITY = "com.qsoft.onlinedio";
    // content://<authority>/<path to type>
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/profile");

    public static final String _ID = "_id";

    public static final String ID = "id";

    public static final String FACEBOOK_ID = "facebook_id";

    public static final String USERNAME = "username";

    public static final String PASSWORD = "password";

    public static final String AVATAR = "avatar";

    public static final String COVER_IMAGE = "cover_image";

    public static final String DISPLAY_NAME = "display_name";

    public static final String FULL_NAME = "full_name";

    public static final String PHONE = "phone";

    public static final String BIRTHDAY = "birthday";

    public static final String GENDER = "gender";

    public static final String COUNTRY_ID = "country_id";

    public static final String STORAGE_PLAN_ID = "storage_plan_id";

    public static final String DESCRIPTION = "description";

    public static final String CREATED_AT = "created_at";

    public static final String UPDATE_AT = "updated_at";
}
