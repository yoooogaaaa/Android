package com.example.alertdialog;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CustomDialog customDialog = new CustomDialog(MainActivity.this);
        customDialog.setCancel("取消", new CustomDialog.IOnCancelListener() {
            @Override
            public void onCancel(CustomDialog dialog) {
                Toast.makeText(MainActivity.this, "已取消！",Toast.LENGTH_SHORT).show();
            }
        });
        customDialog.setConfirm("登录", new CustomDialog.IOnConfirmListener(){
            @Override
            public void onConfirm(CustomDialog dialog) {
                Toast.makeText(MainActivity.this, "已登录！",Toast.LENGTH_SHORT).show();
            }
        });
        customDialog.show();

    }
}
