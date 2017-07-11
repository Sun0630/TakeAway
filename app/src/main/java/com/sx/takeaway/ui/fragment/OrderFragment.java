package com.sx.takeaway.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sx.takeaway.R;
import com.sx.takeaway.dagger2.component.fragment.DaggerOrderFragmentComponent;
import com.sx.takeaway.dagger2.module.PresenterModule;
import com.sx.takeaway.model.net.bean.Order;
import com.sx.takeaway.presenter.activity.OrderPresenter;
import com.sx.takeaway.ui.IView;
import com.sx.takeaway.ui.adapter.OrderRecyclerViewAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author sunxin
 * @Date 2017/5/22 16:58
 * @Description 订单列表展示
 */

public class OrderFragment extends BaseFragment implements IView{

    @BindView(R.id.rv_order_list)
    RecyclerView mRvOrderList;

    OrderRecyclerViewAdapter adapter;

    @Inject
    OrderPresenter mOrderPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, null);
        ButterKnife.bind(this, view);
        DaggerOrderFragmentComponent
                .builder()
                .presenterModule(new PresenterModule(this))
                .build()
                .in(this);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new OrderRecyclerViewAdapter();
        mRvOrderList.setAdapter(adapter);
        mRvOrderList.setLayoutManager(new LinearLayoutManager(this.getContext(),LinearLayoutManager.VERTICAL,false));

    }

    @Override
    public void onResume() {
        super.onResume();
        //获取数据
        mOrderPresenter.getData();
    }

    @Override
    public void success(Object o) {
        if (o instanceof List){
            List<Order> orders = (List<Order>) o;
            adapter.setOrders(orders);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void failed(String msg) {

    }
}
