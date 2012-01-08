
package com.gsbina.android.foltter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "foltter";
    private static final String USER_TABLE = "d_user";
    private static final String FOLDER_TABLE = "d_folder";
    private static final String MESSAGE_TABLE = "d_message";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String name = FoltterController.NAME + " TEXT NOT NULL";
        String twitterId = FoltterController.TWITTER_ID + " INTEGER NOT NULL";
        String screenName = FoltterController.SCREEN_NAME + " TEXT NOT NULL";
        String folderId = FoltterController.FOLDER_ID + " INTEGER";
        String imageUrl = FoltterController.IMAGE_URL + " TEXT";
        String messageId = FoltterController.MESSAGE_ID + " INTEGER NOT NULL";
        String sender_id = FoltterController.SENDER_ID + " INTEGER NOT NULL";
        String message = FoltterController.MESSAGE + " TEXT";
        String send_ts = FoltterController.SEND_TS + " TEXT NOT NULL";
        String read_ts = FoltterController.READ_TS + " TEXT";

        String createUserTable = makeSqlCreateTable(USER_TABLE, twitterId,
                screenName, name, imageUrl, folderId);
        String createFolderTable = makeSqlCreateTable(FOLDER_TABLE, name);
        String createMessageTable = makeSqlCreateTable(MESSAGE_TABLE,
                messageId, message, sender_id, send_ts, read_ts);
        db.execSQL(createUserTable);
        db.execSQL(createFolderTable);
        db.execSQL(createMessageTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
    }

    private String makeSqlCreateTable(String tableName, String... columns) {
        StringBuffer sb = new StringBuffer("CREATE TABLE " + tableName);
        sb.append(" (_id INTEGER PRIMARY KEY AUTOINCREMENT");
        for (String column : columns) {
            sb.append(", " + column);
        }
        sb.append(")");

        return null;
    }
}
