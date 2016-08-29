package com.fuxiaosong.wotui.model;

import cn.bmob.v3.BmobUser;

/**
 * Created by fuxiaosong on 16/8/28.
 */
public class User extends BmobUser {
    String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
