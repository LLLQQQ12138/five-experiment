package com.example.login_test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HandlerActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mButton1, mButton2;
    private TextView textView;
    //声明当前时间类 注意时间类的两种格式
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd,hh-mm-ss");
    // private String time;
    //Handler处理机制 是用来处理消息的
    private Handler handler = new Handler() {
        //Handler里的handMessage方法
        public void handleMessage(Message msg) {
            //通过switch里的msg.what字段来判断传来的消息
            switch (msg.what){
                case 1:
                    String time= (String) msg.obj;
                    textView.setText(time);

                    break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);
        mButton1 = (Button) findViewById(R.id.btn1);
        //mButton2 = (Button) findViewById(R.id.btn2);
        textView = (TextView) findViewById(R.id.text);
        mButton1.setOnClickListener(this);
        //mButton2.setOnClickListener(this);
    }
    //Button的点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                //注意这里，必须new一个Thread类，否则数据不更新
                new Thread() {
                    //覆写Run方法
                    @Override
                    public void run() {
                        //要用一个while循坏
                        while (true) {
                            //更新时间
                            String time = format.format(new Date());
                            Message msg = handler.obtainMessage();
                            msg.obj = time;
                            msg.what = 1;
                            handler.sendMessage(msg);
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }.start();
                break;

        }
    }


    class MyThread extends Thread {

    }
}
