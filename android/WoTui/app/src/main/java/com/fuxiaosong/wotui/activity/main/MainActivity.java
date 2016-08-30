package com.fuxiaosong.wotui.activity.main;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fuxiaosong.wotui.R;
import com.fuxiaosong.wotui.activity.entry.LoginActivity;
import com.fuxiaosong.wotui.activity.setting.AboutActivity;
import com.fuxiaosong.wotui.model.User;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by fuxiaosong on 16/8/28.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText contentET;
    private Button pushBtn,clearBtn,pullBtn,copyBtn;

    //Handler
    private Handler handler;
    private final int PULL_DONE = 200;

    //系统剪切板服务对象
    ClipboardManager cm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化视图和点击事件
        initViewsAndEvents();

        //获取系统剪切板服务
        cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

        //handler 用于处理Pull完成后填充到 EditText 中
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == PULL_DONE){
                    contentET.setText(msg.obj.toString());
                    contentET.setSelection(msg.obj.toString().length());
                }
            }
        };
    }

    /**
     * 初始化视图和点击事件
     *
     */
    public void initViewsAndEvents(){
        contentET = (EditText) findViewById(R.id.content_et);
        pushBtn = (Button) findViewById(R.id.push_btn);
        clearBtn = (Button) findViewById(R.id.clear_btn);
        pullBtn = (Button) findViewById(R.id.pull_btn);
        copyBtn = (Button) findViewById(R.id.copy_btn);

        pushBtn.setOnClickListener(this);
        clearBtn.setOnClickListener(this);
        pullBtn.setOnClickListener(this);
        copyBtn.setOnClickListener(this);
    }

    /**
     * Push 函数
     *
     */
    public void push(){
        final String content = contentET.getText().toString();
        if("".equals(content)){
            Toast.makeText(this , "内容为空,不能Push" , Toast.LENGTH_SHORT).show();
        }else{
            User user = new User();
            user.setContent(content);

            user.update(User.getCurrentUser().getObjectId(), new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    String msg = "";
                    if(e == null){
                        msg = "Push 成功";
                    }else{
                        msg = "Push 失败";
                    }
                    Toast.makeText(MainActivity.this , msg , Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    /**
     * Pull 函数
     *
     */
    public void pull(){
        BmobQuery<User> query = new BmobQuery<>();
        query.addWhereEqualTo("objectId", User.getCurrentUser().getObjectId());
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> lstUsers, BmobException e) {
                String message = "";
                if(e==null){
                    Message msg = handler.obtainMessage();
                    msg.what = PULL_DONE;
                    msg.obj = lstUsers.get(0).getContent() == null ? "" : lstUsers.get(0).getContent();

                    handler.sendMessage(msg);

                    message = "Pull 成功";
                }else{
                    message = "Pull 失败";
                }
                Toast.makeText(MainActivity.this , message , Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Clear 函数
     *
     */
    public void clear(){
        contentET.setText("");
    }

    /**
     * Copy 函数
     *
     */
    public void copy(){
        ClipData data = ClipData.newPlainText("woTui" , contentET.getText().toString());
        cm.setPrimaryClip(data);
        Toast.makeText(MainActivity.this , "复制成功" , Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            //Push
            case R.id.push_btn:
                push();
                break;
            //Clear
            case R.id.clear_btn:
                clear();
                break;
            //Pull
            case R.id.pull_btn:
                pull();
                break;
            //Copy
            case R.id.copy_btn:
                copy();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //填充菜单
        getMenuInflater().inflate(R.menu.menu_main , menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.quit_item){
            //清除缓存用户对象
            User.logOut();

            //转到登录Activity
            Intent intent = new Intent(this , LoginActivity.class);
            startActivity(intent);
            this.finish();
        }else if(item.getItemId() == R.id.about_item){
            //转到 About Activity
            Intent intent = new Intent(this , AboutActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}