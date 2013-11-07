package com.qsoft.ondio.util;

import com.qsoft.ondio.accountmanager.ParseComServer;
import com.qsoft.ondio.accountmanager.ServerAuthenticate;

public class Common
{
    public final static String CONTENT_AUTHORITY = "com.qsoft.onlinedio";
    // Constants representing column positions from PROJECTION.
    public static final int COLUMN_ID = 0;
    public static String TYPE_SYNC = "TYPE_SYNC";
    public final static String PARAM_USER_ID = "USER_ID";
    private final int REQ_SIGNUP = 1;
    public final static String ARG_ACCOUNT_TYPE = "com.qsoft.onlinedio";
    public final static String ARG_AUTH_TYPE = "AUTH_TYPE";
    public final static String ARG_ACCOUNT_NAME = "ACCOUNT_NAME";
    public final static String ARG_IS_ADDING_NEW_ACCOUNT = "IS_ADDING_ACCOUNT";
    public static final String KEY_ERROR_MESSAGE = "ERR_MSG";
    public final static String PARAM_USER_PASS = "USER_PASS";
    public static final ServerAuthenticate sServerAuthenticate = new ParseComServer();

    public static final String USERDATA_USER_OBJ_ID = "userObjectId";   //Parse.com object id
    public static final String AUTHTOKEN_TYPE_READ_ONLY = "Read only";
    public static final String AUTHTOKEN_TYPE_READ_ONLY_LABEL = "Read only access to an Udinic account";

    public static final String AUTHTOKEN_TYPE_FULL_ACCESS = "Full access";
    public static final String AUTHTOKEN_TYPE_FULL_ACCESS_LABEL = "Full access to an Udinic account";
    public static final int COLUMN_FEED_ID = 1;
    public static final int COLUMN_USER_ID = 2;
    public static final int COLUMN_TITLE = 3;
    public static final int COLUMN_THUMBNAIL = 4;
    public static final int COLUMN_DESCRIPTION = 5;
    public static final int COLUMN_SOUND_PATH = 6;
    public static final int COLUMN_DURATION = 7;
    public static final int COLUMN_PLAYED = 8;
    public static final int COLUMN_CREATED_AT = 9;
    public static final int COLUMN_UPDATED_AT = 10;
    public static final int COLUMN_LIKES = 11;
    public static final int COLUMN_VIEWED = 12;
    public static final int COLUMN_COMMENTS = 13;
    public static final int COLUMN_USERNAME = 14;
    public static final int COLUMN_DISPLAY_NAME = 15;
    public static final int COLUMN_AVATAR = 16;

}
