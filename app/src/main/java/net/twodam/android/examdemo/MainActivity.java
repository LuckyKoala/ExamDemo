package net.twodam.android.examdemo;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText textUsername, textPassword;
    private Button buttonRegister, buttonLogin;
    private DBHelper dbHelper;
    public static UserDao userDao;
    public static QuestionDao questionDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DBHelper(this);
        userDao = new UserDao(dbHelper);
        questionDao = new QuestionDao(dbHelper);

        textUsername = (EditText) findViewById(R.id.textUsername);
        textPassword = (EditText) findViewById(R.id.textPassword);
        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = textUsername.getText().toString().trim();
                String password = textPassword.getText().toString().trim();
                if(username.isEmpty() || password.isEmpty()) {
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("系统提示")
                            .setMessage("用户名或密码为空")
                            .show();
                } else {
                    userDao.register(username, password);
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("系统提示")
                            .setMessage("注册完毕")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //Do nothing
                                }
                            })
                            .show();
                }
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = textUsername.getText().toString().trim();
                String password = textPassword.getText().toString().trim();
                if(username.isEmpty() || password.isEmpty()) {
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("系统提示")
                            .setMessage("用户名或密码为空")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //Do nothing
                                }
                            })
                            .show();
                } else {
                    if(userDao.login(username, password)){
                        Intent intent = new Intent(MainActivity.this, ExamActivity.class);
                        MainActivity.this.startActivity(intent);
                    } else {
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("系统提示")
                                .setMessage("用户名或密码错误")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //Do nothing
                                    }
                                })
                                .show();
                    }
                }
            }
        });
    }
}
