package com.github.kevin.inject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.kevin.inject.base.BaseActivity;
import com.github.kevin.library.annocations.ContentView;
import com.github.kevin.library.annocations.InjectView;
import com.github.kevin.library.annocations.OnClick;
import com.github.kevin.library.annocations.OnLongClick;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {
    @InjectView(R.id.tv)
    TextView tv;
    @InjectView(R.id.btn)
    protected Button btn;//不能设置私有权限，否则不能通过反射赋值

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tv.setText("欢迎来到首页！");
    }

    @OnClick({R.id.btn, R.id.tv})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.btn:
                Toast.makeText(this, "This Button is clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv:
                Toast.makeText(this, "This TextView is clicked", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @OnLongClick({R.id.btn, R.id.tv})
    public boolean longClick(View view) {
        switch (view.getId()) {
            case R.id.btn:
                Toast.makeText(this, "This Button is longclicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv:
                Toast.makeText(this, "This TextView is longclicked", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

}
