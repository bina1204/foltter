
package com.gsbina.android.foltter;

import android.content.Context;

public class FoltterController {

    public static final String NAME = "name";
    public static final String TWITTER_ID = "twitter_id";
    public static final String SCREEN_NAME = "screen_name";
    public static final String FOLDER_ID = "folder_id";
    public static final String IMAGE_URL = "image_url";
    public static final String MESSAGE_ID = "message_id";
    public static final String SENDER_ID = "sender_id";
    public static final String MESSAGE = "message";
    public static final String SEND_TS = "send_ts";
    public static final String READ_TS = "read_ts";

    private Context mContext;

    public FoltterController(Context context) {
        mContext = context;
    }

    public boolean isExistFolder(int folderId) {
        DatabaseHelper db = new DatabaseHelper(mContext);
        return false;
    }

}
