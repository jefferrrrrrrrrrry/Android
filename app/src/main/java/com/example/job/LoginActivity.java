package com.example.job;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    Button button_login;
    Button button_register;
    EditText text_account;
    EditText text_password;
    private boolean isFirstTime = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_login);
        button_login = findViewById(R.id.login_login);
        button_register = findViewById(R.id.login_register);
        ;
        text_account = findViewById(R.id.login_account);
        text_password = findViewById(R.id.login_password);
        text_account.setText("123");
        text_password.setText("456");
        initializeDataIfNeeded();
        User.loadUserList(this);
//        if(savedInstanceState!=null){
//            text_account.setText(savedInstanceState.getString("text_account"));
//            text_password.setText(savedInstanceState.getString("text_password"));
//            System.out.println("6666666666666666666666");
//        }

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = text_account.getText().toString();
                ;
                String password = text_password.getText().toString();
                authenticate(name, password);
            }
        });

        button_register.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View view) {
                                                   Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                                                   startActivity(intent);
                                               }
                                           }
        );
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("text_account", text_account.getText().toString());
        outState.putString("text_password", text_password.getText().toString());
    }

    private void authenticate(String username, String password) {
        User user = findUserInDatabase(username); // Assume you have a method to find user

        if (user == null) {
            Toast.makeText(this, "不存在的用户名", Toast.LENGTH_SHORT).show();
            clearInputFields();
        } else {
            if (user.getPassword().equals(password)) {
                Toast.makeText(this, "已成功登录", Toast.LENGTH_SHORT).show();
                // Add your code for successful login here
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                Module.getInstance().setCurrent_user(user);
                startActivity(intent);
            } else {
                Toast.makeText(this, "密码错误，请重新输入", Toast.LENGTH_SHORT).show();
                clearInputFields();
            }
        }
    }

    private void clearInputFields() {
        text_account.setText("");
        text_password.setText("");
    }

    private User findUserInDatabase(String username) {
        // Implement your method to find user in database
        // For example:
        // return database.findUser(username);
        return User.getUser(username);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isFirstTime) {
            isFirstTime = false;
        } else
        // 在这里编写你自己的代码
        {
            text_account.setText("");
            text_password.setText("");
        }// 例如，刷新UI，重新开始动画，恢复暂停的任务等
    }

    private void initializeDataIfNeeded() {
        String fileName = "user_data.json";
        File file = new File(getFilesDir(), fileName);
        if (!file.exists()) {
            try {
                FileUtils.copyAssetToInternalStorage(this, fileName, fileName);
            } catch (IOException e) {
                e.printStackTrace();
                // Handle the error
            }
        }
    }
}