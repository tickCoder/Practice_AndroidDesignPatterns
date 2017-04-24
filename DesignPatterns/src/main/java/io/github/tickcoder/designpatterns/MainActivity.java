package io.github.tickcoder.designpatterns;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import io.github.tickcoder.designpatterns.chapter01.Chapter01Activity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnGo2Chapter01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtnGo2Chapter01 = (Button)findViewById(R.id.btn_go2_chapter01);
        mBtnGo2Chapter01.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_go2_chapter01: {
                startActivity(new Intent(MainActivity.this, Chapter01Activity.class));
                break;
            }
            default:break;
        }
    }
}
