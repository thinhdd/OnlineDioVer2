package com.qsoft.ondio.data.dao.orm;

import com.qsoft.ondio.model.orm.Feed;
import com.qsoft.ondio.model.orm.FeedContract;
import com.tojc.ormlite.android.OrmLiteSimpleContentProvider;
import com.tojc.ormlite.android.framework.MatcherController;
import com.tojc.ormlite.android.framework.MimeTypeVnd;

/**
 * User: Le
 * Date: 11/11/13
 */
public class CCContentProvider extends OrmLiteSimpleContentProvider<GenericDatabaseHelper>
{
    @Override
    protected Class<GenericDatabaseHelper> getHelperClass()
    {
        return GenericDatabaseHelper.class;
    }

    @Override
    public boolean onCreate()
    {
        setMatcherController(new MatcherController()
                .add(Feed.class, MimeTypeVnd.SubType.DIRECTORY, "", FeedContract.CONTENT_URI_PATTERN_MANY)
                .add(Feed.class, MimeTypeVnd.SubType.ITEM, "#", FeedContract.CONTENT_URI_PATTERN_ONE)
        );
        return true;
    }
}