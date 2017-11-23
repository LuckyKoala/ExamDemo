package net.twodam.android.examdemo;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

public class ExamActivity extends AppCompatActivity {
    private TextView textCaption;
    private RadioButton buttonA, buttonB, buttonC, buttonD;

    private Question[] questions;
    private Question currentQuestion;
    private int cursor = 0;
    private int boundary;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        textCaption = (TextView) findViewById(R.id.textCaption);
        buttonA = (RadioButton) findViewById(R.id.buttonA);
        buttonB = (RadioButton) findViewById(R.id.buttonB);
        buttonC = (RadioButton) findViewById(R.id.buttonC);
        buttonD = (RadioButton) findViewById(R.id.buttonD);

        questions = MainActivity.questionDao.getAllQuestions();
        boundary = questions.length;
        System.out.println(String.format("Boundary: %d", boundary));
        showNextQuestion(buttonA);
    }

    private boolean hasNextQuestion() {
        return cursor < boundary && cursor >= 0;
    }

    private void moveCursorForward() {
        cursor++;
    }

    private void showNextQuestion(RadioButton radioButton) {
        radioButton.setChecked(false);
        if(hasNextQuestion()) {
            currentQuestion = questions[cursor];
            textCaption.setText(currentQuestion.getCaption());
            String[] candidates = currentQuestion.getCandidates();
            buttonA.setText(candidates[0]);
            buttonB.setText(candidates[1]);
            buttonC.setText(candidates[2]);
            buttonD.setText(candidates[3]);
            moveCursorForward();
        } else {
            endExam();
        }
    }

    private void endExam() {
        final User currentUser = MainActivity.userDao.getCurrentUser();

        new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage(String.format("考试结束啦,历史成绩为%d,本次成绩为%d", currentUser.getLast_score(), score))
                .setPositiveButton("退出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        currentUser.setLast_score(score);
                        MainActivity.userDao.update(currentUser);
                        finish();
                    }
                })
                .show();
    }

    private void rightAnswer(RadioButton radioButton) {
        score++;
        showNextQuestion(radioButton);
    }

    private void wrongAnswer(final RadioButton radioButton) {
        new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("答错啦")
                .setPositiveButton("继续", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showNextQuestion(radioButton);
                    }
                })
                .setNegativeButton("重做", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Do nothing
                    }
                })
                .show();
    }

    public void onRadioButtonClicked(View view) {
        RadioButton radioButton = (RadioButton) view;
        boolean checked = radioButton.isChecked();
        int answerIndex = -1;
        switch(view.getId()) {
            case R.id.buttonA:
                answerIndex = 0;
                break;
            case R.id.buttonB:
                answerIndex = 1;
                break;
            case R.id.buttonC:
                answerIndex = 2;
                break;
            case R.id.buttonD:
                answerIndex = 3;
                break;
        }

        if (checked && currentQuestion.isRightAnswer(answerIndex)) {
            rightAnswer(radioButton);
        } else {
            wrongAnswer(radioButton);
        }
    }
}
