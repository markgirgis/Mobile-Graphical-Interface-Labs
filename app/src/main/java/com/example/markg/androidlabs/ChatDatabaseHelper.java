package com.example.markg.androidlabs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by markg on 2017-10-18.
 */

public class ChatDatabaseHelper extends SQLiteOpenHelper {

    protected static final String TABLE_MESSAGES = "messages";
    protected static final String KEY_ID = "id";
    protected static final String KEY_MESSAGE = "message";

    private static final String DATABASE_NAME = "Chats.db";
    private static final int VERSION_NUM = 4;

    private static final String DATABASE_CREATE = "create table "
            + TABLE_MESSAGES + "( " + KEY_ID
            + " integer primary key autoincrement, " + KEY_MESSAGE
            + " text not null);";

    public ChatDatabaseHelper(Context ctx)
    {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);

        Log.i(ChatDatabaseHelper.class.getName(), "Calling onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(ChatDatabaseHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGES);
        onCreate(db);
    }
}
