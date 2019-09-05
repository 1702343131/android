package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import com.example.myapplication.utils.MD5Utils;

public class Register extends AppCompatActivity {
    //1.获取界面上的控件
    //2.button的点击事件的监听
    //3.处理点击事件
    // 3.1获取控件的值
    //3.2检查数据的有效性
    //3.3将注册信息存储
    private Toolbar toolbar;
    private EditText etUsername, etPaaword, etPwdAgain;
    private Button btnRegister;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initToolbar();//初始化toolbar
         initView();//初始化界面控件
         initData();//初始化传入的数据

        //1. 获取界面上的控件
        initView();
        //2. button的点击事件的监听
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //3. 处理点击事件
                //3.1 获取控件的值
                String username = etUsername.getText().toString();
                String password = etPaaword.getText().toString();
                String passwordAgain = etPwdAgain.getText().toString();
                //3.2 检查控件的有效性
                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(Register.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(password) || TextUtils.isEmpty(passwordAgain)) {
                    Toast.makeText(Register.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(passwordAgain)) {
                    Toast.makeText(Register.this, "两次密码必须一致", Toast.LENGTH_SHORT).show();
                } else {
                    //3.3 将注册信息存储
                    savePref(username, MD5Utils.md5(password));
                    //3.4 跳转登录页面
                    // 给bnt1添加点击响应事件
                    Intent intent = new Intent(Register.this, MainActivity.class);
                    intent.putExtra("username", username);
                    //启动
                    startActivity(intent);
                }
            }
        });
    }

    private void initData() {
        String username = readPres();
        if (!TextUtils.isEmpty(username)){
            etUsername.setText(username);
        }
    }

    private String readPres() {
        SharedPreferences sp = getSharedPreferences("userInfo",MODE_PRIVATE);
        return sp.getString("username","");
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.title_toolbar);
        toolbar.setTitle("注册");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
                   actionBar.setDisplayHomeAsUpEnabled(true);//设置返回键
            //actionBar.setHomeButtonEnabled(true);//设置是否为首页

        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void  onClick(View v){
                Register.this.finish();
            }
        });
    }


    private void savePref(String username, String password) {
        SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
        editor.putString("username", username);
        editor.putString("password", password);
        editor.apply();
    }

    private void initView() {
        etUsername = findViewById(R.id.name);
        etPaaword = findViewById(R.id.password);
        etPwdAgain = findViewById(R.id.passwordAgain);
        btnRegister = findViewById(R.id.register);

        TextView tvRegister = findViewById(R.id.register);
        tvRegister.setOnClickListener(new  View.OnClickListener(){
            @Override
            public  void  onClick(View v){
                 Intent intent = new Intent( Register.this,MainActivity.class);
                 startActivity(intent);

            }
        });
    }
        }
