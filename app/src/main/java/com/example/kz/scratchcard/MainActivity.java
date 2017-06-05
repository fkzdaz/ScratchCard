package com.example.kz.scratchcard;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.kz.scratchcard.view.Text_Rubbler;

public class MainActivity extends AppCompatActivity {
    private Text_Rubbler rubbler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
//        setContentView(new Rubble(this,"谢谢惠顾",new Rect(100, 200,
//                 300,250),2,1f,14));
        setContentView(R.layout.activity_main);
        rubbler = (Text_Rubbler) findViewById(R.id.rubbler);
        // 设置的颜色必须要有透明度。
        rubbler.beginRubbler(0xFFFF0000, 30, 1f);// 设置橡皮擦的宽度等
    }
}
