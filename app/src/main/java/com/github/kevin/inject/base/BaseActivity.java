package com.github.kevin.inject.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.kevin.library.InjectManager;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //帮助子类进行布局、控件、事件的注入
        InjectManager.inject(this);
    }
}
