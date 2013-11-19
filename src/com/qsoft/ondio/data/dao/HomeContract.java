package com.qsoft.ondio.data.dao;

import android.net.Uri;

public class HomeContract
{
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/com.qsoft.home";
    public static final String CONTENT_TYPE_DIR = "vnd.android.cursor.dir/com.qsoft.home";

    public static final String AUTHORITY = "com.qsoft.onlinedio";
    // content://<authority>/<path to type>
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/home");

    public static final String _ID = "_id";

    public static final String ACCOUNT_ID= "account_id";

    public static final String FEED_ID = "feed_id";

    public static final String USER_ID = "user_id";

    public static final String TITLE = "title";

    public static final String THUMBNAIL = "thumbnail";

    public static final String SOUND_PATH = "sound_path";

    public static final String DESCRIPTION = "description";

    public static final String DURATION = "duration";

    public static final String PLAYED = "played";

    public static final String CREATED_AT = "created_at";

    public static final String UPDATED_AT = "updated_at";

    public static final String LIKES = "likes";

    public static final String VIEWED = "viewed";

    public static final String COMMENTS = "comments";

    public static final String USERNAME = "username";

    public static final String DISPLAY_NAME = "display_name";

    public static final String AVATAR = "avatar";


}
