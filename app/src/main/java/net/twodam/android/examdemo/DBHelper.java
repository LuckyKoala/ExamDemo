package net.twodam.android.examdemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import net.twodam.android.examdemo.QuestionReaderContract.QuestionEntry;

import static android.provider.BaseColumns._ID;
import static net.twodam.android.examdemo.UserReaderContract.UserEntry;

/**
 * Created by iwar on 2017/11/20.
 */

public class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "QuestionDemo.db";

    private static final String SQL_CREATE_USER_ENTRIES = "CREATE TABLE "
            + UserEntry.TABLE_NAME + " (" + _ID + " INTEGER PRIMARY KEY,"
            + UserEntry.COLUMN_NAME_USERNAME + " TEXT,"
            + UserEntry.COLUMN_NAME_PASSWORD + " TEXT,"
            + UserEntry.COLUMN_NAME_LAST_SCORE + " INTEGER)";
    private static final String SQL_DELETE_USER_ENTRIES = "DROP TABLE IF EXISTS "
            + UserEntry.TABLE_NAME;
    private static final String SQL_CREATE_QUESTION_ENTRIES = "CREATE TABLE "
            + QuestionEntry.TABLE_NAME + " (" + QuestionEntry._ID + " INTEGER PRIMARY KEY,"
            + QuestionEntry.COLUMN_NAME_CAPTION + " TEXT,"
            + QuestionEntry.COLUMN_NAME_ANSWER_INDEX + " INTEGER,"
            + QuestionEntry.COLUMN_NAME_CANDIDATE + " TEXT)";
    private static final String SQL_DELETE_QUESTION_ENTRIES = "DROP TABLE IF EXISTS "
            + QuestionEntry.TABLE_NAME;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_USER_ENTRIES);
        db.execSQL(SQL_CREATE_QUESTION_ENTRIES);

        //init
        db.beginTransaction();
        try {
            db.insert(UserEntry.TABLE_NAME, null,
                    new User("test", "123456", 0).toValues());
            db.insert(QuestionEntry.TABLE_NAME, null,
                    new Question("Select second answer!", 1, new String[]{ "A", "B", "C", "D"}).toValues());
            db.insert(QuestionEntry.TABLE_NAME, null,
                    new Question("True or false?", 0, new String[]{ "true", "false"}).toValues());
            db.insert(QuestionEntry.TABLE_NAME, null,
                    new Question("1+3=?", 2, new String[]{ "1", "3", "4", "5", "6"}).toValues());
            db.insert(QuestionEntry.TABLE_NAME, null,
                    new Question("5/0=?", 0, new String[]{ "NaN", "5", "0", "1"}).toValues());

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_USER_ENTRIES);
        db.execSQL(SQL_DELETE_QUESTION_ENTRIES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
