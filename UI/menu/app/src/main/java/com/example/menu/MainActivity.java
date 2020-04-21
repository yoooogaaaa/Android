package com.example.menu;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView tv_test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_test = findViewById(R.id.tv_test);
        registerForContextMenu(tv_test);}
        public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle item selection
            switch (item.getItemId()) {
                case R.id.menu1_1:
                    tv_test.setTextSize(10);
                    return true;
                case R.id.menu1_2:
                    tv_test.setTextSize(16);
                    return true;
                case R.id.menu1_3:
                    tv_test.setTextSize(20);
                    return true;
                case R.id.menu2:
                    Toast toast = Toast.makeText(MainActivity.this,"点击了普通菜单项",Toast.LENGTH_SHORT);
                    toast.show();
                    return true;
                case R.id.menu3_1:
                    tv_test.setTextColor(Color.BLACK);
                    return true;
                case R.id.menu3_2:
                    tv_test.setTextColor(Color.RED);
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
    }
}
