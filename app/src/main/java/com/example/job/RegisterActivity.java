package com.example.job;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    Button register;
    Button back;
    EditText text_account;
    EditText text_password;
    EditText text_password2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_register);
        register = findViewById(R.id.re_register);
        back = findViewById(R.id.re_back);
        text_account = findViewById(R.id.re_text);
        text_password = findViewById(R.id.re_password);
        text_password2 = findViewById(R.id.re_password2);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String account = text_account.getText().toString();
                String password = text_password.getText().toString();
                String password2 = text_password2.getText().toString();
                if(register(account,password,password2)) {
                    finish();
                }
                /*if (account.length() < 1 || password.length() < 1) {
                    Toast.makeText(getApplicationContext(), "未填写账号/密码", Toast.LENGTH_SHORT).show();
                } else if (false) {
                    Toast.makeText(getApplicationContext(), "该账号已存在", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(password2)) {
                    Toast.makeText(getApplicationContext(), "密码与确认密码不同，请重新输入密码", Toast.LENGTH_SHORT).show();
                } else {
                    // TODO: register
                    Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_SHORT).show();
                    finish();
                }*/
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    public Boolean register(String username,String password,String confirm){
        if(User.getUser(username)!=null){
            Toast.makeText(getApplicationContext(), "该账号已存在", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!User.normaluser(username)){
            Toast.makeText(getApplicationContext(), "用户名不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!User.noramlpassword(password)){
            Toast.makeText(getApplicationContext(), "密码至少8位，并且由数字和字母组成", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!confirm.equals(password)){
            Toast.makeText(getApplicationContext(), "两次密码不同，请重新输入", Toast.LENGTH_SHORT).show();
            return false;
        }
        Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_SHORT).show();
        User.register(username,password);
        //数据库里面加入user
        return true;
    }
}