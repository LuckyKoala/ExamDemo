package net.twodam.android.examdemo;

import android.provider.BaseColumns;

/**
 * Created by iwar on 2017/11/17.
 */

public final class UserReaderContract {
    private UserReaderContract() {}

    public static class UserEntry implements BaseColumns {
        public static final String TABLE_NAME = "user";
        public static final String COLUMN_NAME_USERNAME = "username";
        public static final String COLUMN_NAME_PASSWORD = "password";
        public static final String COLUMN_NAME_LAST_SCORE= "last_score";
    }
}
