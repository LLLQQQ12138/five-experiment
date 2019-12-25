package com.example.login_test;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Notification;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.nio.Buffer;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private String userid;
    private EditText edit;


    //private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button prompt = (Button)findViewById(R.id.prompt);
        Button login = (Button)findViewById(R.id.login);
        Button input = (Button)findViewById(R.id.input);
        Button receive = (Button)findViewById(R.id.receive);
        Button yin_intent = (Button)findViewById(R.id.yin_intent);
        Button save = (Button)findViewById(R.id.save);
        Button load = (Button)findViewById(R.id.load);
        Button handler = (Button)findViewById(R.id.handler);
         edit = (EditText) findViewById(R.id.edit);


        prompt.setOnClickListener(this);
        login.setOnClickListener(this);
        input.setOnClickListener(this);
        receive.setOnClickListener(this);
        yin_intent.setOnClickListener(this);
        save.setOnClickListener(this);
        load.setOnClickListener(this);
        handler.setOnClickListener(this);

    }

   /* @Override
    protected void onDestroy() {
        super.onDestroy();
        String inputText = edit.getText().toString();
        save(inputText);
    }*/

    public void save(String inputText){

        FileOutputStream out = null;
        BufferedWriter writer = null;
        try{
            out = openFileOutput("data", Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(inputText);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try{
                if(writer!=null)
                {
                    writer.close();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

    }

    public String load(){
        FileInputStream in = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        try
        {
            in = openFileInput("data");
            reader = new BufferedReader(new InputStreamReader(in));
            String line ="";
            while((line = reader.readLine())!=null)
            {
                content.append(line);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(reader!=null)
                try{
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            return content.toString();
        }

    }

    public void showWaiterAuthorizationDialog() {

        // LayoutInflater是用来找layout文件夹下的xml布局文件，并且实例化
        LayoutInflater factory = LayoutInflater.from(MainActivity.this);
        // 把activity_login中的控件定义在View中
        final View textEntryView = factory.inflate(R.layout.login_activity,
                null);

        // 将LoginActivity中的控件显示在对话框中
        new AlertDialog.Builder(MainActivity.this)
                // 对话框的标题
                .setTitle("登陆")
                // 设定显示的View
                .setView(textEntryView)
                // 对话框中的“登陆”按钮的点击事件
                .setPositiveButton("登陆", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        // 获取用户输入的“用户名”，“密码”
                        // 注意：textEntryView.findViewById很重要，因为上面factory.inflate(R.layout.activity_login,
                        // null)将页面布局赋值给了textEntryView了
                        final EditText etUserName = (EditText) textEntryView
                                .findViewById(R.id.UserId);
                        final EditText etPassword = (EditText) textEntryView
                                .findViewById(R.id.Password);

                        // 将页面输入框中获得的“用户名”，“密码”转为字符串
                        String userName = etUserName.getText().toString()
                                .trim();
                        String password = etPassword.getText().toString()
                                .trim();

                        // 现在为止已经获得了字符型的用户名和密码了，接下来就是根据自己的需求来编写代码了
                        // 这里做一个简单的测试，假定输入的用户名和密码都是1则进入其他操作页面（OperationActivity）
                        if (userName.equals("abc") && password.equals("123")) {
                            // 跳转到OperationActivity
                            Intent intent = new Intent();
                            intent.setClass(MainActivity.this,
                                    SuccessActivity.class);
                            startActivity(intent);
                            // 关闭当前页面
                            MainActivity.this.finish();

                        } else {
                            Toast.makeText(MainActivity.this, "密码或用户名错误",
                                    Toast.LENGTH_SHORT).show();

                            try {
                                // 注意此处是通过反射，修改源代码类中的字段mShowing为true，系统会认为对话框打开
                                // 从而调用dismiss()
                                Field field = dialog.getClass().getSuperclass()
                                        .getDeclaredField("mShowing");
                                field.setAccessible(true);
                                field.set(dialog, false);
                                dialog.dismiss();

                            } catch (Exception e) {

                            }
                        }
                    }
                })
                // 对话框的“退出”单击事件
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                })
                // 设置dialog是否为模态，false表示模态，true表示非模态
                .setCancelable(false)
                // 对话框的创建、显示
                .create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch(requestCode)
        {
            case 1:
                if(resultCode == RESULT_OK)
                {
                    String returnData = data.getStringExtra("data_return");
                    Toast.makeText(MainActivity.this, returnData,
                            Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(MainActivity.this, "error",
                            Toast.LENGTH_SHORT).show();
                break;
                default:
        }
    }

    @Override
    public void onClick(View v) {
        //添加逻辑
        LayoutInflater factory = LayoutInflater.from(MainActivity.this);
        final View DialogView = factory.inflate(R.layout.login_activity, null);


        switch (v.getId()) {
            case R.id.prompt:
                AlertDialog.Builder dialog_prompt = new AlertDialog.Builder(MainActivity.this);

                dialog_prompt.setTitle("这是提示标题");
                dialog_prompt.setMessage("这是提示内容");
                dialog_prompt.setCancelable(false);
                dialog_prompt.setPositiveButton("确定", new DialogInterface.
                        OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                dialog_prompt.setNegativeButton("取消", new DialogInterface.
                        OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                dialog_prompt.show();
                break;

            case R.id.login:
                showWaiterAuthorizationDialog();
                break;

            case R.id.input:

                String  data = "传递成功！";
                Intent input_intent = new Intent(MainActivity.this,IntentActivity.class);
                input_intent.putExtra("extra_data",data);
                startActivity(input_intent);

                break;
            case R.id.receive:
                Intent receive_intent = new Intent(MainActivity.this,IntentActivity.class);
                startActivityForResult(receive_intent,1);
                break;

            case R.id.yin_intent:
                Intent intent = new Intent("com.example.Login_Test.ACTION_START");
                intent.addCategory("com.example.Login_Test.abc");
                startActivity(intent);
                break;
            case R.id.save:
            String inputText = edit.getText().toString();
            save(inputText);
            break;
            case R.id.load:

            String output = load();
            if(!TextUtils.isEmpty(output)){
                edit.setText(output);
                edit.setSelection(output.length());
                Toast.makeText(this,output,Toast.LENGTH_SHORT).show();
            }
            break;
            case R.id.handler:
                Intent handler_intent = new Intent(MainActivity.this, HandlerActivity.class);
                startActivity(handler_intent);
            default:
                break;
        }
    }
}
