package com.sx.takeaway.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.sx.takeaway.R;
import com.sx.takeaway.ui.fragment.CommentFragment;
import com.sx.takeaway.ui.fragment.GoodsFragment;
import com.sx.takeaway.ui.fragment.SellerFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.sx.takeaway.R.id.toolbar;

/**
 * @Author sunxin
 * @Date 2017/5/30 21:19
 * @Description 商家详情页面
 */

public class SellerDetailActivity extends BaseActivity {
    @BindView(toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tabs)
    TabLayout mTabs;
    @BindView(R.id.vp)
    ViewPager mVp;

    //TabLayout 显示的文字
    private String[] titles = {"商品","评价","商家"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_detail);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        long id = intent.getLongExtra("seller_id", -1);
        //拿到name
        String name = intent.getStringExtra("name");
        mToolbar.setTitle(name);
        //替换ActionBar
        setSupportActionBar(mToolbar);
        //设置返回按钮
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //设置TabLayout和ViewPager
        MyAdapter adapter = new MyAdapter(getSupportFragmentManager());
        mVp.setAdapter(adapter);
        //绑定TabLayout与ViewPager
        mTabs.setupWithViewPager(mVp);

    }

    private class MyAdapter extends FragmentPagerAdapter{

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position){
                case 0:
                    fragment = new GoodsFragment();
                    break;
                case 1:
                    fragment = new CommentFragment();
                    break;
                case 2:
                    fragment = new SellerFragment();
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
