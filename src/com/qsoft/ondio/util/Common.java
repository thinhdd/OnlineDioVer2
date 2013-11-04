package com.qsoft.ondio.util;

import com.qsoft.ondio.accountmanager.ParseComServer;
import com.qsoft.ondio.accountmanager.ServerAuthenticate;

public class Common
{
    public final static String CONTENT_AUTHORITY = "com.qsoft.onlinedio";
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
}
