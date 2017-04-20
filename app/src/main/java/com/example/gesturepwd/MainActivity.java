package com.example.gesturepwd;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 *
 * @author lzh
 * 2017-04-20
 *
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init(){
        btnClick = (Button) findViewById(R.id.btnClick);
        btnClick.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        intent.setClass(this,GestureMainActivity.class);
        startActivity(intent);
    }
}
