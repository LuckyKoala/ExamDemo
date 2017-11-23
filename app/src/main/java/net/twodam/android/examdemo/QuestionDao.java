package net.twodam.android.examdemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import net.twodam.android.examdemo.QuestionReaderContract.QuestionEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static net.twodam.android.examdemo.QuestionReaderContract.QuestionEntry.*;

/**
 * Created by jkx5 on 2017/11/16.
 */

public class QuestionDao {
    private DBHelper dbHelper;

    public QuestionDao(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public Question[] getAllQuestions() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                COLUMN_NAME_CAPTION,
                COLUMN_NAME_ANSWER_INDEX,
                COLUMN_NAME_CANDIDATE
        };
        Cursor cursor = db.query(TABLE_NAME, projection,
                null, null, null, null, null);
        Question[] questions = new Question[cursor.getCount()];
        int i = 0;
        while(cursor.moveToNext()) {
            String caption = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_CAPTION));
            int answerIndex = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NAME_ANSWER_INDEX));
            String candidateStr = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_CANDIDATE));
            System.out.println(String.format("caption: %s, answerIndex: %d, candidateStr: %s", caption, answerIndex, candidateStr));
            questions[i++] = new Question(caption, answerIndex, candidateStr);
        }
        cursor.close();

        return questions;
    }
}
