package com.sx.takeaway.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sx.takeaway.R;
import com.sx.takeaway.ui.activity.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author sunxin
 * @Date 2017/5/22 16:58
 * @Description 用户信息展示页面
 * 1，判断：用户是否已经登录，需要一个变量存储，通常存储用户的唯一标示，其他信息存储在本地数据库即可
 * 2，没有登录，走登录入口
 * 3，已经登录，查询用户名和手机号码显示在页面
 */

public class UserFragment extends BaseFragment {

    @BindView(R.id.tv_user_setting)
    ImageView mTvUserSetting;
    @BindView(R.id.iv_user_notice)
    ImageView mIvUserNotice;
    @BindView(R.id.login)
    ImageView mLogin;
    @BindView(R.id.username)
    TextView mUsername;
    @BindView(R.id.phone)
    TextView mPhone;
    @BindView(R.id.ll_userinfo)
    LinearLayout mLlUserinfo;
    @BindView(R.id.address)
    ImageView mAddress;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @OnClick({R.id.tv_user_setting, R.id.login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_user_setting:
                break;
            case R.id.login:
                //跳转到登录页面
                startActivity(new Intent(this.getContext(), LoginActivity.class));
                break;
        }
    }
}
