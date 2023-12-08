package com.example.job;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

public class UpdateProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        Objects.requireNonNull(getSupportActionBar()).hide();

        findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.update_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile(((EditText)findViewById(R.id.password_edit_text)).getText().toString(),((EditText)findViewById(R.id.confirm_password_edit_text)).getText().toString());
            }
        });

        findViewById(R.id.delete_account_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAccount();
                Intent intent = new Intent(UpdateProfileActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }

    // Here should be your implementation of updateProfile and deleteAccount methods
    private void updateProfile(String password,String confirm) {
        if(!password.equals(confirm)){
            Toast.makeText(getApplicationContext(), "两次密码不一致", Toast.LENGTH_SHORT).show();
            return;
        }else {
            User current = Module.getInstance().getUser();
            current.update(current.getUsername(),password);
            User.saveUserList(this);
            Toast.makeText(getApplicationContext(), "成功更新", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        // ...
    }

    private void deleteAccount() {
        Module.getInstance().deleteAccount();
        User.saveUserList(this);
        finish();
        // ...
    }
}