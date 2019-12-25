package com.example.login_test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class IntentActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent);
        Intent intent = getIntent();
        String data = intent.getStringExtra("extra_data");
        if(data != null && data.length() != 0)
        {Toast.makeText(IntentActivity.this, data,
                Toast.LENGTH_SHORT).show();}


        Button rtn = findViewById(R.id.rtn);
        rtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("data_return","返回数据成功！");
                setResult(RESULT_OK,intent);
                finish();
            }
        });



    }
}
