package com.fuxiaosong.wotui.activity.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.fuxiaosong.wotui.R;

/**
 * Created by fuxiaosong on 16/8/31.
 */
public class AboutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        //修改 ActionBar 标题为 About
        getSupportActionBar().setTitle(getResources().getString(R.string.about));

    }
}
