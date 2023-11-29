package com.example.job;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    Button button_login;
    Button button_register;
    EditText text_account;
    EditText text_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_login);
        button_login = findViewById(R.id.login_login);
        button_register = findViewById(R.id.login_register);;
        text_account = findViewById(R.id.login_account);
        text_password = findViewById(R.id.login_password);

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = text_account.getText().toString();;
                String password = text_password.getText().toString();
                authenticate(name,password);
                /*if (Objects.equals(name, "123") && Objects.equals(password, "456")) { // TODO: condition
                    Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "用户不存在或密码错误", Toast.LENGTH_SHORT).show();
                }*/
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
    private void authenticate(String username, String password) {
        User user = findUserInDatabase(username); // Assume you have a method to find user

        if (user == null) {
            Toast.makeText(this, "不存在的用户名", Toast.LENGTH_SHORT).show();
            clearInputFields();
        } else {
            if (user.getPassword().equals(password)) {
                Toast.makeText(this, "已成功登录", Toast.LENGTH_SHORT).show();
                // Add your code for successful login here
                System.out.println(user.getUsername());
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
}