package net.twodam.android.examdemo;

import android.provider.BaseColumns;

/**
 * Created by jkx5 on 2017/11/16.
 */

public final class QuestionReaderContract {
    private QuestionReaderContract() {}

    public static class QuestionEntry implements BaseColumns {
        public static final String TABLE_NAME = "question";
        public static final String COLUMN_NAME_CAPTION = "caption";
        public static final String COLUMN_NAME_ANSWER_INDEX = "answer_index";
        public static final String COLUMN_NAME_CANDIDATE = "candidate_str";
    }
}
