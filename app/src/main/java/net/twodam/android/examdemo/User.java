package net.twodam.android.examdemo;

import android.content.ContentValues;

import static net.twodam.android.examdemo.UserReaderContract.UserEntry.*;

/**
 * Created by iwar on 2017/11/17.
 */

public class User {
    private long id;
    private String username;
    private String password;
    private int last_score;

    public User(long id, String username, String password, int last_score) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.last_score = last_score;
    }

    public User(String username, String password, int last_score) {
        this(0L, username, password, last_score);
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getLast_score() {
        return last_score;
    }

    public void setLast_score(int last_score) {
        this.last_score = last_score;
    }

    public ContentValues toValues() {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_USERNAME, username);
        values.put(COLUMN_NAME_PASSWORD, password);
        values.put(COLUMN_NAME_LAST_SCORE, last_score);
        return values;
    }
}
