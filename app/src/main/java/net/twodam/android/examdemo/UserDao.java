package net.twodam.android.examdemo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import static net.twodam.android.examdemo.UserReaderContract.UserEntry.*;

/**
 * Created by iwar on 2017/11/17.
 */

public class UserDao {
    private DBHelper dbHelper;
    private User currentUser;

    public UserDao(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public boolean login(String username, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                _ID,
                COLUMN_NAME_LAST_SCORE
        };
        String selection = COLUMN_NAME_USERNAME + " = ? and " + COLUMN_NAME_PASSWORD + " = ? ";
        String[] selectionArgs = { username, password };
        Cursor cursor = db.query(TABLE_NAME, projection,
                selection, selectionArgs, null, null, null, "1");

        if(cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow(_ID));
            int last_score = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NAME_LAST_SCORE));
            this.currentUser = new User(id, username, password, last_score);
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }

    public void register(String username, String password) {
        final int last_score = 0;
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.insert(TABLE_NAME, null, new User(username, password, 0).toValues());
    }

    public boolean update(User user) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selection = _ID + " = ?";
        String[] selectionArgs = { Long.toString(user.getId()) };

        int count = db.update(
                TABLE_NAME,
                user.toValues(),
                selection,
                selectionArgs);
        return count == 1;
    }

    public User getCurrentUser() {
        return currentUser;
    }
}
