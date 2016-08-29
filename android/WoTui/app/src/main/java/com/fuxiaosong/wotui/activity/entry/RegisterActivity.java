package com.fuxiaosong.wotui.activity.entry;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fuxiaosong.wotui.R;
import com.fuxiaosong.wotui.model.User;
import com.fuxiaosong.wotui.utils.MD5Utils;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by fuxiaosong on 16/8/28.
 */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText userNameET,passwordET;
    private Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //初始化视图和点击事件
        initViewsAndEvents();
    }

    /**
     * 初始化视图和点击事件
     *
     */
    public void initViewsAndEvents(){
        userNameET = (EditText) findViewById(R.id.user_name_et);
        passwordET = (EditText) findViewById(R.id.password_et);
        registerBtn = (Button) findViewById(R.id.register_btn);

        registerBtn.setOnClickListener(this);
    }

    /**
     * 注册
     *
     */
    public void register(){
        //获取用户名并判断用户名是否为空
        final String userName = userNameET.getText().toString();
        if ("".equals(userName)) {
            Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        //获取密码并判断密码是否为空
        final String password = passwordET.getText().toString();
        if ("".equals(password)) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        //封装User对象
        User user = new User();
        user.setUsername(userName);
        user.setPassword(MD5Utils.bytesToMD5(password.getBytes()));

        //开始注册
        user.signUp(new SaveListener<User>() {
            @Override
            public void done(User bmobUser, BmobException e) {
                //注册成功
                if (e == null) {
                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    RegisterActivity.this.finish();
                } else {
                    Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.register_btn){
            register();
        }
    }
}