package com.sx.mvp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sx.mvp.dagger2.component.DaggerMainActivityComponent;
import com.sx.mvp.dagger2.module.MainActivityModule;
import com.sx.mvp.presenter.MainActivityPresenter;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText username;
    private EditText password;
    private Button login;
    private ProgressDialog mDialog;
    @Inject
    MainActivityPresenter mPresenter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        //使用Dagger2解耦
//        mPresenter = new MainActivityPresenter(this);
        DaggerMainActivityComponent component = (DaggerMainActivityComponent) DaggerMainActivityComponent
                .builder()
                .mainActivityModule(new MainActivityModule(this))
                .build();
        component.in(this);
    }

    private void initView() {
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        mDialog = new ProgressDialog(this);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                login();
                break;
        }
    }

    private void login() {
        // validate
        String usernameString = username.getText().toString().trim();
        String passwordString = password.getText().toString().trim();

        boolean checkUserInfo = checkUserInfo(usernameString, passwordString);

        if (checkUserInfo) {
            //不为空
            mDialog.show();
            //登录
            mPresenter.login(usernameString,passwordString);
        } else {
            //为空
            Toast.makeText(this, "用户名和密码不能为空", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 检验用户输入
     *
     * @param username
     * @param password
     * @return
     */
    public boolean checkUserInfo(String username, String password) {
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            return false;
        }
        return true;
    }


    /**
     * 登录成功
     */
    public void success() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mDialog.dismiss();
                Toast.makeText(MainActivity.this, "登录成功，欢迎回来"+username.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 登录失败
     */
    public void failed() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mDialog.dismiss();
                Toast.makeText(MainActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
