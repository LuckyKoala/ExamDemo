package net.twodam.android.examdemo;

import android.content.ContentValues;

import static net.twodam.android.examdemo.QuestionReaderContract.QuestionEntry.COLUMN_NAME_ANSWER_INDEX;
import static net.twodam.android.examdemo.QuestionReaderContract.QuestionEntry.COLUMN_NAME_CANDIDATE;
import static net.twodam.android.examdemo.QuestionReaderContract.QuestionEntry.COLUMN_NAME_CAPTION;

/**
 * Created by jkx5 on 2017/11/16.
 */

public class Question {
    private String caption;
    private int answerIndex;
    private String[] candidates;

    public Question(String caption, int answerIndex, String canditateStr) {
        this(caption, answerIndex, splitToCandidateArray(canditateStr));
    }

    public Question(String caption, int answerIndex, String[] canditates) {
        this.caption = caption;
        this.answerIndex = answerIndex;
        this.candidates = canditates;
    }

    public boolean isRightAnswer(int index) {
        return answerIndex == index;
    }

    public String getCaption() {
        return caption;
    }

    public int getAnswerIndex() {
        return answerIndex;
    }

    public String[] getCandidates() {
        int length = candidates.length;
        String[] suitableCandidates = new String[4];
        for (int i = 0; i < 4; i++) {
            suitableCandidates[i] = i<length ? candidates[i] : "--此处无选项--";
        }
        return suitableCandidates;
    }

    public String getCandidateStr() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < candidates.length; i++) {
            builder.append(candidates[i]).append(';');
        }
        return builder.toString();
    }

    private static String[] splitToCandidateArray(String str) {
        return str.split(";");
    }

    public ContentValues toValues() {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_CAPTION, caption);
        values.put(COLUMN_NAME_ANSWER_INDEX, answerIndex);
        values.put(COLUMN_NAME_CANDIDATE, getCandidateStr());
        return values;
    }
}
