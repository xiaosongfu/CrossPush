package com.fuxiaosong.wotui.activity.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.fuxiaosong.wotui.R;
import com.fuxiaosong.wotui.activity.entry.LoginActivity;
import com.fuxiaosong.wotui.activity.main.MainActivity;
import com.fuxiaosong.wotui.model.User;

import cn.bmob.v3.Bmob;

/**
 * Created by fuxiaosong on 16/8/28.
 */
public class WelcomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //Activity全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN , WindowManager.LayoutParams.FLAG_FULLSCREEN);


        //初始化Bmob
        Bmob.initialize(this, "2f65675e2c58b7010206b5b274e4fc1f");

        //2.5秒后跳转
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                //已经登录过,则是跳转到 MainActivity,否则跳转到 LoginActivity
                if(User.getCurrentUser() == null){
                    intent = new Intent(WelcomeActivity.this , LoginActivity.class);
                }else{
                    intent = new Intent(WelcomeActivity.this , MainActivity.class);
                }
                startActivity(intent);
                //当前的Activity需要finish
                WelcomeActivity.this.finish();
            }
        }, 2500);
    }
}