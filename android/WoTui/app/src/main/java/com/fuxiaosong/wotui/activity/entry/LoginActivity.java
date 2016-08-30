package com.fuxiaosong.wotui.activity.entry;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fuxiaosong.wotui.R;
import com.fuxiaosong.wotui.activity.main.MainActivity;
import com.fuxiaosong.wotui.model.User;
import com.fuxiaosong.wotui.utils.MD5Utils;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by fuxiaosong on 16/8/28.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText userNameET,passwordET;
    private Button loginBtn,registerBtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //修改 ActionBar 标题为 Login
        getSupportActionBar().setTitle(getResources().getString(R.string.login));

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
        loginBtn = (Button) findViewById(R.id.login_btn);

        loginBtn.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
    }

    /**
     * 登录
     *
     */
    public void login(){
        //获取用户名并判断用户名是否为空
        final String userName = userNameET.getText().toString();
        if("".equals(userName)){
            Toast.makeText(this , "用户名不能为空" , Toast.LENGTH_SHORT).show();
            return ;
        }

        //获取密码并判断密码是否为空
        final String password = passwordET.getText().toString();
        if("".equals(password)){
            Toast.makeText(this , "密码不能为空" , Toast.LENGTH_SHORT).show();
            return ;
        }

        //封装User对象
        User user = new User();
        user.setUsername(userName);
        user.setPassword(MD5Utils.bytesToMD5(password.getBytes()));

        //开始登录
        user.login(new SaveListener<User>() {
            @Override
            public void done(User bmobUser, BmobException e) {
                //登录成功
                if(e == null){
                    Intent intent = new Intent(LoginActivity.this , MainActivity.class);
                    startActivity(intent);
                    LoginActivity.this.finish();
                }else{
                    Toast.makeText(LoginActivity.this , "登录失败,用户名或密码错误" , Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            //注册,当前Activity不需要finish
            case R.id.register_btn:
                Intent intent = new Intent(this , RegisterActivity.class);
                startActivity(intent);
                break;
            //登录
            case R.id.login_btn:
                login();
                break;
        }
    }
}